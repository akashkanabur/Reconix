package com.reconix.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.reconix.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    glowColor: Color = ElectricBlue,
    cornerRadius: Dp = 16.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.7f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "glowAlpha"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(CardDark.copy(alpha = 0.85f))
            .border(1.dp, glowColor.copy(alpha = glowAlpha), RoundedCornerShape(cornerRadius))
    ) {
        Column(modifier = Modifier.padding(contentPadding), content = content)
    }
}

@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = ElectricBlue,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(color, NeonPurple)),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
fun CyberTopBar(
    title: String,
    onNavigateBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onNavigateBack != null) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = ElectricBlue
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        actions()
    }
}

@Composable
fun ShimmerCard(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f, targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
        label = "shimmerTranslate"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(CardDark, CardDarkElevated, CardDark),
                    start = Offset(translateAnim - 200f, 0f),
                    end = Offset(translateAnim, 0f)
                )
            )
    )
}

@Composable
fun SeverityBadge(severity: com.reconix.domain.model.Severity) {
    val (color, label) = when (severity) {
        com.reconix.domain.model.Severity.CRITICAL -> CriticalRed to "CRITICAL"
        com.reconix.domain.model.Severity.HIGH -> HighOrange to "HIGH"
        com.reconix.domain.model.Severity.MEDIUM -> MediumYellow to "MEDIUM"
        com.reconix.domain.model.Severity.LOW -> LowBlue to "LOW"
        com.reconix.domain.model.Severity.INFO -> InfoGray to "INFO"
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier, glowColor = color, contentPadding = 12.dp) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label, 
                    fontSize = 11.sp, 
                    color = TextSecondary, 
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
                }
            }
            Text(
                text = value, 
                fontWeight = FontWeight.Bold, 
                fontSize = 18.sp, 
                color = TextPrimary
            )
        }
    }
}

@Composable
fun TerminalText(lines: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A0F0A))
            .border(1.dp, NeonGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        lines.forEach { line ->
            Text(
                text = "> $line",
                fontSize = 12.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = NeonGreen,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun GradientBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier.background(
            Brush.radialGradient(
                colors = listOf(NeonPurple.copy(alpha = 0.08f), DeepBlack),
                radius = 800f
            )
        ),
        content = content
    )
}
