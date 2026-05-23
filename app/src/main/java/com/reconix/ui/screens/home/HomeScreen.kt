package com.reconix.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.reconix.domain.model.BountyProgram
import com.reconix.domain.model.Vulnerability
import com.reconix.navigation.Screen
import com.reconix.ui.components.*
import com.reconix.ui.theme.*
import com.reconix.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = DeepBlack,
        bottomBar = { ReconixBottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AiAssistant.route) },
                containerColor = Color.Transparent,
                contentColor = ElectricBlue,
                modifier = Modifier
                    .background(
                        Brush.radialGradient(listOf(NeonPurple.copy(alpha = 0.3f), Color.Transparent)),
                        CircleShape
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            Brush.linearGradient(listOf(ElectricBlue, NeonPurple)),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "AI Assistant", tint = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { HomeHeader(state.username, navController) }
            item { StatsRow(state) }
            item { QuickActionsSection(navController) }
            item {
                SectionHeader("Recent Findings", onSeeAll = { navController.navigate(Screen.VulnerabilityTracker.route) })
            }
            if (state.isLoading) {
                items(3) { ShimmerCard(modifier = Modifier.fillMaxWidth().height(80.dp).padding(horizontal = 16.dp, vertical = 4.dp)) }
            } else {
                items(state.recentVulnerabilities) { VulnListItem(it) }
            }
            item {
                SectionHeader("Trending Programs", onSeeAll = { navController.navigate(Screen.DomainDiscovery.route) })
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.isLoading) {
                        items(3) { ShimmerCard(modifier = Modifier.width(200.dp).height(120.dp)) }
                    } else {
                        items(state.trendingPrograms) { ProgramCard(it) }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(username: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(NeonPurple.copy(alpha = 0.1f), Color.Transparent))
            )
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Good morning,", color = TextSecondary, fontSize = 14.sp)
            Text(username, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = TextPrimary)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = ElectricBlue)
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(ElectricBlue, NeonPurple)))
                    .clickable { navController.navigate(Screen.Profile.route) },
                contentAlignment = Alignment.Center
            ) {
                Text("H", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
private fun StatsRow(state: com.reconix.viewmodel.HomeUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard("Scans", state.totalScans.toString(), Icons.Default.Search, ElectricBlue, Modifier.weight(1f))
        StatCard("Targets", state.savedTargets.toString(), Icons.Default.Flag, NeonPurple, Modifier.weight(1f))
        StatCard("Findings", state.openFindings.toString(), Icons.Default.BugReport, CriticalRed, Modifier.weight(1f))
    }
}

@Composable
private fun QuickActionsSection(navController: NavController) {
    val actions = listOf(
        Triple("Domain", Icons.Default.Language, Screen.DomainDiscovery.route),
        Triple("Subdomains", Icons.Default.AccountTree, Screen.SubdomainEnum.route),
        Triple("Tools", Icons.Default.Build, Screen.ReconTools.route),
        Triple("Reports", Icons.Default.Description, Screen.Reports.route)
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text("Quick Actions", fontWeight = FontWeight.SemiBold, color = TextPrimary, modifier = Modifier.padding(bottom = 12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            actions.forEach { (label, icon, route) ->
                QuickActionItem(label, icon, onClick = { navController.navigate(route) }, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun QuickActionItem(label: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(icon, contentDescription = label, tint = ElectricBlue, modifier = Modifier.size(24.dp))
        Text(label, fontSize = 11.sp, color = TextSecondary)
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextPrimary)
        TextButton(onClick = onSeeAll) { Text("See all", color = ElectricBlue, fontSize = 13.sp) }
    }
}

@Composable
private fun VulnListItem(vuln: Vulnerability) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardDark)
            .padding(12.dp),
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

@Composable
private fun ProgramCard(program: BountyProgram) {
    GlassCard(modifier = Modifier.width(200.dp), glowColor = NeonPurple) {
        Text(program.name, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 14.sp, maxLines = 1)
        Spacer(Modifier.height(4.dp))
        Text(program.platform, color = ElectricBlue, fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(NeonGreen.copy(alpha = 0.1f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text("Up to ${program.maxBounty}", color = NeonGreen, fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun ReconixBottomBar(navController: NavController) {
    val items = listOf(
        Triple("Home", Icons.Default.Home, Screen.Home.route),
        Triple("Domains", Icons.Default.Language, Screen.DomainDiscovery.route),
        Triple("Vulns", Icons.Default.BugReport, Screen.VulnerabilityTracker.route),
        Triple("Tools", Icons.Default.Build, Screen.ReconTools.route),
        Triple("Profile", Icons.Default.Person, Screen.Profile.route)
    )
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar(containerColor = SurfaceDark, tonalElevation = 0.dp) {
        items.forEach { (label, icon, route) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ElectricBlue,
                    selectedTextColor = ElectricBlue,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted,
                    indicatorColor = ElectricBlue.copy(alpha = 0.1f)
                )
            )
        }
    }
}
