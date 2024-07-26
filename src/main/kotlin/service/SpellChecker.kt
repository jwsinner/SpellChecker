package service

import io.Dictionary
import io.SourceText
import java.util.stream.Collectors
import kotlin.streams.toList

class SpellChecker(private val dictionary: Dictionary, private val sourceText: SourceText) {

    private val misspelledWords: MutableList<String> = getMisspelledWords()

    fun getMisspelledWords(): MutableList<String>{
        val terms = dictionary.getTerms() ?: return mutableListOf()
        val text = sourceText.getLines() ?: return mutableListOf()
        return text.parallelStream()
            .flatMap{line ->
                line.parallelStream()
                    .filter { !terms.contains(it) }
                    .map { it }
            }
            .collect(Collectors.toList())
    }

//    fun getSuggestions(): List<String>{
//
//    }

    fun getEditDistance(first: String, second: String): Int{
        val distance = Array(first.length + 1){IntArray(second.length + 1)}

        for(i in 0..first.length){
            for(j in 0..second.length){
                when {
                    i == 0 -> distance[i][j] = j
                    j == 0 -> distance[i][j] = i
                    first[i - 1] == second[j - 1] -> distance[i][j] = distance[i - 1][j - 1]
                    else -> distance[i][j] = 1 + minOf(distance[i - 1][j - 1], distance[i - 1][j], distance[i][j - 1])
                }
            }
        }
        return distance[first.length][second.length]
    }
}