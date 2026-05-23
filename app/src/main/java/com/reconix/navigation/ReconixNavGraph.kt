package com.reconix.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reconix.ui.screens.ai.AiAssistantScreen
import com.reconix.ui.screens.auth.ForgotPasswordScreen
import com.reconix.ui.screens.auth.LoginScreen
import com.reconix.ui.screens.auth.RegisterScreen
import com.reconix.ui.screens.domain.DomainDiscoveryScreen
import com.reconix.ui.screens.home.HomeScreen
import com.reconix.ui.screens.notifications.NotificationsScreen
import com.reconix.ui.screens.onboarding.OnboardingScreen
import com.reconix.ui.screens.premium.PremiumScreen
import com.reconix.ui.screens.profile.ProfileScreen
import com.reconix.ui.screens.reports.ReportsScreen
import com.reconix.ui.screens.settings.SettingsScreen
import com.reconix.ui.screens.splash.SplashScreen
import com.reconix.ui.screens.subdomain.SubdomainEnumScreen
import com.reconix.ui.screens.tools.DnsLookupScreen
import com.reconix.ui.screens.tools.HttpHeadersScreen
import com.reconix.ui.screens.tools.PortScannerScreen
import com.reconix.ui.screens.tools.ReconToolsScreen
import com.reconix.ui.screens.tools.SslCheckerScreen
import com.reconix.ui.screens.tools.TechDetectorScreen
import com.reconix.ui.screens.tools.WhoisLookupScreen
import com.reconix.ui.screens.vulnerability.VulnerabilityTrackerScreen

@Composable
fun ReconixNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            fadeIn(tween(300)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        exitTransition = {
            fadeOut(tween(300)) + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        popEnterTransition = {
            fadeIn(tween(300)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popExitTransition = {
            fadeOut(tween(300)) + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinished = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.DomainDiscovery.route) {
            DomainDiscoveryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.SubdomainEnum.route) {
            SubdomainEnumScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.VulnerabilityTracker.route) {
            VulnerabilityTrackerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.ReconTools.route) {
            ReconToolsScreen(
                onNavigateBack = { navController.popBackStack() },
                onToolSelected = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.Reports.route) {
            ReportsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToPremium = { navController.navigate(Screen.Premium.route) }
            )
        }
        composable(Screen.Premium.route) {
            PremiumScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AiAssistant.route) {
            AiAssistantScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.WhoisLookup.route) {
            WhoisLookupScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.DnsLookup.route) {
            DnsLookupScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.HttpHeaders.route) {
            HttpHeadersScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.PortScanner.route) {
            PortScannerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.TechDetector.route) {
            TechDetectorScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.SslChecker.route) {
            SslCheckerScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
