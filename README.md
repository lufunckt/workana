# Missão Secreta – Operação Santa Maria

App Android offline em **Kotlin + Jetpack Compose** com 5 fases investigativas e mural final de troféus.

## Requisitos
- Android Studio (Hedgehog ou superior)
- JDK 17
- SDK Android 34

## Como abrir no Android Studio
1. Abra o Android Studio.
2. Clique em **Open**.
3. Selecione a pasta deste projeto (`workana`).
4. Aguarde o **Gradle Sync** finalizar.
5. Execute no emulador/dispositivo com o botão **Run**.

## Como gerar APK
1. No Android Studio, abra:
   - **Build > Build Bundle(s) / APK(s) > Build APK(s)**
2. Aguarde o build terminar.
3. Clique em **locate** na notificação final.
4. O APK ficará em `app/build/outputs/apk/debug/app-debug.apk`.

## Estrutura principal
- `MainActivity.kt`: entrada do app.
- `GameScreen.kt`: fluxo por `currentPhase` e navegação simples por estado.
- `Screens.kt`: composables separados para tela inicial, 5 fases e tela final.
- `ProgressStore.kt`: progresso salvo localmente em `SharedPreferences`.
- Sem internet, sem banco de dados, sem bibliotecas complexas.

## Personalização rápida
Dentro de `MainActivity.kt`, procure pelos comentários e textos de cada fase para alterar:
- perguntas
- respostas corretas
- mensagens de sucesso/erro
- ordem e conteúdo dos desafios

## Observação
- Não há integração com IA neste app.
