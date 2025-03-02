package gov.jets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu menu;
    protected final MenuItem new_item;
    protected final MenuItem open_item;
    protected final MenuItem save_item;
    protected final MenuItem exit_item;
    protected final Menu menu0;
    protected final MenuItem delete_item;
    protected final MenuItem undo_item;
    protected final MenuItem cut_item;
    protected final MenuItem copy_item;
    protected final MenuItem paste_item;
    protected final MenuItem select_item;
    protected final Menu menu1;
    protected final MenuItem about_item;
    protected final AnchorPane anchorPane;
    protected final ScrollPane scrollPane;
    protected final TextArea textArea;
    private File currentFile = null;
    FileChooser fileChooser = new FileChooser();


    public Controller(Stage primaryStage) {

        menuBar = new MenuBar();
        menu = new Menu();
        new_item = new MenuItem();
        open_item = new MenuItem();
        save_item = new MenuItem();
        exit_item = new MenuItem();
        menu0 = new Menu();
        undo_item = new MenuItem();
        delete_item = new MenuItem();
        cut_item = new MenuItem();
        copy_item = new MenuItem();
        paste_item = new MenuItem();
        select_item = new MenuItem();
        
        menu1 = new Menu();
        about_item = new MenuItem();
        anchorPane = new AnchorPane();
        scrollPane = new ScrollPane();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        menu.setMnemonicParsing(false);
        menu.setText("File");

        new_item.setMnemonicParsing(false);
        new_item.setText("New");

        open_item.setMnemonicParsing(false);
        open_item.setText("Open");
        
        save_item.setMnemonicParsing(false);
        save_item.setText("Save");

        exit_item.setMnemonicParsing(false);
        exit_item.setText("Exit");

        menu0.setMnemonicParsing(false);
        menu0.setText("Edit");
        
        undo_item.setMnemonicParsing(true);
        undo_item.setText("Undo");

        delete_item.setMnemonicParsing(false);
        delete_item.setText("Delete");

        cut_item.setMnemonicParsing(true);
        cut_item.setText("Cut");

        copy_item.setMnemonicParsing(true);
        copy_item.setText("Copy");

        paste_item.setMnemonicParsing(true);
        paste_item.setText("Paste");

        select_item.setMnemonicParsing(true);
        select_item.setText("Select All");

        menu1.setMnemonicParsing(false);
        menu1.setText("Help");

        about_item.setMnemonicParsing(false);
        about_item.setText("About");
        setTop(menuBar);

        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        scrollPane.setPrefHeight(200.0);
        scrollPane.setPrefWidth(200.0);

        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setPrefHeight(375.0);
        textArea.setPrefWidth(598.0);
        scrollPane.setContent(textArea);
        setCenter(anchorPane);

        menu.getItems().add(new_item);
        menu.getItems().add(open_item);
        menu.getItems().add(save_item);
        menu.getItems().add(exit_item);
        menuBar.getMenus().add(menu);
        menu0.getItems().add(undo_item);
        menu0.getItems().add(cut_item);
        menu0.getItems().add(copy_item);
        menu0.getItems().add(paste_item);
         menu0.getItems().add(delete_item);
        menu0.getItems().add(select_item);
        menuBar.getMenus().add(menu0);
        menu1.getItems().add(about_item);
        menuBar.getMenus().add(menu1);
        anchorPane.getChildren().add(scrollPane);
     /********************************************************/   
        // open
        open_item.setOnAction((final ActionEvent e) -> {
            File file = fileChooser.showOpenDialog(primaryStage);

            try (FileInputStream fis = new FileInputStream(file)) {
                StringBuilder content = new StringBuilder();
                int byteRead;
        
                // Read the file byte by byte
                while ((byteRead = fis.read()) != -1) {
                    content.append((char) byteRead);  // Convert byte to char and append
                }
                // Set the content to the TextArea
                textArea.setText(content.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error reading the file.");
                }
        });
        //save
        save_item.setOnAction((final ActionEvent e) -> {
            if (currentFile == null) {
                // Show save dialog if no file is currently open
                currentFile = fileChooser.showSaveDialog(primaryStage);
            }
            if (currentFile != null) {
                try (FileOutputStream fos = new FileOutputStream(currentFile)) {
                    String content = textArea.getText();
                    if (content != null) {
                        // Write the content in one go
                        fos.write(content.getBytes());
                    }
        
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error writing to the file.");
                }
            }
        });
          // new
        new_item.setOnAction((final ActionEvent e) -> {
            if(!textArea.getText().isEmpty()){
                ButtonType saveButton = new ButtonType("Save");
                ButtonType dontSaveButton = new ButtonType("Don't Save");
                ButtonType cancelButton = ButtonType.CANCEL;
                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save changes?");
                alert.getButtonTypes().setAll(saveButton , dontSaveButton,cancelButton);
                
                //handle alert buttons
                Optional<ButtonType> response = alert.showAndWait();
                if (response.isPresent()) {
                    if (response.get() == saveButton) {
                        fileChooser.showSaveDialog(primaryStage);
                    } else if (response.get() == dontSaveButton) {
                        textArea.clear();
                    }
                }
                textArea.clear();
            }
        });
        about_item.setOnAction((ActionEvent e) -> {
            Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
            aboutAlert.setTitle("About Notepad");
            aboutAlert.setHeaderText("Notepad Application");
            aboutAlert.setContentText("Developed by: Hadeer");
            aboutAlert.showAndWait();
        });
        undo_item.setOnAction((ActionEvent e) -> {
            textArea.undo();
        });
        cut_item.setOnAction((ActionEvent e) -> {
            textArea.cut();
        });
        copy_item.setOnAction((ActionEvent e) -> {
            textArea.copy();
        });
        paste_item.setOnAction((ActionEvent e) -> {
            textArea.paste();
        });
        select_item.setOnAction((ActionEvent e) -> {
            textArea.selectAll();
        });
        delete_item.setOnAction((ActionEvent e) -> {
            textArea.replaceSelection(""); 
        });
        // exit
        exit_item.setOnAction((final ActionEvent e) -> {
            primaryStage.close();
        });
}
}