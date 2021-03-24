package game;

import java.util.Random;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import splash.MainMenu;

public class GameTimer extends AnimationTimer{
	//attributes
	private GraphicsContext gc;
	private Scene theScene;
	private Pacman pacman;
	private ArrayList<Pacman> livesList;
	private ArrayList<Wall> wall;
	private ArrayList<PacDot> pacdots;
	private ArrayList<Fruit> fruits;
	private ArrayList<PowerPellet> pellets;
	private ArrayList<Ghost> ghostList;
	private ArrayList<PointAnimation> pts;
	private Ghost blinky;
	private Ghost clyde;
	private Ghost inky;
	private Ghost pinky;
	private int dotsEaten;
	private int totalDots;
	private int fruitsEaten;
	private int pelletsEaten;
	private int speed;
	private int ghostScore;
	private int ghostEaten;

	
	//Reference: https://www.cleanpng.com/png-pac-man-logo-font-brand-clip-art-video-game-fashio-6078119/
	private final Image textLogo = new Image("Images/pacman-textlogo.png", 225, 140, false, false);
	private KeyCode reserved;
	private boolean start;
	private boolean loselyf;

	//background
//	public static Image background=new Image("images/christmasBackground.jpeg",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	
	//constructor
	GameTimer(GraphicsContext gc, Scene theScene){
		this.start=true;
		this.gc = gc;
		this.theScene = theScene;
		this.pacman = new Pacman("PacMan main",15,15);
		this.livesList=new ArrayList<Pacman>();
		for(int i=0;i<this.pacman.getLives();i++) {
			this.livesList.add(new Pacman("lives",15,15));
		}
		this.wall=new ArrayList<Wall>();
		this.pacdots = new ArrayList<PacDot>();
		this.fruits = new ArrayList<Fruit>();
		this.pellets = new ArrayList<PowerPellet>();
		this.pts = new ArrayList<PointAnimation>();
		this.ghostList = new ArrayList<Ghost>();
		this.blinky = new Ghost("blinky",285,210,-1,0,false, wall); //red
		this.clyde = new Ghost("clyde", 330,270,0,-1,true, wall); //orange
		this.inky = new Ghost("inky",240,270,0,-1,true,wall); //blue
		this.pinky = new Ghost("pinky",285,270,0,-1,true, wall); //pink
		ghostList.add(this.blinky);
		ghostList.add(this.inky);
		ghostList.add(this.clyde);
		ghostList.add(this.pinky);
	
		this.initWall();
		this.dotsEaten = -1;
		this.fruitsEaten = 0;
		this.pelletsEaten = 0;
		this.initPacdots();
		this.speed=3;
		this.totalDots=0;
		this.reserved = KeyCode.RIGHT;
		
		//Instantiate power pellets
		this.pellets.add(new PowerPellet(120,90));
		this.pellets.add(new PowerPellet(120,435));
		this.pellets.add(new PowerPellet(450,90));
		this.pellets.add(new PowerPellet(450,435));
		this.addFruits();
		this.showRandomFruit();
		this.gc.setStroke(Color.RED);
		this.gc.setLineWidth(5);

		this.loselyf=false;

		//call method to handle mouse click event
		this.handleKeyPressEvent();
		
	}
	public boolean checkPath(int pathNum) {
		if(pathNum ==0) {
			this.pacman.setPosition(this.pacman.getX()-this.speed, this.pacman.getY());
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.collidesWith(this.pacman) == true) {			
					this.pacman.setPosition(this.pacman.getX()+this.speed, this.pacman.getY());
					return true;
				} 
			}
			System.out.println("Left area is free");
			this.pacman.setPosition(this.pacman.getX()+this.speed, this.pacman.getY());
			return false;
		}
		else if(pathNum ==1) {
			this.pacman.setPosition(this.pacman.getX(), this.pacman.getY()-this.speed);
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.collidesWith(this.pacman) == true) {	
					this.pacman.setPosition(this.pacman.getX(), this.pacman.getY()+this.speed);
					return true;
				}
			}
			System.out.println("Up area is free");
			this.pacman.setPosition(this.pacman.getX(), this.pacman.getY()+this.speed);
			return false;
		}
		else if(pathNum ==2) {
			this.pacman.setPosition(this.pacman.getX()+this.speed, this.pacman.getY());
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.collidesWith(this.pacman) == true) {	
					this.pacman.setPosition(this.pacman.getX()-this.speed, this.pacman.getY());
					return true;
				}
			}
			System.out.println("Right area is free");
			this.pacman.setPosition(this.pacman.getX()-this.speed, this.pacman.getY());
			return false;
		}
		else if(pathNum ==3) {
			this.pacman.setPosition(this.pacman.getX(), this.pacman.getY()+this.speed);
			for(int i = 0; i < this.wall.size(); i++) {
				Wall w = this.wall.get(i);
				if(w.collidesWith(this.pacman) == true) {	
					this.pacman.setPosition(this.pacman.getX(), this.pacman.getY()-this.speed);
					return true;
				}
			}
			System.out.println("Bottom area is free");
			this.pacman.setPosition(this.pacman.getX(), this.pacman.getY()-this.speed);
			return false;
		}
		
		return true;
	}
	
	@Override
	public void handle(long currentNanoTime) {
		//animation before starting
		//renders each wall
		for(int i=0;i<this.wall.size();i++)	this.wall.get(i).render(this.gc);
		this.gc.setFont(new Font("Consolas", 45));
		this.gc.setFill(Color.YELLOW);
		Image ready=new Image("images/ready.png",350,80,false,false);
		this.gc.drawImage(ready, 125,235);
		
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(
			new KeyFrame(Duration.seconds(2),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						start=false;
					}
				}));
		timeline.play();
		
		if(this.start) return;
		
		//clears the screen
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		//renders each wall
		for(int i=0;i<this.wall.size();i++)	this.wall.get(i).render(this.gc);
				
		setRightPanel();
		
		this.totalDots=0;

		//Render block for sprites and food. Renders consider the conditionals.
		this.renderPowerPellets();
		this.renderAnimations();
		this.renderPacdots();
		this.renderFruits();

		this.renderGhosts();

		//call the methods to move Pac-Man
		this.pacman.render(this.gc);
		this.pacman.move();

		//checks for wall collision
		this.checkWallCollision(this.pacman);
		
		//Check sprite collision
		this.checkGhostCollision();
		
		//Code block for registration of the reserved keys to pacman's movement
		this.conditionalShift();

		
		//checks if characters are going to warp from left to right
		this.warp(this.pacman);
		for(Ghost ghost : this.ghostList) {
			this.warp(ghost);
		}
		
		//checks if the player wins
		this.checkWin();
		
		//blocks the movements once pacman loses a life
		if(this.loselyf) this.blockKeyPressEvent();
	}
	
	//checker if the player wins the game
	private void checkWin() {
		for(int i=0;i<this.pacdots.size();i++) {
			if(this.pacdots.get(i).isVisible())	this.totalDots++;
		}
		
		GameTimer gt=this;
		
		//player wins if there are no PacDots left
		if(this.totalDots==0) {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(0.5),
					new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							pacman.die(true);
							gt.stop();
						}
					}));
			timeline.play();
		}
	}
	
	//renders the ghosts
	private void renderGhosts() {
		int blinky_posX = 0, blinky_posY = 0;
		
		for(Ghost ghost : ghostList) {
			if(ghost.isVisible())
				ghost.render(this.gc);
		}
		
		for(Ghost ghost : ghostList) {
			//If the ghost is inside the house
			if((ghost.getY()>= 240 && ghost.getY() <= 300) && (ghost.getX() >= 225 && ghost.getX() <= 375)) {
				if(ghost.getIsEaten()) {
					ghost.setIsEaten(false);
					if(ghost.getName().equalsIgnoreCase(Ghost.BLINKY))	ghost.changeImage(Ghost.GHOST_BLINKY);
					else if(ghost.getName().equalsIgnoreCase(Ghost.INKY))	ghost.changeImage(Ghost.GHOST_INKY);
					else if(ghost.getName().equalsIgnoreCase(Ghost.PINKY))	ghost.changeImage(Ghost.GHOST_PINKY);
					else if(ghost.getName().equalsIgnoreCase(Ghost.CLYDE)) ghost.changeImage(Ghost.GHOST_CLYDE);
				}
				ghost.setIsInside(true);
				ghost.getTargetPath(285, 210);
				ghost.conditionalShift();
			}
			//if the ghost is eaten
			else if(ghost.getIsEaten()) {
				if(ghost.getName().equals(Ghost.BLINKY)) {
					ghost.getTargetPath(300,285);
//					this.gc.strokeText("B", 300,285);
				}else if(ghost.getName().equals(Ghost.CLYDE)) {
					ghost.getTargetPath(300,285);
//					this.gc.strokeText("C", 300,285);
				}else if(ghost.getName().equals(Ghost.INKY)) {
					ghost.getTargetPath(300,285);
//					this.gc.strokeText("I", 300,285);
				}else {
					ghost.getTargetPath(300,285);
//					this.gc.strokeText("P", 300,285);
				}
				ghost.conditionalShift();
			}
			//if the ghost is vulnerable
			else if(ghost.getVulnerability()) {
				if(ghost.getName().equals(Ghost.BLINKY)) {
					ghost.getTargetPath(20,45);
//					this.gc.strokeText("B", 20, 45);
				}else if(ghost.getName().equals(Ghost.CLYDE)) {
					ghost.getTargetPath(565, 45);
//					this.gc.strokeText("C", 565, 45);
				}else if(ghost.getName().equals(Ghost.INKY)) {
					ghost.getTargetPath(20, 565);
//					this.gc.strokeText("I", 20, 565);
				}else {
					ghost.getTargetPath(565,565);
//					this.gc.strokeText("P", 565, 565);
				}
				ghost.conditionalShift();
			}
			//other cases
			else {
				ghost.setIsInside(false);
				if(ghost.getIsEaten()) {
					ghost.setIsEaten(false);
					if(ghost.getName().equalsIgnoreCase(Ghost.BLINKY))	ghost.changeImage(Ghost.GHOST_BLINKY);
				}
				if(ghost.getName().equals("blinky")) {
					ghost.getTargetPath(this.pacman.getX() + 15, this.pacman.getY()+15);
					blinky_posX = ghost.getX();
					blinky_posY = ghost.getY();
//					this.gc.strokeText("B", this.pacman.getX()+15, this.pacman.getY()+15);
				}
				else if(ghost.getName().equals("pinky")) {
					ghost.getTargetPath(this.pacman.getPinkyTarget(0)+15, this.pacman.getPinkyTarget(1)+15);
//					this.gc.strokeText("P", this.pacman.getPinkyTarget(0)+15, this.pacman.getPinkyTarget(1)+15);
				} else if(ghost.getName().equals("clyde")) {
					if(ghost.distanceFromPacman(this.pacman.getX(), this.pacman.getY()) > 200) {
						System.out.println("DISTANCE FROM PACMAN: " + (ghost.distanceFromPacman(this.pacman.getX(), this.pacman.getY())));
						ghost.getTargetPath(this.pacman.getX() + 15, this.pacman.getY()+15);
//						this.gc.strokeText("C", this.pacman.getX()+15, this.pacman.getY()+15);
					} else {
						ghost.getTargetPath(0, GameStage.WINDOW_HEIGHT-50);
//						this.gc.strokeText("C", 0, GameStage.WINDOW_HEIGHT-50);
					}
				} else if(ghost.getName().equals("inky")) {
					
					int x = this.pacman.getX(), y = this.pacman.getY();
					
					int final_x = x+((x-blinky_posX)*2), final_y = y+(y-blinky_posY)*2;
					
					ghost.getTargetPath(final_x+15, final_y+15);
//					this.gc.strokeText("I", final_x+15, final_y+15);					
				}
				ghost.conditionalShift();
			}
			ghost.move();
			ghost.stop();
		}
	}
	
	//resets the ghost and pacman sprites when pacman loses a life
	private void resetSprites() {
		this.blinky = new Ghost("blinky",285,210,-1,0,false,wall); //red
		this.clyde = new Ghost("clyde", 330,270,0,-1,true,wall); //orange
		this.inky = new Ghost("inky",230,270,0,-1,true,wall); //blue
		this.pinky = new Ghost("pinky",285,270,0,-1,true,wall); //pink
		this.ghostList.clear();
		this.ghostList.add(this.blinky);
		this.ghostList.add(this.pinky);
		this.ghostList.add(this.clyde);
		this.ghostList.add(this.inky);
		this.pacman.setPosition(15, 15);
		this.pacman.changeImage(Pacman.PACMAN_RIGHT_IMAGE);
	}
	
	//renders the power pellets
	private void renderPowerPellets() {
		//Render power pellets
		for(int i = 0; i < this.pellets.size(); i++) {
			if(this.pellets.get(i).collidesWith(this.pacman) && this.pellets.get(i).isVisible() == true ) {
				//Animation
				this.pts.add(new PointAnimation(this.pellets.get(i).getX(), this.pellets.get(i).getY(), 20, 0));			

				this.pellets.get(i).setVisible(false);
				this.pelletsEaten++;
				int time=5;
				
				//only ghosts that are not yet eaten change into vulnerable state
				for(Ghost ghost:ghostList){
					if(!ghost.getIsEaten())
						ghost.setVulnerable(true);
				}
				
				//ghosts become vulnerable after 5 seconds
				Timeline timeline = new Timeline();
				timeline.getKeyFrames().add(
					new KeyFrame(Duration.seconds(time),
						new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								for(Ghost ghost:ghostList){
									ghost.setVulnerable(false);
								}
								ghostEaten = 0;
							}
						}));
				timeline.play();
			}
			
			if(this.pellets.get(i).isVisible())
				this.pellets.get(i).render(this.gc);
		}
	}
	
	//renders the animations
	private void renderAnimations() {
		
		for(PointAnimation ptAnim : pts) {
			ptAnim.initializeAnimation();
		}
		for(PointAnimation ptAnim : pts) {
			if(ptAnim.isVisible() == true)
				ptAnim.render(gc);
		}
	}
	
	//renders the pacdots
	private void renderPacdots() {
		//render VISIBLE and UNEATEN power pacdots
		for(int i=0;i<this.pacdots.size();i++) {
			if(this.pacdots.get(i).collidesWith(this.pacman) && this.pacdots.get(i).isVisible() == true) {
				//Initialize animations for pacdots
				this.pts.add(new PointAnimation(this.pacdots.get(i).getX(), this.pacdots.get(i).getY(), 5, 10));			
				this.pacdots.get(i).setVisible(false);
				this.dotsEaten++;
			}
			
			for(int j = 0; j < this.pellets.size(); j++) {
				if(this.pacdots.get(i).collidesWith(this.pellets.get(j))) {
					this.pacdots.get(i).setVisible(false);
				}
			}
		
			if(this.pacdots.get(i).isVisible()) {
				this.pacdots.get(i).render(this.gc);
			}
			
		}
	}
	
	//renders the fruits
	private void renderFruits() {
		for(int i = 0; i < this.fruits.size(); i++) {
			if(this.fruits.get(i).collidesWith(this.pacman)&& this.fruits.get(i).isVisible() == true ) {
				this.fruits.get(i).setVisible(false);
				this.fruitsEaten++;
			}
			
			if(this.fruits.get(i).isVisible())
				this.fruits.get(i).render(this.gc);
		}
	}
	
	//checks if pacman colldes with a ghost
	private void checkGhostCollision() {
		//For every ghost in ghost list, check collisions to pacman.
		for(Ghost ghost : ghostList) {
			if(ghost.collidesWith(this.pacman) && !ghost.getIsEaten()) {
				if(ghost.isVisible() == true) {
					if(ghost.getVulnerability() == false) {
						//When pacman collides to a invulnerable ghost, reset position of pacman to the x,y position before the collision. Initiate
						//the animation and block all keypress event from the user. Stop all the ghosts and initiate checking 
						//of pacman's life after the reduction. If pacman's life is more than 0, reset sprites and callback the keypress event handler. If life <= 0,
						//call the die() function to display the end game prompt and switch scene.
						this.pacman.setPosition(this.pacman.getX()-this.pacman.getDX(), this.pacman.getY()-this.pacman.getDY());
						stopPacman();
						setReserved(null);
						for(Ghost ghost2 : ghostList) {
							ghost2.stop();
							ghost2.setVisible(false);
						}
						
						this.pacman.loseLife();
						this.loselyf=true;
						this.blockKeyPressEvent();
						//Do something after losing life
						if(this.pacman.getLives() <= 0) {
							this.pacman.die(false);
							this.stop();
						}
						
						Timeline timeline = new Timeline();
						timeline.getKeyFrames().add(
							new KeyFrame(Duration.seconds(1.5),
								new EventHandler<ActionEvent>() {
									public void handle(ActionEvent e) {
										resetSprites();
										loselyf=false;
										handleKeyPressEvent();
									}
								}));
						timeline.play();
					} else {
						//If ghost is vulnerable
//						ghost.setVisible(false);	
						if(!ghost.getIsEaten()) {
							this.ghostEaten++;
							this.ghostScore += (Math.pow(2, ghostEaten))*100; //Point system solved thru exponentiation: 2^ghostEaten*100pts
																		//2^1 = 2*100 = 200, 2^2 =4*100 = 400, 2^3 == 8*100 = 800, 2^4= 16*100 = 1600 pts
							this.pts.add(new PointAnimation(ghost.getX(), ghost.getY(), 20,(int)((Math.pow(2, ghostEaten))*100)));
						}						
						ghost.setIsEaten(true);
						ghost.changeImage(Ghost.GHOST_EYE);
					}
				}
			}
		}
	}
	
	//sets reserved movements for open paths
	private void conditionalShift() {
		if(this.reserved == KeyCode.DOWN || this.reserved == KeyCode.S) {
			if(checkPath(3) == false) {
				movePacman(this.reserved);
			}
		} else if(this.reserved == KeyCode.LEFT || this.reserved == KeyCode.A) {
			if(checkPath(0) == false) {
				movePacman(this.reserved);
			}
		} else if(this.reserved == KeyCode.UP || this.reserved == KeyCode.W) {
			if(checkPath(1) == false) {
				movePacman(this.reserved);
			}
		} else if(this.reserved == KeyCode.RIGHT  || this.reserved == KeyCode.D) {
			if(checkPath(2) == false) {
				movePacman(this.reserved);
			}
		}		
	}
	
	//sets up the right panel
	private void setRightPanel() {
		this.gc.drawImage(textLogo, MainMenu.WINDOW_WIDTH*0.72, 0);
		this.gc.setFont(new Font("Consolas", 30));
		this.gc.setFill(Color.YELLOW);
		this.gc.fillText("SCORE:   ",MainMenu.WINDOW_WIDTH*0.72, MainMenu.WINDOW_HEIGHT*0.35);
		int tempScore=(dotsEaten*PacDot.worth+pelletsEaten*PowerPellet.worth+fruitsEaten*100)+(this.ghostScore);
		this.pacman.updateScore(tempScore);
		this.gc.fillText(" " + this.pacman.getScore(),MainMenu.WINDOW_WIDTH*0.82, MainMenu.WINDOW_HEIGHT*0.35);
		this.gc.fillText("LIVES:   ",MainMenu.WINDOW_WIDTH*0.72, MainMenu.WINDOW_HEIGHT*0.55);
		
		//displays how many lives are left
		int initial=(int) (MainMenu.WINDOW_WIDTH*0.85);
		for(int i=0;i<this.pacman.getLives();i++) {
			Pacman lives=livesList.get(i);
			lives.setPosition(initial, (int)(MainMenu.WINDOW_HEIGHT*0.5));
			lives.render(this.gc);
			initial+=40;
		}
		
	}
	
	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                setReserved(code);          
			}
		});
    }
	
	//blocks the key press event
	private void blockKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
                setReserved(null);          
			}
		});
	}
	
	//sets the reserved movements
	public void setReserved(KeyCode code) {
		this.reserved = code;
	}
	   
	//method that will move Pac-Man depending on the key pressed
	private void movePacman(KeyCode ke) {
		//move up
		if(ke==KeyCode.UP || ke==KeyCode.W) {
			this.pacman.setDY(-this.speed);
			this.pacman.changeImage(Pacman.PACMAN_UP_IMAGE);
		}
		//move left
		if(ke==KeyCode.LEFT || ke==KeyCode.A) {
			this.pacman.setDX(-this.speed);
			this.pacman.changeImage(Pacman.PACMAN_LEFT_IMAGE);
		}
		//move down
		if(ke==KeyCode.DOWN || ke==KeyCode.S) {
			this.pacman.setDY(this.speed);
			this.pacman.changeImage(Pacman.PACMAN_DOWN_IMAGE);
		}
		//move right
		if(ke==KeyCode.RIGHT || ke==KeyCode.D) {
			this.pacman.setDX(this.speed);
			this.pacman.changeImage(Pacman.PACMAN_RIGHT_IMAGE);
		}
		
		System.out.println(ke+" key executed.");
   	}
	
	//checks the wall collision for the character
	private boolean checkWallCollision(Sprite sprite) {
		for(int i=0;i<this.wall.size();i++) {
			Wall w=this.wall.get(i);
			if(sprite.collidesWith(w) && w.getName().equals("wall")){
				System.out.println("WALL "+i);
				//sets the position of the sprite to its previous position before collision
				sprite.setPosition(sprite.getX()-sprite.getDX(), sprite.getY()-sprite.getDY());
				if(sprite instanceof Ghost) {
					((Ghost) sprite).setIsCollide(true);
				} else if (sprite instanceof Pacman) {
					stopPacman();
					return true;
				}
			}else if(sprite.collidesWith(w) && w.getName().equals("gate")) {
				if (sprite instanceof Pacman) {
					sprite.setPosition(sprite.getX()-sprite.getDX(), sprite.getY()-sprite.getDY());
					stopPacman();
					return true;
				}
				
			}
		}
		
		return false;
	}
	 
	
//	method that will stop the character's movement; set the character's DX and DY to 0
	private void stopPacman(){
		this.pacman.setDX(0);
		this.pacman.setDY(0);
	}
	
	
	//warps the character to the opposite side of the tunnel
	private void warp(Sprite sprite) {
		boolean warp=false;
		if(sprite.getX()<=-30 && sprite.getY()>=270 && sprite.getY()<=300) {
			System.out.println("WARP");
			sprite.setPosition(585, 270);
			warp=true;
		}
		if(sprite.getX()>=600 && sprite.getY()>=270 && sprite.getY()<=300) {
			System.out.println("WARP");
			sprite.setPosition(0, 270);
			warp=true;
		}
		if(sprite instanceof Ghost) {
			if(sprite.getX()<=-20)
				sprite.setPosition(590,270);
			else if(sprite.getX()>=590)
				sprite.setPosition(0, 270);
		}
		if(warp) {
			this.blockKeyPressEvent();
			if(sprite instanceof Ghost) {
				((Ghost) sprite).getTargetPath(285, 240);
			}
		}
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(
			new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						handleKeyPressEvent();
					}
				}));
		timeline.play();
	}
	
	//Add fruits to all areas of the map except the ghost house, but set visibility to false
	private void addFruits() {
		int x = 15, y = 15;
		
		for(int i = 0; i < 37; i++) {
			for(int j = 0; j < 37; j++) {
				if(!(x > 203 && x < 380	 && y > 215 && y < 305))
					if(!(x > 0 && x < 110 && y > 185 && y < 375))
						if(!(x > 460 && y > 185 && y < 375))
							this.fruits.add(new Fruit(x,y));
				x+= 15;
			}
			x = 15;
			y+= 15;
		}
		
		for(int i = 0; i < this.wall.size(); i++) {
			for(int j = 0; j < this.fruits.size(); j++) {
				if(this.fruits.get(j).collidesWith(this.wall.get(i))) {
					this.fruits.remove(j);
				}				
			}
		}
	}
	
	//renders the fruit randomly
	private void showRandomFruit() {
		Random rand = new Random();
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(
			new KeyFrame(Duration.seconds(rand.nextInt(20)+5),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						//renders only one fruit
						for(int i=0;i<fruits.size();i++) {
							if(fruits.get(i).isVisible()) return;
							
						}
						int r=rand.nextInt(fruits.size());
						for(int j=0;j<wall.size();j++) {
							if(fruits.get(r).collidesWith(wall.get(j)))	return;
						}
						fruits.get(r).setVisible(true);
					}
				}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	//initialize the pacdots
	private void initPacdots() {
		int x = 22, y = 22;
		
		for(int i = 0; i < 37; i++) {
			for(int j = 0; j < 37; j++) {
				if(!(x > 173 && x < 410	 && y > 185 && y < 375))
					if(!(x > 0 && x < 110 && y > 185 && y < 375))
						if(!(x > 460 && y > 185 && y < 375))
							this.pacdots.add(new PacDot(x,y));
				x+= 15;
			}
			x = 22;
			y+= 15;
		}
		
		for(int i = 0; i < this.wall.size(); i++) {
			for(int j = 0; j < this.pacdots.size(); j++) {
				if(this.pacdots.get(j).collidesWith(this.wall.get(i))) {
					this.pacdots.get(j).setVisible(false);
				}				
			}
		}
		
	}
	
	//initialize all the blocks of walls in the game
	private void initWall() {
		int y=0;
		int x=0;
		
		//outer box
		//upper outer
		y=0;
		for(int i=0;i<40;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		int last_x=x-15;
		//left outer
		y=0;
		for(int i=0;i<40;i++) {
			if(y<=180 || y>=375)	this.wall.add(new Wall("wall",0,y));
			y+=15;
		}
		int last_y=y;
		
		//left wall breaks
		//upper h
		y=195;
		x=0;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//upper v
		y=195;
		x=105;
		for(int i=0;i<5;i++) {
			this.wall.add(new Wall("wall",x,y));
			y+=15;
		}
		
		//upper h
		y=255;
		x=0;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//lower h
		y=300;
		x=0;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//lower v
		y=300;
		x=105;
		for(int i=0;i<5;i++) {
			this.wall.add(new Wall("wall",x,y));
			y+=15;
		}
		
		//lower h
		y=360;
		x=0;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//lower outer
		x=0;
		for(int i=0;i<40;i++) {
			this.wall.add(new Wall("wall",x,last_y-15));
			x+=15;
		}
		
		//right outer
		y=0;
		for(int i=0;i<40;i++) {
			if(y<=180 || y>=375)	this.wall.add(new Wall("wall",last_x,y));
			y+=15;
		}
		
		//right wall breaks
		//upper h
		y=195;
		x=480;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//upper v
		y=195;
		x=480;
		for(int i=0;i<5;i++) {
			this.wall.add(new Wall("wall",x,y));
			y+=15;
		}
		
		//upper h
		y=255;
		x=480;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//lower h
		y=300;
		x=480;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//lower v
		y=300;
		x=480;
		for(int i=0;i<5;i++) {
			this.wall.add(new Wall("wall",x,y));
			y+=15;
		}
		
		//lower h
		y=360;
		x=480;
		for(int i=0;i<8;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//middle upper
		x=285;
		for(int j=0;j<2;j++) {
			y=15;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//block 1
		y=45;
		for(int j=0;j<3;j++) {
			x=45;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 2
		y=45;
		for(int j=0;j<3;j++) {
			x=150;
			for(int i=0;i<7;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 3
		y=45;
		for(int j=0;j<3;j++) {
			x=345;
			for(int i=0;i<7;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 4
		y=45;
		for(int j=0;j<3;j++) {
			x=480;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 5
		y=120;
		for(int j=0;j<3;j++) {
			x=45;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//t block upper left
		//block 6 v
		x=150;
		for(int j=0;j<2;j++) {
			y=120;
			for(int i=0;i<10;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//block 6 h
		y=180;
		for(int j=0;j<2;j++) {
			x=180;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//t block upper middle
		//block 7 h
		y=120;
		for(int j=0;j<2;j++) {
			x=210;
			for(int i=0;i<12;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 7 v
		x=285;
		for(int j=0;j<2;j++) {
			y=150;
			for(int i=0;i<4;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//t block upper right
		//block 8 v
		x=420;
		for(int j=0;j<2;j++) {
			y=120;
			for(int i=0;i<10;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//block 8 h
		y=180;
		for(int j=0;j<2;j++) {
			x=345;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 9
		y=120;
		for(int j=0;j<3;j++) {
			x=480;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//ghost house
		//upper
		y=240;
		x=210;
		for(int i=0;i<12;i++) {
			if(x<285 || x>300) {
				this.wall.add(new Wall("wall",x,y));
			}else {
				this.wall.add(new Wall("gate",x,y)); //this is the gate of the ghost house
			}
			x+=15;
		}
		
		//left
		y=240;
		x=210;
		for(int i=0;i<4;i++) {
			this.wall.add(new Wall("wall",x,y));
			y+=15;
		}
		
		//lower
		y=300;
		x=210;
		for(int i=0;i<12;i++) {
			this.wall.add(new Wall("wall",x,y));
			x+=15;
		}
		
		//left
		y=240;
		x=375;
		for(int i=0;i<4;i++) {
			this.wall.add(new Wall("wall",x,y));
			y+=15;
		}
		
		//block 10 v
		x=150;
		for(int j=0;j<2;j++) {
			y=300;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//t block lower middle
		//block 11 h
		y=345;
		for(int j=0;j<2;j++) {
			x=210;
			for(int i=0;i<12;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 11 v
		x=285;
		for(int j=0;j<2;j++) {
			y=375;
			for(int i=0;i<4;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//block 12 v
		x=420;
		for(int j=0;j<2;j++) {
			y=300;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//l block lower left
		//block 13 h
		y=405;
		for(int j=0;j<2;j++) {
			x=45;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 13 v
		x=75;
		for(int j=0;j<3;j++) {
			y=405;
			for(int i=0;i<6;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//block 14
		y=405;
		for(int j=0;j<2;j++) {
			x=150;
			for(int i=0;i<7;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 15
		y=405;
		for(int j=0;j<2;j++) {
			x=345;
			for(int i=0;i<7;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//l block lower right
		//block 16 h
		y=405;
		for(int j=0;j<2;j++) {
			x=480;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//block 16 v
		x=480;
		for(int j=0;j<3;j++) {
			y=405;
			for(int i=0;i<6;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//lower left middle
		y=465;
		for(int j=0;j<2;j++) {
			x=15;
			for(int i=0;i<2;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//lower right middle
		y=465;
		for(int j=0;j<2;j++) {
			x=555;
			for(int i=0;i<2;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//t block lowest left
		//block 17 v
		x=150;
		for(int j=0;j<2;j++) {
			y=465;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		//block 17 h
		y=525;
		for(int j=0;j<2;j++) {
			x=45;
			for(int i=0;i<14;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
		//t block lower middle
		//block 18 h
		y=465;
		for(int j=0;j<2;j++) {
			x=210;
			for(int i=0;i<12;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		//block 18 v
		x=285;
		for(int j=0;j<2;j++) {
			y=480;
			for(int i=0;i<5;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		
		//t block lowest right
		//block 19 v
		x=420;
		for(int j=0;j<2;j++) {
			y=465;
			for(int i=0;i<6;i++) {
				this.wall.add(new Wall("wall",x,y));
				y+=15;
			}
			x+=15;
		}
		//block 19 h
		y=525;
		for(int j=0;j<2;j++) {
			x=345;
			for(int i=0;i<14;i++) {
				this.wall.add(new Wall("wall",x,y));
				x+=15;
			}
			y+=15;
		}
		
	}

}
