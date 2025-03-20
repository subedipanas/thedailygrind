package com.example.thedailygrind.features.pomodoro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.thedailygrind.R
import com.example.thedailygrind.ui.theme.TheDailyGrindTheme

@Composable
fun PomodoroTimer(
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.layout_row_padding_vertical))
            .fillMaxWidth()
    ) {
        Button(
            onClick = {  },
        ) {
            Text(
                text = stringResource(R.string.start_button),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {  },
            enabled = false,
        ) {
            Text(
                text = stringResource(R.string.pause_button),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {  },
            enabled = false,
        ) {
            Text(
                text = stringResource(R.string.end_button),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun PomodoroTimerPreview() {
    TheDailyGrindTheme {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            PomodoroTimer()
        }
    }
}