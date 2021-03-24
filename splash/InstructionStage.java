package splash;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InstructionStage {
	//attributes
	private Scene scene;
	private Stage stage;
	private Group group;
	private Canvas canvas;
	private GraphicsContext gc;
	private Element pacmanInstruction;
	private Element questionL;
	private Element questionR;
	private Button backbtn;
		
	//constants
	public static final int WINDOW_WIDTH=900;
	public static final int WINDOW_HEIGHT=600;
	
	//constructor
	public InstructionStage(){
		this.group=new Group();
		this.scene=new Scene(group,InstructionStage.WINDOW_WIDTH,InstructionStage.WINDOW_HEIGHT,Color.BLACK);
		this.canvas=new Canvas(InstructionStage.WINDOW_WIDTH,InstructionStage.WINDOW_HEIGHT);
		this.gc=canvas.getGraphicsContext2D();
		this.pacmanInstruction=new Element(300,75,Element.PACMAN_INSTRUCTION_TYPE);
		this.questionL=new Element(240,10,Element.QUESTION_TYPE);
		this.questionR=new Element(610,10,Element.QUESTION_TYPE);
		this.backbtn=new Button("<- Back to Main Screen");
		this.addComponents();
	}
	
	//method in adding the GUI components into the stage
	private void addComponents() {
		String instructions="\n[1] Play as Pac-Man and eat all the PacDots in the map!"
				+ "\n\n[2] Collect Power Pellets in every quadrant of the map"
				+ " for a surprise!"
				+ "\n\n[3] Fruits randomly appear in the game,"
				+ " collect them before they disappear!"
				+ "\n\n[4] Don't let the ghosts touch you!";
		gc.clearRect(0,0,InstructionStage.WINDOW_WIDTH,InstructionStage.WINDOW_HEIGHT);
		
		//text UI modifications
		this.gc.setFill(Color.WHITE);
		Font titleFont=Font.font("Consolas",FontWeight.BOLD,50);
		Font contextFont=Font.font("Consolas",FontWeight.LIGHT,20);
		this.gc.setFont(titleFont);
		this.gc.fillText("How to Play",300,50);
		this.gc.setFill(Color.YELLOW);
		this.gc.setFont(contextFont);
		this.gc.fillText(instructions,30,400);
		this.gc.setFill(Color.WHITE);
		
		//button modifications
		this.backbtn.setLayoutX(30);
		this.backbtn.setLayoutY(25);
		
		//action when the back button is clicked
		backbtn.setOnMouseClicked(new EventHandler <MouseEvent>(){
			public void handle(MouseEvent e) {
				MainMenu.mainStage.setTitle("Pacman");
				MainMenu.setScene(MainMenu.getScene());
			}
		});

		//loading and adding images to the stage
		ImageView pacmanInstructionIV = new ImageView();
		addToStage(this.pacmanInstruction,pacmanInstructionIV);
		
		ImageView questionLIV = new ImageView();
		addToStage(this.questionL,questionLIV);
		
		ImageView questionRIV = new ImageView();
		addToStage(this.questionR,questionRIV);
		
		//adding the stage components to the group
		this.group.getChildren().add(canvas);
		this.group.getChildren().add(pacmanInstructionIV);
		this.group.getChildren().add(questionLIV);
		this.group.getChildren().add(questionRIV);
		this.group.getChildren().add(backbtn);
		
		this.scene.getStylesheets().add("mainCSS.css");
	}
	
	//adds the image to the stage
	private void addToStage(Element elem, ImageView iv) {
		elem.render(iv);
	}
	
	//getter
	public Scene getScene() {
		return this.scene;
	}
}
