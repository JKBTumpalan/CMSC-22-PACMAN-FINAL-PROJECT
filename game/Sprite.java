package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	protected Image img;
	protected ImageView iv;
	protected int x, y, dx, dy, rDy,rDx;
	protected boolean visible;
	protected double width;
	protected double height;
	protected int size;
	
	public Sprite(int xPos, int yPos, int size){
		this.x = xPos;
		this.y = yPos;
		this.size=size;
		this.visible = true;
	}
	
	//method to set the object's image
	protected void loadImage(Image img){
		try{
			this.img = img;
	        this.setSize();
		} catch(Exception e){}
	}
	
	//method to set the image to the image view node
	void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);
    }
	
	//method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
	    this.height = this.img.getHeight();
	}
	//method that will check for collision of two sprites
	public boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2) || rectangle1.contains(rectangle2);
	}
		
	//method that will return the bounds of an image
	Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}
	
	//method to return the image
	Image getImage(){
		return this.img;
	}
	//getters
	public int getX() {
    	return this.x;
	}

	public int getY() {
    	return this.y;
	}
	
	public int getDX(){
		return this.dx;
	}
	public int getDY(){
		return this.dy;
	}
	
	public boolean getVisible(){
		return visible;	
	}
	public boolean isVisible(){
		if(visible) return true;
		return false;
	}
	
	//setters
	public void setDX(int dx){
		this.dx = dx;
	}
	
	public void setDY(int dy){
		this.dy = dy;
	}
	
	public void set_rDY(int rdy) {
		this.rDy = rdy;
	}

	public void set_rDX(int rdx) {
		this.rDx = rdx;
	}

	public void setWidth(double val){
		this.width = val;
	}
	
	public int getSize(){
		return this.size;
	}

	public void setHeight(double val){
		this.height = val;
	}
	
	public void setVisible(boolean value){
		this.visible = value;
	}
	
	public void setPosition(int x, int y) {
		this.x=x;
		this.y=y;
	}

}
