import java.io.File

fun main(args: Array<String>) {
    File(args.first()).readLines().stream().limit(5).forEach(System.out::println)
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}