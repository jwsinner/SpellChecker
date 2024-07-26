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
}