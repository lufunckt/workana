package com.missaosecreta.operacaosantamaria

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun GameScreen() {
    val context = LocalContext.current

    // currentPhase controla o fluxo da missão.
    // 0 = início, 1..5 = fases, 6 = tela final.
    var currentPhase by remember { mutableStateOf(loadPhase(context)) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8E6CF)) {
        when (currentPhase) {
            0 -> StartScreen(onStart = { advanceTo(1, context) { currentPhase = it } })
            1 -> Phase1Screen(onNext = { advanceTo(2, context) { currentPhase = it } })
            2 -> Phase2Screen(onNext = { advanceTo(3, context) { currentPhase = it } })
            3 -> Phase3Screen(onNext = { advanceTo(4, context) { currentPhase = it } })
            4 -> Phase4Screen(onNext = { advanceTo(5, context) { currentPhase = it } })
            5 -> Phase5Screen(onNext = { advanceTo(6, context) { currentPhase = it } })
            else -> FinalScreen(onRestart = { advanceTo(0, context) { currentPhase = it } })
        }
    }
}

private fun advanceTo(phase: Int, context: android.content.Context, setPhase: (Int) -> Unit) {
    setPhase(phase)
    savePhase(context, phase)
}
