package com.reconix.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash")

    val radarAngle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label = "radar"
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 1.2f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "pulse"
    )

    var logoAlpha by remember { mutableFloatStateOf(0f) }
    var subtitleAlpha by remember { mutableFloatStateOf(0f) }

    val logoAnim by animateFloatAsState(
        targetValue = logoAlpha, animationSpec = tween(1000), label = "logoFade"
    )
    val subtitleAnim by animateFloatAsState(
        targetValue = subtitleAlpha, animationSpec = tween(800, delayMillis = 600), label = "subtitleFade"
    )

    LaunchedEffect(Unit) {
        logoAlpha = 1f
        delay(400)
        subtitleAlpha = 1f
        delay(3000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack),
        contentAlignment = Alignment.Center
    ) {
        // World grid background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridColor = ElectricBlue.copy(alpha = 0.05f)
            val step = 40.dp.toPx()
            var x = 0f
            while (x < size.width) {
                drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                x += step
            }
            var y = 0f
            while (y < size.height) {
                drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                y += step
            }
        }

        // Radar animation
        Canvas(modifier = Modifier.size(280.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val maxRadius = size.minDimension / 2

            // Concentric circles
            for (i in 1..4) {
                drawCircle(
                    color = ElectricBlue.copy(alpha = 0.15f),
                    radius = maxRadius * i / 4,
                    center = center,
                    style = Stroke(1.5f)
                )
            }

            // Radar sweep
            val sweepBrush = Brush.sweepGradient(
                0f to Color.Transparent,
                0.25f to ElectricBlue.copy(alpha = 0.4f),
                0.26f to Color.Transparent,
                center = center
            )
            drawCircle(brush = sweepBrush, radius = maxRadius, center = center)

            // Sweep line
            val angleRad = Math.toRadians(radarAngle.toDouble())
            drawLine(
                color = ElectricBlue.copy(alpha = 0.8f),
                start = center,
                end = Offset(
                    center.x + (maxRadius * cos(angleRad)).toFloat(),
                    center.y + (maxRadius * sin(angleRad)).toFloat()
                ),
                strokeWidth = 2f
            )

            // Blip dots
            listOf(45f, 120f, 200f, 310f).forEachIndexed { i, angle ->
                val r = maxRadius * listOf(0.4f, 0.65f, 0.5f, 0.75f)[i]
                val a = Math.toRadians(angle.toDouble())
                drawCircle(
                    color = CyanHighlight.copy(alpha = 0.9f),
                    radius = 4f,
                    center = Offset(center.x + (r * cos(a)).toFloat(), center.y + (r * sin(a)).toFloat())
                )
            }
        }

        // Logo + tagline
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.alpha(logoAnim)
        ) {
            Text(
                text = "RECONIX",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.horizontalGradient(listOf(ElectricBlue, NeonPurple))
                ),
                letterSpacing = 8.sp
            )
            Text(
                text = "Discover. Analyze. Secure.",
                fontSize = 14.sp,
                color = TextSecondary,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(subtitleAnim)
            )
        }
    }
}
