package com.hamza.data.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ameer Hamza on 03/09/2023
 **/
interface DispatchersProvider {
    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getMainImmediate(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}