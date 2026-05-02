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
fun MissionApp() {
    val context = LocalContext.current
    // currentPhase controla em qual fase a agente está.
    // Para alterar fluxo do jogo, ajuste o when abaixo.
    var currentPhase by remember { mutableStateOf(loadPhase(context)) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8E6CF)) {
        when (currentPhase) {
            0 -> StartScreen { currentPhase = 1; savePhase(context, currentPhase) }
            1 -> Phase1Screen { currentPhase = 2; savePhase(context, currentPhase) }
            2 -> Phase2Screen { currentPhase = 3; savePhase(context, currentPhase) }
            3 -> Phase3Screen { currentPhase = 4; savePhase(context, currentPhase) }
            4 -> Phase4Screen { currentPhase = 5; savePhase(context, currentPhase) }
            5 -> Phase5Screen { currentPhase = 6; savePhase(context, currentPhase) }
            else -> FinalScreen { currentPhase = 0; savePhase(context, currentPhase) }
        }
    }
}
