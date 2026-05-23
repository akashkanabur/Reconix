package com.reconix.ui.screens.ai

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.CyberTopBar
import com.reconix.ui.theme.*
import kotlinx.coroutines.launch

data class ChatMessage(val content: String, val isUser: Boolean, val timestamp: Long = System.currentTimeMillis())

private val suggestedPrompts = listOf(
    "How do I enumerate subdomains?",
    "Explain SQL injection",
    "Best recon tools for bug bounty",
    "How to write a vulnerability report?"
)

@Composable
fun AiAssistantScreen(onNavigateBack: () -> Unit) {
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hi! I'm your AI recon assistant. Ask me anything about bug bounty, recon techniques, or vulnerability analysis.", false)
        )
    }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isTyping by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val typingDot by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse),
        label = "dot"
    )

    fun sendMessage() {
        val text = input.trim()
        if (text.isBlank()) return
        messages.add(ChatMessage(text, true))
        input = ""
        isTyping = true
        scope.launch {
            listState.animateScrollToItem(messages.size - 1)
            kotlinx.coroutines.delay(1500)
            isTyping = false
            messages.add(ChatMessage(generateAiResponse(text), false))
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("AI Recon Assistant", onNavigateBack) {
            Box(
                modifier = Modifier.size(8.dp).clip(CircleShape).background(NeonGreen)
                    .align(Alignment.CenterVertically)
            )
            Spacer(Modifier.width(8.dp))
            Text("Online", color = NeonGreen, fontSize = 12.sp)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { msg -> ChatBubble(msg) }
            if (isTyping) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AiAvatar()
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp))
                                .background(CardDarkElevated).padding(12.dp)
                        ) {
                            Text("Thinking...", color = ElectricBlue.copy(alpha = 0.5f + typingDot * 0.5f), fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        // Suggested prompts
        if (messages.size == 1) {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                items(suggestedPrompts) { prompt ->
                    TextButton(onClick = { input = prompt; sendMessage() }) {
                        Text("→ $prompt", color = ElectricBlue, fontSize = 13.sp)
                    }
                }
            }
        }

        // Input bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Ask anything...", color = TextMuted) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = CardDark,
                    unfocusedContainerColor = CardDark
                ),
                maxLines = 3
            )
            IconButton(
                onClick = ::sendMessage,
                modifier = Modifier.size(48.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(ElectricBlue, NeonPurple)))
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isUser) AiAvatar()
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    if (message.isUser) RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp)
                    else RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp)
                )
                .background(
                    if (message.isUser) Brush.linearGradient(listOf(ElectricBlue, NeonPurple))
                    else Brush.linearGradient(listOf(CardDarkElevated, CardDarkElevated))
                )
                .padding(12.dp)
        ) {
            Text(message.content, color = TextPrimary, fontSize = 14.sp, lineHeight = 20.sp)
        }
    }
}

@Composable
private fun AiAvatar() {
    Box(
        modifier = Modifier.size(32.dp).clip(CircleShape)
            .background(Brush.linearGradient(listOf(ElectricBlue.copy(alpha = 0.3f), NeonPurple.copy(alpha = 0.3f)))),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
    }
}

private fun generateAiResponse(input: String): String {
    val lower = input.lowercase()
    return when {
        "subdomain" in lower -> "For subdomain enumeration, I recommend: 1) Subfinder for passive recon, 2) Amass for comprehensive discovery, 3) Certificate transparency logs via crt.sh. Always check for wildcard DNS before scanning."
        "sql" in lower -> "SQL injection occurs when user input is unsafely included in SQL queries. Test with payloads like `' OR '1'='1`, `'; DROP TABLE users;--`. Use tools like SQLMap for automated testing. Always get written permission first."
        "report" in lower -> "A good vulnerability report includes: 1) Clear title with severity, 2) Affected URL/endpoint, 3) Step-by-step reproduction, 4) Impact assessment, 5) Proof of concept (screenshots/video), 6) Suggested remediation."
        "recon" in lower || "tool" in lower -> "Essential recon tools: Subfinder (subdomains), Shodan (exposed services), Waybackurls (historical URLs), Nuclei (vulnerability scanning), Burp Suite (web proxy), Amass (attack surface mapping)."
        else -> "Great question! For bug bounty recon, always start with passive reconnaissance before active scanning. Check the program scope carefully, document everything, and focus on high-impact vulnerabilities. What specific area would you like to explore?"
    }
}
