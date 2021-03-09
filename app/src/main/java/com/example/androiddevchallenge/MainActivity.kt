/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(MainViewModel())
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(viewModel: MainViewModel) {
    val hour = viewModel.hour.observeAsState("00")
    val minute = viewModel.minute.observeAsState("00")
    val seconds = viewModel.seconds.observeAsState("05")
    val startTimer = viewModel.timerStarted.observeAsState(false)

    Column {
        TopAppBar(title = { Text(text = "Count Down!!") })
        Column(modifier = Modifier
            .background(color = MaterialTheme.colors.secondaryVariant)
            .padding(8.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
            Card(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
                if (!startTimer.value) {
                    PrepareTimer(hour = hour.value,
                        onHourChanged = { viewModel.onHourChange(it) },
                        min = minute.value,
                        onMinuteChanged = { viewModel.onMinuteChange(it) },
                        sec = seconds.value,
                        onSecondChanged = { viewModel.onSecondsChange(it) },
                        onTimerStarted = { viewModel.onTimerChanged(startTimer.value.xor(true)) })
                } else {
                    StartTimer(
                        hour = hour.value,
                        min = minute.value,
                        sec = seconds.value
                    )
                }
            }
        }
    }
}

@Composable
fun PrepareTimer(
    hour: String,
    onHourChanged: (String) -> Unit,
    min: String,
    onMinuteChanged: (String) -> Unit,
    sec: String,
    onSecondChanged: (String) -> Unit,
    onTimerStarted: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Set the timer", modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = hour,
                    onValueChange = onHourChanged,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp),
                    label = { Text(text = "HH") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = min,
                    onValueChange = onMinuteChanged,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp),
                    label = { Text(text = "MM") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = sec,
                    onValueChange = onSecondChanged,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp),
                    label = { Text(text = "SS") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1
                )
            }
            Button(modifier = Modifier.padding(8.dp), onClick = onTimerStarted) {
                Text(text = "Start")
            }
        }
    }
}


@Composable
fun StartTimer(
    hour: String,
    min: String,
    sec: String
) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = hour, modifier = Modifier.padding(8.dp), style = typography.h2)
                Text(text = ":", modifier = Modifier.padding(8.dp), style = typography.h2)
                Text(text = min, modifier = Modifier.padding(8.dp), style = typography.h2)
                Text(text = ":", modifier = Modifier.padding(8.dp), style = typography.h2)
                Text(text = sec, modifier = Modifier.padding(8.dp), style = typography.h2)
            }
            if (hour.toInt() == 0 && min.toInt() == 0 && sec.toInt() == 0) {
                Text(text = "Time up!!", modifier = Modifier.padding(8.dp), style = typography.h3)
            }
        }
    }
}


@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(MainViewModel())
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(MainViewModel())
    }
}
