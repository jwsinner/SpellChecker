import io.Dictionary
import io.SourceText
import service.SpellChecker
import util.DictionaryTrie
import java.io.File

fun main(args: Array<String>) {
    val dictionaryLocation = args[0]
    val sourceLocation = args[1]

    val dictionary = Dictionary(dictionaryLocation)
    val source = SourceText(sourceLocation)
    val spellChecker = SpellChecker(dictionary, source)

    spellChecker.displayErrorsWithSuggestions()

}