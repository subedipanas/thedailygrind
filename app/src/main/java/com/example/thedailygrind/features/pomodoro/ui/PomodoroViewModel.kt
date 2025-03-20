package com.example.thedailygrind.features.pomodoro.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class TimerState {
    Running,
    Stopped,
}

data class PomodoroTimerState (
    val timerState: TimerState = TimerState.Stopped
)

class PomodoroViewModel: ViewModel() {
    private val _timerState = MutableStateFlow(PomodoroTimerState())
    val timerState: StateFlow<PomodoroTimerState> = _timerState.asStateFlow()

    private fun updateTimerState (newTimerState: TimerState) {
        _timerState.value = PomodoroTimerState(
            timerState = newTimerState
        )
    }

    fun startTimer() {
        updateTimerState(TimerState.Running)
    }

    fun stopTimer() {
        updateTimerState(TimerState.Stopped)
    }
}