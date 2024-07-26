package util

import io.Dictionary

class DictionaryTrie(private val dictionary: Dictionary) {

    private var rootNode: TrieNode = TrieNode()

    fun insert(word: String){
        var node = rootNode
        for(character in word.toCharArray())
            node = node.children.getOrPut(character){TrieNode()}
        node.isEndOfWord = true
    }

    fun findWordsWithPrefix(prefix: String): List<String>{
        val words = arrayListOf<String>()
        var node = rootNode
        prefix.forEach { c: Char ->
            node = node.children[c] ?: return words
        }
        findAllWords(node, StringBuilder(prefix), words)
        return words
    }

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