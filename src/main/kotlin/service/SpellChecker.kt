package service

import io.Dictionary
import io.SourceText
import model.Suggestion
import util.DictionaryTrie
import java.util.PriorityQueue
import java.util.SortedMap
import java.util.stream.Collectors

class SpellChecker(private val dictionary: Dictionary, private val sourceText: SourceText) {

    /**
     * Returns a map of words not found in the provided dictionary (misspelled)
     * The key is a [Pair] with the first value being the row, and the second value being the column
     */
    fun getMisspelledWords(): HashMap<Pair<Int, Int>, String> {
        val terms = dictionary.getTerms() ?: return hashMapOf()
        val text = sourceText.getWordsWithoutPunctuation() ?: return hashMapOf()
        return text.entries.parallelStream()
            .filter { entry ->
                !terms.contains(entry.value) }
            .collect(Collectors.toMap(
                {it.key},
                {it.value},
                {oldValue, _ -> oldValue},
                ::HashMap
            ))
    }

    /**
     * Returns a map of suggested words based on the misspelled [word]
     * sorted first by the Levenshtein distance, then by the index of the suggested word
     */
    fun getSuggestions(word: String): PriorityQueue<Suggestion>{
        val dictTrie = DictionaryTrie(dictionary)
        val suggestions = PriorityQueue<Suggestion>(Comparator { o1, o2 ->
            when{
                o1.distance != o2.distance -> o1.distance - o2.distance
                else -> o1.word.compareTo(o2.word)
            }

        })
        dictTrie.loadDictionary()
        dictTrie.findWordsWithPrefix(word.substring(0,1))
            .forEachIndexed {index, dictWord ->
                val distance = getEditDistance(word, dictWord)
                if(distance <= 2){
                    val suggestion = Suggestion(distance, dictWord)
                    suggestions.add(suggestion)
                }
            }
        return suggestions
    }

    /**
     * Returns the Levenshtein distance between the [first] string and [second] string provided.
     *
     */
    fun getEditDistance(first: String, second: String): Int{
        val distance = Array(first.length + 1){IntArray(second.length + 1)}

        for(i in 0..first.length)
            distance[i][0] = i
        for(j in 0..second.length)
            distance[0][j] = j

        var cost = 0

        for(i in 1..first.length){
            for(j in 1..second.length){
                cost = if(first[i - 1] == second[j - 1])
                    0
                else
                    1
                distance[i][j] = minOf(
                    distance[i - 1][j - 1] + cost,
                    distance[i - 1][j] + 1,
                    distance[i][j - 1] + 1
                )
                if(i > 1 && j > 1 && first[i - 1] == second[j - 2] && first[i - 2] == second[j - 1])
                        distance[i][j] = minOf(distance[i][j], distance[i - 2][j - 2] + 1)
            }
        }
        return distance[first.length][second.length]
    }

    /**
     * A void print function that displays the row, column, the words before and after the typo
     * as well as a list of suggested words indented based on Levenshtein distance
     */
    fun displayErrorsWithSuggestions(){
        val errors = getMisspelledWords()
        if(errors.isEmpty()){
            println("No spelling errors found")
            return
        }
        val allWords = sourceText.getWords()
        errors.entries.stream()
            .forEach { word ->
                val key = word.key
                val first = key.first
                val second = key.second
                println("Row: $first, Col: $second - ${allWords?.get(Pair(first, second - 1))} ${allWords?.get(key)} ${allWords?.get(Pair(first, second + 1))}")
                getSuggestions(word.value).forEach { println("${if(it.distance == 1)"\t\t" else "\t"}| ${it.word}") }
            }

    }
}