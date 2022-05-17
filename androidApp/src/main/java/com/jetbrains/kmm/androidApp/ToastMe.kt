package com.jetbrains.kmm.androidApp

import android.app.Activity
import android.widget.Toast
import kotlin.random.Random

class ToastMe : Runnable {
    private val activity: Activity
    private val rand: Random = Random(10000)

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun run() {
        while (true) {
            val threadId = Thread.currentThread().id
            Thread.sleep(10000L)
            activity.runOnUiThread{
                Toast.makeText(activity, String.format("[Thread/%d] - (%d) message every 10s", threadId, rand.nextInt()), Toast.LENGTH_LONG).show()
            }
        }
    }
}


