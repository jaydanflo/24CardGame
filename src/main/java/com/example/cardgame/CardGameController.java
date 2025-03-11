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
        String basePath = "src/main/resources/CardImages/";

        String[] suits = {"hearts", "diamonds", "clubs", "spades"};

        String[] valueNames = {"", "", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};


        for (int i = 0; i < 4; i++) {
            int cardValue = cardValues[i];
            String valueName = valueNames[cardValue];
            String suitName = suits[i % 4];

            String imagePath = basePath + valueName + "_of_" + suitName + ".png";

            switch (i) {
                case 0:
                    card1Image.setImage(new Image(getClass().getResourceAsStream(imagePath)));
                    break;
                case 1:
                    card2Image.setImage(new Image(getClass().getResourceAsStream(imagePath)));
                    break;
                case 2:
                    card3Image.setImage(new Image(getClass().getResourceAsStream(imagePath)));
                    break;
                case 3:
                    card4Image.setImage(new Image(getClass().getResourceAsStream(imagePath)));
                    break;
            }
        }
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

    private double evaluateExpression(String expression) throws Exception {
        javax.script.ScriptEngineManager mgr = new javax.script.ScriptEngineManager();
        javax.script.ScriptEngine engine = mgr.getEngineByName("JavaScript");
        return (double) engine.eval(expression);
    }

    private boolean usesAllNumbers(String expression) {
        List<Integer> usedNumbers = new ArrayList<>();
        for (int num : cardValues) usedNumbers.add(num);

        for (String token : expression.trim().split("\\s+")) {
            if (!token.isEmpty()) {
                try {
                    int value = Integer.parseInt(token);
                    if (!usedNumbers.remove((Integer) value)) return false;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return usedNumbers.isEmpty();
    }
}
