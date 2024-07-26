package io

import org.junit.jupiter.api.Test
import kotlin.test.assertNull

class SourceTextTest {

    @Test
    fun `getText returns a list of string lists from file input`(){
        val text = SourceText("sourceTextTest.txt")
        val lines = text.getWords()
        lines!!.forEach { it is HashMap<*, *> }
    }

    @Test
    fun `getText returns null if file doesn't exist`(){
        val text = SourceText("nothing.txt")
        val lines = text.getWords()
        assertNull(lines)
    }
}