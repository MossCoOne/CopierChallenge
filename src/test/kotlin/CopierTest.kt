import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class CopierTest {
    private val sourceMock = mock<ISource>()
    private val destination = mock<IDestination>()
    lateinit var copier: Copier

    @Before
    fun setUp() {
        copier = Copier(sourceMock, destination)
    }

    @Test
    fun `copy reads a character from source`() {

        copier.copy()

        verify(sourceMock, atLeastOnce()).getChar()
    }

    @Test
    fun `copy writes a character to the destination after reading it from source`() {
        val expectedChar = 'a'
        whenever(sourceMock.getChar()).thenReturn(expectedChar)

        copier.copy()

        val captor = argumentCaptor<Char>()
        verify(destination).setChar(captor.capture())
        assertEquals(expectedChar, captor.firstValue)
    }

    @Test
    fun `copy does not write a character to destination when a new line is encountered`() {

        val newLine = '\n'
        whenever(sourceMock.getChar()).thenReturn(newLine)

        copier.copy()

        verify(destination, never()).setChar(newLine)
    }


    @Test
    fun `copyChars reads characters from source`() {

        copier.copyChars(2)

        verify(sourceMock, atLeastOnce()).readChars(2)
    }

    @Test
    fun `copyChars writes chars to destination after reading them from source `() {

        val chars = "chars".toCharArray()
        whenever(sourceMock.readChars(5)).thenReturn(chars)

        copier.copyChars(5)

        verify(destination, atLeastOnce()).writeChars(chars)
    }

    @Test
    fun `copyChars writes same copied chars to destination after reading them from source `() {

        val chars = "chars".toCharArray()
        whenever(sourceMock.readChars(5)).thenReturn(chars)

        copier.copyChars(5)

        val argumentCaptor = argumentCaptor<CharArray>()
        verify(destination).writeChars(argumentCaptor.capture())
        argumentCaptor.let {
            assertEquals(chars.size, it.firstValue.size)
            assertEquals(chars[0], it.firstValue[0])
            assertEquals(chars[1], it.firstValue[1])
            assertEquals(chars[2], it.firstValue[2])
            assertEquals(chars[3], it.firstValue[3])
            assertEquals(chars[4], it.firstValue[4])
        }

    }

    @Test
    fun `copyChars should only write chars before next line`() {
        val expectedChars = "ert".toCharArray()
        whenever(sourceMock.readChars(11)).thenReturn("ert\ntjkj\nrhg".toCharArray())

        copier.copyChars(11)

        val argumentCaptor = argumentCaptor<CharArray>()
        verify(destination).writeChars(argumentCaptor.capture())
        argumentCaptor.let {
            assertEquals(expectedChars.size, it.firstValue.size)
            assertEquals(expectedChars[0], it.firstValue[0])
            assertEquals(expectedChars[1], it.firstValue[1])
            assertEquals(expectedChars[2], it.firstValue[2])
        }

    }

    @Test
    fun `copyChars should not write chars when charArray from source is null`() {

        whenever(sourceMock.readChars(2)).thenReturn(charArrayOf())

        copier.copyChars(2)


        verify(destination, never()).writeChars(any())
    }

    @Test
    fun `copyChars should not write chars when charArray is empty`() {

        whenever(sourceMock.readChars(5)).thenReturn(charArrayOf())

        copier.copyChars(5)


        verify(destination, never()).writeChars(any())
    }
}