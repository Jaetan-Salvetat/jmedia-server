package com.jmedia.utils

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendedTransaction(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }