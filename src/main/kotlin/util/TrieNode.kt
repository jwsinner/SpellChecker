package util

data class TrieNode(
    var children: HashMap<Char, TrieNode> = hashMapOf(),
    var isEndOfWord: Boolean = false
)
