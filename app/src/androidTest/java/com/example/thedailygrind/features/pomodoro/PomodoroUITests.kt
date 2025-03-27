package com.example.thedailygrind.features.pomodoro

import android.content.Context
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.thedailygrind.AppContainer
import com.example.thedailygrind.R
import com.example.thedailygrind.ui.theme.TheDailyGrindTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PomodoroUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testButtonsOnLoad() {
        composeTestRule.setContent {
            TheDailyGrindTheme {
                AppContainer()
            }
        }

        val startButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_start_button))
        val pauseButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_pause_button))
        val stopButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_stop_button))

        startButton.assertIsEnabled()
        pauseButton.assertIsNotEnabled()
        stopButton.assertIsNotEnabled()
    }

    @Test
    fun testStartStopTimer() {
        composeTestRule.setContent {
            TheDailyGrindTheme {
                AppContainer()
            }
        }

        val startButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_start_button))
        val pauseButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_pause_button))
        val stopButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_stop_button))

        startButton.performClick()
        composeTestRule.waitForIdle()

        startButton.assertIsNotEnabled()
        pauseButton.assertIsEnabled()
        stopButton.assertIsEnabled()

        stopButton.performClick()
        composeTestRule.waitForIdle()

        startButton.assertIsEnabled()
        pauseButton.assertIsNotEnabled()
        stopButton.assertIsNotEnabled()
    }

    @Test
    fun testStartPauseStopTimer() {
        composeTestRule.setContent {
            TheDailyGrindTheme {
                AppContainer()
            }
        }

        val startButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_start_button))
        val pauseButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_pause_button))
        val stopButton = composeTestRule.onNodeWithTag(context.resources.getString(R.string.tt_pomodoro_stop_button))

        startButton.performClick()
        composeTestRule.waitForIdle()

        startButton.assertIsNotEnabled()
        pauseButton.assertIsEnabled()
        stopButton.assertIsEnabled()


        pauseButton.performClick()
        composeTestRule.waitForIdle()

        startButton.assertIsEnabled()
        pauseButton.assertIsNotEnabled()
        stopButton.assertIsNotEnabled()


        startButton.performClick()
        composeTestRule.waitForIdle()

        stopButton.performClick()
        composeTestRule.waitForIdle()

        startButton.assertIsEnabled()
        pauseButton.assertIsNotEnabled()
        stopButton.assertIsNotEnabled()
    }
}