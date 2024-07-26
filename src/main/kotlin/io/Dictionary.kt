package io

import java.io.File
import java.io.IOException

class Dictionary(private val dictionaryLocation: String) {

    private val terms = getText(dictionaryLocation)

    private fun getText(location: String): List<String>? {
        return try {
            File(location)
                .bufferedReader()
                .readLines()
                .map { it }
        } catch (e: IOException){
            println(e.stackTrace)
            null
        }
    }

    fun getTerms(): List<String>?{
        return terms?.map {  it }
    }

}