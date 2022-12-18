import util.IntVector
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(num: Int) = File(String.format("input/day%02d/input.txt", num)).readLines()

/**
 * Reads lines from the given example txt file.
 */
fun readExampleLines(num: Int) = File(String.format("input/day%02d/example.txt", num)).readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Ensure answer is as expected.
 */
fun <T> shouldBe(expected: T, actual: T) {
    if (expected != actual) {
        println("Expected $expected, was $actual")
        throw RuntimeException()
    }
    println(actual)
}

fun vec2(x: Int, y: Int) : IntVector {
    return IntVector.of(x, y)
}

fun vec(vararg c: Int) : IntVector {
    return IntVector(c)
}