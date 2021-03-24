package game;

import java.util.Random;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Wall extends Sprite{
	//attributes
	private String name;
	
	//image
	public final static Image 	WALL_IMAGE = new Image("images/wall.png",Wall.WALL_WIDTH,Wall.WALL_WIDTH,false,false);
	public final static Image GHOST_GATE = new Image("images/gate.png",Wall.WALL_WIDTH,Wall.WALL_WIDTH,false,false);
	private final static int WALL_WIDTH = 15;
	public final static String WALL = "wall";
	public final static String GATE = "gate";

	//constructor
	public Wall(String name, int x, int y){
		super(x,y,Wall.WALL_WIDTH);
		this.name = name;
		this.loadImage(name);
	}
	
	//getters

	
	private void loadImage(String name) {
		if(name.equals(GATE)) {
			this.loadImage(GHOST_GATE);
		}else {
			this.loadImage(WALL_IMAGE);
		}
		
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name) {

		this.name = name;
	}
}
