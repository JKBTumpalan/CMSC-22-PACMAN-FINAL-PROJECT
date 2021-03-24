package splash;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen {
	//attributes
	private Scene scene;
	private Stage stage;
	private Group group;
	private Canvas canvas;
	private GraphicsContext gc;
	private Element pacmanLoading;
	private Element loadingBar;
	//constants
	public static final int WINDOW_WIDTH=900;
	public static final int WINDOW_HEIGHT=600;
	
	//constructor
	public LoadingScreen(){
		this.group=new Group();
		this.scene=new Scene(group,LoadingScreen.WINDOW_WIDTH,LoadingScreen.WINDOW_HEIGHT,Color.BLACK);
		this.canvas=new Canvas(LoadingScreen.WINDOW_WIDTH,LoadingScreen.WINDOW_HEIGHT);
		this.gc=canvas.getGraphicsContext2D();
		this.pacmanLoading=new Element(0,100,Element.PACMAN_LOADING_TYPE);
		this.loadingBar=new Element(200,325,Element.LOADING_BAR_TYPE);
	}
	
	
	//method in setting the class stage
	public void setStage(Stage stage) {
		this.stage=stage;
		this.addComponents();
		
		Timeline timeline=new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(7),
						new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								MainMenu menu=new MainMenu(stage);
								menu.setStage(stage);
								timeline.stop();
							}
						}));
		timeline.playFromStart();
		
		this.stage.setTitle("PAC-MAN");
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	//method in adding the GUI components into the stage
	private void addComponents() {
		gc.clearRect(0,0,LoadingScreen.WINDOW_WIDTH,LoadingScreen.WINDOW_HEIGHT);

		//loading and adding images to the stage
		ImageView pacmanLoadingIV = new ImageView();
		addToStage(this.pacmanLoading,pacmanLoadingIV);
		
		ImageView loadingBarIV = new ImageView();
		addToStage(this.loadingBar,loadingBarIV);
		
		//adding the stage components to the group
		this.group.getChildren().add(canvas);
		this.group.getChildren().add(pacmanLoadingIV);
		this.group.getChildren().add(loadingBarIV);
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
