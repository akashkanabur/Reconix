package com.reconix.ui.screens.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.NeonButton
import com.reconix.ui.theme.*
import kotlinx.coroutines.launch

data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val accentColor: Color
)

private val pages = listOf(
    OnboardingPage(Icons.Default.Search, "Discover Targets",
        "Find bug bounty programs from HackerOne, Bugcrowd, and more. Filter by scope, platform, and reward range.", ElectricBlue),
    OnboardingPage(Icons.Default.AutoAwesome, "Automate Recon",
        "Run WHOIS, DNS, subdomain enumeration, port scanning, and tech detection — all from one powerful hub.", NeonPurple),
    OnboardingPage(Icons.Default.BugReport, "Track Vulnerabilities",
        "Log findings with severity labels, screenshots, and notes. Generate professional PDF reports instantly.", CyanHighlight)
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            OnboardingPage(pages[page])
        }

        // Bottom controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Dots
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(pages.size) { i ->
                    val isSelected = pagerState.currentPage == i
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isSelected) ElectricBlue else TextMuted)
                            .size(if (isSelected) 24.dp else 8.dp, 8.dp)
                    )
                }
            }

            if (pagerState.currentPage == pages.lastIndex) {
                NeonButton("Get Started", onClick = onFinished, modifier = Modifier.fillMaxWidth())
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onFinished) {
                        Text("Skip", color = TextSecondary)
                    }
                    NeonButton("Next", onClick = {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }, modifier = Modifier.width(120.dp))
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(page: OnboardingPage) {
    val infiniteTransition = rememberInfiniteTransition(label = "page")
    val iconScale by infiniteTransition.animateFloat(
        initialValue = 0.9f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Reverse),
        label = "iconScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(
                    Brush.radialGradient(listOf(page.accentColor.copy(alpha = 0.2f), Color.Transparent))
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = null,
                tint = page.accentColor,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(Modifier.height(48.dp))

        Text(
            text = page.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = page.description,
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}
