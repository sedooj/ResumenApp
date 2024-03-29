package com.sedooj.resumen.navigation.config

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object ScreensTransitions : DestinationStyle.Animated() {
    override val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)
        get() = {
            fadeIn(tween(500))
//            this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        }
    override val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)
        get() = {
            fadeOut(tween(500))
//            this.slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        }
    override val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)
        get() = {
            fadeIn(tween(500))
//            this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        }
    override val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)
        get() = {
            fadeOut(tween(500))
//            this.slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        }
}

object ConfirmationScreenTransitions : DestinationStyle.Animated() {
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