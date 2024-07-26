package io

import org.junit.jupiter.api.Test
import kotlin.test.assertNull

class DictionaryTest {

    @Test
    fun `getText returns a list of single string lists from input file`(){
        val d = Dictionary("dictionaryTest.txt")
        val strings = d.getTerms()
        assert(strings!!.size == 6)
    }

    @Test
    fun `getText returns null if file doesn't exist`(){
        val text = Dictionary("nothing.txt")
        val lines = text.getTerms()
        assertNull(lines)
    }
}