package com.example.mytestproject.ui.weatherData.todayWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.utils.CityDataCache
import com.example.domain.models.WeatherData
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
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
internal class CurrentWeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase

    @Mock
    private lateinit var cityDataCache: CityDataCache

    @Mock
    private lateinit var observerViewState: Observer<WeatherViewState>

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_api_fetch_current_weather_data_success() {
        try {
            // Given
            val cityId = 555
            val currentWeather = WeatherData(
                "Italy", "55", "iconURL",
                "2020-10-19", "Clear sky"
            )

            `when`(cityDataCache.loadCityId()).thenReturn(cityId)

            `when`(fetchCurrentWeatherUseCase.invoke(cityId, "I")).thenReturn(
                Single.just(
                    currentWeather
                )
            )

            currentWeatherViewModel =
                CurrentWeatherViewModel(fetchCurrentWeatherUseCase, cityDataCache)
            currentWeatherViewModel.weatherViewState.observeForever(observerViewState)

            // When
            currentWeatherViewModel.refreshWeatherDataList()

            // Then
           val inOrder = Mockito.inOrder(observerViewState)
            inOrder.verify(observerViewState)
                .onChanged(WeatherViewState.CurrentWeatherLoaded(currentWeather))
            inOrder.verify(observerViewState).onChanged(WeatherViewState.Loading)
        } finally {
            currentWeatherViewModel.weatherViewState.removeObserver(observerViewState)
        }

    }

    @Test
    fun test_api_fetch_current_weather_data_error() {
        try{
        // Given
        val cityId = 234524
        `when`(cityDataCache.loadCityId()).thenReturn(cityId)

        `when`(fetchCurrentWeatherUseCase.invoke(cityId, "I")).thenReturn(
            Single.error(
               Throwable("Api error")
            )
        )

        currentWeatherViewModel =
            CurrentWeatherViewModel(fetchCurrentWeatherUseCase, cityDataCache)
        currentWeatherViewModel.weatherViewState.observeForever(observerViewState)

        // When
        currentWeatherViewModel.refreshWeatherDataList()

        // Then
       val inOrder = Mockito.inOrder(observerViewState)
            inOrder.verify(observerViewState).onChanged(WeatherViewState.Loading)
            inOrder.verify(observerViewState).onChanged(WeatherViewState.Error)
        } finally {
        currentWeatherViewModel.weatherViewState.removeObserver(observerViewState)
    }

}


}