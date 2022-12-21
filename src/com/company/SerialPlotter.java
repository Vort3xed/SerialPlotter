package com.company;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.company.GeneratePlane.createGridPattern;
import static com.company.StaticNodes.*;

public class SerialPlotter extends Application {

    static Group g = new Group();
    static Pane pointPlane = new Pane();
    static Scene scene = new Scene(g, 1200, 680);
    static boolean canContinueParsing = true;

    static String selectedFileLocation = "src/com/company/DataVis/data.txt";

    public void start(Stage stage) {
        g.getChildren().add(pointPlane);
        Styling.styleStage(stage, scene, "Serial Plotter", true);
        stage.show();

        SetInterface vslInterface = new SetInterface(true, "grid");
        initializeInterface(vslInterface);

        Styling.styleRectangles(leftCoverRectangle,0,0,47.1,500,createGridPattern());
        Styling.styleRectangles(rightCoverRectangle,1160,0,210,500,createGridPattern());
        g.getChildren().addAll(leftCoverRectangle,rightCoverRectangle);

        Styling.styleVBox(buttonArray,50, 525,10);
        g.getChildren().addAll(buttonArray);

        toggleRealTime.setOnAction(this::handleRealTimeModulation);
        parseProvidedData.setOnAction(this::handleVisualizeData);
        uploadFileToVisualize.setOnAction(this::handleFileSelection);

    }

    private void handleFileSelection(ActionEvent event) {
        if (canContinueParsing) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select file");
            File selectedFile = fileChooser.showOpenDialog(uploadFileToVisualize.getScene().getWindow());
            if (selectedFile != null) {
                selectedFileLocation = selectedFile.getAbsolutePath();
            }
        }
    }

    public static void initializeInterface(SetInterface interfaceInfo) {
        buttonArray.getChildren().addAll(toggleRealTime,parseProvidedData);
        g.getChildren().add(uploadFileToVisualize);
        Styling.styleButtons(toggleRealTime,"Displaying Realtime",60,180,"-fx-background-color: #00ff00");
        Styling.styleButtons(parseProvidedData,"Parse Data",60,180,"-fx-background-color: #808080");
        Styling.styleButtons(uploadFileToVisualize,240,525,"Upload Data",60,100,"-fx-background-color: #808080");
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
            Styling.styleLines(xAxis, 50, 500, 1150, 500, 5);
            Styling.styleLines(yAxis, 50, 50, 50, 500, 5);

            g.getChildren().add(xAxis);
            g.getChildren().add(yAxis);
        }

    }
    private void handleRealTimeModulation(ActionEvent event) {
        System.out.println("hi");
    }
    private void handleVisualizeData(ActionEvent event) {
        animate(extractFileData(),50);
        canContinueParsing = true;
    }

    public static String[] extractFileData() {
        try {
            List<String> listOfStrings = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader(selectedFileLocation));
            String line = bf.readLine();
            //C:/Users/agney/OneDrive/Desktop/hmm.txt
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

    public static void animate(String[] array,int step){
        if (canContinueParsing) {
            Circle previousCircle = null;
            for (int i = 0; i < array.length; i++) {
                Circle circle = new Circle(i * step + 1250, 500 - Integer.parseInt(array[i]) * 5, 3);
                pointPlane.getChildren().add(circle);

                if (previousCircle != null) {
                    Line line = new Line(previousCircle.getCenterX(), previousCircle.getCenterY(),
                            circle.getCenterX(), circle.getCenterY());
                    pointPlane.getChildren().add(line);
                }

                previousCircle = circle;
            }
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5 + 0.05 * array.length), new KeyValue(pointPlane.translateXProperty(),
                    -500 - array.length * 10)));
            timeline.play();

        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
