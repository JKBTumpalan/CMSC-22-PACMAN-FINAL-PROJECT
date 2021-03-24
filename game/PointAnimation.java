package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PointAnimation extends Sprite{
	
	private int worth;
	public final static Image TEN_PT_IMAGE = new Image("images/coin.png",PointAnimation.TEN_PT_WIDTH,PointAnimation.TEN_PT_WIDTH,false,false);
	public final static Image H2_PT_IMAGE = new Image("images/200.png",60,40,false,false);
	public final static Image H4_PT_IMAGE = new Image("images/400.png",50,35,false,false);
	public final static Image H8_PT_IMAGE = new Image("images/800.png",50,35,false,false);
	public final static Image H16_PT_IMAGE = new Image("images/1600.png",60,40,false,false);
	public final static Image POWER_IMAGE = new Image("images/powertext.png",150,80,false,false);
	public final static Image FRUIT_SCORE = new Image("images/200.png",60,40,false,false);
	private final static int TEN_PT_WIDTH = 10;


	public PointAnimation(int xPos, int yPos, int size, int worth) {
		super(xPos, yPos, size);
		this.worth = worth;
		this.dy = -3;
		if(worth == 10)
			this.loadImage(TEN_PT_IMAGE);
		else if(worth == 0) {
			this.loadImage(POWER_IMAGE);
			this.size = 40;
		} else if (worth == 200) {
			this.loadImage(H2_PT_IMAGE);
		}else if (worth == 400) {
			this.loadImage(H4_PT_IMAGE);
		}else if (worth == 800) {
			this.loadImage(H8_PT_IMAGE);
		}else if (worth == 1600) {
			this.loadImage(H16_PT_IMAGE);
		}
		
		
		initializeAnimation();
		
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(
			new KeyFrame(Duration.seconds(.6),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						stop();
						setVisible(false);
					}
				}));
		timeline.play();
	
	}
	
	
	public void stop() {
		this.dx = this.dy = 0;
	}
	
	public void initializeAnimation() {
		this.x += this.dx;
		this.y += this.dy;
	}

}
