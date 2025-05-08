package ru.yandex.loginapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private var loginViewModel: LoginViewModel? = null
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        loginViewModel = null
    }

    @Test
    fun `login with empty fields sets EmptyFieldsError`() = runTest {
        val testEmail = ""
        val testPassword = ""
        val expected = LoginScreenState.EmptyFieldsError
        loginViewModel?.login(testEmail, testPassword)
        val actual = loginViewModel?.state?.value
        assertEquals(expected, actual)
    }

    @Test
    fun `login with invalid email sets EmailValidationError`() = runTest {
        val testEmail = "qwertyu"
        val testPassword = "qwerty"
        val expected = LoginScreenState.EmailValidationError
        loginViewModel?.login(testEmail, testPassword)
        val actual = loginViewModel?.state?.value
        assertEquals(expected, actual)
    }

    @Test
    fun `login with valid data sets Loading`() = runTest {
        val testEmail = "none@none.no"
        val testPassword = "qwerty"
        val expected = LoginScreenState.Loading
        loginViewModel?.login(testEmail, testPassword)
        testScheduler.advanceTimeBy(2.seconds)
        val actual = loginViewModel?.state?.value
        assertEquals(expected, actual)
    }

    @Test
    fun `login with valid data sets Loading then Success`() = runTest {
        val testEmail = "none@none.no"
        val testPassword = "qwerty"
        val expected = LoginScreenState.Loading
        loginViewModel?.login(testEmail, testPassword)
        testScheduler.advanceTimeBy(2.seconds)
        val actual = loginViewModel?.state?.value
        assertEquals(expected, actual)
        testScheduler.advanceUntilIdle()
        val expectedSuccess = LoginScreenState.Success
        val actualSuccess = loginViewModel?.state?.value
        assertEquals(expectedSuccess, actualSuccess)

    }
}