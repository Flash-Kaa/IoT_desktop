package presentation.di

import data.di.DiData
import domain.usecases.GetActuatorStateUseCase
import domain.usecases.GetDelayUseCase
import domain.usecases.GetPowerUseCase
import domain.usecases.GetTemperatureActuatorStateUseCase
import domain.usecases.GetTemperatureUseCase
import domain.usecases.UpdateActuatorStateUseCase
import domain.usecases.UpdateDelayUseCase
import domain.usecases.UpdatePowerUseCase
import domain.usecases.UpdateTemperatureActuatorStateUseCase
import domain.usecases.UpdateTemperatureUseCase
import presentation.detectors.DetectorsInterceptor
import presentation.properties.PropertiesInterceptor

/**
 * DI substitute for use cases
 */
internal object DiUseCases {
    private val updateDelayUseCase: UpdateDelayUseCase by lazy {
        UpdateDelayUseCase(DiData.delayRepository)
    }

    private val getDelayUseCase: GetDelayUseCase by lazy {
        GetDelayUseCase(DiData.delayRepository)
    }

    private val getInsideTemperatureUseCase: GetTemperatureUseCase by lazy {
        GetTemperatureUseCase(DiData.insideSensor)
    }

    private val getOutsideTemperatureUseCase: GetTemperatureUseCase by lazy {
        GetTemperatureUseCase(DiData.outsideTemperature)
    }

    private val updateInsideTemperatureUseCase: UpdateTemperatureUseCase by lazy {
        UpdateTemperatureUseCase(DiData.room)
    }

    private val updateOutsideTemperatureUseCase: UpdateTemperatureUseCase by lazy {
        UpdateTemperatureUseCase(DiData.street)
    }

    private val getPowerUseCase: GetPowerUseCase by lazy {
        GetPowerUseCase(DiData.actuator)
    }

    private val getActuatorStateUseCase: GetActuatorStateUseCase by lazy {
        GetActuatorStateUseCase(DiData.actuator)
    }

    private val getTemperatureActuatorStateUseCase: GetTemperatureActuatorStateUseCase by lazy {
        GetTemperatureActuatorStateUseCase(DiData.actuator)
    }

    private val updatePowerUseCase: UpdatePowerUseCase by lazy {
        UpdatePowerUseCase(DiData.actuator)
    }
    private val updateActuatorStateUseCase: UpdateActuatorStateUseCase by lazy {
        UpdateActuatorStateUseCase(DiData.actuator)
    }
    private val updateTemperatureActuatorStateUseCase: UpdateTemperatureActuatorStateUseCase by lazy {
        UpdateTemperatureActuatorStateUseCase(DiData.actuator)
    }


    val propertiesInterceptor: PropertiesInterceptor by lazy {
        PropertiesInterceptor(
            getPowerUseCase = getPowerUseCase,
            getDelayUseCase = getDelayUseCase,
            getInsideTemperatureUseCase = getInsideTemperatureUseCase,
            getOutsideTemperatureUseCase = getOutsideTemperatureUseCase,
            getActuatorStateUseCase = getActuatorStateUseCase,
            getTemperatureActuatorStateUseCase = getTemperatureActuatorStateUseCase,
            updateInsideTemperatureUseCase = updateInsideTemperatureUseCase,
            updateOutsideTemperatureUseCase = updateOutsideTemperatureUseCase,
            updateDelayUseCase = updateDelayUseCase,
            updatePowerUseCase = updatePowerUseCase,
            updateActuatorStateUseCase = updateActuatorStateUseCase,
            updateTemperatureActuatorStateUseCase = updateTemperatureActuatorStateUseCase,
        )
    }

    val detectorsInterceptor: DetectorsInterceptor by lazy {
        DetectorsInterceptor(
            getInsideTemperatureUseCase = getInsideTemperatureUseCase,
            getOutsideTemperatureUseCase = getOutsideTemperatureUseCase,
            getDelayUseCase = getDelayUseCase,
            getPowerUseCase = getPowerUseCase,
            getTemperatureActuatorStateUseCase = getTemperatureActuatorStateUseCase
        )
    }
}