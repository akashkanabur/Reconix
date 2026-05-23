package com.reconix.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.reconix.ui.theme.ReconixTheme

@Preview(showBackground = true, showSystemUi = true, name = "Splash Screen")
@Composable
fun SplashScreenPreview() {
    ReconixTheme {
        SplashScreen(onFinished = {})
    }
}
