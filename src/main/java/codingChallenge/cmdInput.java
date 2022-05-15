package codingChallenge;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * Provides
 */
public class cmdInput
{

    /**
     * Reads cmd input. If input is a valid text file, attempts to analyse the text.
     */
    public static void main (String[] args)
    {
        System.out.println("--- Text Analyser ---");
        System.out.println("Rules for what defines a word: ");
        System.out.println(" - & ' and - are valid characters for a word");
        System.out.println(" - Numbers are not valid words");
        System.out.println(" - Date strings in the format of dd/mm/yyyy are a valid word");
        System.out.println(" - The following characters are invalid and are ignored: ");
        System.out.println(" -  \\n\\r.,!?:;|\\/<>~@#[]{}=_+)(*^%$£\"");

        System.out.println("Please enter the file path for a text file to analyse:");
        // read console input
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            // create a byte array of the text in file.
            final byte[] fileText = Files.readAllBytes(Paths.get(reader.readLine()));
            TextAnalyseBusiness.analyseText(new String(fileText));
        }
        catch (NullPointerException | IOException | InvalidPathException e)
        {
            System.out.println("File not found.");
        }

    }
}
