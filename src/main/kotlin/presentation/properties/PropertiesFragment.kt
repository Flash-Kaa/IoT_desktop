package presentation.properties

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entity.ActuatorState
import presentation.ScreenAction
import presentation.ScreenState

@Composable
internal fun PropertiesFragment(
    screenState: MutableState<ScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SlideBar(
            title = "Outside temperature",
            value = screenState.value.outsideTemp
        ) {
            screenAction(ScreenAction.ChangeOutsideTemperature(it))
        }

        Spacer(Modifier.height(32.dp))

        SlideBar(
            title = "Inside temperature",
            value = screenState.value.insideTemp
        ) {
            screenAction(ScreenAction.ChangeInsideTemperature(it))
        }

        Spacer(Modifier.height(48.dp))

        Text(
            text = "Actuator on/off",
            fontSize = 18.sp
        )

        Switch(
            checked = screenState.value.actuatorState == ActuatorState.Auto,
            onCheckedChange = {
                val state = if (it) ActuatorState.Auto else ActuatorState.Off

                screenAction(ScreenAction.ChangeActuatorState(state))
            }
        )

        if (screenState.value.actuatorState != ActuatorState.Auto) {
            Spacer(Modifier.height(14.dp))
            Switch(screenState, screenAction)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SlideBar(
    title: String,
    value: Float,
    onValueChanged: (Float) -> Unit
) {
    val trackColor = if (value <= 5) {
        Color(77, 225, 255)
    } else if (value <= 24) {
        Color.Green
    } else {
        Color.Red
    }

    Text(
        text = title,
        fontSize = 24.sp
    )

    Slider(
        value = value,
        onValueChange = onValueChanged,
        valueRange = -10f..40f,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        thumb = {
            val icon = if (value <= 5) {
                "❄\uFE0F"
            } else if (value <= 24) {
                "\uD83D\uDFE2"
            } else {
                "\uD83D\uDD25"
            }

            Text(
                text = icon,
                fontSize = 32.sp
            )
        },
        colors = SliderDefaults.colors(
            activeTrackColor = trackColor
        )
    )
}

@Composable
private fun Switch(
    screenState: MutableState<ScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    val directionRight = remember { mutableStateOf<Boolean>(false) }

    val transition = updateTransition(targetState = screenState.value.actuatorState, label = "switchTransition")
    val offset = transition.animateDp(label = "offsetAnimation") { state ->
        when (state) {
            ActuatorState.Down -> 10.dp
            ActuatorState.Off -> 40.dp
            else -> 70.dp
        }
    }

    Text(
        text = "Temperature actuator state: ${screenState.value.actuatorState.name}",
        fontSize = 18.sp
    )

    Spacer(Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .width(130.dp)
            .height(40.dp)
            .background(Color.Gray, shape = CircleShape)
            .clickable {
                val state = when (screenState.value.actuatorState) {
                    ActuatorState.Up -> {
                        if (directionRight.value) {
                            directionRight.value = false
                        }
                        ActuatorState.Off
                    }

                    ActuatorState.Down -> {
                        if (!directionRight.value) {
                            directionRight.value = true
                        }

                        ActuatorState.Off
                    }

                    else -> if (!directionRight.value) ActuatorState.Down else ActuatorState.Up
                }

                screenAction.invoke(ScreenAction.ChangeActuatorState(state))
            },
        contentAlignment = Alignment.CenterStart
    ) {
        val color = when (screenState.value.actuatorState) {
            ActuatorState.Up -> Color.Red
            ActuatorState.Down -> Color(77, 225, 255)
            else -> Color.White
        }

        Box(
            modifier = Modifier
                .offset(x = offset.value)
                .height(30.dp)
                .width(50.dp)
                .clip(RoundedCornerShape(60.dp))
                .background(color)
        )
    }
}