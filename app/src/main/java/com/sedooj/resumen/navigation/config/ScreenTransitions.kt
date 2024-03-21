package com.sedooj.resumen.navigation.config

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object ScreensTransitions : DestinationStyle.Animated() {
    override val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)
        get() = {
            this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        }
    override val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)
        get() = {
            this.slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        }
    override val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)
        get() = {
            this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        }
    override val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)
        get() = {
            this.slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        }
}