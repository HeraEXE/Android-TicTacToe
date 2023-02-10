package net.herasevyan.tictactoe.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    val history = listOf(
        "0,0,1,2,1,0,1,2,0",
        "0,0,1,2,1,0,1,2,0",
        "0,0,1,2,1,0,1,2,0",
        "0,0,1,2,1,0,1,2,0",
        "0,0,1,2,1,0,1,2,0",
        "0,0,1,2,1,0,1,2,0",
        "0,0,1,2,1,0,1,2,0"
    )
}