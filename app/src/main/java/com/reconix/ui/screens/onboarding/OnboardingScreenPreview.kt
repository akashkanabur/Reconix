package com.reconix.ui.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.reconix.ui.theme.ReconixTheme

@Preview(showBackground = true, showSystemUi = true, name = "Onboarding Screen")
@Composable
fun OnboardingScreenPreview() {
    ReconixTheme {
        OnboardingScreen(onFinished = {})
    }
}
