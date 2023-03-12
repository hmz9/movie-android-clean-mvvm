package com.hamza.data.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Ameer Hamza on 03/09/2023
 */
class DiskExecutor : Executor {

    private val diskExecutor: Executor = Executors.newSingleThreadExecutor()

    override fun execute(runnable: Runnable) {
        diskExecutor.execute(runnable)
    }
}