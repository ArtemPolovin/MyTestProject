package com.example.mytestproject.ui.weatherData.dailyWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.utils.CityDataCache
import com.example.data.utils.FAHRENHEIT_TYPE
import com.example.data.utils.IMPERIAL
import com.example.data.utils.SettingsCache
import com.example.domain.models.WeatherData
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
internal class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchDailyWeatherUseCase: FetchDailyWeatherUseCase

    @Mock
    private lateinit var cityDataCache: CityDataCache

    @Mock
    private lateinit var settingsCache: SettingsCache

    private lateinit var dailyWeatherViewModel: DailyWeatherViewModel

    private val numberOfDays = 5
    private val cityId = 333
    private val unitSystem = IMPERIAL

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }

        MockitoAnnotations.initMocks(this)

        `when`(settingsCache.getUnitSystem()).thenReturn(unitSystem)

        `when`(cityDataCache.loadCityId()).thenReturn(cityId)


    }

    @Test
    fun `test api fetch weather data success`() {

        // Given
        val listOfApeWeatherData = listOf(
            WeatherData("Moscow", "73", "iconURL", "2020-10-18", "Clear sky", FAHRENHEIT_TYPE),
            WeatherData("Moscow", "82", "iconURL", "2020-10-19", "Clear sky", FAHRENHEIT_TYPE),
            WeatherData("Moscow", "80", "iconURL", "2020-10-20", "Clear sky", FAHRENHEIT_TYPE),
            WeatherData("Moscow", "89", "iconURL", "2020-10-21", "Clear sky", FAHRENHEIT_TYPE),
            WeatherData("Moscow", "85", "iconURL", "2020-10-22", "Clear sky", FAHRENHEIT_TYPE)
        )

        `when`(fetchDailyWeatherUseCase.invoke(cityId, numberOfDays, unitSystem)).thenReturn(
            Single.just(listOfApeWeatherData)
        )

        dailyWeatherViewModel =
            DailyWeatherViewModel(fetchDailyWeatherUseCase, cityDataCache, settingsCache)

        val list = mutableListOf<WeatherViewState>()
        val observer = Observer<WeatherViewState> {
            list.add(it)
        }

        try {

            dailyWeatherViewModel.weatherDataViewState.observeForever(observer)

            // When
            dailyWeatherViewModel.daysForForecastWeather(numberOfDays)

            //Then
            assertEquals(list[0], WeatherViewState.Loading)
            assertEquals(list[1], WeatherViewState.DailyWeatherLoaded(listOfApeWeatherData))
        } finally {
            dailyWeatherViewModel.weatherDataViewState.removeObserver(observer)
        }

    }

    @Test
    fun `test api fetch weather data error`() {

        // Given
        `when`(
            fetchDailyWeatherUseCase.invoke(
                cityId,
                numberOfDays,
                unitSystem
            )
        ).thenReturn(Single.error(Throwable("Api error")))

        dailyWeatherViewModel =
            DailyWeatherViewModel(fetchDailyWeatherUseCase, cityDataCache, settingsCache)

        val list = mutableListOf<WeatherViewState>()
        val observer = Observer<WeatherViewState> {
            list.add(it)
        }

        try {

            dailyWeatherViewModel.weatherDataViewState.observeForever(observer)

            // When
            dailyWeatherViewModel.daysForForecastWeather(numberOfDays)

            // Then
            assertEquals(list[0], WeatherViewState.Loading)
            assertEquals(list[1], WeatherViewState.Error)
        } finally {
            dailyWeatherViewModel.weatherDataViewState.removeObserver(observer)
        }

    }
}