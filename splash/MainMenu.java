package splash;

import game.GameStage;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenu {
	static Scene mainScene;
	private Pane mainPane;
	//private Group mainGroup;
	private VBox mainBox;
	static Stage mainStage;
	private Canvas mainCanvas;
	private GraphicsContext mainGC;
	
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 600;
	
	public final Image bg = new Image("Images/FadedBackground.png", 900, 600, false, false);
	
//	int adjustment=50;
	
	//Reference: https://www.stickpng.com/img/games/pac-man/pac-man
	private final Image staticPac = new Image("Images/pacman-static.png", 100, 100, false, false);
	//Reference: https://www.cleanpng.com/png-pac-man-logo-font-brand-clip-art-video-game-fashio-6078119/
	private final Image textLogo = new Image("Images/pacman-textlogo.png", 450, 100, false, false);

	public MainMenu(Stage stage){
		this.mainBox = new VBox();
		this.mainStage = stage;
		this.mainPane = new Pane();
		//this.mainGroup = new Group();
		this.mainCanvas = new Canvas(MainMenu.WINDOW_WIDTH, MainMenu.WINDOW_HEIGHT);
		this.mainGC = mainCanvas.getGraphicsContext2D();
		mainScene = new Scene(mainPane, MainMenu.WINDOW_WIDTH, MainMenu.WINDOW_HEIGHT);
	}
		
	public void setStage(Stage mainStage) {
		
		mainScene.getStylesheets().add("mainCSS.css");
		Button startBtn = new Button("   START   ");
		Button instrctBtn = new Button("   HOW TO PLAY   ");
		Button abtBtn = new Button("   ABOUT GAME    ");
		Button devBtn = new Button("   ABOUT US   ");
		Button exitBtn = new Button("   EXIT   ");
		
		this.mainGC.drawImage(bg,0,0);
		this.mainGC.drawImage(textLogo, MainMenu.WINDOW_WIDTH*0.25, 20);

		
		mainBox.getChildren().addAll( startBtn, instrctBtn, abtBtn,devBtn, exitBtn);
		mainBox.setPadding(new Insets(10,10,10,10));
		mainBox.setLayoutX(MainMenu.WINDOW_WIDTH*0.4);
		mainBox.setLayoutY(MainMenu.WINDOW_HEIGHT*0.25);
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setSpacing(40);
		mainPane.getChildren().add(mainCanvas);
		mainPane.getChildren().add(mainBox);
		
		startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event1) {
				handleButtons("start");
			}
		});
		instrctBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event1) {
				handleButtons("instructions");
			}
		});
		abtBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event1) {
				handleButtons("abtGame");
			}
		});
		devBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event1) {
				handleButtons("abtUs");
			}
		});
		exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event1) {
				handleButtons("exit");
			}
		});
		
		mainStage.setScene(mainScene);
		mainStage.setTitle("PACMAN");
		mainStage.show();
	}
	
	public void handleButtons(String btnName) {
		if (btnName.equals("start")) {
			System.out.println("Starting game...");
			newGame();
		} else if (btnName.equals("instructions")) {
			//Set a whole new pane for instructions
			System.out.println("  [ GAME INSTRUCTIONS ]  ");
			setInstructionsPage();
		} else if (btnName.equals("abtGame")) {
			System.out.println("  [ GAME HISTORY ] ");
			setAboutPane();
			
		} else if (btnName.equals("abtUs")) {
			System.out.println(" [ DEVELOPERS' DETAILS ");
			setAboutDev();
		} else if (btnName.equals("exit")) {
			System.out.println("Exiting game...");
			System.exit(0);
		}
	}
	
	public static void newGame(){
		
		PauseTransition pause = new PauseTransition(Duration.seconds(0.005));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
//				NewGame mainGame = new NewGame();
				
				GameStage game = new GameStage();
				mainStage.setScene(game.getScene());
				mainStage.setTitle("Pacman 1.0");
//				mainStage.setScene(mainGame.getScene());
			}
		});
		pause.play();
	}
	
	private void setAboutPane(){
		
		PauseTransition pause = new PauseTransition(Duration.seconds(0.005));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				About about = new About();
				mainStage.setTitle("About Pacman");
				mainStage.setScene(about.getScene());
			}
		});
		pause.play();
	}
	
private void setAboutDev(){
		
		PauseTransition pause = new PauseTransition(Duration.seconds(0.005));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				AboutDev aboutdev = new AboutDev();
				mainStage.setTitle("About the Developers");
				mainStage.setScene(aboutdev.getScene());
			}
		});
		pause.play();
	}
	
	
	private void setInstructionsPage(){
		
		PauseTransition pause = new PauseTransition(Duration.seconds(0.005));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				InstructionStage instructPane = new InstructionStage();
				mainStage.setTitle("Game Instructions");
				mainStage.setScene(instructPane.getScene());
			}
		});
		
		pause.play();
	}
	
	public static Scene getScene() {
		return mainScene;
	}
	
	public static void setScene(Scene scene) {
		mainStage.setScene(scene);
	}
	
	
}
