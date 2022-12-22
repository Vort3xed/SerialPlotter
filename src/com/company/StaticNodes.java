package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class StaticNodes {
    static Line yAxis = new Line();
    static Line xAxis = new Line();

    static Button toggleRealTime = new Button();
    static Button parseProvidedData = new Button();
    static Button uploadFileToVisualize = new Button();

    static Rectangle leftCoverRectangle = new Rectangle();
    static Rectangle rightCoverRectangle = new Rectangle();
    static VBox buttonArray = new VBox();

    static Label statusIdentifier = new Label();
    static TextField setStep = new TextField();
    static TextField setRange = new TextField();
    static TextField setSpeed = new TextField();
    static Button enterData = new Button();
    static VBox setterArray = new VBox();
}
