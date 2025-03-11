package com.example.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardGameController {
    private int[] cardValues = new int[4];

    @FXML private ImageView card1Image;
    @FXML private ImageView card2Image;
    @FXML private ImageView card3Image;
    @FXML private ImageView card4Image;

    @FXML private TextField expressionField;
    @FXML private Label resultLabel;

    public void initialize() {
        generateNewCards();
    }

    @FXML
    private void generateNewCards() {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            cardValues[i] = rand.nextInt(13) + 1;
        }
        updateCardImages();
        resultLabel.setText("");
        expressionField.clear();
    }

    private void updateCardImages() {
        String basePath = "src/main/resources/CardImages";

        card1Image.setImage(new Image(getClass().getResourceAsStream(basePath + cardValues[0] + ".png")));
        card2Image.setImage(new Image(getClass().getResourceAsStream(basePath + cardValues[1] + ".png")));
        card3Image.setImage(new Image(getClass().getResourceAsStream(basePath + cardValues[2] + ".png")));
        card4Image.setImage(new Image(getClass().getResourceAsStream(basePath + cardValues[3] + ".png")));
    }

    @FXML
    private void verifyExpression() {
        String expression = expressionField.getText();
        if (!usesAllNumbers(expression)) {
            resultLabel.setText("Error: Use all four numbers exactly once.");
            return;
        }

        try {
            double result = evaluateExpression(expression);
            resultLabel.setText(Math.abs(result - 24) < 1e-6 ? "Correct!" : "Incorrect!");
        } catch (Exception e) {
            resultLabel.setText("Error: Invalid expression.");
        }
    }

    private double evaluateExpression(String e) {
        return 0;
    }

    private boolean usesAllNumbers(String expression) {
        List<Integer> usedNumbers = new ArrayList<>();
        for (int num : cardValues) usedNumbers.add(num);

        for (String token : expression.replaceAll("[^0-9]+", " ").trim().split(" ")) {
            if (!token.isEmpty()) {
                int value = Integer.parseInt(token);
                if (!usedNumbers.remove((Integer) value)) return false;
            }
        }
        return usedNumbers.isEmpty();
    }

}