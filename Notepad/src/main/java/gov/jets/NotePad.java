
package gov.jets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class NotePad extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Controller notepad = new Controller(primaryStage);
                
        StackPane root = new StackPane();
        root.getChildren().add(notepad);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Notpad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
