package com.hamza.clean.presentation.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hamza.clean.presentation.util.rules.CoroutineTestRule
import org.junit.Rule

/**
 * Created by Ameer Hamza on 03/09/2023
 **/
open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

}