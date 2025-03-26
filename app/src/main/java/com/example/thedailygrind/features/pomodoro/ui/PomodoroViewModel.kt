package com.example.thedailygrind.features.pomodoro.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.thedailygrind.TheDailyGrindApplication
import com.example.thedailygrind.features.pomodoro.data.PomodoroRepository
import com.example.thedailygrind.features.pomodoro.data.TimerState
import com.example.thedailygrind.features.pomodoro.data.WorkState
import com.example.thedailygrind.features.pomodoro.data.toEnum
import com.example.thedailygrind.features.pomodoro.data.toInt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PomodoroWorkerState (
    val workState: WorkState = WorkState.None,
    val startedAt: Long = 0L,
    val prevDuration: Long = 0L,
    val workCount: Int = 0
)

data class AppState(
    val timerState: TimerState = TimerState.Stopped,
    val pausedAt: Long = 0L
)

class PomodoroViewModel (private val pomodoroRepository: PomodoroRepository): ViewModel() {
    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val workerState: StateFlow<PomodoroWorkerState> = pomodoroRepository.progressInfo
        .map { info ->

            val workState: WorkState? = info.progress.getInt("workState", WorkState.None.toInt()).toEnum<WorkState>()
            val startedAt: Long = info.progress.getLong("started", 1L)
            val prevDuration: Long = info.progress.getLong("prevDuration", 0L)
            val workCount: Int = info.progress.getInt("workCount", 0)

            PomodoroWorkerState(
                workState = workState ?: WorkState.None,
                startedAt = startedAt,
                prevDuration = prevDuration,
                workCount = workCount
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PomodoroWorkerState()
        )

    private fun updateAppState(timerState: TimerState) {
        _appState.value = AppState(
            timerState = timerState,
            pausedAt = if (timerState == TimerState.Paused) System.currentTimeMillis() else 0L
        )
    }

    fun startTimer() {
        val pausedAt = _appState.value.pausedAt

        updateAppState(TimerState.Running)
        if (pausedAt > 0) {
            // get values to resume work session
            val prevState = workerState.value.workState
            val prevCount = workerState.value.workCount
            val prevDuration = (pausedAt - workerState.value.startedAt) + workerState.value.prevDuration

            pomodoroRepository.startTimer(prevState, prevDuration, prevCount)
        } else {
            pomodoroRepository.startTimer()
        }
    }

    fun pauseTimer() {
        updateAppState(TimerState.Paused)
        pomodoroRepository.stopTimer()
    }

    fun stopTimer() {
        updateAppState(TimerState.Stopped)
        pomodoroRepository.stopTimer()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val pomodoroRepository =
                    (this[APPLICATION_KEY] as TheDailyGrindApplication).container.pomodoroRepository
                PomodoroViewModel(
                    pomodoroRepository=pomodoroRepository
                )
            }
        }
    }
}