package ru.yandex.loginapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with empty fields sets EmptyFieldsError`() = runTest {
        val testEmail = ""
        val testPassword = ""
        val expected = LoginScreenState.EmptyFieldsError
        loginViewModel.login(testEmail, testPassword)
        advanceUntilIdle()
        val actual = loginViewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `login with invalid email sets EmailValidationError`() = runTest {
        val testEmail = "qwertyu"
        val testPassword = "qwerty"
        val expected = LoginScreenState.EmailValidationError
        loginViewModel.login(testEmail, testPassword)
        advanceUntilIdle()
        val actual = loginViewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `login with valid data sets Loading`() = runTest {
        val testEmail = "none@none.no"
        val testPassword = "qwerty"
        val expected = LoginScreenState.Loading
        loginViewModel.login(testEmail, testPassword)
        advanceTimeBy(1.milliseconds)
        val actual = loginViewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `login with valid data sets Loading then Success`() = runTest {
        val testEmail = "none@none.no"
        val testPassword = "qwerty"
        val expected = LoginScreenState.Loading
        loginViewModel.login(testEmail, testPassword)
        advanceTimeBy(1.milliseconds)
        val actual = loginViewModel.state.value
        assertEquals(expected, actual)
        advanceUntilIdle()
        val expectedSuccess = LoginScreenState.Success
        val actualSuccess = loginViewModel.state.value
        assertEquals(expectedSuccess, actualSuccess)

    }
}