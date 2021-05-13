package com.example.hilt.base

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@Suppress("EXPERIMENTAL_API_USAGE")
abstract class BaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testCoroutineDispatcher)
        MockKAnnotations.init(this)

        setUp()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()

        tearDown()
    }

    @CallSuper
    abstract fun setUp()
    open fun tearDown() {}
}
