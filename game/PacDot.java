package game;

import javafx.scene.image.Image;

public class PacDot extends Food{
	public static int worth;
	public final static Image PELLET_IMAGE = new Image("images/pacdot.png",PacDot.PELLET_WIDTH,PacDot.PELLET_WIDTH,false,false);
	private final static int PELLET_WIDTH = 15;

	PacDot(int xPos, int yPos){
		super(xPos, yPos, PacDot.PELLET_WIDTH);
		PacDot.worth = 10;
		this.loadImage(PELLET_IMAGE);
	}
	
	public void setVisible(boolean value){
		this.visible = value;
	}
	
	public void eat() {
		this.setVisible(false);
	}
}
