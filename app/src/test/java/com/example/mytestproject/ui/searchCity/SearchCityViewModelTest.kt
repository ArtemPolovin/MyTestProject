package com.example.mytestproject.ui.searchCity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.utils.CityDataCache
import com.example.domain.models.CityModel
import com.example.domain.useCase.cities.GetLastChosenCitiesUseCase
import com.example.domain.useCase.cities.InsertCityToLastChosenCitiesEntityUseCase
import com.example.mytestproject.util.CityFilter
import com.example.mytestproject.util.Event
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
internal class SearchCityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var cityFilter: CityFilter

    @Mock
    private lateinit var cityDataCache: CityDataCache

    @Mock
    private lateinit var getLastChosenCitiesUseCase: GetLastChosenCitiesUseCase

    @Mock
    private lateinit var insertCityToLastChosenCitiesEntityUseCase: InsertCityToLastChosenCitiesEntityUseCase

    @Mock
    lateinit var observerListCityModel: Observer<List<CityModel>>

    @Mock
    lateinit var observerEvent: Observer<Event<Int>>

    private lateinit var searchCityViewModel: SearchCityViewModel

    @Before
    fun setUp() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }

        MockitoAnnotations.initMocks(this)

        `when`(getLastChosenCitiesUseCase.invoke()).thenReturn(
            Single.just(
                listOf(
                    CityModel(234, "Moscow", "Russia"),
                    CityModel(345, "Milan", "Italy"),
                    CityModel(6662, "Sacrament", "USA")
                )
            )
        )

        searchCityViewModel = SearchCityViewModel(
            cityFilter,
            cityDataCache,
            getLastChosenCitiesUseCase,
            insertCityToLastChosenCitiesEntityUseCase
        )
        searchCityViewModel.filteredCityList.observeForever(observerListCityModel)
        searchCityViewModel.lastChosenCities.observeForever(observerListCityModel)
        searchCityViewModel.navigateToCurrentWeather.observeForever(observerEvent)
    }

    @Test
    fun get_last_chosen_cities_is_not_empty() {
        try {
            // When
            val privateGetLastChosenCitiesMethod =
                SearchCityViewModel::class.java.getDeclaredMethod("getLastChosenCities")
            privateGetLastChosenCitiesMethod.isAccessible = true

            val value = searchCityViewModel.lastChosenCities.value
            val isEmpty = value?.isEmpty()

            // Then
            isEmpty?.let { assertFalse(it) }
            assertEquals(3, value?.size)
        } finally {
            searchCityViewModel.lastChosenCities.removeObserver(observerListCityModel)
        }
    }


    @Test
    fun search_city_is_not_empty() {
        // Given
        val cityName = "Moscow"
        `when`(cityFilter.filterCityList(cityName)).thenReturn(
            listOf(
                CityModel(3435, cityName, "Russia")
            )
        )

        try {
            // When
            searchCityViewModel.searchCity(cityName)

            val value = searchCityViewModel.filteredCityList.value
            val isEmpty = value?.isEmpty()

            // Then
            isEmpty?.let { assertFalse(it) }
            assertEquals(1, value?.size)
        } finally {
            searchCityViewModel.filteredCityList.removeObserver(observerListCityModel)
        }

    }

    @Test
    fun on_city_chose_check_navigateToCurrentWeather_value() {

        // Given
        val cityId = 234
        val cityList = searchCityViewModel.filteredCityList.value
        `when`(insertCityToLastChosenCitiesEntityUseCase.invoke(cityId, cityList)).thenReturn(
            Completable.never()
        )

        try {
            // When
            searchCityViewModel.onCityChosen(cityId)
            val value = searchCityViewModel.navigateToCurrentWeather.value

            // Then
            assertNotNull(value)
            assertTrue(value is Event)
        } finally {
            searchCityViewModel.navigateToCurrentWeather.removeObserver(observerEvent)
            searchCityViewModel.filteredCityList.removeObserver(observerListCityModel)
        }

    }

    @Test
    fun get_chosen_city_name_by_id_not_null() {

        // Given
        val cityId = 3435
        val cityName = "Kiev"
        `when`(cityFilter.filterCityList(cityName)).thenReturn(
            listOf(
                CityModel(cityId, cityName, "Ukraine")
            )
        )
        searchCityViewModel.searchCity(cityName)

        // When
        val chosenCityName = createPrivateGetChosenCityNameByIdMethod(cityId)

        // Then
        assertEquals(cityName, chosenCityName)
    }

    private fun createPrivateGetChosenCityNameByIdMethod(cityId: Int): String {
        val privateGetChosenCityNameByIdMethod =
            SearchCityViewModel::class.java.getDeclaredMethod(
                "getChosenCityNameById",
                Int::class.java
            )
        privateGetChosenCityNameByIdMethod.isAccessible = true

        return privateGetChosenCityNameByIdMethod.invoke(searchCityViewModel, cityId) as String
    }


}