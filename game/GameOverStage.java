package game;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import splash.MainMenu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverStage {
	//attributes
	private Group group;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Image gameover;
	private Image gamewin;
	private int score;
	private boolean winner;
	
	//constructor
	GameOverStage(int score, boolean winner){
		this.group = new Group();
		this.scene = new Scene(group, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.BLACK);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.score=score;
		this.winner=winner;
		this.scene.getStylesheets().add("mainCSS.css");
		this.setProperties();
	}
	
	private void setProperties(){
		this.gc.clearRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT); //clears the canvas
		group.getChildren().add(this.canvas);

		Font theFont = Font.font("Consolas",FontWeight.BOLD,40);//set font type, style and size
		this.gc.setFont(theFont);											
	
		//prompts the message
		if(this.winner) {
			this.gamewin = new Image("images/YouWon.png",350,80, false, false);
			this.gc.drawImage(gamewin,GameStage.WINDOW_WIDTH*0.3, 20);
		}else {
			this.gameover = new Image("images/gameover.png",350,80, false, false);
			this.gc.drawImage(gameover,GameStage.WINDOW_WIDTH*0.32, 20);
		}
		
		//shoes the score
		this.gc.setFill(Color.YELLOW);										//set font color of text
		this.gc.fillText("Final Score: "+this.score, GameStage.WINDOW_WIDTH*0.3, GameStage.WINDOW_HEIGHT*0.35);
		
		//buttons
		
		Button playbtn = new Button("Play Again");

		//displays the main game stage when the user clicks play again
		playbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				MainMenu.newGame();
			}
		});
		
		Button exitbtn = new Button("Exit Game");
		
		//closes the stage when the user clicks exit
		exitbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.exit(0);
			}
		});
		
		//button properties
		playbtn.setLayoutX(400);
		playbtn.setLayoutY(300);
		exitbtn.setLayoutX(400);
		exitbtn.setLayoutY(400);
		group.getChildren().add(exitbtn);
		group.getChildren().add(playbtn);
	}
	
	//adds the image to the stage
	
	Scene getScene(){
		return this.scene;
	}
}
