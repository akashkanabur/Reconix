package com.reconix.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.domain.model.*
import com.reconix.ui.components.*
import com.reconix.ui.theme.*

// ── Preview-only stateless version of HomeScreen ──────────────────────────────

@Composable
private fun HomeScreenContent() {
    val sampleVulns = listOf(
        Vulnerability(1, "XSS in Search", "acme.com", Severity.HIGH, VulnStatus.OPEN, "Reflected XSS"),
        Vulnerability(2, "SQLi in Login", "techcorp.io", Severity.CRITICAL, VulnStatus.IN_PROGRESS, "SQL Injection"),
        Vulnerability(3, "IDOR on /api/user", "startup.xyz", Severity.MEDIUM, VulnStatus.OPEN, "Insecure Direct Object Reference")
    )
    val samplePrograms = listOf(
        BountyProgram("1", "Acme Corp", "HackerOne", "acme.com", "\$10,000", "Web"),
        BountyProgram("2", "TechCorp", "Bugcrowd", "techcorp.io", "\$5,000", "API"),
        BountyProgram("3", "StartupXYZ", "Intigriti", "startup.xyz", "\$2,500", "Mobile")
    )

    Scaffold(
        containerColor = DeepBlack,
        bottomBar = {
            NavigationBar(containerColor = SurfaceDark, tonalElevation = 0.dp) {
                listOf("Home" to Icons.Default.Home, "Domains" to Icons.Default.Language,
                    "Vulns" to Icons.Default.BugReport, "Tools" to Icons.Default.Build,
                    "Profile" to Icons.Default.Person).forEachIndexed { i, (label, icon) ->
                    NavigationBarItem(
                        selected = i == 0, onClick = {},
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ElectricBlue, selectedTextColor = ElectricBlue,
                            unselectedIconColor = TextMuted, unselectedTextColor = TextMuted,
                            indicatorColor = ElectricBlue.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier.size(56.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(ElectricBlue, NeonPurple))),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = Color.White)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(NeonPurple.copy(alpha = 0.1f), Color.Transparent)))
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Good morning,", color = TextSecondary, fontSize = 14.sp)
                        Text("HackerPro", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = TextPrimary)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = ElectricBlue)
                        }
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape)
                                .background(Brush.linearGradient(listOf(ElectricBlue, NeonPurple))),
                            contentAlignment = Alignment.Center
                        ) { Text("H", fontWeight = FontWeight.Bold, color = Color.White) }
                    }
                }
            }
            // Stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard("Scans", "42", Icons.Default.Search, ElectricBlue, Modifier.weight(1f))
                    StatCard("Targets", "3", Icons.Default.Flag, NeonPurple, Modifier.weight(1f))
                    StatCard("Findings", "3", Icons.Default.BugReport, CriticalRed, Modifier.weight(1f))
                }
            }
            // Quick Actions
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("Quick Actions", fontWeight = FontWeight.SemiBold, color = TextPrimary,
                        modifier = Modifier.padding(bottom = 12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf("Domain" to Icons.Default.Language, "Subdomains" to Icons.Default.AccountTree,
                            "Tools" to Icons.Default.Build, "Reports" to Icons.Default.Description
                        ).forEach { (label, icon) ->
                            Column(
                                modifier = Modifier.weight(1f).clip(RoundedCornerShape(14.dp))
                                    .background(CardDark).padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(icon, contentDescription = label, tint = ElectricBlue, modifier = Modifier.size(24.dp))
                                Text(label, fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                    }
                }
            }
            // Recent Findings header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Findings", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextPrimary)
                    TextButton(onClick = {}) { Text("See all", color = ElectricBlue, fontSize = 13.sp) }
                }
            }
            // Vuln items
            items(sampleVulns.size) { i ->
                val vuln = sampleVulns[i]
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp)).background(CardDark).padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SeverityBadge(vuln.severity)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(vuln.title, fontWeight = FontWeight.Medium, color = TextPrimary, fontSize = 14.sp)
                        Text(vuln.target, color = TextSecondary, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }
            }
            // Trending Programs header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Trending Programs", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextPrimary)
                    TextButton(onClick = {}) { Text("See all", color = ElectricBlue, fontSize = 13.sp) }
                }
            }
            // Program cards row
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(samplePrograms.size) { i ->
                        val p = samplePrograms[i]
                        GlassCard(modifier = Modifier.width(200.dp), glowColor = NeonPurple) {
                            Text(p.name, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 14.sp, maxLines = 1)
                            Spacer(Modifier.height(4.dp))
                            Text(p.platform, color = ElectricBlue, fontSize = 12.sp)
                            Spacer(Modifier.height(8.dp))
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(6.dp))
                                    .background(NeonGreen.copy(alpha = 0.1f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) { Text("Up to ${p.maxBounty}", color = NeonGreen, fontSize = 11.sp) }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Home Dashboard")
@Composable
fun HomeScreenPreview() {
    ReconixTheme { HomeScreenContent() }
}
