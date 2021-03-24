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



public class AboutDev{
	
	private Pane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;
	private Image aboutUs;
	
	public AboutDev(){ //changed constructor method to public
		this.pane = new Pane();
		this.scene = new Scene(pane, 900,600);
		this.canvas = new Canvas(900,600);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties();
	}
	
	public void setProperties() {
		
		//Set Background Image:
		this.aboutUs = new Image("images/AboutUs.png",900,600,false,false);
		this.gc.drawImage(aboutUs, 0, 0 );	
		
		//Back Button:
		
		Button backbtn = new Button("<- Back to Main Screen");
		this.addEventHandler(backbtn);
		backbtn.setLayoutX(10);
		backbtn.setLayoutY(20);
		
		
		pane.getChildren().add(this.canvas);
		pane.getChildren().add(backbtn);
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