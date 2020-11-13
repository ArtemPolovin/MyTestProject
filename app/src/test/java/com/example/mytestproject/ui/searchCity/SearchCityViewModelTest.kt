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
import org.mockito.Mockito.verify
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
                    CityModel(6662, "Sacramento", "USA")
                )
            )
        )

        searchCityViewModel = SearchCityViewModel(
            cityFilter,
            cityDataCache,
            getLastChosenCitiesUseCase,
            insertCityToLastChosenCitiesEntityUseCase
        )
    }

    @Test
    fun `get last chosen cities is not empty`() {

        // Given
        val list = mutableListOf<List<CityModel>>()
        val observer = Observer<List<CityModel>> {
            list.add(it)
        }

        try {

            // When
            searchCityViewModel.lastChosenCities.observeForever(observer)

            val value = searchCityViewModel.lastChosenCities.value

            // Then
            val lastChosenCitiesList = listOf(
                CityModel(6662, "Sacramento", "USA"),
                CityModel(345, "Milan", "Italy"),
                CityModel(234, "Moscow", "Russia")
            )

            assertEquals(lastChosenCitiesList[0], value?.get(0))
            assertEquals(lastChosenCitiesList[1], value?.get(1))
            assertEquals(lastChosenCitiesList[2], value?.get(2))
        } finally {
            searchCityViewModel.lastChosenCities.removeObserver(observer)
        }
    }


    @Test
    fun `search city is not empty`() {
        // Given
        val cityName = "Moscow"
        val cityId = 234
        `when`(cityFilter.filterCityList(cityName)).thenReturn(
            listOf(
                CityModel(cityId, cityName, "Russia")
            )
        )

        val list = mutableListOf<List<CityModel>>()
        val observer = Observer<List<CityModel>> {
            list.add(it)
        }

        try {

            searchCityViewModel.lastChosenCities.observeForever(observer)

            // When
            searchCityViewModel.searchCity(cityName)

            val value = searchCityViewModel.filteredCityList.value

            // Then
            val filteredCityList = listOf(CityModel(cityId, cityName, "Russia"))

            assertEquals(filteredCityList[0], value?.get(0))

        } finally {
            searchCityViewModel.filteredCityList.removeObserver(observer)
        }

    }

    @Test
    fun `on city chose check navigateToCurrentWeather value`() {

        // Given
        val cityId = 234

        val listCityModel = mutableListOf<List<CityModel>>()
        val observerListCityModel = Observer<List<CityModel>> {
            listCityModel.add(it)
        }

        val listEvent = mutableListOf<Event<Int>>()
        val observerEvent = Observer<Event<Int>> {
            listEvent.add(it)
        }

        try {

            searchCityViewModel.navigateToCurrentWeather.observeForever(observerEvent)

            searchCityViewModel.filteredCityList.observeForever(observerListCityModel)

            val cityList = searchCityViewModel.filteredCityList.value
            `when`(insertCityToLastChosenCitiesEntityUseCase.invoke(cityId, cityList)).thenReturn(
                Completable.never()
            )

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
    fun `check passed data to cityDataCash `() {

        // Given
        val moscowCityId = 222
        val moscowCity = "Moscow"

        val filteredCityList = listOf(
            CityModel(6662, "Sacramento", "USA"),
            CityModel(345, "Milan", "Italy"),
            CityModel(moscowCityId, moscowCity, "Russia")
        )

        `when`(cityFilter.filterCityList(moscowCity)).thenReturn(filteredCityList)

        `when`(
            insertCityToLastChosenCitiesEntityUseCase.invoke(moscowCityId, filteredCityList)
        ).thenReturn (Completable.never())

        searchCityViewModel.searchCity(moscowCity)

        // When
        searchCityViewModel.onCityChosen(moscowCityId)

        // Then
        verify(cityDataCache).saveCityId(moscowCityId)
        verify(cityDataCache).saveCityName(moscowCity)
        verify(insertCityToLastChosenCitiesEntityUseCase).invoke(moscowCityId,filteredCityList)

    }


}