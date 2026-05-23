package com.reconix.ui.screens.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reconix.navigation.Screen
import com.reconix.ui.components.*
import com.reconix.ui.screens.auth.CyberTextField
import com.reconix.ui.theme.*
import com.reconix.viewmodel.ReconToolsViewModel

data class ToolItem(val name: String, val icon: ImageVector, val color: Color, val route: String)

val tools = listOf(
    ToolItem("WHOIS Lookup", Icons.Default.Info, ElectricBlue, Screen.WhoisLookup.route),
    ToolItem("DNS Lookup", Icons.Default.Dns, NeonPurple, Screen.DnsLookup.route),
    ToolItem("HTTP Headers", Icons.Default.Http, CyanHighlight, Screen.HttpHeaders.route),
    ToolItem("Port Scanner", Icons.Default.Router, HighOrange, Screen.PortScanner.route),
    ToolItem("Tech Detector", Icons.Default.Code, NeonGreen, Screen.TechDetector.route),
    ToolItem("SSL Checker", Icons.Default.Lock, MediumYellow, Screen.SslChecker.route)
)

@Composable
fun ReconToolsScreen(onNavigateBack: () -> Unit, onToolSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Recon Tools Hub", onNavigateBack)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tools) { tool ->
                ToolGridCard(tool, onClick = { onToolSelected(tool.route) })
            }
        }
    }
}

@Composable
private fun ToolGridCard(tool: ToolItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .clickable(onClick = onClick)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(tool.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(tool.icon, contentDescription = tool.name, tint = tool.color, modifier = Modifier.size(28.dp))
            }
            Text(tool.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
private fun ToolScreen(
    title: String,
    placeholder: String,
    accentColor: Color,
    onNavigateBack: () -> Unit,
    onRun: (ReconToolsViewModel) -> Unit,
    viewModel: ReconToolsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) { onDispose { viewModel.reset() } }

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar(title, onNavigateBack)
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CyberTextField(state.query, viewModel::onQueryChange, placeholder, Icons.Default.Search)
                }
                NeonButton("Run", onClick = { onRun(viewModel) },
                    modifier = Modifier.width(80.dp).height(56.dp), color = accentColor,
                    enabled = state.query.isNotBlank() && !state.isLoading)
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = accentColor)
                }
            }

            state.error?.let {
                GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = CriticalRed) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = CriticalRed)
                        Text(it, color = CriticalRed, fontSize = 13.sp)
                    }
                }
            }

            state.result?.let { result ->
                GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = accentColor) {
                    Text("Results for: ${result.query}", color = accentColor, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                    TerminalText(result.results, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable fun WhoisLookupScreen(onNavigateBack: () -> Unit) =
    ToolScreen("WHOIS Lookup", "Domain (e.g. example.com)", ElectricBlue, onNavigateBack, { it.runWhois() })

@Composable fun DnsLookupScreen(onNavigateBack: () -> Unit) =
    ToolScreen("DNS Lookup", "Domain (e.g. example.com)", NeonPurple, onNavigateBack, { it.runDns() })

@Composable fun HttpHeadersScreen(onNavigateBack: () -> Unit) =
    ToolScreen("HTTP Headers", "URL (e.g. https://example.com)", CyanHighlight, onNavigateBack, { it.runHttpHeaders() })

@Composable fun PortScannerScreen(onNavigateBack: () -> Unit) =
    ToolScreen("Port Scanner", "Host (e.g. 192.168.1.1)", HighOrange, onNavigateBack, { it.runPortScan() })

@Composable fun TechDetectorScreen(onNavigateBack: () -> Unit) =
    ToolScreen("Tech Detector", "URL (e.g. https://example.com)", NeonGreen, onNavigateBack, { it.runTechDetect() })

@Composable fun SslCheckerScreen(onNavigateBack: () -> Unit) =
    ToolScreen("SSL Checker", "Domain (e.g. example.com)", MediumYellow, onNavigateBack, { it.runSsl() })
