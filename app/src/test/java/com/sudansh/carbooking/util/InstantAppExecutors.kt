package com.sudansh.carbooking.util

import com.sudansh.carbooking.data.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}