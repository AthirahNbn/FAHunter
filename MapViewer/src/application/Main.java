/**
 * 
* This program is a Map Viewer application that will display 
* the whole map of the game, Diamond Hunter.
* 
* Main class is to run the source code and display user interface
* for the map viewer.
* 
* @author Athirah Binti Noorbaidrulnizar (014351)
* @author Fateen Farhanah Mohd Nasri (015419)
* @author Hanisah Binti Mohd Radzi (015382)
* @version 1.0
* @since   2016-12-15 
*/

package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
				Parent root = FXMLLoader.load(getClass().getResource("view/GUI.fxml"));
				Scene scene = new Scene(root, 760, 630);
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
				primaryStage.setTitle("Diamond Hunter Map Viewer");
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
