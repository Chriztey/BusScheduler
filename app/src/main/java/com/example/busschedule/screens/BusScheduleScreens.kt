/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.busschedule.R
import com.example.busschedule.components.DeleteConfirmationDialog
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.navigation.BusScheduleNavHost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

enum class BusScheduleScreens {
    FullSchedule,
    RouteSchedule,
    EntrySchedule
}

@Composable
fun BusScheduleApp(

) {
    val navController = rememberNavController()

    val fullScheduleTitle = stringResource(R.string.full_schedule)
    var topAppBarTitle by remember { mutableStateOf(fullScheduleTitle) }

    val onBackHandler = {
        topAppBarTitle = fullScheduleTitle
        navController.navigateUp()
    }

    Scaffold(
        topBar = {
            BusScheduleTopAppBar(
                title = topAppBarTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                onBackClick = { onBackHandler() }
            )
        }
    ) { innerPadding ->
        BusScheduleNavHost(
            navController = navController,
            contentPadding = innerPadding,
            )

    }
}





@Composable
fun BusScheduleScreen(
    busSchedules: List<BusSchedule>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    stopName: String? = null,
    onScheduleClick: ((String) -> Unit)? = null,
    onScheduleDeleteClick: ((BusSchedule) -> Unit)? = null,
    onScheduleEditClick: ((Int) -> Unit)? = null
) {
    val stopNameText = if (stopName == null) {
        stringResource(R.string.stop_name)
    } else {
        "$stopName ${stringResource(R.string.route_stop_name)}"
    }
    val layoutDirection = LocalLayoutDirection.current

    Column(
        modifier = modifier.padding(
            start = contentPadding.calculateStartPadding(layoutDirection),
            end = contentPadding.calculateEndPadding(layoutDirection),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                )
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stopNameText)
            Text(stringResource(R.string.arrival_time))
        }
        Divider()
        BusScheduleDetails(
            contentPadding = PaddingValues(
                bottom = contentPadding.calculateBottomPadding()
            ),
            busSchedules = busSchedules,
            onScheduleClick = onScheduleClick,
            onScheduleDeleteClick = onScheduleDeleteClick,
            onScheduleEditClick = onScheduleEditClick
        )


    }
}

/*
 * Composable for BusScheduleDetails which show list of bus schedule
 * When [onScheduleClick] is null, [stopName] is replaced with placeholder
 * as it is assumed [stopName]s are the same as shown
 * in the list heading display in [BusScheduleScreen]
 */
@Composable
fun BusScheduleDetails(
    busSchedules: List<BusSchedule>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onScheduleClick: ((String) -> Unit)? = null,
    onScheduleDeleteClick: ((BusSchedule) -> Unit)? = null,
    onScheduleEditClick: ((Int) -> Unit)? = null
) {

    var openDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {


        items(
            items = busSchedules,
            key = { busSchedule -> busSchedule.id }
        ) { schedule ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true) {
                            if (onScheduleClick != null) {
                                onScheduleClick.invoke(schedule.stopName)
                            } else {
                                onScheduleEditClick?.invoke(schedule.id)
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (onScheduleClick == null) {
                        Text(
                            text = "--",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = dimensionResource(R.dimen.font_large).value.sp,
                                fontWeight = FontWeight(300)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            text = schedule.stopName,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = dimensionResource(R.dimen.font_large).value.sp,
                                fontWeight = FontWeight(300)
                            )
                        )
                    }

                    Text(

                        text = formatTimeFromMillis(schedule.arrivalTimeInMillis.toLong()),

                        //                    text = SimpleDateFormat("hh:mm a", Locale.getDefault())
                        //                        .format(Date(schedule.arrivalTimeInMillis.toLong())),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = dimensionResource(R.dimen.font_large).value.sp,
                            fontWeight = FontWeight(600)
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(2f)
                    )
                }
                if (onScheduleDeleteClick != null) {
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.End),
                        onClick = {
                            openDialog = true

                        }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                    }

                    DeleteConfirmationDialog(
                        onConfirm = { onScheduleDeleteClick.invoke(schedule) },
                        openDialog = openDialog,
                        onDismissRequest = { openDialog = false })

                }
            }
        }
    }
}

fun formatTimeFromMillis(timeInMillis: Long): String {
    val timeZone = TimeZone.getDefault()
    val offset = timeZone.rawOffset
    val date = Date(timeInMillis - offset)
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScheduleTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(
                            R.string.back
                        )
                    )
                }
            },
            modifier = modifier
        )
    } else {
        TopAppBar(
            title = { Text(title) },
            modifier = modifier
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FullScheduleScreenPreview() {
//    BusScheduleTheme {
//        FullScheduleScreen(
//            busSchedules = List(3) { index ->
//                BusSchedule(
//                    index,
//                    "Main Street",
//                    111111
//                )
//            },
//            onScheduleClick = {},
//            addSchedule = {}
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RouteScheduleScreenPreview() {
//    BusScheduleTheme {
//        RouteScheduleScreen(
//            stopName = "Main Street",
//            busSchedules = List(3) { index ->
//                BusSchedule(
//                    index,
//                    "Main Street",
//                    111111
//                )
//            }
//        )
//    }
//}
