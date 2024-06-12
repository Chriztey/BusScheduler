package com.example.busschedule.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.busschedule.screens.BusScheduleEditViewModel
import com.example.busschedule.screens.BusScheduleNewEntryScreens
import com.example.busschedule.screens.BusScheduleScreens
import com.example.busschedule.screens.EditScheduleDestination
import com.example.busschedule.screens.EditScheduleScreen
import com.example.busschedule.screens.FullScheduleDestination
import com.example.busschedule.screens.FullScheduleScreen
import com.example.busschedule.screens.RouteScheduleDestination
import com.example.busschedule.screens.RouteScheduleScreen

@Composable
fun BusScheduleNavHost(
    navController: NavHostController,
    contentPadding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = FullScheduleDestination.route
    ) {
        composable(FullScheduleDestination.route) {
            FullScheduleScreen(
                contentPadding = contentPadding,
                onScheduleClick = { busStopName ->
                    RouteScheduleDestination.title = busStopName
                    navController.navigate(
                        "${RouteScheduleDestination.route}/${busStopName}"
                                  )
//                    busStopName ->
//                        navController.navigate(
//                            "${BusScheduleScreens.RouteSchedule.name}/$busStopName"
//                        )
//                    topAppBarTitle = busStopName
                },
                addSchedule = {
                    navController.navigate(BusScheduleScreens.EntrySchedule.name)
                }
            )
        }

        composable(BusScheduleScreens.EntrySchedule.name) {
            BusScheduleNewEntryScreens(
                navigateBack = navController::navigateUp,
                contentPadding = contentPadding
            )
        }

        composable(
            route = RouteScheduleDestination.routeWithArgs,
            arguments = listOf(navArgument(RouteScheduleDestination.busRouteArg) { type = NavType.StringType })
        ) {
            RouteScheduleScreen(
                contentPadding = contentPadding,
                onBack = navController::navigateUp,
                onScheduleEditClick = {
                    navController.navigate("${EditScheduleDestination.route}/${it}") }
            )

        }

        composable(
            route = EditScheduleDestination.routeWithArgs,
            arguments = listOf(navArgument(EditScheduleDestination.EditRouteArg) { type = NavType.IntType })
        ) {
            EditScheduleScreen(
                contentPadding = contentPadding,
                navigateBack = navController::navigateUp
                )

        }
    }

}