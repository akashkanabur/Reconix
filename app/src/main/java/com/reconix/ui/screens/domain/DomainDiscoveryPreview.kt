package com.reconix.ui.screens.domain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.reconix.domain.model.BountyProgram
import com.reconix.ui.components.*
import com.reconix.ui.theme.*

private val previewPrograms = listOf(
    BountyProgram("1", "Acme Corporation", "HackerOne", "acme.com", "\$10,000", "Web", false),
    BountyProgram("2", "TechCorp Inc", "Bugcrowd", "techcorp.io", "\$5,000", "API", true),
    BountyProgram("3", "StartupXYZ", "Intigriti", "startup.xyz", "\$2,500", "Mobile", false),
    BountyProgram("4", "GlobalBank", "YesWeHack", "globalbank.com", "\$20,000", "Web + API", true),
    BountyProgram("5", "CloudSystems", "HackerOne", "cloudsys.io", "\$8,000", "Cloud", false)
)
private val previewPlatforms = listOf("All", "HackerOne", "Bugcrowd", "Intigriti", "YesWeHack")

@Composable
private fun DomainDiscoveryContent() {
    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Domain Discovery", onNavigateBack = {})

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search programs...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ElectricBlue) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricBlue, unfocusedBorderColor = TextMuted,
                focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                focusedContainerColor = CardDark, unfocusedContainerColor = CardDark
            ),
            singleLine = true
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(previewPlatforms.size) { i ->
                val platform = previewPlatforms[i]
                FilterChip(
                    selected = i == 0,
                    onClick = {},
                    label = { Text(platform, fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue.copy(alpha = 0.2f),
                        selectedLabelColor = ElectricBlue,
                        containerColor = CardDark, labelColor = TextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true, selected = i == 0,
                        selectedBorderColor = ElectricBlue, borderColor = TextMuted
                    )
                )
            }
        }

        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(previewPrograms.size) { i ->
                val program = previewPrograms[i]
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = if (program.isSaved) NeonPurple else ElectricBlue
                ) {
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
                                    modifier = Modifier.clip(RoundedCornerShape(6.dp))
                                        .background(NeonGreen.copy(alpha = 0.1f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) { Text("Max: ${program.maxBounty}", color = NeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Medium) }
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(6.dp))
                                        .background(ElectricBlue.copy(alpha = 0.1f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) { Text(program.scope, color = ElectricBlue, fontSize = 11.sp) }
                            }
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                if (program.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save",
                                tint = if (program.isSaved) NeonPurple else TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Domain Discovery")
@Composable
fun DomainDiscoveryPreview() {
    ReconixTheme { DomainDiscoveryContent() }
}
