package splash;

public class Element extends Sprite{
	private String name;
	
	public final static String PACMAN_INSTRUCTION_IMAGE = "Images/pacmanInstruction.gif";
	public final static String QUESTION_IMAGE = "Images/question.gif";
	public final static String PACMAN_LOADING_IMAGE = "Images/pacmanLoading.gif";
	public final static String LOADING_BAR_IMAGE = "Images/loadingBar.gif";
	
	public final static String PACMAN_INSTRUCTION_TYPE = "pacmanInstruction";
	public final static String QUESTION_TYPE = "question";
	public final static String PACMAN_LOADING_TYPE = "pacmanLoading";
	public final static String LOADING_BAR_TYPE = "loadingBar";
	
	public Element(double xPos, double yPos, String name) {
		super(xPos,yPos);
		this.name = name;
		
		//call the loadImage method depending on the object type
				if (this.name.equals(Element.PACMAN_INSTRUCTION_TYPE)) 
					this.loadImage(Element.PACMAN_INSTRUCTION_IMAGE, 300, 300);
				else if (this.name.equals(Element.QUESTION_TYPE))
					this.loadImage(Element.QUESTION_IMAGE, 50, 50);
				else if (this.name.equals(Element.PACMAN_LOADING_TYPE))
					this.loadImage(Element.PACMAN_LOADING_IMAGE, InstructionStage.WINDOW_WIDTH, 300);
				else if (this.name.equals(Element.LOADING_BAR_TYPE))
					this.loadImage(Element.LOADING_BAR_IMAGE, 500, 300);
				
	}

	//getter method
	public void setName(String name) {
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}

	
}
