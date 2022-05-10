class Copier(private val source: ISource, private val destination: IDestination) {
    fun copy() {
        val char: Char = source.getChar()
        if (char != '\n') {
            destination.setChar(char)
        }
    }

    fun copyChars(count: Int) {
        val charsFromSource = getCharsIfNextLineExist(source.readChars(count))
        val charsToWrite = getCharsIfNextLineExist(charsFromSource)
        if (charsToWrite != null && charsToWrite.isNotEmpty()) {
            destination.writeChars(charsToWrite)
        }
    }

    private fun getCharsIfNextLineExist(charsToAdd: CharArray?): CharArray? {
        var count = 0
        return if (charsToAdd?.contains('\n') == true) {
            val arraySize = charsToAdd.indexOf('\n')
            var charsBeforeNextLine = CharArray(arraySize)
            while (count < arraySize) {
                charsBeforeNextLine[count] = charsToAdd[count]
                count++
            }
            charsBeforeNextLine
        } else {
            charsToAdd
        }
    }
}