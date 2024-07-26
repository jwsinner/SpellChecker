package io

import java.io.File
import java.io.IOException

class SourceText(private val sourceTextLocation: String): Text<Map<Pair<Int, Int>,String>?> {

    private val words = getText(sourceTextLocation)
    private val wordsWithoutPunctuation = getTextWithoutPunctuation(sourceTextLocation)

     override fun getText(location: String): Map<Pair<Int, Int>, String>? {
         val map = HashMap<Pair<Int, Int>, String>()
         try {
            val lines = File(location).readLines()
            lines.forEachIndexed { index, line ->
                line.split(" ")
                    .forEachIndexed {i, word ->
                        map[Pair(index, i)] = word
                    }
            }
        } catch (e: IOException){
            e.printStackTrace()
            return null
        }
         return map
    }

    private fun getTextWithoutPunctuation(location: String): Map<Pair<Int, Int>, String>? {
        val map = HashMap<Pair<Int, Int>, String>()
        try {
            val lines = File(location).readLines()
            lines.forEachIndexed { index, line ->
                line.replace(Regex("[^a-zA-Z ]"), "")
                    .lowercase()
                    .split(" ")
                    .forEachIndexed {i, word ->
                        map[Pair(index, i)] = word
                    }
            }
        } catch (e: IOException){
            e.printStackTrace()
            return null
        }
        return map
    }

    fun getWords(): Map<Pair<Int, Int>, String>?{
        return words
    }
    fun getWordsWithoutPunctuation(): Map<Pair<Int, Int>, String>?{
        return wordsWithoutPunctuation
    }
}