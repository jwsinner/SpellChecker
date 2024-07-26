package util

import io.Dictionary

class DictionaryTrie(private val dictionary: Dictionary) {

    private var rootNode: TrieNode = TrieNode()

    /**
     * Insert new [word] into trie
     * Either get value if character exists, or put a new TrieNode() for that character
     */
    private fun insert(word: String){
        var node = rootNode
        for(character in word.toCharArray())
            node = node.children.getOrPut(character){TrieNode()}
        node.isEndOfWord = true
    }

    /**
     * Returns a list of all words that start with the same [prefix].
     * Current use is to start searching for words that begin with a specific letter.
     */
    fun findWordsWithPrefix(prefix: String): List<String>{
        val words = arrayListOf<String>()
        var node = rootNode
        prefix.forEach { c: Char ->
            node = node.children[c] ?: return words
        }
        findAllWords(node, StringBuilder(prefix), words)
        return words
    }

    // Recursive backtracking function to find all words that start with the provided prefix
    private fun findAllWords(node: TrieNode, prefix: StringBuilder, words: ArrayList<String>){
        if(node.isEndOfWord) words.add(prefix.toString())
        node.children.entries.stream()
            .forEach { entry ->
                prefix.append(entry.key)
                findAllWords(entry.value, prefix, words)
                prefix.deleteCharAt(prefix.length - 1)
            }
    }

    fun loadDictionary(){
        dictionary.getTerms()?.stream()?.forEach { insert(it) }
    }

    fun getTrie(): TrieNode{
        return rootNode
    }
}