package org.example.cardgame24.model;

import javafx.scene.image.Image;

/**
 * Represents a playing card in the came
 * @param suit The suit of the card (e.g., clubs,hearts)
 * @param value face value of the card (1-14)
 * @param img holds the image of the card to be used for later.
 */
public record Card(String suit, int value, Image img) {
}
