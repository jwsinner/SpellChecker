package io

import java.io.File
import java.io.IOException

class SourceText(private val sourceTextLocation: String): Text<List<List<String>>?> {

    private val lines = getText(sourceTextLocation)

     override fun getText(location: String): List<List<String>>? {
        return try {
            File(location)
                .bufferedReader()
                .readLines()
                .map { line -> line.replace(Regex("[^a-zA-Z ]"), "").lowercase().split(" ")}
        } catch (e: IOException){
            e.printStackTrace()
            null
        }
    }

    fun getLines(): List<List<String>>?{
        return lines
    }

}