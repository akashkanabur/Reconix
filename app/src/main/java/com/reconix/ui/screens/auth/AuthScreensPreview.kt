package com.reconix.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.reconix.ui.theme.ReconixTheme

@Preview(showBackground = true, showSystemUi = true, name = "Login Screen")
@Composable
fun LoginScreenPreview() {
    ReconixTheme {
        LoginScreen(
            onLoginSuccess = {},
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Register Screen")
@Composable
fun RegisterScreenPreview() {
    ReconixTheme {
        RegisterScreen(
            onRegisterSuccess = {},
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Forgot Password Screen")
@Composable
fun ForgotPasswordScreenPreview() {
    ReconixTheme {
        ForgotPasswordScreen(onNavigateBack = {})
    }
}
