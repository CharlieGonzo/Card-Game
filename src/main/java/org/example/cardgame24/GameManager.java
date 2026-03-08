package org.example.cardgame24;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.example.cardgame24.model.Card;
import org.example.cardgame24.util.ImageContainer;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class holds the state of the game
 * @author Charles Gonzalez Jr
 * The functionality this class provides is:
 * - handling off current cards in play.
 * - handling of equation results
 * - use of the {@link ImageContainer} class
 * - verification of the validation of the funtion and its answer. we use expr4j library to handle the expressions
 * - processes new cards when game refreshes.
 */
public class GameManager {
    Card[] currentCards; // current cards in play

    double currentAns; //  for the frontend so they can have access to the latest answer
    private final ImageContainer container = new ImageContainer(); // holds all Card Records

    public GameManager() throws IOException { // throws IO Exception from ImageContainer
        currentCards = new Card[4];
    }

    /**
     * Verification of the users formula
     * @param formula the formula the user wants to verify
     * @return true if formula passes all conditions, false if not
     */
    public boolean verify(String formula){
        Integer[] currNums =new Integer[]{currentCards[0].value(),currentCards[1].value(),currentCards[2].value(),currentCards[3].value()};
        boolean checkEq = checkEquation(formula);
        return usedCorrectNumbers(formula,currNums) && checkEq ;
    }

    /**
     * checks if equations equals 24 and is also a valid equation. sets currentAns variable to Integer.MinValue to have the front end handle the error
     * @param formula the formula the user is trying the validate
     * @return true if equal to 24, false if not
     */
    private boolean checkEquation(String formula){
        try {
            Expression e = new ExpressionBuilder(formula)
                    .build();
            currentAns = e.evaluate();
            System.out.println(currentAns);
            if (currentAns == 24.0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentAns = Integer.MIN_VALUE;
        return false;
    }

    /**
     * strips the formula to only numbers and checks if it is the right numbers
     * @param formula numbers used in users formula
     * @param drawnCards numbers that are expected
     * @return if the user used the right numbers or not
     */
    public boolean usedCorrectNumbers(String formula, Integer[] drawnCards) {
        // Extract all numbers from the string
        List<Integer> usedNumbers = Pattern.compile("\\d+")
                .matcher(formula)
                .results()
                .map(m -> Integer.parseInt(m.group()))
                .sorted()
                .toList();
        System.out.println(usedNumbers);
        List<Integer> sortedDrawn = Arrays.stream(drawnCards).sorted().toList();
        System.out.println(sortedDrawn);
        return usedNumbers.equals(sortedDrawn);
    }

    /**
     * generates 4 random cards and updates the currentCards variable
     */
    public void generateNewCards(){
        System.out.println("here");
        int count = 0;
        for(Card c: container.generateCards()){
            currentCards[count++] = c;
        }
        System.out.println(Arrays.toString(currentCards));
    }

    public Card[] getCurrentCards(){
        return currentCards;
    }

    public double getCurrentAns(){
        return currentAns;
    }

}
