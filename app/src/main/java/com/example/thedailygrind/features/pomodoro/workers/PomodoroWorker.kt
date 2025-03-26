package com.example.thedailygrind.features.pomodoro.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.thedailygrind.features.pomodoro.data.DEFAULT_DURATION_LONG_BREAK
import com.example.thedailygrind.features.pomodoro.data.DEFAULT_DURATION_SHORT_BREAK
import com.example.thedailygrind.features.pomodoro.data.DEFAULT_DURATION_WORK
import com.example.thedailygrind.features.pomodoro.data.DEFAULT_MAX_WORK_COUNT
import com.example.thedailygrind.features.pomodoro.data.WorkState
import com.example.thedailygrind.features.pomodoro.data.toEnum
import com.example.thedailygrind.features.pomodoro.data.toInt
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

const val TAG = "pomodoro.workers.PomodoroWorker"

class PomodoroWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    private var maxWorkCount: Int = DEFAULT_MAX_WORK_COUNT
    private var stateStartTime by Delegates.notNull<Long>()

    private lateinit var currentWorkState: WorkState
    private lateinit var stateDurations: HashMap<String, Long>

    private suspend fun updateProgress(prevDuration: Long, workCount: Int) {
        val data = Data.Builder()
            .putInt("workState", currentWorkState.toInt())
            .putLong("started", stateStartTime)
            .putLong("prevDuration", prevDuration)
            .putInt("workCount", workCount)
            .build()

        setProgress(data)
    }

    private suspend fun startState(state: WorkState, prevDuration: Long, workCount: Int) {
        currentWorkState = state
        stateStartTime = System.currentTimeMillis()

        updateProgress(prevDuration, workCount)
        delay((stateDurations[state.toString()] ?: 0L) - prevDuration)
    }

    /**
     * @input workDuration: Long - duration of work sessions
     * @input shortBreakDuration: Long - duration of short breaks
     * @input longBreakDuration: Long - duration of long breaks
     * @input maxWorkCount: Int - max work sessions per long break
     * @input prevWorkState: Int - WorkState.toInt() of state last paused
     * @input prevWorkCount: Int - work sessions when paused
     * @input prevWorkDuration: Long - duration of session when paused
     */
    @Suppress("UNREACHABLE_CODE")
    override suspend fun doWork(): Result {

        maxWorkCount = inputData.getInt("maxWorkCount", DEFAULT_MAX_WORK_COUNT)
        stateDurations = hashMapOf(
            WorkState.Work.toString() to inputData.getLong("workDuration", DEFAULT_DURATION_WORK),
            WorkState.ShortBreak.toString() to inputData.getLong("shortBreakDuration", DEFAULT_DURATION_SHORT_BREAK),
            WorkState.LongBreak.toString() to inputData.getLong("longBreakDuration", DEFAULT_DURATION_LONG_BREAK),
        )

        var workState = inputData.getInt("prevWorkState", WorkState.Work.toInt()).toEnum<WorkState>() ?: WorkState.Work
        var workCount = inputData.getInt("prevWorkCount", 0)
        var prevWorkDuration = inputData.getLong("prevWorkDuration", 0)

        return withContext(Dispatchers.IO) {
            return@withContext try {
                while (true) {
                    startState(workState, prevWorkDuration, workCount)
                    prevWorkDuration = 0

                    if (workState == WorkState.Work) {
                        workCount += 1
                        workState = if (workCount % maxWorkCount == 0) {
                            WorkState.LongBreak
                        } else {
                            WorkState.ShortBreak
                        }
                    } else {
                        workState = WorkState.Work
                    }
                }
                Result.success()
            } catch (throwable: CancellationException) {
                Log.i(TAG, "Pomodoro Worker paused/stopped", throwable)
                Result.success()
            } catch (throwable: Throwable) {
                Log.e(TAG, "Pomodoro Worker failure", throwable)
                Result.failure()
            }
        }
    }
}