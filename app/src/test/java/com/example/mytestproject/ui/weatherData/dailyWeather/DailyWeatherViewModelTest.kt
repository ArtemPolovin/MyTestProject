package com.example.mytestproject.ui.weatherData.dailyWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.utils.CityDataCache
import com.example.domain.models.WeatherData
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
internal class DailyWeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchDailyWeatherUseCase: FetchDailyWeatherUseCase

    @Mock
    private lateinit var cityDataCache: CityDataCache

    @Mock
    private lateinit var observerViewState: Observer<WeatherViewState>

    private lateinit var dailyWeatherViewModel: DailyWeatherViewModel

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_api_fetch_weather_data_success() {
        try {
            // Given
            val numberOfDays = 5
            val cityId = 333
            `when`(cityDataCache.loadCityId()).thenReturn(cityId)

            val listOfApeWeatherData = listOf(
                WeatherData("Moscow", "73", "iconURL", "2020-10-18", "Clear sky"),
                WeatherData("Moscow", "82", "iconURL", "2020-10-19", "Clear sky"),
                WeatherData("Moscow", "80", "iconURL", "2020-10-20", "Clear sky"),
                WeatherData("Moscow", "89", "iconURL", "2020-10-21", "Clear sky"),
                WeatherData("Moscow", "85", "iconURL", "2020-10-22", "Clear sky")
            )

            `when`(cityDataCache.loadCityId()).thenReturn(cityId)

            `when`(fetchDailyWeatherUseCase.invoke(cityId, numberOfDays, "I")).thenReturn(
                Single.just(listOfApeWeatherData)
            )

            dailyWeatherViewModel = DailyWeatherViewModel(fetchDailyWeatherUseCase, cityDataCache)
            dailyWeatherViewModel.weatherDataViewState.observeForever(observerViewState)

            // When
            dailyWeatherViewModel.daysForForecastWeather(numberOfDays)

            //Then
            val inOrder = Mockito.inOrder(observerViewState)
            inOrder.verify(observerViewState).onChanged(WeatherViewState.Loading)
            inOrder.verify(observerViewState)
                .onChanged(WeatherViewState.DailyWeatherLoaded(listOfApeWeatherData))

        } finally {
            dailyWeatherViewModel.weatherDataViewState.removeObserver(observerViewState)
        }

    }

    @Test
    fun test_api_fetch_weather_data_error() {
        try {
            // Given
            val numberOfDays = 3
            val cityId = 555
            `when`(cityDataCache.loadCityId()).thenReturn(cityId)


            `when`(
                fetchDailyWeatherUseCase.invoke(
                    cityId,
                    numberOfDays,
                    "I"
                )
            ).thenReturn(Single.error(Throwable("Api error")))

            dailyWeatherViewModel = DailyWeatherViewModel(fetchDailyWeatherUseCase, cityDataCache)
            dailyWeatherViewModel.weatherDataViewState.observeForever(observerViewState)

            // When
            dailyWeatherViewModel.daysForForecastWeather(numberOfDays)

            // Then
            val inOrder = Mockito.inOrder(observerViewState)
            inOrder.verify(observerViewState).onChanged(WeatherViewState.Loading)
            inOrder.verify(observerViewState).onChanged(WeatherViewState.Error)
        } finally {
            dailyWeatherViewModel.weatherDataViewState.removeObserver(observerViewState)
        }

    }
}