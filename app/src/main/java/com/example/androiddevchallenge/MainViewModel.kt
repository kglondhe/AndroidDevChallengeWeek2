package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _hour = MutableLiveData("00")
    private val _minute = MutableLiveData("00")
    private val _seconds = MutableLiveData("05")
    private val _isTimerStarted = MutableLiveData(false)

    val hour : LiveData<String> = _hour
    val minute : LiveData<String> = _minute
    val seconds : LiveData<String> = _seconds
    val timerStarted: LiveData<Boolean> = _isTimerStarted

    private lateinit var countDownTimer: CountDownTimer

    fun onHourChange(newHour: String) {
        _hour.value = newHour
    }

    fun onMinuteChange(newMinute: String) {
        _minute.value = newMinute
    }

    fun onSecondsChange(newSeconds: String) {
        _seconds.value = newSeconds
    }

    fun onTimerChanged(isTimerStarted: Boolean) {
        _isTimerStarted.value = isTimerStarted
        if (isTimerStarted) {
            val totalTime = calculateTimeInSeconds()
            countDownTimer = object : CountDownTimer(totalTime*1000, 1000) {
                override fun onFinish() {
                    _isTimerStarted.value = false
                }

                override fun onTick(p0: Long) {
                    val sec = _seconds.value?.toInt()
                    val hour = _hour.value?.toInt()
                    val min = _minute.value?.toInt()
                    if (sec != null && sec > 0) {
                        val newSec = sec.minus(1)
                        _seconds.value = if (newSec >= 10) newSec.toString() else "0$newSec"
                    } else if (min != null && min > 0) {
                        val newMin = min.minus(1)
                        _minute.value = if (newMin >= 10) newMin.toString() else "0$newMin"
                        _seconds.value = "59"
                    } else if (hour != null && hour > 0) {
                        val newHour = hour.minus(1)
                        _hour.value = if (newHour >= 10) newHour.toString() else "0$newHour"
                        _minute.value = "59"
                        _seconds.value = "59"
                    }

                }
            }
            countDownTimer.start()
        }
    }

    fun calculateTimeInSeconds(): Long {
        return (hour.value?.toLong() ?: 0) * 60 * 60 + (minute.value?.toLong()
            ?: 0) * 60 + (seconds.value?.toLong()
            ?: 0)
    }

}