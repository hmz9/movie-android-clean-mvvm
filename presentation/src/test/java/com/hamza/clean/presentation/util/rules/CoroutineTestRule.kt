package com.hamza.clean.presentation.util.rules

import com.hamza.data.util.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Created by Ameer Hamza on 03/09/2023
 **/
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule : TestWatcher() {

    internal val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    val testDispatcherProvider = object : DispatchersProvider {
        override fun getIO(): CoroutineDispatcher = testDispatcher
        override fun getMain(): CoroutineDispatcher = testDispatcher
        override fun getMainImmediate(): CoroutineDispatcher = testDispatcher
        override fun getDefault(): CoroutineDispatcher = testDispatcher
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineTestRule.runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
    testDispatcher.runBlockingTest(block)
}