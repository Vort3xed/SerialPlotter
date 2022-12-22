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
import javafx.scene.text.Font;
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
    static int step = 50;
    static int range = 5;
    static double speedFactor = 5;
    static boolean realTimeDisplay = true;

    public void start(Stage stage) {
        g.getChildren().add(pointPlane);
        Styling.styleStage(stage, scene, "Serial Plotter", true);
        stage.show();

        SetInterface vslInterface = new SetInterface(true, "grid");
        initializeInterface(vslInterface);

        Styling.styleRectangles(leftCoverRectangle,0,0,47.1,500,createGridPattern());
        Styling.styleRectangles(rightCoverRectangle,1160,0,210,500,createGridPattern());
        g.getChildren().addAll(leftCoverRectangle,rightCoverRectangle);

        Styling.styleVBox(buttonArray,50, 525,7);
        Styling.styleVBox(setterArray,500,550,7);
        g.getChildren().addAll(buttonArray,setterArray);

        toggleRealTime.setOnAction(this::handleRealTimeModulation);
        parseProvidedData.setOnAction(this::handleVisualizeData);
        uploadFileToVisualize.setOnAction(this::handleFileSelection);
        enterData.setOnAction(this::handleDataParsing);
    }

    public static void initializeInterface(SetInterface interfaceInfo) {
        g.getChildren().addAll(uploadFileToVisualize,statusIdentifier);
        Styling.styleButtons(uploadFileToVisualize,240,525,"Upload Data",60,180,"-fx-background-color: #808080");
        uploadFileToVisualize.setMaxWidth(180);

        Styling.styleButtons(toggleRealTime,"Displaying Realtime",60,180,"-fx-background-color: #50C878");
        Styling.styleButtons(parseProvidedData,"Parse Data",60,180,"-fx-background-color: #808080");
        buttonArray.getChildren().addAll(toggleRealTime,parseProvidedData);

      //  Styling.styleLabels();
        Styling.styleLabels(statusIdentifier, Font.font(18), "Enter Range and Step",500,520);
        Styling.styleTextBoxes(setRange,"Enter Y Range",true);
        Styling.styleTextBoxes(setStep,"Enter Step",true);
        Styling.styleTextBoxes(setSpeed,"Enter Speed",true);
        Styling.styleButtons(enterData,"Submit",30,90,"-fx-background-color: #808080");
        setterArray.getChildren().addAll(setRange,setStep,setSpeed,enterData);

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
    private void handleFileSelection(ActionEvent event) {
        if (canContinueParsing) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select file");
            File selectedFile = fileChooser.showOpenDialog(uploadFileToVisualize.getScene().getWindow());
            if (selectedFile != null) {
                selectedFileLocation = selectedFile.getAbsolutePath();
                uploadFileToVisualize.setText("Uploaded: "+selectedFile.getName());
            }
        }
    }
    private void handleDataParsing(ActionEvent event) {
        try {
            step = Integer.parseInt(setStep.getText());
            range = Integer.parseInt(setRange.getText());
            if (realTimeDisplay) {
                speedFactor = Integer.parseInt(setSpeed.getText());
                setSpeed.clear();
            }
            setStep.clear();
            setRange.clear();
            statusIdentifier.setText("Parse Successful");
        } catch (Exception e) {
            statusIdentifier.setText("Parse Failed! Integers Only!");
        }
    }
    private void handleRealTimeModulation(ActionEvent event) {
        if (realTimeDisplay) {
            setterArray.getChildren().remove(setSpeed);
            realTimeDisplay = false;
            toggleRealTime.setStyle("-fx-background-color: #6082B6");
            toggleRealTime.setText("Displaying Instant");
        } else {
            setterArray.getChildren().remove(enterData);
            setterArray.getChildren().add(setSpeed);
            realTimeDisplay = true;
            setterArray.getChildren().add(enterData);
            toggleRealTime.setStyle("-fx-background-color: #50C878");
            toggleRealTime.setText("Displaying Realtime");
        }
    }//#32CD32
    private void handleVisualizeData(ActionEvent event) {
        animate(extractFileData());
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

    public static void animate(String[] array){
        if (canContinueParsing) {
            Circle previousCircle = null;
            for (int i = 0; i < array.length; i++) {
                Circle circle = new Circle(i * step + 1250, 500 - Integer.parseInt(array[i]) * range, 3);
                pointPlane.getChildren().add(circle);

                if (previousCircle != null) {
                    Line line = new Line(previousCircle.getCenterX(), previousCircle.getCenterY(),
                            circle.getCenterX(), circle.getCenterY());
                    pointPlane.getChildren().add(line);
                }

                previousCircle = circle;
            }
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds((speedFactor/1000) * step * array.length), new KeyValue(pointPlane.translateXProperty(),
                    -500 - array.length * step)));
            timeline.play();

        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
