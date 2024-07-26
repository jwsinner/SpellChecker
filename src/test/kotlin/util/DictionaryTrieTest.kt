package util

import io.Dictionary
import org.junit.jupiter.api.Test

class DictionaryTrieTest {

    private val dictionary: Dictionary = Dictionary("dictionaryTest.txt")

    @Test
    fun `insert populates the trie with data from strings`(){
        val trie = DictionaryTrie(dictionary)
        trie.loadDictionary()
        assert(trie.getTrie().children.size == 2)
    }

    @Test
    fun `findWordsWithPrefix returns list of words that start with the same letter`(){
        val trie = DictionaryTrie(dictionary)
        trie.loadDictionary()
        val words = trie.findWordsWithPrefix("a")
        assert(words.size == 6)
    }

    @Test
    fun `findWordsWithPrefix returns list of words that start with the same prefix`(){
        val trie = DictionaryTrie(dictionary)
        trie.loadDictionary()
        val words = trie.findWordsWithPrefix("bb")
        assert(words.size == 5)
    }

    @Test
    fun `findWordsWithPrefix returns empty list if no words are found`(){
        val trie = DictionaryTrie(dictionary)
        trie.loadDictionary()
        val words = trie.findWordsWithPrefix("f")
        assert(words.isEmpty())
    }
}