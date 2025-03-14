package com.example.thedailygrind

import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.thedailygrind.ui.theme.TheDailyGrindTheme
import com.example.thedailygrind.ui.theme.primaryContainerDark
import com.example.thedailygrind.ui.theme.primaryContainerLight
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class TheDailyGrindUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAppBarTitle() {
        composeTestRule.setContent {
            TheDailyGrindTheme {
                AppContainer()
            }
        }

        val expectedHeader = "The Daily Grind"
        composeTestRule.onNodeWithText(expectedHeader).assertExists()
    }

    @Test
    fun testLightThemeHeaderBackgroundColor() {
        composeTestRule.setContent {
            TheDailyGrindTheme (
                darkTheme = false
            ) {
                AppContainer()
            }
        }

        val appBarTag = "testTag_AppBar"
        val expectedHeaderBackgroundColor = primaryContainerLight

        val pixels = IntArray(10)

        composeTestRule.onNodeWithTag(appBarTag).captureToImage()
            .readPixels(pixels, startX = 1, startY = 1, width = 5, height = 2)

        for (pixel in pixels) {
            assertEquals(expectedHeaderBackgroundColor.convert(ColorSpaces.Srgb).hashCode(), pixel)
        }

    }

    @Test
    fun testDarkThemeHeaderBackgroundColor() {
        composeTestRule.setContent {
            TheDailyGrindTheme (
                darkTheme = true
            ) {
                AppContainer()
            }
        }

        val appBarTag = "testTag_AppBar"
        val expectedHeaderBackgroundColor = primaryContainerDark

        val pixels = IntArray(10)

        composeTestRule.onNodeWithTag(appBarTag).captureToImage()
            .readPixels(pixels, startX = 1, startY = 1, width = 5, height = 2)

        for (pixel in pixels) {
            assertEquals(expectedHeaderBackgroundColor.convert(ColorSpaces.Srgb).hashCode(), pixel)
        }

    }
}