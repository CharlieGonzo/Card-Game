package org.example.cardgame24.util;

import javafx.scene.image.Image;
import org.example.cardgame24.HelloApplication;
import org.example.cardgame24.model.Card;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * A container class to hold all the {@link Card} records in the game.
 * @author Charles Gonzalez Jr
 * The function this class provides are:
 * - A Hashmap to hold all card records in lists picked by what suit they are in
 * - loading all images into memory
 * - random generation of cards
 */
public class ImageContainer {
    HashMap<String, LinkedList<Card>> imageContainer;

    public ImageContainer(){
        imageContainer = new HashMap<>();
        try {
            loadImages();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadImages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(HelloApplication.class.getResource("images.txt").openStream())); // retrieve stream of file names
        String line;
        while((line = reader.readLine()) != null) { // go through list of files
            String[] split = line.split("_");
            cleanUpFileName(split);
            addCardToContainer(split,line);

        }
        reader.close();
    }

    private void cleanUpFileName(String[] split){
        if(split.length < 3){ // if length is less than three, it messed up loading a file. Closing program
            throw new RuntimeException("Error loading assets");
        }
        split[2] = split[2].substring(0, split[2].length() - 4);// cut that png off
    }

    private void addCardToContainer(String[] split, String line){
        Card newCard = new Card(
                split[2], // suit
                getValue(split[0]), // value
                new Image(String.valueOf(HelloApplication.class.getResource(line.replace("\uFEFF", "").trim()))) // once again fixing powershell text mess+ image
        );
        imageContainer.computeIfAbsent(split[2], k -> new LinkedList<>()).add(newCard); // if key doesnt exist, create linked list and add card, otherwise. just add card
    }

    public List<Card> generateCards(){
       return imageContainer.values().stream()
                .flatMap(List::stream) // flattens all suit lists into one deck to get random of all suits
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.shuffle(list);
                    return list.stream();
                }))
                .limit(4) // only send back 4 cards
                .toList();
    }

    private int getValue(String val){
        return switch(val){
            case String s when s.equals("king") -> 13;
            case String s when s.equals("queen") -> 12;
            case String s when s.equals("jack") -> 11;
            case String s when s.equals("ace") -> 1;
            default -> Integer.parseInt(val.replaceAll("[^\\d]", "")); // the replace all method is to cleanup whatever mess powershell left hidden.
        };
    }

}
