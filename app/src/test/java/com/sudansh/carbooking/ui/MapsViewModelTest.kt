package com.sudansh.carbooking.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.sudansh.carbooking.data.Resource
import com.sudansh.carbooking.repository.CabRepository
import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MapsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MapsViewModel
    @Mock
    lateinit var repo: CabRepository
    @Mock
    lateinit var liveResults: Observer<Resource<List<CabLive>>>
    @Mock
    lateinit var avaibleResults: Observer<Resource<List<Availability>>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = MapsViewModel(repo)
        viewModel.cabLive.observeForever(liveResults)
        viewModel.availability.observeForever(avaibleResults)
    }

    @Test
    fun testRefresh() {
        viewModel.cabLive.observeForever(mock())
        viewModel.getLive()
        //verify findAll is called with true
        Mockito.verify(repo).getCabLive(true)
    }

    @Test
    fun availability() {
        viewModel.availability.observeForever(mock())
        viewModel.getAvailability(100, 200)

        verify(repo).getAvailability(100, 200)
    }
}