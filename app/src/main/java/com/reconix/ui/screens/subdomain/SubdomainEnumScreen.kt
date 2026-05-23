package com.reconix.ui.screens.subdomain

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reconix.domain.model.Subdomain
import com.reconix.ui.components.*
import com.reconix.ui.screens.auth.CyberTextField
import com.reconix.ui.theme.*
import com.reconix.viewmodel.SubdomainViewModel

@Composable
fun SubdomainEnumScreen(onNavigateBack: () -> Unit, viewModel: SubdomainViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = "rotation"
    )

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Subdomain Enumeration", onNavigateBack)

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CyberTextField(
                        value = state.domain,
                        onValueChange = viewModel::onDomainChange,
                        label = "Target Domain",
                        leadingIcon = Icons.Default.Language
                    )
                }
                NeonButton(
                    text = if (state.isScanning) "Stop" else "Scan",
                    onClick = viewModel::startScan,
                    modifier = Modifier.width(90.dp).height(56.dp),
                    color = if (state.isScanning) CriticalRed else ElectricBlue
                )
            }

            if (state.scanLogs.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (state.isScanning) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = ElectricBlue,
                            modifier = Modifier.size(16.dp).rotate(rotation))
                    }
                    Text(
                        if (state.isScanning) "Scanning..." else "${state.subdomains.size} subdomains found",
                        color = if (state.isScanning) ElectricBlue else NeonGreen,
                        fontSize = 13.sp, fontWeight = FontWeight.Medium
                    )
                }
                TerminalText(state.scanLogs, modifier = Modifier.fillMaxWidth().heightIn(max = 160.dp))
            }
        }

        if (state.subdomains.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Results (${state.subdomains.size})", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                IconButton(onClick = { /* export */ }) {
                    Icon(Icons.Default.Download, contentDescription = "Export", tint = ElectricBlue)
                }
            }
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.subdomains) { SubdomainItem(it) }
            }
        }
    }
}

@Composable
private fun SubdomainItem(subdomain: Subdomain) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(CardDark)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(if (subdomain.status == 200) NeonGreen else if (subdomain.status > 0) HighOrange else TextMuted)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(subdomain.subdomain, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            if (subdomain.ipAddress.isNotBlank()) {
                Text(subdomain.ipAddress, color = TextSecondary, fontSize = 11.sp)
            }
        }
        if (subdomain.status > 0) {
            Text(
                subdomain.status.toString(),
                color = if (subdomain.status == 200) NeonGreen else HighOrange,
                fontSize = 12.sp, fontWeight = FontWeight.Bold
            )
        }
        IconButton(onClick = { /* copy */ }, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(16.dp))
        }
    }
}
