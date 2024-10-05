package presentation.properties

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entity.ActuatorState
import domain.entity.TemperatureActuatorState

/**
 * UI for update data
 */
@Composable
internal fun PropertiesFragment(
    screenState: MutableState<PropertiesScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Title
        Text(
            text = "Data settings",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(32.dp))

        // Slider for changing outside temperature
        SlideBar(
            title = "Outside temperature",
            value = screenState.value.outsideTemp,
            minTemperature = -50f
        ) {
            screenAction(ScreenAction.ChangeOutsideTemperature(it))
        }

        Spacer(Modifier.height(24.dp))

        // Slider for changing inside temperature
        SlideBar(
            title = "Inside temperature",
            value = screenState.value.insideTemp,
            minTemperature = -10f
        ) {
            screenAction(ScreenAction.ChangeInsideTemperature(it))
        }

        // Slider for changing delay
        DelaySlider(screenState, screenAction)

        // Slider for changing actuator power
        ActuatorPower(screenState, screenAction)

        // Slider for changing actuator state
        ActuatorSwitch(screenState, screenAction)

        if (screenState.value.actuatorState != ActuatorState.Auto) {
            // Custom switch for changing temperature actuator state
            Switch(screenState, screenAction)
        }
    }
}

@Composable
private fun ActuatorPower(
    screenState: MutableState<PropertiesScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    Spacer(Modifier.height(16.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = String.format("Actuator power: ", screenState.value.actuatorPower),
            fontSize = 22.sp
        )

        Slider(
            value = screenState.value.actuatorPower,
            onValueChange = {
                screenAction(ScreenAction.ChangeActuatorPower(it))
            },
            valueRange = .1f..1f,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun DelaySlider(
    screenState: MutableState<PropertiesScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    Spacer(Modifier.height(16.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = String.format("Delay: "),
            fontSize = 22.sp
        )

        Slider(
            value = screenState.value.delay.toFloat(),
            onValueChange = {
                screenAction(ScreenAction.ChangeDelay(it.toLong()))
            },
            steps = 100,
            valueRange = 100f..10000f,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun ActuatorSwitch(
    screenState: MutableState<PropertiesScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    Spacer(Modifier.height(16.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Subtitle
        Text(
            text = "Actuator:",
            fontSize = 22.sp
        )

        Spacer(Modifier.width(16.dp))

        // Description start
        Text(
            text = "handle",
            fontSize = 18.sp
        )

        Spacer(Modifier.width(6.dp))

        // On/off handle state on actuator
        Switch(
            checked = screenState.value.actuatorState == ActuatorState.Auto,
            onCheckedChange = {
                val state = if (it) ActuatorState.Auto else ActuatorState.Handle
                screenAction(ScreenAction.ChangeActuatorState(state))
            }
        )

        Spacer(Modifier.width(6.dp))

        // Description end
        Text(
            text = "auto",
            fontSize = 18.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SlideBar(
    title: String,
    value: Float,
    minTemperature: Float,
    onValueChanged: (Float) -> Unit
) {
    // Track color
    val trackColor = if (value <= 17) {
        Color(77, 225, 255)
    } else if (value <= 25) {
        Color.Green
    } else {
        Color.Red
    }

    // Subtitle
    Text(
        text = title,
        fontSize = 22.sp
    )

    // Slider
    Slider(
        value = value,
        onValueChange = onValueChanged,
        valueRange = minTemperature..40f,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        thumb = {
            // Thumb emoji
                val icon = if (value <= 17) {
                "❄\uFE0F"
            } else if (value <= 25) {
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
    screenState: MutableState<PropertiesScreenState>,
    screenAction: (ScreenAction) -> Unit
) {
    Spacer(Modifier.height(16.dp))

    val directionRight = remember { mutableStateOf<Boolean>(false) }

    // Animation for changing state
    val transition = updateTransition(targetState = screenState.value.temperatureActuatorState, label = "switchTransition")
    val offset = transition.animateDp(label = "offsetAnimation") { state ->
        when (state) {
            TemperatureActuatorState.Cold -> 10.dp
            TemperatureActuatorState.None -> 40.dp
            else -> 70.dp
        }
    }

    // Subtitle
    Text(
        text = "Actuator run (cold/none/hot)",
        fontSize = 22.sp
    )

    Spacer(Modifier.height(16.dp))

    // Custom switch
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(40.dp)
            .background(Color.Gray, shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                // Error if now is not handle state
                if (screenState.value.actuatorState != ActuatorState.Handle) {
                    return@clickable
                }

                // Change direction and get state
                val state = when (screenState.value.temperatureActuatorState) {
                    TemperatureActuatorState.Hot -> {
                        if (directionRight.value) {
                            directionRight.value = false
                        }
                        TemperatureActuatorState.None
                    }

                    TemperatureActuatorState.Cold -> {
                        if (!directionRight.value) {
                            directionRight.value = true
                        }

                        TemperatureActuatorState.None
                    }

                    else -> if (!directionRight.value) TemperatureActuatorState.Cold else TemperatureActuatorState.Hot
                }

                // Change state
                screenAction.invoke(ScreenAction.ChangeTemperatureActuatorState(state))
            },
        contentAlignment = Alignment.CenterStart
    ) {
        // Thumb color
        val color = when (screenState.value.temperatureActuatorState) {
            TemperatureActuatorState.Hot -> Color.Red
            TemperatureActuatorState.Cold -> Color(77, 225, 255)
            else -> Color.White
        }

        // Thumb
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