package io

import java.io.File
import java.io.IOException
import java.util.Collections

class Dictionary(private val dictionaryLocation: String): Text<HashSet<String>?> {

    private val terms = getText(dictionaryLocation)

    override fun getText(location: String): HashSet<String>? {
        return try {
            File(location)
                .bufferedReader()
                .readLines()
                .toHashSet()
        } catch (e: IOException){
            println(e.stackTrace)
            null
        }
    }

    fun getTerms(): HashSet<String>?{
        return terms
    }

}