package com.reconix.ui.screens.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.reconix.ui.theme.ReconixTheme

@Preview(showBackground = true, showSystemUi = true, name = "Notifications Screen")
@Composable
fun NotificationsScreenPreview() {
    ReconixTheme {
        NotificationsScreen(onNavigateBack = {})
    }
}
