package com.example.thedailygrind.features.pomodoro.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.thedailygrind.features.pomodoro.workers.PomodoroWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class WorkManagerPomodoroRepository(context: Context): PomodoroRepository {

    private val workManager = WorkManager.getInstance(context)

    override val progressInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData(TAG_POMODORO_WORKER).asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    override fun startTimer(prevState: WorkState, prevDuration: Long, prevCount: Int) {
        val inputData = addSettingsInput(Data.Builder())
            .putInt("prevWorkState", prevState.toInt())
            .putLong("prevWorkDuration", prevDuration)
            .putInt("prevWorkCount", prevCount)
            .build()

        val timerWorker = OneTimeWorkRequestBuilder<PomodoroWorker>()
            .addTag(TAG_POMODORO_WORKER)
            .setInputData(inputData)
            .build()

        workManager.beginUniqueWork(
            NAME_POMODORO_WORKER,
            ExistingWorkPolicy.REPLACE,
            timerWorker
        ).enqueue()
    }

    override fun stopTimer() {
        workManager.cancelAllWorkByTag(TAG_POMODORO_WORKER)
    }

    private fun addSettingsInput(inputBuilder: Data.Builder): Data.Builder {
        return inputBuilder
            .putLong("workDuration", 10 * 1000)
            .putLong("shortBreakDuration", 5 * 1000)
            .putLong("longBreakDuration", 10 * 1000)
            .putInt("maxWorkCount", 3)
    }
}