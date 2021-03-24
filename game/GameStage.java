package game;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import splash.MainMenu;

public class GameStage {
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 900;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private MediaPlayer playSong;

	//the class constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.BLACK);	
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);	
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene);
		this.root.getChildren().add(canvas);
		setSceneProperties();
		
	}
	
	public void setSceneProperties() {
		//sound at the start of the game
		mediaPlayer();
		
		this.gametimer.start();
		Button backbtn = new Button("<- Back to Main Screen");
		this.addEventHandler(backbtn);
		backbtn.setLayoutX(WINDOW_WIDTH*.7);
		backbtn.setLayoutY(WINDOW_HEIGHT*.7);
		

		root.setStyle("-fx-background-color: Black;-fx-border-color: White;");
		root.getChildren().add(backbtn);
		this.scene.getStylesheets().add("mainCSS.css");
		
	}
	
	private void mediaPlayer() {
		
		String musicFile = "src/images/StartMusic.mp3";     // For example
		Media sound = new Media(new File(musicFile).toURI().toString());
		this.playSong = new MediaPlayer(sound);
		this.playSong.setAutoPlay(true);
	}
		
	

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				//insert call for previous scene
				MainMenu.setScene(MainMenu.getScene());
				gametimer.stop();
			}
		});
	}
	public Scene getScene() {
		return this.scene;
	}
	
}

