package com.example.busschedule.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.busschedule.data.AppViewModelProvider
import com.example.busschedule.navigation.NavigationDestination
import kotlinx.coroutines.launch

object RouteScheduleDestination: NavigationDestination {
    override val route: String
        get() = "routeschedule"

    override var title: String = ""

    const val busRouteArg = "busRoute"
    val routeWithArgs = "$route/{$busRouteArg}"
}

@Composable
fun RouteScheduleScreen(
    viewModel: BusScheduleDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onBack: () -> Unit = {},
    onScheduleEditClick : (Int) -> Unit

) {
    val routeSchedule by viewModel.busRouteUiState.collectAsState()
    val scope = rememberCoroutineScope()

    BackHandler { onBack() }
    BusScheduleScreen(
        busSchedules = routeSchedule.schedule,
        modifier = modifier,
        contentPadding = contentPadding,
        stopName = viewModel.route,
        onScheduleDeleteClick = {
            scope.launch {
                viewModel.deleteSchedule(it)
                onBack()
            }
        },
        onScheduleEditClick = onScheduleEditClick

    )
}