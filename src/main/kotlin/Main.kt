fun main(args: Array<String>) {
    val listA = listOf(1, 23, 34, 21, 23)
    val listB = listOf(23, 34, 21)
    println(findCommonNumbersInArray(listA, listB))
}

private fun findCommonNumbersInArray(
    listA: List<Int>,
    listB: List<Int>
): List<Int> {
    val commonNumbers = mutableListOf<Int>()
    for (number in listA) {
        if (listB.contains(number)) {
            commonNumbers.add(number)
        }
    }
    return commonNumbers
}

