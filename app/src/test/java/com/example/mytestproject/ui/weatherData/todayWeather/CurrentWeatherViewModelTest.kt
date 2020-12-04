package com.example.mytestproject.ui.weatherData.todayWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.utils.CityDataCache
import com.example.data.utils.FAHRENHEIT_TYPE
import com.example.data.utils.IMPERIAL
import com.example.data.utils.SettingsCache
import com.example.domain.models.WeatherData
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
internal class CurrentWeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase

    @Mock
    private lateinit var cityDataCache: CityDataCache

    @Mock
    private lateinit var settingsCache: SettingsCache

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel

    private val cityId = 555
    private val unitSystem = IMPERIAL

    @Before
    fun setUp() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }

        MockitoAnnotations.initMocks(this)

        `when`(cityDataCache.loadCityId()).thenReturn(cityId)
        `when`(settingsCache.getUnitSystem()).thenReturn(unitSystem)
    }

    @Test
    fun `test api fetch current weather data success`() {

        // Given
        val currentWeather = WeatherData(
            "Italy", "55", "iconURL",
            "2020-10-19", "Clear sky",
            FAHRENHEIT_TYPE
        )

        `when`(fetchCurrentWeatherUseCase.invoke(cityId,unitSystem)).thenReturn(
            Single.just(
                currentWeather
            )
        )

        currentWeatherViewModel =
            CurrentWeatherViewModel(fetchCurrentWeatherUseCase, cityDataCache,settingsCache)

        val list = mutableListOf<WeatherViewState>()
        val observer = Observer<WeatherViewState> {
            list.add(it)
        }

        try {

            currentWeatherViewModel.weatherViewState.observeForever(observer)

            // When
            currentWeatherViewModel.refreshWeatherDataList()

            // Then
            assertEquals(list[0], WeatherViewState.CurrentWeatherLoaded(currentWeather))
            assertEquals(list[1], WeatherViewState.Loading)
            assertEquals(list[2], WeatherViewState.CurrentWeatherLoaded(currentWeather))
        } finally {
            currentWeatherViewModel.weatherViewState.removeObserver(observer)
        }

    }

    @Test
    fun `test api fetch current weather data error`() {

        // Given
        `when`(fetchCurrentWeatherUseCase.invoke(cityId, unitSystem)).thenReturn(
            Single.error(
                Throwable("Api error")
            )
        )

        currentWeatherViewModel =
            CurrentWeatherViewModel(fetchCurrentWeatherUseCase, cityDataCache,settingsCache)

        val list = mutableListOf<WeatherViewState>()
        val observer = Observer<WeatherViewState>{
            list.add(it)
        }

        try {
            currentWeatherViewModel.weatherViewState.observeForever(observer)

            // When
            currentWeatherViewModel.refreshWeatherDataList()

            // Then
            assertEquals(list[0], WeatherViewState.Error)
            assertEquals(list[1], WeatherViewState.Loading)
            assertEquals(list[2], WeatherViewState.Error)
        } finally {
            currentWeatherViewModel.weatherViewState.removeObserver(observer)
        }

    }


}