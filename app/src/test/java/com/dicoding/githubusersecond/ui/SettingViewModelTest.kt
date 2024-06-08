package com.dicoding.githubusersecond.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.githubusersecond.helper.SettingPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class SettingViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var settingPreferences: SettingPreferences

    @Before
    fun setUp() {
        settingPreferences = mock(SettingPreferences::class.java)
        settingViewModel = SettingViewModel(settingPreferences)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `saveThemeSetting should call saveThemeSetting from settingPreferences`() = runBlockingTest {
        val isDarkModeActive = true
        `when`(settingPreferences.saveThemeSetting(isDarkModeActive)).then { }

        settingViewModel.saveThemeSetting(isDarkModeActive)

        verify(settingPreferences).saveThemeSetting(isDarkModeActive)
    }
}
