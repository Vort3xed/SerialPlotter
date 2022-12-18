package com.company;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.company.GeneratePlane.createGridPattern;
import static com.company.StaticNodes.xAxis;
import static com.company.StaticNodes.yAxis;

public class SerialPlotter extends Application {


    static Group g = new Group();
    static Pane pointPlane = new Pane();
    static Scene scene = new Scene(g, 800, 600);
    static TranslateTransition transition;

    public void start(Stage stage) {
        Rectangle coverRectangle = new Rectangle();
        Styling.styleRectangles(coverRectangle,0,0,50,500);
        coverRectangle.setFill(createGridPattern());
        g.getChildren().addAll(coverRectangle,pointPlane);
        Styling.styleStage(stage, scene, "Serial Plotter", true);
        stage.show();
        SetInterface vslInterface = new SetInterface(true, "grid");
        initializeInterface(vslInterface);

        animate(extractFileData());

    }

    public static void initializeInterface(SetInterface interfaceInfo) {
        if (interfaceInfo.getModifyBackground().equals("grid")) {
            scene.setFill(createGridPattern());
        } else if (interfaceInfo.getModifyBackground().equals("gradient")) {
            scene.setFill(new LinearGradient(
                    0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#5f0086")),
                    new Stop(1, Color.web("#010080")))
            );
        }
        if (interfaceInfo.isAllowAxis()) {
            Styling.styleLines(xAxis, 50, 50, 50, 500, 5);
            Styling.styleLines(yAxis, 50, 500, 700, 500, 5);
            g.getChildren().add(xAxis);
            g.getChildren().add(yAxis);
        }

    }

    public static String[] extractFileData() {
        try {
            List<String> listOfStrings = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader("src/com/company/DataVis/data.txt"));
            String line = bf.readLine();

            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
            bf.close();
            return listOfStrings.toArray(new String[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static void animate(String[] array){
        Circle previousCircle = null;
        for (int i = 0; i < array.length; i++) {
            Circle circle = new Circle(i * 50 + 100, 500 - Integer.parseInt(array[i])*5, 1);
            pointPlane.getChildren().add(circle);

            if (previousCircle != null) {
                Line line = new Line(previousCircle.getCenterX(), previousCircle.getCenterY(),
                        circle.getCenterX(), circle.getCenterY());
                pointPlane.getChildren().add(line);
            }

            previousCircle = circle;
        }
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(pointPlane.translateXProperty(),
                -500 - array.length*10)));
        timeline.play();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
