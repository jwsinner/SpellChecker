package util

import org.junit.jupiter.api.Test

class DictionaryTrieTest {

    @Test
    fun `insert populates the trie with data from strings`(){
        val trie = DictionaryTrie()
        trie.insert("first")
        trie.insert("fired")
        trie.insert("second")
        assert(trie.getTrie().children.size == 2)
    }

    @Test
    fun `findWordsWithPrefix returns list of words that start with the same letter`(){
        val trie = DictionaryTrie()
        trie.insert("first")
        trie.insert("fired")
        trie.insert("fried")
        val words = trie.findWordsWithPrefix("f")
        assert(words.size == 3)
    }

    @Test
    fun `findWordsWithPrefix returns list of words that start with the same prefix`(){
        val trie = DictionaryTrie()
        trie.insert("first")
        trie.insert("fired")
        trie.insert("fried")
        val words = trie.findWordsWithPrefix("fir")
        assert(words.size == 2)
    }
}