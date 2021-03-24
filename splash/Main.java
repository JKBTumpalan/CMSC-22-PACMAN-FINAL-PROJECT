package splash;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage mainStage) throws Exception{
		
		//Begin the start method by launching the loading screen
		LoadingScreen loadScreen = new LoadingScreen();
		loadScreen.setStage(mainStage);
		
		primaryStage = mainStage;
	}
	
	public static Stage getStage() {
		return primaryStage;
	}
	
	public static void setPrimaryStage(Stage stage) {
		primaryStage = stage;
	}
}
