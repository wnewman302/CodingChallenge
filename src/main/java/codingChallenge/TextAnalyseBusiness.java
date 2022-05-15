package codingChallenge;

import java.util.*;

/**
 * Provides the text analysis logic.
 */
public class TextAnalyseBusiness
{

    /** Stores the total length of all valid word characters. */
    private static double totalLength;

    /** Current position in the text array. */
    static int position = 0;

    /** List of valid words. */
    static final ArrayList<String> wordList = new ArrayList<>();

    /** Char array of the text to analyse. */
    static char[] text;

    /**
     * Analyses the provided text string and outputs various information about it to console.
     * E.g. Total word count, average word length, amount of words at each length etc.
     *
     * @param textToAnalyse String containing the words to analyse.
     */
    public static void analyseText(final String textToAnalyse)
    {
        final long startTime = System.currentTimeMillis();

        resetVariables();

        // split the text into words, removing any invalid characters.
        final String[] splitString = splitter(textToAnalyse);

        final HashMap<Integer, Integer> wordLengths = getLengths(splitString);

        final int wordCount = splitString.length;
        final double averageLength = totalLength / wordCount;

        // prints word count
        System.out.printf((TextAnalyseConstants.WORD_COUNT) + "%n", wordCount);

        // prints average word length rounded to 3 decimals.
        System.out.printf((TextAnalyseConstants.AVERAGE_WORD_LENGTH) + "%n", Math.round(averageLength * 1000) / 1000D);

        // get the most amount of times a word length appears.
        final int amountForMostFrequent = Collections.max(wordLengths.values());
        final List<Integer> lengthsForMostFrequent = new ArrayList<>();

        printWordsOfEachLength(wordLengths, amountForMostFrequent, lengthsForMostFrequent);

        printMostFrequent(amountForMostFrequent, lengthsForMostFrequent);

        System.out.println();
        System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    /**
     * Resets the class variables.
     */
    private static void resetVariables()
    {
        totalLength = 0;
        position = 0;
        wordList.clear();
        text = null;
    }

    /**
     * Print each word length and the amount of words at that length.
     * Also builds up the list for the most frequently occurring word length.
     *
     * @param wordLengths Stores each word length and the amount of words at that length.
     * @param amountForMostFrequent Amount of times the most frequent word length occurs.
     * @param lengthsForMostFrequent List of word lengths that are the most frequently occurring.
     */
    private static void printWordsOfEachLength(final HashMap<Integer, Integer> wordLengths,
                                               final int amountForMostFrequent,
                                               final List<Integer> lengthsForMostFrequent)
    {
        for (Map.Entry<Integer, Integer> word : wordLengths.entrySet())
        {
            System.out.printf((TextAnalyseConstants.WORDS_OF_LENGTH) + "%n", word.getKey(), word.getValue());
            if (word.getValue() == amountForMostFrequent)
            {
                lengthsForMostFrequent.add(word.getKey());
            }
        }
    }

    /**
     * Prints the most frequent word lengths and how many times words of that length appear.
     *
     * @param amountForMostFrequent Amount of words that are the most frequent length.
     * @param lengthsForMostFrequent List of word lengths that are the most frequently occurring.
     */
    private static void printMostFrequent(final int amountForMostFrequent, final List<Integer> lengthsForMostFrequent)
    {
        System.out.print("The most frequently occurring word length is " + amountForMostFrequent
                + ", for word lengths of ");
        for (Integer length : lengthsForMostFrequent)
        {
            final int indexOfLength = lengthsForMostFrequent.indexOf(length);

            // if there is only 1 most frequent length
            if (indexOfLength == 0 || lengthsForMostFrequent.size() == 1)
            {
                System.out.print(length);
            }
            // if this is the last most frequent length
            else if (indexOfLength == (lengthsForMostFrequent.size() - 1))
            {
                System.out.println(" & " + length);
            }
            else
            {
                System.out.print(", " + length);
            }
        }
    }

    /**
     * Gets the amount of words of each length from the provided string array.
     *
     * @param splitText String array containing valid words.
     * @return Hashmap containing word length and the amount of words at that length.
     */
    private static HashMap<Integer, Integer> getLengths(final String[] splitText)
    {
        final HashMap<Integer, Integer> wordLengths = new HashMap<>();

        for (String word : splitText)
        {
            int length = word.length();
            // add the word length to the list of lengths. If the length already exists,
            // increment the amount of words of that length.
            wordLengths.put(length, wordLengths.containsKey(length) ? wordLengths.get(length) + 1 : 1);
            totalLength += length;
        }

        return wordLengths;
    }

    /**
     * Split the provided text string into only valid words.
     * @param textToAnalyse Text string to split into words.
     * @return an array of valid words.
     */
    private static String[] splitter(final String textToAnalyse)
    {
        text = textToAnalyse.toCharArray();
        final StringBuilder word = new StringBuilder();

        while (position < text.length)
        {
            final char character = text[position];
            // if the character is valid, add it to the currently in progress word
            if (isValid(character))
            {
                word.append(character);
            }
            // if the character is not valid then attempt to add the word created so far to the list of words
            else
            {
                if (!word.isEmpty())
                {
                    wordList.add(word.toString());
                }
                // clear builder
                word.setLength(0);
            }
            position++;
        }
        return wordList.toArray(new String[0]);
    }

    /**
     * Checks if the provided character is a valid word character.
     * @param character character to verify.
     * @return if the character is a valid word character.
     */
    private static boolean isValid(final char character)
    {
        // check if the character is not an invalid character
        final boolean valid;
        switch (character) {
            case ' ', '\n', '\r', '.', ',', '!', '?', ':', ';',
                    '|', '\\', '/', '<', '>', '~', '@', '#', '[', ']', '{', '}', '=', '_', '+',
                    '(', ')', '*', '^', '£', '$', '%' -> valid = false;
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                // if the character is a number and the character is either the first character,
                // or it has a valid word splitter before it, then attempt to create a date string
                if ((position == 0 || previousCharacter().matches(TextAnalyseConstants.VALID_DATE_PREFIX_REGEX))) {
                    attemptDateString();
                }
                valid = false;
            }
            default -> valid = true;
        }

        return valid;
    }

    /**
     * Returns the character before the current in the text string.
     * @return returns the previous character as a string.
     */
    private static String previousCharacter()
    {
        return String.valueOf(text[position -1]);
    }

    /**
     * Attempts to validate a date string against the dd/mm/yyyy format.
     */
    private static void attemptDateString()
    {
        final StringBuilder dateString = new StringBuilder();

        for (int i = 0; i < 10; i++)
        {
            // get next character
            final String nextCharacter = String.valueOf(text[position]);
            // increment position
            position++;
            // if the character is a valid date character, add it to the in progress date
            if (validDateCharacter(i, nextCharacter))
            {
                dateString.append(nextCharacter);
            }
            // if not a valid date character, clean the currently in progress date string
            else
            {
                dateString.setLength(0);
                break;
            }

        }
        // decrement the position by 1 as when this method returns to the splitter,
        // it will increment by 1 more than is needed.
        position--;

        // if a date was successfully found, add it to the list of words.
        if (!dateString.isEmpty())
        {
            wordList.add(dateString.toString());
        }
    }

    /**
     * Validate the provided character with a given index value against the date format dd/mm/yyyy.
     *
     * @param index index of the character to validate.
     * @param character character to validate.
     * @return whether the character is valid for the provided position in the date format string.
     */
    private static boolean validDateCharacter(final int index, final String character)
    {
        return switch (index) {
            case 2, 5 -> character.equals("/");
            default -> character.matches(TextAnalyseConstants.NUMBER_REGEX);
        };
    }
}
