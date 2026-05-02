package com.missaosecreta.operacaosantamaria

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun BaseLayout(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(title, color = Color(0xFF7B2CBF), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        content()
    }
}

@Composable
fun StartScreen(onStart: () -> Unit) { BaseLayout("Missão Secreta") { Text("Operação Santa Maria", fontSize = 24.sp, color = Color(0xFFFF5EA8), fontWeight = FontWeight.Bold); Text("Agente Olga, sua nova missão foi liberada."); Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(58.dp)) { Text("Iniciar missão") } } }

@Composable
fun Phase1Screen(onNext: () -> Unit) {
    var sequence by remember { mutableStateOf(listOf<String>()) }
    var message by remember { mutableStateOf("") }
    // Edite peças e sequência correta desta fase aqui.
    val pieces = listOf("📚", "➡️", "🛏️")
    BaseLayout("Fase 1 – Montagem de pistas") {
        Text("Monte a sequência correta.")
        Text(sequence.joinToString(" "), fontSize = 34.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) { pieces.forEach { Button(onClick = { if (sequence.size < 3) sequence = sequence + it }) { Text(it, fontSize = 22.sp) } } }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) { OutlinedButton(onClick = { sequence = emptyList(); message = "" }) { Text("Limpar") }; Button(onClick = { message = if (sequence == listOf("📚", "➡️", "🛏️")) "A pista está no quarto, perto dos livros." else "A sequência ainda não revela o caminho. Tente outra combinação." }) { Text("Confirmar") } }
        Text(message)
        if (message.startsWith("A pista")) Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text("Próxima fase") }
    }
}

@Composable
fun Phase2Screen(onNext: () -> Unit) { val options = listOf("Foto da estante", "Anotação: histórias", "Mapa da casa", "Objeto falso"); val selected = remember { mutableStateListOf<String>() }; var message by remember { mutableStateOf("") }; BaseLayout("Fase 2 – Detetive de evidências") { options.forEach { item -> val isSelected = selected.contains(item); Card(modifier = Modifier.fillMaxWidth().clickable { if (isSelected) selected.remove(item) else selected.add(item) }.border(2.dp, if (isSelected) Color(0xFFFF8C42) else Color(0xFF7B2CBF), RoundedCornerShape(14.dp))) { Text(item, modifier = Modifier.padding(16.dp)) } }; Button(onClick = { val ok = selected.contains("Foto da estante") && selected.contains("Anotação: histórias") && selected.size == 2; message = if (ok) "Boa análise, agente. As evidências apontam para o local certo." else "Essas evidências não combinam. Revise sua investigação." }, modifier = Modifier.fillMaxWidth()) { Text("Analisar evidências") }; Text(message); if (message.startsWith("Boa")) Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text("Próxima fase") } } }

@Composable
fun Phase3Screen(onNext: () -> Unit) { var answer by remember { mutableStateOf("") }; var message by remember { mutableStateOf("") }; BaseLayout("Fase 3 – Código e criptografia") { Text("🐱 = 1\n🐱🐱 = 2\n🐱🐱🐱 = ?", fontSize = 28.sp); OutlinedTextField(value = answer, onValueChange = { answer = it }, label = { Text("Resposta") }); Button(onClick = { message = if (answer.trim() == "3") "Código decifrado." else "Código incorreto. Conte novamente os gatinhos." }, modifier = Modifier.fillMaxWidth()) { Text("Verificar código") }; Text(message); if (message.startsWith("Código decifrado")) Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text("Próxima fase") } } }

@Composable
fun Phase4Screen(onNext: () -> Unit) { var message by remember { mutableStateOf("") }; BaseLayout("Fase 4 – Escolhas com consequência") { Text("Você acha que a pista está onde?"); listOf("Quarto", "Cozinha", "Sala").forEach { Button(onClick = { message = if (it == "Quarto") "Local confirmado." else "Agente… revise sua lógica antes de seguir." }, modifier = Modifier.fillMaxWidth()) { Text(it) } }; Text(message); if (message.startsWith("Local")) Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text("Próxima fase") } } }

data class MemoryCard(val id: Int, val symbol: String, var revealed: Boolean = false, var matched: Boolean = false)

@Composable
fun Phase5Screen(onNext: () -> Unit) { val symbols = remember { listOf("🐱", "🐱", "🔍", "🔍", "📚", "📚").shuffled(Random(7)) }; val cards = remember { mutableStateListOf<MemoryCard>().apply { symbols.forEachIndexed { i, s -> add(MemoryCard(i, s)) } } }; var opened by remember { mutableStateOf(listOf<Int>()) }; var code by remember { mutableStateOf("") }; var message by remember { mutableStateOf("") }; val allMatched = cards.all { it.matched }; fun processCard(index: Int) { val card = cards[index]; if (card.matched || card.revealed || opened.size == 2) return; card.revealed = true; opened = opened + index; if (opened.size == 2) { val a = cards[opened[0]]; val b = cards[opened[1]]; if (a.symbol == b.symbol) { a.matched = true; b.matched = true } else { a.revealed = false; b.revealed = false }; opened = emptyList() } }; BaseLayout("Fase 5 – Memória com pistas escondidas") { LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.height(220.dp), contentPadding = PaddingValues(4.dp)) { itemsIndexed(cards) { index, card -> Box(modifier = Modifier.padding(6.dp).size(90.dp).background(if (card.revealed || card.matched) Color(0xFFFFD60A) else Color(0xFF7B2CBF), RoundedCornerShape(12.dp)).clickable { processCard(index) }, contentAlignment = Alignment.Center) { Text(if (card.revealed || card.matched) card.symbol else "❓", fontSize = 30.sp, color = Color.Black) } } }; if (allMatched) { Text("Digite o código final encontrado no local."); OutlinedTextField(value = code, onValueChange = { code = it.uppercase() }, label = { Text("Código final") }); Button(onClick = { if (code.trim() == "LIVRO07") onNext() else message = "Código final inválido. Procure melhor a pista." }, modifier = Modifier.fillMaxWidth()) { Text("Validar") } }; Text(message) } }

@Composable
fun FinalScreen(onRestart: () -> Unit) { BaseLayout("Tela final – Mural da espiã") { Text("Parabéns, Agente Olga. Você completou a missão e conquistou todos os troféus do mural da espiã."); FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { listOf("🏆 Mestre das pistas", "🏆 Detetive oficial", "🏆 Decifradora de códigos", "🏆 Escolhas precisas", "🏆 Memória de espiã").forEach { Card { Text(it, modifier = Modifier.padding(12.dp)) } } }; Button(onClick = onRestart, modifier = Modifier.fillMaxWidth()) { Text("Jogar novamente") } } }
