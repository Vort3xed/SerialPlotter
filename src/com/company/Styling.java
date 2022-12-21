package com.company;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

public class Styling {
    public static void drawVectors(Line name, double startX, double startY, double endX, double endY){
        name.setStartX(startX);
        name.setStartY(startY);
        name.setEndX(endX);
        name.setEndY(endY);
    }
    public static void styleButtons(Button name, double posX, double posY, String cssStyle){
        name.setLayoutX(posX);
        name.setLayoutY(posY);
        name.setStyle(cssStyle);
    }
    public static void styleButtons(Button name, double posX, double posY){
        name.setLayoutX(posX);
        name.setLayoutY(posY);
    }
    public static void styleButtons(Button name, String text, String cssStyle){
        name.setText(text);
        name.setStyle(cssStyle);
    }
    public static void styleButtons(Button name, String text, int maxHeight, int maxWidth, String cssStyle){
        name.setText(text);
        name.setMinHeight(maxHeight);
        name.setMinWidth(maxWidth);
        name.setStyle(cssStyle);
    }
    public static void styleButtons(Button name, int posX, int posY, String text, int maxHeight, int maxWidth, String cssStyle){
        name.setText(text);
        name.setLayoutX(posX);
        name.setLayoutY(posY);
        name.setMinHeight(maxHeight);
        name.setMinWidth(maxWidth);
        name.setStyle(cssStyle);
    }
    public static void styleCircles(Circle name, double posX, double posY){
        name.setLayoutX(posX);
        name.setLayoutY(posY);
    }
    public static void styleRectangles(Rectangle name, double posX, double posY, double width, double height, ImagePattern img){
        name.setLayoutX(posX);
        name.setLayoutY(posY);
        name.setWidth(width);
        name.setHeight(height);
        name.setFill(img);
    }
    public static void styleLines(Line name, double startX, double startY, double endX, double endY, int strokeWidth){
        name.setStartX(startX);
        name.setStartY(startY);
        name.setEndX(endX);
        name.setEndY(endY);
        name.setStrokeWidth(strokeWidth);
    }
    public static void styleLineIdentifiers(Label name, double posX, double posY, Paint color){
        name.setLayoutX(posX);
        name.setLayoutY(posY);
        name.setTextFill(color);
    }
    public static void styleLabels(Label name, Font font, double posX, double posY){
        name.setFont(font);
        name.setLayoutX(posX);
        name.setLayoutY(posY);
    }
    public static void styleTextBoxes(TextField textFieldBoxes, String promptText, boolean traverse, double posX, double posY){
        textFieldBoxes.setPromptText(promptText);
        textFieldBoxes.setFocusTraversable(traverse);
        textFieldBoxes.setLayoutX(posX);
        textFieldBoxes.setLayoutY(posY);
    }
    public static void styleStage(Stage stage, Scene scene, String title, Boolean resizability){
        stage.setScene(scene);
        stage.setResizable(resizability);
        stage.setTitle(title);
    }
    public static void styleVBox(VBox box, int posX, int posY, int spacing){
        box.setLayoutX(posX);
        box.setLayoutY(posY);
        box.setSpacing(spacing);
    }
}
