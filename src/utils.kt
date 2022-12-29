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

/**
 * Create a copy of the collection, clear the collection, and invoke function for each value in the copy.
 */
inline fun <T> copyClearForEach(collection: MutableCollection<T>, each : (T) -> Unit) {
    val cpy = ArrayList(collection)
    collection.clear()
    cpy.forEach { each(it) }
}

/**
 * The collection will be cleared. Then the given procedure will be called
 * for each element previously in the collection.
 * The procedure may add (or re-add) elements to the collection.
 * This alternating clear-invoke process repeats until the collection remains empty.
 */
fun <T> repeatUntilEmpty(collection: MutableCollection<T>, each : (T) -> Unit) {
    val l = ArrayList<T>()
    while (collection.isNotEmpty()) {
        l.addAll(collection)
        collection.clear()
        l.forEach { each(it) }
        l.clear()
    }
}

/**
 * Invoke action for all four unit directions in 2-dimensional space.
 */
inline fun inFourDirections( action: (IntVector) -> Unit) {
    var p = vec(1, 0)
    action(p)
    repeat(3) {
        p = p.rotate(0, 1)
        action(p)
    }
}

fun inBounds(min: IntVector, max: IntVector, p: IntVector): Boolean {
    for (i in 0..2)
        if (p[i] !in min[i]..max[i]) return false
    return true
}