import codingChallenge.TextAnalyseBusiness;
import codingChallenge.TextAnalyseConstants;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextAnalyseTest
{

    /**
     * Tests a standard text file.
     * @throws IOException exception for if the file cannot be accessed.
     */
    @Test
    void analyseStandardText() throws IOException
    {
        final ByteArrayOutputStream console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));

        final byte[] fileText = Files.readAllBytes(Paths.get("src/test/testData/standardText.txt"));
        TextAnalyseBusiness.analyseText(new String(fileText));

        final String[] result = console.toString().split("\r\n");

        assertEquals(String.format(TextAnalyseConstants.WORD_COUNT, "812"), result[0]);

        assertEquals(String.format(TextAnalyseConstants.AVERAGE_WORD_LENGTH, "3.989"), result[1]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "1", "4"), result[2]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "2", "90"), result[3]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "3", "325"), result[4]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "4", "144"), result[5]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "5", "122"), result[6]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "6", "53"), result[7]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "7", "32"), result[8]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "8", "28"), result[9]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "9", "12"), result[10]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "10", "2"), result[11]);
    }

    /**
     * Tests a date string text file.
     * @throws IOException exception for if the file cannot be accessed.
     */
    @Test
    void analyseDateString() throws IOException
    {
        ByteArrayOutputStream console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));

        byte[] fileText = Files.readAllBytes(Paths.get("src/test/testData/dateStrings.txt"));
        TextAnalyseBusiness.analyseText(new String(fileText));

        final String[] result = console.toString().split("\r\n");

        assertEquals(String.format(TextAnalyseConstants.WORD_COUNT, "15"), result[0]);

        assertEquals(String.format(TextAnalyseConstants.AVERAGE_WORD_LENGTH, "5.133"), result[1]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "1", "1"), result[2]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "2", "1"), result[3]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "3", "1"), result[4]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "4", "6"), result[5]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "5", "2"), result[6]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "7", "1"), result[7]);

        assertEquals(String.format(TextAnalyseConstants.WORDS_OF_LENGTH, "10", "3"), result[8]);

    }


}
