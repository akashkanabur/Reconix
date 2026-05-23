package com.reconix.ui.screens.reports

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.*
import com.reconix.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

data class ReportItem(val id: Long, val title: String, val target: String, val findingsCount: Int, val createdAt: Long)

@Composable
fun ReportsScreen(onNavigateBack: () -> Unit) {
    val sampleReports = remember {
        listOf(
            ReportItem(1, "HackerOne - Acme Corp", "acme.com", 5, System.currentTimeMillis() - 86400000),
            ReportItem(2, "Bugcrowd - TechCorp", "techcorp.io", 3, System.currentTimeMillis() - 172800000),
            ReportItem(3, "Intigriti - StartupXYZ", "startupxyz.com", 8, System.currentTimeMillis() - 259200000)
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        CyberTopBar("Reports", onNavigateBack)

        if (sampleReports.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Description, contentDescription = null, tint = TextMuted, modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(12.dp))
                    Text("No reports yet", color = TextSecondary)
                }
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(sampleReports) { report ->
                    ReportCard(report)
                }
            }
        }
    }
}

@Composable
private fun ReportCard(report: ReportItem) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = ElectricBlue) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ElectricBlue.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Description, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(report.title, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 14.sp)
                Text(report.target, color = ElectricBlue, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("${report.findingsCount} findings", color = TextSecondary, fontSize = 12.sp)
                    Text(dateFormat.format(Date(report.createdAt)), color = TextMuted, fontSize = 12.sp)
                }
            }
            Row {
                IconButton(onClick = { /* share */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = TextSecondary)
                }
                IconButton(onClick = { /* download */ }) {
                    Icon(Icons.Default.Download, contentDescription = "Download", tint = ElectricBlue)
                }
            }
        }
    }
}
