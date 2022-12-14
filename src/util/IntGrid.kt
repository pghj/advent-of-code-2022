package util

class IntGrid {
    val grid : ArrayList<IntArray>
    val width : Int
    val height : Int

    constructor(g : ArrayList<IntArray>) {
        grid = g
        width = grid[0].size
        height = grid.size
    }

    constructor(w: Int, h: Int) {
        width = w
        height = h
        grid = ArrayList(height)
        for (i in 0 until height) grid.add(IntArray(width) {0})
    }

    operator fun get(at: IntVector) : Int {
        return grid[at[1]][at[0]]
    }

    operator fun set(at: IntVector, v: Int) {
        grid[at[1]][at[0]] = v
    }

    operator fun set(x: Int, y: Int, v: Int) {
        grid[y][x] = v
    }

    operator fun contains(at: IntVector) : Boolean {
        return at[0] in 0 until width && at[1] in 0 until height
    }

    fun forEach(f: (Int)->Unit){
        for (y in 0 until height) {
            val r = grid[y]
            for (x in 0 until width) f(r[x])
        }
    }

    fun forEachIndexed(f: (IntVector, Int)->Unit){
        for (y in 0 until height) {
            val r = grid[y]
            for (x in 0 until width) f(IntVector.of(x, y), r[x])
        }
    }

    fun compute(f: (Int)->Int){
        for (y in 0 until height) {
            val r = grid[y]
            for (x in 0 until width) r[x] = f(r[x])
        }
    }

    fun computeIndexed(f: (IntVector, Int)->Int){
        for (y in 0 until height) {
            val r = grid[y]
            for (x in 0 until width) r[x] = f(IntVector.of(x, y), r[x])
        }
    }

    fun print(f: (Int)->String){
        for (y in 0 until height) {
            val r = grid[y]
            for (x in 0 until width) {
                print(f(r[x]))
            }
            println()
        }
    }


}