package com.missaosecreta.operacaosantamaria

import android.content.Context

private const val PREF_NAME = "mission_progress"
private const val PHASE_KEY = "currentPhase"

fun savePhase(context: Context, phase: Int) {
    context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .edit().putInt(PHASE_KEY, phase).apply()
}

fun loadPhase(context: Context): Int {
    return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .getInt(PHASE_KEY, 0)
}
