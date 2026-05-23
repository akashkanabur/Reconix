package com.reconix.ui.screens.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.reconix.ui.theme.ReconixTheme

@Preview(showBackground = true, showSystemUi = true, name = "Reports Screen")
@Composable
fun ReportsScreenPreview() {
    ReconixTheme {
        ReportsScreen(onNavigateBack = {})
    }
}
