package splash;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;



public class About{
	
	private Pane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Image bg;
	private Image pacman;
	private Image ghost1,ghost2,ghost3,ghost4;
	
	public About(){ //changed constructor method to public
		this.pane = new Pane();
		this.scene = new Scene(pane, 900,600);
		this.canvas = new Canvas(900,600);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties();
	}
	
	public void setProperties() {
		
		//Set Background Image:
		this.bg = new Image("images/About.png",900,600,false,false);
		this.gc.drawImage(bg, 0, 0 );	
		
		
		//Set Title:
		Text title = new Text(300,70, "About Pacman");						
		title.setFont(Font.font("Consolas",FontWeight.BOLD,40));
		title.setFill(Color.YELLOW);
		
		
		//About Text:
		
		Text character = new Text(50,350,"Characters: ");
		character.setFont(Font.font("Consolas",FontWeight.BOLD,20));
		character.setFill(Color.YELLOW);
		
		//Set Images of Pacman and Ghosts:
		//PacMan
		
		this.pacman = new Image("images/pac-man.png",50,50,false,false);
		this.gc.drawImage(pacman, 100, 370);
		Text pacmaninfo = new Text(100,450,"Pac-Man");
		pacmaninfo.setFont(Font.font("Consolas",FontWeight.BOLD,15));
		pacmaninfo.setFill(Color.YELLOW);
	
		
		this.ghost1 = new Image("images/blinky.png",50,50,false,false);
		this.gc.drawImage(ghost1, 250, 370);
		Text ghost1info = new Text(250,450,"Blinky");
		ghost1info.setFont(Font.font("Consolas",FontWeight.BOLD,15));
		ghost1info.setFill(Color.RED);
		
		
		
		this.ghost2 = new Image("images/Clyde.png",50,50,false,false);
		this.gc.drawImage(ghost2, 400, 370);
		Text ghost2info = new Text(400,450,"Clyde");
		ghost2info.setFont(Font.font("Consolas",FontWeight.BOLD,15));
		ghost2info.setFill(Color.ORANGE);
		
		
		
		this.ghost3 = new Image("images/inky.png",50,50,false,false);
		this.gc.drawImage(ghost3, 550, 370);
		Text ghost3info = new Text(550,450,"Inky");
		ghost3info.setFont(Font.font("Consolas",FontWeight.BOLD,15));
		ghost3info.setFill(Color.BLUE);
		
		
		this.ghost4 = new Image("images/pinky.png",50,50,false,false);
		this.gc.drawImage(ghost4, 700,370);
		Text ghost4info = new Text(700,450,"Pinky");
		ghost4info.setFont(Font.font("Consolas",FontWeight.BOLD,15));
		ghost4info.setFill(Color.PINK);
		
		//Back Button:
		
		Button backbtn = new Button("<- Back to Main Screen");
		this.addEventHandler(backbtn);
		backbtn.setLayoutX(10);
		backbtn.setLayoutY(20);
		
		
		pane.getChildren().add(this.canvas);
		pane.getChildren().addAll(title, character, pacmaninfo, ghost1info, ghost2info, ghost3info, ghost4info, backbtn);
		this.scene.getStylesheets().add("mainCSS.css");
	}
	
	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				//insert call for previous scene
				MainMenu.mainStage.setTitle("Pacman");
				MainMenu.setScene(MainMenu.getScene());
			}
		});
	}

	public Scene getScene(){ //changed getScene method from private to public.
		return this.scene;
	}
	
}