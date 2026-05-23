package com.reconix.ui.screens.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.CyberTopBar
import com.reconix.ui.components.GlassCard
import com.reconix.ui.components.TerminalText
import com.reconix.ui.screens.auth.CyberTextField
import com.reconix.ui.theme.*

@Composable
private fun ReconToolsHubContent() {
    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Recon Tools Hub", onNavigateBack = {})
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tools.size) { i ->
                val tool = tools[i]
                Box(
                    modifier = Modifier.aspectRatio(1f).clip(RoundedCornerShape(20.dp))
                        .background(CardDark).clickable {}.padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(16.dp))
                                .background(tool.color.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(tool.icon, contentDescription = tool.name, tint = tool.color, modifier = Modifier.size(28.dp))
                        }
                        Text(tool.name, color = TextPrimary, fontSize = 13.sp,
                            fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
private fun WhoisToolContent() {
    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("WHOIS Lookup", onNavigateBack = {})
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CyberTextField("example.com", {}, "Domain (e.g. example.com)", Icons.Default.Search)
                }
                com.reconix.ui.components.NeonButton("Run", onClick = {}, modifier = Modifier.width(80.dp).height(56.dp), color = ElectricBlue)
            }
            GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = ElectricBlue) {
                Text("Results for: example.com", color = ElectricBlue, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(8.dp))
                TerminalText(
                    listOf(
                        "Registrar: GoDaddy LLC",
                        "Created: 2010-03-15",
                        "Expires: 2025-03-15",
                        "Nameservers: ns1.example.com, ns2.example.com"
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PortScannerContent() {
    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Port Scanner", onNavigateBack = {})
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CyberTextField("192.168.1.1", {}, "Host (e.g. 192.168.1.1)", Icons.Default.Router)
                }
                com.reconix.ui.components.NeonButton("Run", onClick = {}, modifier = Modifier.width(80.dp).height(56.dp), color = HighOrange)
            }
            GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = HighOrange) {
                Text("Results for: 192.168.1.1", color = HighOrange, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(8.dp))
                TerminalText(
                    listOf("Port 22: OPEN", "Port 80: OPEN", "Port 443: OPEN", "Port 3306: OPEN", "Port 8080: OPEN"),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Recon Tools Hub")
@Composable
fun ReconToolsHubPreview() {
    ReconixTheme { ReconToolsHubContent() }
}

@Preview(showBackground = true, showSystemUi = true, name = "WHOIS Lookup Tool")
@Composable
fun WhoisToolPreview() {
    ReconixTheme { WhoisToolContent() }
}

@Preview(showBackground = true, showSystemUi = true, name = "Port Scanner Tool")
@Composable
fun PortScannerToolPreview() {
    ReconixTheme { PortScannerContent() }
}
