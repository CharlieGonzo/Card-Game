package org.example.cardgame24.util;

import javafx.scene.image.Image;
import org.example.cardgame24.model.Card;
import net.objecthunter.exp4j.ExpressionBuilder;

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

    double currentAns;//  for the frontend so they can have access to the latest answer

    String currentEquation;// Gemini will generate the equation for us.

    boolean isGeminiLoading;
    boolean isValid = false;

    private final ImageContainer container = new ImageContainer(); // holds all Card Records
    private final AIHelper aiHelper = new AIHelper(); // for gemini requests

    public GameManager() {
        currentCards = new Card[4];
    }

    /**
     * Verification of the users formula
     * @param formula the formula the user wants to verify
     * @return true if formula passes all conditions, false if not
     */
    public boolean verify(String formula){
        return checkEquation(formula) && usedCorrectNumbers(formula,getCurrNums());
    }

    private Integer[] getCurrNums(){
        return new Integer[]{currentCards[0].value(),currentCards[1].value(),currentCards[2].value(),currentCards[3].value()}; // extract card values
    }

    /**
     * checks if equations equals 24 and is also a valid equation. sets currentAns variable to Integer.MinValue to have the front end handle the error
     * @param formula the formula the user is trying the validate
     * @return true if equal to 24, false if not
     */
    private boolean checkEquation(String formula){
        validateEquation(formula);
        if (currentAns == 24.0) {
            return true; // return true if equation equals 24
        }
        return false;
    }



    public boolean validateEquation(String formula){
        try {
            currentAns = new ExpressionBuilder(formula).build().evaluate();
            return true;
        }catch (Exception e){
            currentAns = Integer.MIN_VALUE; // set this value so that front end knows that equation was invalid.
            return false;
        }
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
        List<Integer> sortedDrawn = Arrays.stream(drawnCards).sorted().toList(); // get list of numbers from cards
        return usedNumbers.equals(sortedDrawn); // compare expected and results
    }

    /**
     * generates 4 random cards and updates the currentCards variable
     */
    public Image[] generateNewCards(){
        int count = 0;
        currentEquation = null;
        for(Card c: container.generateCards()){
            currentCards[count++] = c;
        }
        currentEquation = EquationSolver.solve(getCurrNums());
        return new Image[]{currentCards[0].img(),currentCards[1].img(),currentCards[2].img(),currentCards[3].img()}; // send back array of new images
    }

    private Thread startBackgroundService(){
        return new Thread(() -> {
//            currentEquation = aiHelper.getSolution(getCurrNums()).split("=")[0];
            currentEquation = EquationSolver.solve(getCurrNums());
            System.out.println(currentEquation);
        });
    }

    public double getCurrentAns(){
        return currentAns;
    }

    public String getCurrentEquation(){
        return currentEquation;
    }

    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }

    public boolean getIsValid(){
        return isValid;
    }

}
