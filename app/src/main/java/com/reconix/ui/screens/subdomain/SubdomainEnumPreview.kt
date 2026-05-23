package com.reconix.ui.screens.subdomain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.domain.model.Subdomain
import com.reconix.ui.components.*
import com.reconix.ui.screens.auth.CyberTextField
import com.reconix.ui.theme.*

private val previewSubdomains = listOf(
    Subdomain(1, "example.com", "api.example.com", "192.168.1.1", 200),
    Subdomain(2, "example.com", "admin.example.com", "192.168.1.2", 403),
    Subdomain(3, "example.com", "dev.example.com", "192.168.1.3", 200),
    Subdomain(4, "example.com", "staging.example.com", "192.168.1.4", 301),
    Subdomain(5, "example.com", "mail.example.com", "192.168.1.5", 0),
    Subdomain(6, "example.com", "vpn.example.com", "10.0.0.1", 200)
)
private val previewLogs = listOf(
    "Initializing scan for example.com...",
    "Resolving DNS records...",
    "Querying certificate transparency logs...",
    "Brute-forcing common subdomains...",
    "Found 6 subdomains."
)

@Composable
private fun SubdomainEnumContent() {
    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Subdomain Enumeration", onNavigateBack = {})

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CyberTextField(
                        value = "example.com",
                        onValueChange = {},
                        label = "Target Domain",
                        leadingIcon = Icons.Default.Language
                    )
                }
                NeonButton("Scan", onClick = {}, modifier = Modifier.width(90.dp).height(56.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(NeonGreen))
                Text("6 subdomains found", color = NeonGreen, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            TerminalText(previewLogs, modifier = Modifier.fillMaxWidth())
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Results (${previewSubdomains.size})", fontWeight = FontWeight.SemiBold, color = TextPrimary)
            IconButton(onClick = {}) {
                Icon(Icons.Default.Download, contentDescription = "Export", tint = ElectricBlue)
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(previewSubdomains.size) { i ->
                val sub = previewSubdomains[i]
                Row(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
                        .background(CardDark).padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(8.dp).clip(CircleShape).background(
                            when {
                                sub.status == 200 -> NeonGreen
                                sub.status > 0 -> HighOrange
                                else -> TextMuted
                            }
                        )
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(sub.subdomain, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text(sub.ipAddress, color = TextSecondary, fontSize = 11.sp)
                    }
                    if (sub.status > 0) {
                        Text(
                            sub.status.toString(),
                            color = if (sub.status == 200) NeonGreen else HighOrange,
                            fontSize = 12.sp, fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Subdomain Enumeration")
@Composable
fun SubdomainEnumPreview() {
    ReconixTheme { SubdomainEnumContent() }
}
