package game;

import javafx.scene.image.Image;

public class PowerPellet extends Food{
	public static int worth;
	public final static Image PELLET_IMAGE = new Image("images/powerpellet.gif",PowerPellet.PELLET_WIDTH,PowerPellet.PELLET_WIDTH,true,false);
	private final static int PELLET_WIDTH = 30;

	PowerPellet(int xPos, int yPos){
		super(xPos, yPos, PowerPellet.PELLET_WIDTH);
		PowerPellet.worth = 50;
		this.loadImage(PELLET_IMAGE);
	}
	
	public void eat() {
		this.setVisible(false);
	}
}
