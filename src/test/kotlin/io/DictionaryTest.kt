package io

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.reflect.typeOf
import kotlin.test.assertNull

class DictionaryTest {

    @Test
    fun `getText returns a HashSet of single string from input file`(){
        val d = Dictionary("dictionaryTest.txt")
        val strings = d.getTerms()
        assert(strings is HashSet<*>)
        assertEquals(strings!!.size, 17)
    }

    @Test
    fun `getText returns null if file doesn't exist`(){
        val text = Dictionary("nothing.txt")
        val lines = text.getTerms()
        assertNull(lines)
    }
}