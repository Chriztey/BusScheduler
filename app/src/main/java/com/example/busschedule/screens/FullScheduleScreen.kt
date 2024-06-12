package com.example.busschedule.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.busschedule.R
import com.example.busschedule.data.AppViewModelProvider
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.navigation.NavigationDestination
import kotlinx.coroutines.launch

object FullScheduleDestination: NavigationDestination {
    override val route: String
        get() = "fullschedule"

    override val title: String
        get() = "Full Schedule"
}

@Composable
fun FullScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: BusScheduleViewModel = viewModel(factory = AppViewModelProvider.Factory),
    //viewModelDetail: BusScheduleDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onScheduleClick: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    addSchedule: () -> Unit
) {

    val fullSchedule by viewModel.busScheduleUiState.collectAsState()


    Box(
        modifier = Modifier.fillMaxSize(),
        //contentAlignment = Alignment.BottomEnd,
    ) {
        BusScheduleScreen(
            busSchedules = fullSchedule.itemList,
            onScheduleClick = onScheduleClick,
            contentPadding = contentPadding,
            modifier = modifier,
        )
        FloatingActionButton(
            modifier = Modifier
                .padding(
                    bottom = contentPadding
                    .calculateBottomPadding(),
                    end = 16.dp)
                .align(Alignment.BottomEnd),
            onClick = { addSchedule() },
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null
            )
        }
    }
}