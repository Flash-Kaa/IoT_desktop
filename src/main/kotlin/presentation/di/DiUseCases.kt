package presentation.di

import data.di.DiData
import domain.usecases.UpdateDelayUseCase
import domain.usecases.GetDelayUseCase
import domain.usecases.GetPowerUseCase
import domain.usecases.GetTemperatureUseCase
import domain.usecases.UpdatePowerUseCase
import domain.usecases.UpdateTemperatureUseCase
import presentation.detectors.DetectorsInterceptor
import presentation.properties.PropertiesInterceptor

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
        UpdateTemperatureUseCase(DiData.insideSensor)
    }

    private val updateOutsideTemperatureUseCase: UpdateTemperatureUseCase by lazy {
        UpdateTemperatureUseCase(DiData.outsideTemperature)
    }

    private val getPowerUseCase: GetPowerUseCase by lazy {
        GetPowerUseCase(DiData.actuator)
    }

    private val updatePowerUseCase: UpdatePowerUseCase by lazy {
        UpdatePowerUseCase(DiData.actuator)
    }

    val propertiesInterceptor: PropertiesInterceptor by lazy {
        PropertiesInterceptor(
            getPowerUseCase = getPowerUseCase,
            getDelayUseCase = getDelayUseCase,
            getInsideTemperatureUseCase = getInsideTemperatureUseCase,
            getOutsideTemperatureUseCase = getOutsideTemperatureUseCase,
            updateInsideTemperatureUseCase = updateInsideTemperatureUseCase,
            updateOutsideTemperatureUseCase = updateOutsideTemperatureUseCase,
            updateDelayUseCase = updateDelayUseCase,
            updatePowerUseCase = updatePowerUseCase,
        )
    }

    val detectorsInterceptor: DetectorsInterceptor by lazy {
        DetectorsInterceptor(
            getInsideTemperatureUseCase = getInsideTemperatureUseCase,
            getOutsideTemperatureUseCase = getOutsideTemperatureUseCase,
            getDelayUseCase = getDelayUseCase,
            getPowerUseCase = getPowerUseCase
        )
    }
}