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

    public ImageContainer() throws IOException {
        imageContainer = new HashMap<>();
        loadImages();
        System.out.println(imageContainer.toString());
    }

    private void loadImages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(HelloApplication.class.getResource("images.txt").openStream()));
        String line;
        while((line = reader.readLine()) != null) {
            String[] split = line.split("_");
            if(split.length < 3){
                throw new RuntimeException("Error loading assets");
            }
            split[2] = split[2].substring(0, split[2].length() - 4);// cut that png off
            Card newCard = new Card(
                    split[2],
                    getValue(split[0]),
                    new Image(String.valueOf(HelloApplication.class.getResource(line.replace("\uFEFF", "").trim()))) // once again fixing powershell text mess
            );

            imageContainer.computeIfAbsent(split[2], k -> new LinkedList<>()).add(newCard);
        }
        reader.close();
    }

    public List<Card> generateCards(){
       return imageContainer.values().stream()
                .flatMap(List::stream) // flattens all suit lists into one deck
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.shuffle(list);
                    return list.stream();
                }))
                .limit(4)
                .toList();
    }

    private int getValue(String val){
        return switch(val){
            case String s when s.equals("king") -> 13;
            case String s when s.equals("queen") -> 12;
            case String s when s.equals("jack") -> 11;
            case String s when s.equals("ace") -> 1;
            default -> Integer.parseInt(val.replaceAll("[^\\d]", "")); // the replace all method is to cleanup what evey mess powershell left hidden
        };
    }
}
