package util

/**
 * A collection of IntRanges. Adding new ranges will merge intersecting ranges (or ranges that touch).
 * The list of remaining ranges will be sorted.
 */
class IntRangeSet : AbstractCollection<IntRange>() {

    private val list = ArrayList<IntRange>()

    override val size: Int
        get() = list.size

    fun get(index: Int): IntRange {
        return list[index]
    }

    fun add(range: IntRange) {
        if (list.isEmpty()) {
            list.add(range)
        } else {
            var i = list.binarySearch { it.from  - range.from }
            if (i < 0) i = -(i+1)
            list.add(i, range)
            if (i > 0 && (list[i-1] intersects range || list[i-1] touches range) ) {
                mergeForward(i-1)
            } else {
                mergeForward(i)
            }
        }
    }

    fun contains(v: Int) : Boolean {
        var i = list.binarySearch { it.from - v }
        return if (i >= 0) true
        else {
            i = -i-2
            if (i >= 0) { list[i].contains(v) } else false
        }
    }

    private fun mergeForward(i: Int) {
        var r = list[i]
        var j = i+1
        while(j in list.indices) {
            val next = list[j]
            if (r intersects next) {
                r = r union next
                j++
            } else if (r.to+1 == next.from) {
                r = IntRange(r.from, next.to)
                j++
            } else {
                break
            }
        }
        list[i] = r
        list.subList(i+1, j).clear()
    }

    override fun iterator(): Iterator<IntRange> {
        return list.iterator()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("{")
        list.forEachIndexed { i, r -> if (i != 0) sb.append(','); sb.append(r) }
        sb.append("}")
        return sb.toString()
    }

}