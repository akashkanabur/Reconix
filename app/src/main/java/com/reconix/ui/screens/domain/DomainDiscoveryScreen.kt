package com.reconix.ui.screens.domain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reconix.domain.model.BountyProgram
import com.reconix.ui.components.*
import com.reconix.ui.theme.*
import com.reconix.viewmodel.DomainViewModel

private val platforms = listOf("All", "HackerOne", "Bugcrowd", "Intigriti", "YesWeHack")

@Composable
fun DomainDiscoveryScreen(onNavigateBack: () -> Unit, viewModel: DomainViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Domain Discovery", onNavigateBack)

        // Search bar
        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            placeholder = { Text("Search programs...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ElectricBlue) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricBlue,
                unfocusedBorderColor = TextMuted,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = CardDark,
                unfocusedContainerColor = CardDark
            ),
            singleLine = true
        )

        // Platform filters
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(platforms) { platform ->
                val isSelected = (platform == "All" && state.selectedPlatform == null) || platform == state.selectedPlatform
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.onPlatformFilter(if (platform == "All") null else platform) },
                    label = { Text(platform, fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue.copy(alpha = 0.2f),
                        selectedLabelColor = ElectricBlue,
                        containerColor = CardDark,
                        labelColor = TextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        selectedBorderColor = ElectricBlue,
                        borderColor = TextMuted
                    )
                )
            }
        }

        if (state.isLoading) {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(6) { ShimmerCard(modifier = Modifier.fillMaxWidth().height(100.dp)) }
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = CriticalRed, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text(state.error ?: "Unknown error", color = TextSecondary)
                    Spacer(Modifier.height(16.dp))
                    NeonButton("Retry", onClick = viewModel::search, modifier = Modifier.width(120.dp))
                }
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(state.programs) { program ->
                    ProgramListCard(
                        program = program,
                        isSaved = state.savedPrograms.any { it.id == program.id },
                        onToggleSave = { viewModel.toggleSave(program) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgramListCard(program: BountyProgram, isSaved: Boolean, onToggleSave: () -> Unit) {
    GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = if (isSaved) NeonPurple else ElectricBlue) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(program.name, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 15.sp)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(program.platform, color = ElectricBlue, fontSize = 12.sp)
                    Text("•", color = TextMuted)
                    Text(program.domain, color = TextSecondary, fontSize = 12.sp)
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonGreen.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text("Max: ${program.maxBounty}", color = NeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(ElectricBlue.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(program.scope, color = ElectricBlue, fontSize = 11.sp)
                    }
                }
            }
            IconButton(onClick = onToggleSave) {
                Icon(
                    if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save",
                    tint = if (isSaved) NeonPurple else TextMuted
                )
            }
        }
    }
}
