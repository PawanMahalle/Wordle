package com.wordle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Solution to solve Wordle game: https://www.powerlanguage.co.uk/wordle/
 *
 * <p>
 * Twitter: https://twitter.com/search?q=%23wordle
 */
public class Main {

    public static final int WORD_LENGTH = 5;
    public static final int ATTEMPTS = 5;
    public static final int SUGGESTION_COUNT = 10;

    public static void main(String[] args) throws IOException {
        /**
         * Get list of predefined 5-letter words
         */
        List<String> wordList = getWords();

        /**
         * Maintaining separate regex for each position and
         * update the regex based on feedback from Wordle UI.
         */
        List<String> regexList = initializeRegex();

        // Letters which are either green or yellow
        List<Character> detectedLetters = new ArrayList<>();

        for (int i = 0; i < ATTEMPTS; i++) {
            System.out.print("Enter input: ");
            String guess = readInput();

            System.out.print("Enter feedback (X -> Gray/White, Y -> yellow, G -> green): ");
            String feedback = readInput();

            updateRegex(guess, feedback, detectedLetters, regexList);

            System.out.println("Suggestion(s):");
            printTopMatches(detectedLetters, regexList, wordList);
        }
    }

    private static void printTopMatches(List<Character> detectedLetters, List<String> regexList, List<String> wordList) {
        String regex = regexList.stream().collect(Collectors.joining());
        int printCount = 0;
        for (int wordIndex = 0; wordIndex < wordList.size(); wordIndex++) {
            String currentWord = wordList.get(wordIndex);
            // Ensure the word matches regex and contains all letters detected so far
            if (currentWord.matches(regex) && allDetectedLettersPresent(currentWord, detectedLetters)) {
                System.out.println(currentWord);
                if (++printCount == SUGGESTION_COUNT) {
                    break;
                }
            }
        }
    }

    private static boolean allDetectedLettersPresent(String currentWord, List<Character> detectedLetters) {
        for (int i = 0; i < detectedLetters.size(); i++) {
            if (!currentWord.contains(detectedLetters.get(i).toString())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates regex based on the feedback.
     */
    private static void updateRegex(String guess, String feedback, List<Character> detectedLetters, List<String> regexList) {
        for (int pos = 0; pos < WORD_LENGTH; pos++) {

            char currentChar = guess.charAt(pos);
            char feedbackForCurrentChar = feedback.charAt(pos);

            if (isGrayed(feedbackForCurrentChar)) {
                /*
                 When the letter is grayed then the letter is not part of the word in general
                 and hence should be removed from regex for all positions
                 However, in some cases, if the letter repeated and already marked as yellow or green then also
                 the letter is grayed and in that case we should not remove the letter from regex for all the positions.

                 Hence, if the letter is grayed but detected a valid letter then only remove it from regex for
                 current position.
                 Otherwise, remove from regex for all position.
                 */
                if (detectedLetters.contains(currentChar)) {
                    String currRegex = regexList.get(pos);
                    String updatedRegex = currRegex.replace(currentChar, '\0');
                    regexList.set(pos, updatedRegex);
                } else {
                    // Remove current character from all regex
                    for (int regexIndex = 0; regexIndex < WORD_LENGTH; regexIndex++) {
                        String currRegex = regexList.get(regexIndex);
                        String updatedRegex = currRegex.replace(currentChar, '\0');
                        regexList.set(regexIndex, updatedRegex);
                    }
                }
            } else if (isYellow(feedbackForCurrentChar)) {
                String currRegex = regexList.get(pos);
                String updatedRegex = currRegex.replace(currentChar, '\0');
                regexList.set(pos, updatedRegex);

                detectedLetters.add(guess.charAt(pos));
            } else if (isGreen(feedbackForCurrentChar)) {
                // Update regex with fixed character
                regexList.set(pos, String.valueOf(currentChar));

                detectedLetters.add(guess.charAt(pos));
            } else {
                System.out.println("Incorrect character '" + feedbackForCurrentChar + "' in feedback : " + feedback);
            }
        }
    }

    private static List<String> initializeRegex() {
        List<String> regexList = new ArrayList<>();
        for (int i = 0; i < WORD_LENGTH; i++) {
            regexList.add("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]");
        }
        return regexList;
    }

    private static String readInput() {
        Scanner myObj = new Scanner(System.in);
        String input = myObj.nextLine();
        return input.toUpperCase();
    }

    private static List<String> getWords() throws IOException {

        List<String> wordList = new ArrayList<String>();

        File file = new File("src/word-list.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String word;
        while ((word = br.readLine()) != null) {
            wordList.add(word.toUpperCase());
        }

        return wordList;
    }

    private static boolean isGrayed(char c) {
        return c == 'X' || c == 'x';
    }

    private static boolean isGreen(char c) {
        return c == 'G' || c == 'g';
    }

    private static boolean isYellow(char c) {
        return c == 'Y' || c == 'y';
    }
}