package com.example.thedailygrind.data

import android.content.Context
import com.example.thedailygrind.features.pomodoro.data.PomodoroRepository
import com.example.thedailygrind.features.pomodoro.data.WorkManagerPomodoroRepository

interface AppContainer {
    val pomodoroRepository: PomodoroRepository
}

class DefaultAppContainer(context: Context): AppContainer {
    override val pomodoroRepository = WorkManagerPomodoroRepository(context)
}