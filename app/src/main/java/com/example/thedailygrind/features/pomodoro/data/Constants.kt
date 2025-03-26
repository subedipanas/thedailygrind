package com.example.thedailygrind.features.pomodoro.data

enum class TimerState {
    Running, Stopped, Paused
}

enum class WorkState {
    None, Work, ShortBreak, LongBreak
}

const val TAG_POMODORO_WORKER = "POMODORO_WORKER_TAG"
const val NAME_POMODORO_WORKER = "POMODORO_WORKER_NAME"

const val DEFAULT_DURATION_WORK: Long = 15 * 60 * 1000
const val DEFAULT_DURATION_SHORT_BREAK: Long = 5 * 60 * 1000
const val DEFAULT_DURATION_LONG_BREAK: Long = 10 * 60 * 1000
const val DEFAULT_MAX_WORK_COUNT: Int = 5

inline fun <reified T: Enum<T>> Int.toEnum(): T? {
    return enumValues<T>().firstOrNull { it.ordinal == this }
}

inline fun <reified T: Enum<T>> T.toInt(): Int {
    return this.ordinal
}