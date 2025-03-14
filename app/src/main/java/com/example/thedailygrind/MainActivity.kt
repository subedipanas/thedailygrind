package com.example.thedailygrind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.thedailygrind.components.AppBar
import com.example.thedailygrind.ui.theme.TheDailyGrindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheDailyGrindTheme {
                AppContainer()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContainer (modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold (
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AppBar(scrollBehavior = scrollBehavior) }
    ) { innerPadding ->
        AppContent(innerPadding)
    }
}

@Composable
fun AppContent (innerPadding: PaddingValues, modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .padding(innerPadding)
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TheDailyGrindTheme {
        AppContainer()
    }
}