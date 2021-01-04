package  signature
import java.util.Scanner
import java.io.File

fun readFont(pathname: String, text: String): Array<String > {
    val file = File(pathname)
    val lines = file.readLines(Charsets.US_ASCII)
    val lineLength = lines[0].split(" ")[0].toInt()
    val spaceLength = lines[1].split(" ")[1].toInt()

    val fontLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val fontArray = Array(52) {Array(lineLength) {""} }

    for (i in 1..lines.lastIndex step lineLength + 1)
        fontArray[(i - 1) / (lineLength + 1)] = lines.subList(i + 1, i + lineLength + 1).toTypedArray()

    val textLines = Array(lineLength) {""}
    for (letter in text) {
        if (letter != ' ') {
            var i = 0
            for (line in fontArray[fontLetters.indexOf(letter)]) {
                textLines[i] += line
                i++
            }
        } else {
            for (i in 0 until lineLength) {
                textLines[i] += " ".repeat(spaceLength)
            }
        }
    }

    return  textLines
}


fun adjustLine(lenDiff: Int, line: String): String {
    var adjustedLine = line
    adjustedLine = " ".repeat(lenDiff / 2) + adjustedLine

    adjustedLine = "88  $adjustedLine"
    adjustedLine += " ".repeat(lenDiff / 2 + lenDiff % 2)

    adjustedLine += "  88"

    return adjustedLine
}


fun main(args: Array<String>) {
    val stdInput = Scanner(System.`in`)

    print("Enter the name and surname: ")
    val name = stdInput.nextLine()
    print("Enter person's status: ")
    val status = stdInput.nextLine()

    val nameFont = readFont("/home/muhammad/IdeaProjects/roman.txt", name)
    val statusFont = readFont("/home/muhammad/IdeaProjects/medium.txt", status)

    val namesLength = nameFont[0].length
    val statusLength = statusFont[0].length

    val border = "8".repeat(namesLength.coerceAtLeast(statusLength) + 8)

    println(border)
    for (line in nameFont) {
        println(adjustLine((statusLength - namesLength).coerceAtLeast(0), line))
    }
    for (line in statusFont) {
        println(adjustLine((namesLength - statusLength).coerceAtLeast(0), line))
    }
    println(border)
}