package com.reconix.ui.screens.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconix.ui.components.GlassCard
import com.reconix.ui.components.NeonButton
import com.reconix.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
    ) {
        // Animated background gradient
        val infiniteTransition = rememberInfiniteTransition(label = "bg")
        val gradientOffset by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(4000), RepeatMode.Reverse),
            label = "gradientOffset"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(NeonPurple.copy(alpha = 0.15f + gradientOffset * 0.05f), Color.Transparent),
                        radius = 600f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "RECONIX",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.horizontalGradient(listOf(ElectricBlue, NeonPurple))
                ),
                letterSpacing = 6.sp
            )
            Text("Welcome back, hacker", color = TextSecondary, modifier = Modifier.padding(top = 8.dp, bottom = 40.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
                Spacer(Modifier.height(24.dp))

                CyberTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(16.dp))
                CyberTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = { passwordVisible = !passwordVisible }
                )

                TextButton(
                    onClick = onNavigateToForgotPassword,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Forgot Password?", color = ElectricBlue, fontSize = 13.sp)
                }

                Spacer(Modifier.height(8.dp))
                NeonButton(
                    text = if (isLoading) "Signing in..." else "Sign In",
                    onClick = { isLoading = true; onLoginSuccess() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotBlank() && password.isNotBlank() && !isLoading
                )

                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f), color = TextMuted)
                    Text("  OR  ", color = TextMuted, fontSize = 12.sp)
                    Divider(modifier = Modifier.weight(1f), color = TextMuted)
                }
                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onLoginSuccess,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TextMuted)
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = ElectricBlue)
                    Spacer(Modifier.width(8.dp))
                    Text("Continue with Google")
                }
            }

            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account? ", color = TextSecondary)
                TextButton(onClick = onNavigateToRegister) {
                    Text("Sign Up", color = ElectricBlue, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(false) }

    val passwordStrength = when {
        password.length >= 12 && password.any { it.isDigit() } && password.any { !it.isLetterOrDigit() } -> 3
        password.length >= 8 && password.any { it.isDigit() } -> 2
        password.length >= 6 -> 1
        else -> 0
    }
    val strengthColor = listOf(CriticalRed, HighOrange, MediumYellow, NeonGreen)[passwordStrength.coerceIn(0, 3)]
    val strengthLabel = listOf("Weak", "Fair", "Good", "Strong")[passwordStrength.coerceIn(0, 3)]

    Box(
        modifier = Modifier.fillMaxSize().background(DeepBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = ElectricBlue)
                }
                Text("Create Account", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = TextPrimary)
            }
            Spacer(Modifier.height(24.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                CyberTextField(username, { username = it }, "Username", Icons.Default.Person)
                Spacer(Modifier.height(16.dp))
                CyberTextField(email, { email = it }, "Email", Icons.Default.Email, KeyboardType.Email)
                Spacer(Modifier.height(16.dp))
                CyberTextField(
                    value = password, onValueChange = { password = it },
                    label = "Password", leadingIcon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password, isPassword = true,
                    passwordVisible = passwordVisible, onPasswordToggle = { passwordVisible = !passwordVisible }
                )

                if (password.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(4) { i ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (i <= passwordStrength) strengthColor else TextMuted)
                            )
                        }
                        Text(strengthLabel, fontSize = 11.sp, color = strengthColor)
                    }
                }

                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = agreedToTerms,
                        onCheckedChange = { agreedToTerms = it },
                        colors = CheckboxDefaults.colors(checkedColor = ElectricBlue)
                    )
                    Text("I agree to the Terms of Service and Privacy Policy", fontSize = 13.sp, color = TextSecondary)
                }

                Spacer(Modifier.height(16.dp))
                NeonButton(
                    text = "Create Account",
                    onClick = onRegisterSuccess,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = username.isNotBlank() && email.isNotBlank() && password.length >= 6 && agreedToTerms
                )
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(onNavigateBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(DeepBlack)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = ElectricBlue)
                    }
                    Text("Reset Password", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
                }
                Spacer(Modifier.height(16.dp))
                if (sent) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NeonGreen, modifier = Modifier.size(48.dp).align(Alignment.CenterHorizontally))
                    Spacer(Modifier.height(12.dp))
                    Text("Reset link sent to $email", color = TextSecondary, textAlign = TextAlign.Center)
                } else {
                    Text("Enter your email to receive a reset link.", color = TextSecondary)
                    Spacer(Modifier.height(16.dp))
                    CyberTextField(email, { email = it }, "Email", Icons.Default.Email, KeyboardType.Email)
                    Spacer(Modifier.height(16.dp))
                    NeonButton("Send Reset Link", onClick = { sent = true }, modifier = Modifier.fillMaxWidth(), enabled = email.isNotBlank())
                }
            }
        }
    }
}

@Composable
fun CyberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondary) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = ElectricBlue) },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { onPasswordToggle?.invoke() }) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricBlue,
            unfocusedBorderColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = ElectricBlue,
            focusedContainerColor = CardDarkElevated,
            unfocusedContainerColor = CardDark
        ),
        singleLine = true
    )
}
