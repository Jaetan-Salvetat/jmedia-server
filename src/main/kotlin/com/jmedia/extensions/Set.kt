package com.jmedia.extensions

import com.jmedia.models.local.medias.IMedia
import kotlin.reflect.full.createInstance

inline fun <reified T : IMedia> MutableSet<T>.getOrCreate(fromIndex: Int): T = try {
    elementAt(fromIndex)
} catch (exception: Exception) {
    val instance = T::class.createInstance()
    add(instance)
    instance
}

fun <T> MutableSet<T>.replaceAt(index: Int, newValue: T) = mapIndexed { i, value ->
    if (index == i) newValue else value
}.toMutableSet()