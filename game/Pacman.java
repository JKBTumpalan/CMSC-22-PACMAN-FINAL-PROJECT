package game;

import java.util.Random;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import splash.Main;

public class Pacman extends Sprite{
	//attributes
	private String name;
	private int strength;
	private boolean alive;
	public int score;
	private int lives;
	 
	//image
	public final static Image PACMAN_RIGHT_IMAGE = new Image("images/pacman_right.gif",Pacman.PACMAN_WIDTH,Pacman.PACMAN_WIDTH,false,false);
	public final static Image PACMAN_LEFT_IMAGE = new Image("images/pacman_left.gif",Pacman.PACMAN_WIDTH,Pacman.PACMAN_WIDTH,false,false);
	public final static Image PACMAN_UP_IMAGE = new Image("images/pacman_up.gif",Pacman.PACMAN_WIDTH,Pacman.PACMAN_WIDTH,false,false);
	public final static Image PACMAN_DOWN_IMAGE = new Image("images/pacman_down.gif",Pacman.PACMAN_WIDTH,Pacman.PACMAN_WIDTH,false,false);
	public final static Image PACMAN_DEATH_ANIMATION = new Image("images/pacman_dead.gif",Pacman.PACMAN_WIDTH,Pacman.PACMAN_WIDTH,false,false);
	private final static int PACMAN_WIDTH = 30;

	//constructor
	public Pacman(String name, int x, int y){
		super(x,y,Pacman.PACMAN_WIDTH);
		this.name = name;
		this.alive = true;
		this.lives = 3;
		this.loadImage(Pacman.PACMAN_RIGHT_IMAGE);
	}
	//getters

	//Get the targetPath of blinky according to pacman's position
	public int getPinkyTarget(int component) {
		int x = this.getX(), y = this.getY();
		
		if(this.getImage().equals(PACMAN_RIGHT_IMAGE)) {
				x = this.getX()+60;
		}
		else if(this.getImage().equals(PACMAN_LEFT_IMAGE)) {
				x = this.getX()-60;
		}
		else if (this.getImage().equals(PACMAN_DOWN_IMAGE)) {
				x = this.getX()-60;
				y =  this.getY()+60;
		}else if (this.getImage().equals(PACMAN_UP_IMAGE)) {
				y = this.getY()-60;
				x = this.getX()-60;
		}
		
		if (component == 0) return x;
		else return y;
	}
	
	public boolean isAlive(){
		return this.alive;
	} 
	
	public String getName(){
		return this.name;
	}
	
	public void updateScore(int newScore) {
		this.score = newScore;
	}
	
	public int getScore() {
		return this.score;
	}

	public void loseLife() {
		this.lives--;
		this.changeImage(PACMAN_DEATH_ANIMATION);
	}
	
	public void die(boolean winner){
    	this.alive = false;
    	//next code block should provoke restarting of the map state :)
		GameOverStage gameover = new GameOverStage(this.getScore(),winner);
		Main.getStage().setScene(gameover.getScene());
    }
	
	//lets the character move
	public void move() {
		this.x+=this.dx;
		this.y+=this.dy;
	}
	
	//change image
	public void changeImage(Image image) {
		this.loadImage(image);
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getLives() { return this.lives; }

	public void setLives(int lives) {
		this.lives=lives;
	}
}
