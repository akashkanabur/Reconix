package com.reconix.ui.screens.premium

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.CyberTopBar
import com.reconix.ui.components.NeonButton
import com.reconix.ui.theme.*

data class PlanFeature(val text: String, val icon: ImageVector)

private val proFeatures = listOf(
    PlanFeature("Unlimited scans", Icons.Default.AllInclusive),
    PlanFeature("AI recon suggestions", Icons.Default.AutoAwesome),
    PlanFeature("Cloud sync", Icons.Default.Cloud),
    PlanFeature("Advanced analytics", Icons.Default.Analytics),
    PlanFeature("Priority support", Icons.Default.Support),
    PlanFeature("PDF report export", Icons.Default.PictureAsPdf)
)

@Composable
fun PremiumScreen(onNavigateBack: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "premium")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Reverse),
        label = "glow"
    )

    Column(
        modifier = Modifier.fillMaxSize().background(DeepBlack).verticalScroll(rememberScrollState())
    ) {
        CyberTopBar("Premium", onNavigateBack)

        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(NeonPurple.copy(alpha = 0.3f), ElectricBlue.copy(alpha = 0.2f))))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.WorkspacePremium, contentDescription = null,
                        tint = MediumYellow, modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(12.dp))
                    Text("Reconix Pro", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold,
                        style = androidx.compose.ui.text.TextStyle(
                            brush = Brush.horizontalGradient(listOf(ElectricBlue, NeonPurple))
                        ))
                    Text("Unlock the full power of recon", color = TextSecondary, textAlign = TextAlign.Center)
                }
            }

            // Pricing cards
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PricingCard("Monthly", "$9.99", "/mo", false, Modifier.weight(1f))
                PricingCard("Annual", "$79.99", "/yr", true, Modifier.weight(1f), glowAlpha)
            }

            // Features
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Everything included", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                proFeatures.forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(feature.icon, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(20.dp))
                        Text(feature.text, color = TextPrimary, fontSize = 15.sp)
                    }
                }
            }

            NeonButton("Start Free Trial", onClick = {}, modifier = Modifier.fillMaxWidth(), color = NeonPurple)
            Text("7-day free trial • Cancel anytime", color = TextMuted, fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun PricingCard(period: String, price: String, unit: String, isPopular: Boolean, modifier: Modifier, glowAlpha: Float = 0.5f) {
    val borderColor = if (isPopular) NeonPurple.copy(alpha = glowAlpha) else TextMuted.copy(alpha = 0.3f)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(if (isPopular) 2.dp else 1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isPopular) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NeonPurple.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("BEST VALUE", fontSize = 10.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
            }
            Text(period, color = TextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(4.dp))
            Text(price, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp,
                color = if (isPopular) NeonPurple else TextPrimary)
            Text(unit, color = TextMuted, fontSize = 12.sp)
        }
    }
}
