package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Ghost extends Sprite{
	//attributes
	private String name;
	private boolean alive;
	private boolean isCollide;
	private boolean isInside;
	private boolean isVulnerable;
	private int speed;
	private int distance_x;
	private int distance_y;
	private double distanceFromPacman;
	private int rand;
	private int previous2;
	private int previous;
	private KeyCode reserved;
	private int pastMin;
	private ArrayList<Wall> wall;
	private boolean isEaten;
	
	public static int DEFAULT_SPEED=2;
	
	//image
	public final static Image GHOST_INKY = new Image("images/inky.gif",29,29,false,false);
	public final static Image GHOST_CLYDE = new Image("images/clyde.gif",29,29,false,false);
	public final static Image GHOST_BLINKY = new Image("images/blinky.gif",29,29,false,false);
	public final static Image GHOST_PINKY = new Image("images/pinky.gif",29,29,false,false);
	public final static Image GHOST_VULNERABLE = new Image("images/ghost_dead.gif",29,29,false,false);
	public final static Image GHOST_EYE = new Image("images/ghost_eye.gif",29,29,false,false);
	public final static String CLYDE = "clyde"; //orange
	public final static String INKY = "inky"; //blue
	public final static String BLINKY = "blinky"; //red
	public final static String PINKY = "pinky"; //pink
	

	//constructor
	public Ghost(String name, int x, int y, int dx, int dy, boolean in, ArrayList<Wall> wall){
		super(x,y,29);
		this.name = name;
		this.alive = true;
		this.isInside = in; //Red Ghost is outside
		this.loadPicture(name);
		this.dx = dx; //initially  moving left
		this.dy = dy;
		this.speed = 2;
		this.isVulnerable = false;
		this.wall = wall;
		this.reserved = KeyCode.UP; //reserved movement of ghost
	}
	
	//getters
	public void setVulnerable(boolean vul) {
		this.isVulnerable = vul;
		if (vul == true) {
			this.loadImage(GHOST_VULNERABLE);
			//set slower speed for the ghost
			this.speed = 1;
		}
		else {
			if(this.isEaten) {
				this.loadImage(GHOST_EYE);
			}else {
				this.loadPicture(name);
				this.speed = 2;
			}
		}
	}
	
	//loads the picture of the ghost
	private void loadPicture(String name) {
		if(name.equals(CLYDE)) {
			this.loadImage(GHOST_CLYDE);
			
		}else if(name.equals(BLINKY)){
			this.loadImage(GHOST_BLINKY);
			
		}else if(name.equals(INKY)) {
			this.loadImage(GHOST_INKY
					);
		}else if(name.equals(PINKY)){
			this.loadImage(GHOST_PINKY);
		}
	}
	
	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 
	
	public String getName(){
		return this.name;
	}

	public void die(){
    	this.alive = false;
    }
	
	//computes the ghost's distance from pacman
	public double distanceFromPacman(int x, int y) {
		this.distance_x=x;
		this.distance_y=y;
		this.distanceFromPacman=this.distanceFormula(x, this.x, y, this.y);
		System.out.println(this.name+"'s DISTANCE FROM PAC-MAN: "+this.distanceFromPacman);
		
		return this.distanceFromPacman;
	}
	
	//computes the distance using the distance formula
	private double distanceFormula(int x1, int x2, int y1, int y2) {
		double d_x=x1;
		double d_y=y1;
		double d_thisx=x2;
		double d_thisy=y2;
		double distance=0;
		distance=Math.sqrt(Math.pow((d_thisx-d_x),2)+Math.pow((d_thisy-d_y),2));
		return distance;
	}
	
	//checks for open paths
	public boolean checkPath(int pathNum) {
		if(pathNum ==0) {
			this.setPosition(this.getX()-Ghost.DEFAULT_SPEED, this.getY());
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.getName().equals("wall"))
					if(w.collidesWith(this) == true) {			
						this.setPosition(this.getX()+Ghost.DEFAULT_SPEED, this.getY());
						return true;
					} 
			}
			System.out.println("Left area is free");
			this.setPosition(this.getX()+Ghost.DEFAULT_SPEED, this.getY());
			return false;
		}
		else if(pathNum ==1) {
			this.setPosition(this.getX(), this.getY()-Ghost.DEFAULT_SPEED);
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.getName().equals("wall"))
					if(w.collidesWith(this) == true) {	
						this.setPosition(this.getX(), this.getY()+Ghost.DEFAULT_SPEED);
						return true;
					}
			}
			System.out.println("Up area is free");
			this.setPosition(this.getX(), this.getY()+Ghost.DEFAULT_SPEED);
			return false;
		}
		else if(pathNum ==2) {
			this.setPosition(this.getX()+Ghost.DEFAULT_SPEED, this.getY());
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.getName().equals("wall"))
					if(w.collidesWith(this) == true) {	
						this.setPosition(this.getX()-Ghost.DEFAULT_SPEED, this.getY());
						return true;
					}
			}
			System.out.println("Right area is free");
			this.setPosition(this.getX()-Ghost.DEFAULT_SPEED, this.getY());
			return false;
		}
		else if(pathNum ==3) {
			this.setPosition(this.getX(), this.getY()+Ghost.DEFAULT_SPEED);
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.getName().equals("wall"))
					if(w.collidesWith(this) == true) {	
						this.setPosition(this.getX(), this.getY()-Ghost.DEFAULT_SPEED);
						return true;
					}
			}
			System.out.println("Bottom area is free");
			this.setPosition(this.getX(), this.getY()-Ghost.DEFAULT_SPEED);
			return false;
		}
		
		return true;
	}
	
	//sets the reserved keys
	private void setReserved(KeyCode ke) {
		this.reserved = ke;
	}
	
	//Main movement initiator, checks the path and moves the ghost according to the reserved key.
	public void conditionalShift() {
		if(this.reserved == KeyCode.DOWN || this.reserved == KeyCode.S) {
			if(checkPath(3) == false) {
				moveGhost(this.reserved);
			}
		} else if(this.reserved == KeyCode.LEFT || this.reserved == KeyCode.A) {
			if(checkPath(0) == false) {
				moveGhost(this.reserved);
			}
		} else if(this.reserved == KeyCode.UP || this.reserved == KeyCode.W) {
			if(checkPath(1) == false) {
				moveGhost(this.reserved);
			}
		} else if(this.reserved == KeyCode.RIGHT  || this.reserved == KeyCode.D) {
			if(checkPath(2) == false) {
				moveGhost(this.reserved);
			}
		}		
	}
	
	//moves the ghosts
	public void moveGhost(KeyCode ke) {
		//move up
		if(ke==KeyCode.UP || ke==KeyCode.W) {
			this.setDY(-this.speed);
		}
		//move left
		if(ke==KeyCode.LEFT || ke==KeyCode.A) {
			this.setDX(-this.speed);
		}
		//move down
		if(ke==KeyCode.DOWN || ke==KeyCode.S) {
			this.setDY(this.speed);
		}
		//move right
		if(ke==KeyCode.RIGHT || ke==KeyCode.D) {
			this.setDX(this.speed);
		}
	}
	//lets the character move
	
	//gets the coordinates of the target and moves towards it
	public void getTargetPath(int targetX, int targetY) {
		int min;
		
		//Array of distances of the directions
		double[] pathDistances = new double[4];
		int[] minDistances = { 0, 1, 2, 3};
		pathDistances[0] = distanceFormula(this.getX()-Ghost.DEFAULT_SPEED, targetX,this.getY(), targetY);
		pathDistances[1] = distanceFormula(this.getX(), targetX,this.getY()-Ghost.DEFAULT_SPEED, targetY);
		pathDistances[2] = distanceFormula(this.getX()+Ghost.DEFAULT_SPEED, targetX,this.getY(), targetY);
		pathDistances[3] = distanceFormula(this.getX(), targetX,this.getY()+Ghost.DEFAULT_SPEED, targetY);
		System.out.println(Arrays.toString(pathDistances));
		min = 0;
		
		//Sort the distance array and minimum index array for precedence use
		for(int i = 1; i < pathDistances.length; i++) {
			for(int j = i; j > 0; j--) {
				if(pathDistances[j-1] > pathDistances[j]) {
					
					//swap distances
					double temp = pathDistances[j-1];
					pathDistances[j-1] = pathDistances[j];
					pathDistances[j] = temp;
					
					//swap indices
					int temp2 = minDistances[j-1];
					minDistances[j-1] = minDistances[j];
					minDistances[j] = temp2;
					
				}
			}
		}
		
		System.out.println(Arrays.toString(minDistances));
		
		
		//Check paths of the distances in ASCENDING order.
		for(int i = 0; i < (minDistances.length); i++) {
			//If the minimum distance is the opposite of the past movement, void. (The sprite will go back and forth in some instances kapag ganon)
			if(checkPath(minDistances[i]) == false && minDistances[i] != pastMin) {
				min = minDistances[i];
				break;
			}
			else {
				if(minDistances[i] != pastMin)
					min = minDistances[i];
			}
		}
		
		//Set past min to the OPPOSITE of  the current direction
		this.pastMin = (min+2)%4;

		switch(min) {
			case 0:
				System.out.println("Left path minimum");
				System.out.println("Keycode: " + this.reserved);
				setReserved(KeyCode.A);
				break;
			case 1:
				System.out.println("Top path minimum");
				System.out.println("Keycode: " + this.reserved);
				setReserved(KeyCode.W);
				break;
			case 2:
				System.out.println("Right path minimum");
				System.out.println("Keycode: " + this.reserved);
				setReserved(KeyCode.D);
				break;
			case 3:
				boolean down=true;
				if(this.y>=210 && this.x>=285 && this.x<=300 && !this.isEaten) {
					down=false;
				}
				if(down) {
					System.out.println("Down path minimum");
					System.out.println("Keycode: " + this.reserved);
					setReserved(KeyCode.S);
				}
				break;
			default:
				break;
		}
	}
	
	//moves the ghost
	public void move() {		
		this.x+= this.dx;
		this.y+= this.dy;
	}
	
	
	//change image
	public void changeImage(Image image) {
		this.loadImage(image);
	}
	
	//stop DYandDX
	public void stop() {
		this.setDX(0);
		this.setDY(0);
	}

	//Setters and getters
	public boolean isCollide() {
		return this.isCollide;
	}
	
	//if the ghosts are inside the house
	public boolean isInside() {
		return this.isInside;
	}
	
	public void setIsInside(boolean value) {
		this.isInside = value;
	}
	
	public void setIsCollide(boolean value) {
		this.isCollide = value;
	}
	
	//Get vulnerability status
	public boolean getVulnerability() {
		return this.isVulnerable;
	}
	
	//If ghost is eaten
	public void setIsEaten(boolean isEaten) {
		this.isEaten=isEaten;
	}
	
	public boolean getIsEaten() {
		return this.isEaten;
	}
	
	//sets the speed of the ghost
	public void setSpeed(int speed){
		this.speed=speed;
	}

}