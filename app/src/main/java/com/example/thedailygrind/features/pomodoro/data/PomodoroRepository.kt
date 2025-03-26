package com.example.thedailygrind.features.pomodoro.data

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface PomodoroRepository {
    val progressInfo: Flow<WorkInfo>
    fun startTimer(prevState: WorkState = WorkState.Work, prevDuration: Long = 0L, prevCount: Int = 0)
    fun stopTimer()
}