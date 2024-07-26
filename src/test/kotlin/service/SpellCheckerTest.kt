package service

import io.Dictionary
import io.SourceText
import org.junit.jupiter.api.Test

class SpellCheckerTest {

    private val dictionary = Dictionary("dictionaryTest.txt")
    private val source = SourceText("sourceTextTest.txt")

    @Test
    fun `getMisspelledWords returns a list of words not contained in dictionary`(){
        val checker = SpellChecker(dictionary, source)
        val misspelled = checker.getMisspelledWords()
        assert(misspelled.size == 9)
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
}