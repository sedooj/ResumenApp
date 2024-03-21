package com.sedooj.resumen.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootGraph

@NavGraph<RootGraph>(
    route = "preferred_route",
    start = true,
)
annotation class AuthorisationGraph