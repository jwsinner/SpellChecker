package service

import io.Dictionary
import io.SourceText
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SpellCheckerTest {

    private val dictionary = Dictionary("dictionaryTest.txt")
    private val source = SourceText("sourceTextTest.txt")

    @Test
    fun `getMisspelledWords returns a list of words not contained in dictionary`(){
        val checker = SpellChecker(dictionary, source)
        val misspelled = checker.getMisspelledWords()
        assertEquals(misspelled.size, 9)
    }

    @Test
    fun `getMisspelledWords returns empty list if dictionary returns null`(){
        val checker = SpellChecker(Dictionary("test.txt"), source)
        val misspelled = checker.getMisspelledWords()
        assert(misspelled.isEmpty())
    }

    @Test
    fun `getMisspelledWords returns empty list if source text returns null`(){
        val checker = SpellChecker(dictionary, SourceText("test.txt"))
        val misspelled = checker.getMisspelledWords()
        assert(misspelled.isEmpty())
    }

    @Test
    fun `getEditDistance returns proper Levenshtein distance between words`(){
        val first = "spelling"
        val second = "splelin"
        val checker = SpellChecker(dictionary, source)
        val distance = checker.getEditDistance(first, second)
        assertEquals(distance, 3)
    }

    @Test
    fun `getSuggestions returns list of suggested spellings for misspelled word`(){
        val checker = SpellChecker(dictionary, source)
        val suggestions = checker.getSuggestions("spelle")
        assertEquals(suggestions.size, 3)
    }
}