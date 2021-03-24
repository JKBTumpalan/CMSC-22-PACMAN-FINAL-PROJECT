package game;

import javafx.scene.image.Image;

public class Fruit extends Food{
	private int worth;
	public final static Image FRUIT_IMAGE = new Image("images/fruit.png",Fruit.FRUIT_WIDTH,Fruit.FRUIT_WIDTH,false,false);
	private final static int FRUIT_WIDTH = 30;

	Fruit(int xPos, int yPos){
		super(xPos, yPos, Fruit.FRUIT_WIDTH);
		this.worth = 200;
		this.loadImage(FRUIT_IMAGE);
		this.setVisible(false);
	}
}
