package com.jmedia.extensions

import com.jmedia.models.local.medias.IMedia
import kotlin.reflect.full.createInstance

/**
 * Get a [IMedia] instance from a [MutableSet]. Create it if not exist
 *
 * @param fromIndex [Int]
 * @return [T]
 */
inline fun <reified T : IMedia> MutableSet<T>.getOrCreate(fromIndex: Int): T = try {
    elementAt(fromIndex)
} catch (exception: Exception) {
    val instance = T::class.createInstance()
    add(instance)
    instance
}

/**
 * Replace an object form a [MutableSet]
 *
 * @param index [Int]
 * @param newValue [T]
 * @return [MutableSet] of [T] with the item replaced
 */
fun <T> MutableSet<T>.replaceAt(index: Int, newValue: T) = mapIndexed { i, value ->
    if (index == i) newValue else value
}.toMutableSet()