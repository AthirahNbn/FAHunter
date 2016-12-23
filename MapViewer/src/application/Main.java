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
				Scene scene = new Scene(root, 752, 640);
				
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
