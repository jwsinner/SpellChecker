package service

import io.Dictionary
import io.SourceText
import util.DictionaryTrie
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
    fun getSuggestions(word: String): SortedMap<Pair<Int, Int>, String>{
        val dictTrie = DictionaryTrie(dictionary)
        val map = hashMapOf<Pair<Int, Int>, String>()
        dictTrie.loadDictionary()
        dictTrie.findWordsWithPrefix(word.substring(0,1))
            .forEachIndexed {index, dictWord ->
                val distance = getEditDistance(word, dictWord)
                if(distance <= 2)
                    map[Pair(distance, index)] = dictWord
            }
        return map.toSortedMap(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })
    }

    /**
     * Returns the Levenshtein distance between theh [first] string and [second] string provided.
     *
     */
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
                getSuggestions(word.value).forEach { println("${if(it.key.first == 1)"\t\t" else "\t"}| ${it.value}") }
            }

    }
}