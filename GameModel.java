import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {

     private int widthOfGame;
     private int heigthOfGame;
     private DotInfo[][] model;
     private int numberOfSteps;
     private int numberUncovered;
     private int numberOfMines;
     private java.util.Random generator;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        
        this.widthOfGame = width;
        this.heigthOfGame = heigth;
        this.numberOfMines = numberOfMines;
        this.numberOfSteps = 0;
        this.numberUncovered = 0;
        this.model = new DotInfo[width][heigth];
        this.generator = new Random();

        for (int i = 0; i < widthOfGame; i++){
        	for (int j = 0; j < heigthOfGame; j++){
        		model[i][j] = new DotInfo(i,j);
        	}
        }

        for (int a = 0; a < numberOfMines; a++){
            model[generator.nextInt(widthOfGame)][generator.nextInt(heigthOfGame)].setMined();
        }

    }



    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
   public void reset(){

        for (int row = 0; row < heigthOfGame; row++) {
            for (int column = 0; column < widthOfGame; column++) {
                model[column][row] = new DotInfo(column,row);
            }
        }
        for (int a = 0; a < numberOfMines; a++){
            model[generator.nextInt(widthOfGame)][generator.nextInt(heigthOfGame)].setMined();
        }
        numberOfSteps = 0;
        numberUncovered = 0;
    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
        return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
        return widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
        return model[i][j].isMined();

    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
        return model[i][j].hasBeenClicked();

    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
        if (model[i][j].getNeighbooringMines() == 0){
            return true;
        }
        else {
            return false;
        }

    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
        return model[i][j].isCovered();


    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        int nbr=0;
        int till= (i+2);
        int down= (j+2);
        if (i-1>=0 && j-1>=0 && (i+2)<(widthOfGame+1) && (j+2)<(heigthOfGame+1)){
            for (int startx= (i-1); startx < till; startx++){
                for (int starty= (j-1); starty < down; starty++){
                    if (model[startx][starty].isMined()){
                        nbr= (nbr+1);
                    }
                }
            }
        }
        
        else if (i-1<0 && j-1>=0 && (i+2)<(widthOfGame+1) && (j+2)<(heigthOfGame+1)){
            for (int startx= (i); startx < till; startx++){
                for (int starty= (j-1); starty < down; starty++){
                    if (model[startx][starty].isMined()){
                        nbr= (nbr+1);
                    }
                }
            }
        }
        

        else if (i-1>=0 && j-1<0 && (i+2)<(widthOfGame+1) && (j+2)<(heigthOfGame+1)){
            for (int startx= (i-1); startx < till; startx++){
                for (int starty= (j); starty < down; starty++){
                    if (model[startx][starty].isMined()){
                        nbr= (nbr+1);
                    }
                }
            }
        }

        else if (i-1>=0 && j-1>=00 && (i+2)>=(widthOfGame+1) && (j+2)<(heigthOfGame+1)){
            for (int startx= (i-1); startx < (i+1); startx++){
                for (int starty= (j-1); starty < down; starty++){
                    if (model[startx][starty].isMined()){
                        nbr= (nbr+1);
                    }
                }
            }
        }

        else if (i-1>=0 && j-1>=0 && (i+2)<(widthOfGame+1) && (j+2)>=(heigthOfGame+1)){
            for (int startx= (i-1); startx < (till); startx++){
                for (int starty= (j-1); starty < (j+1); starty++){
                    if (model[startx][starty].isMined()){
                        nbr= (nbr+1);
                    }
                }
            }
        }

        return nbr;

    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
        model[i][j].uncover();
        numberUncovered=0;
        for (int x=0; x < widthOfGame; x++){
                for (int y= 0; y < heigthOfGame; y++){
                    if (!(model[x][y].isCovered())){
                        numberUncovered= (numberUncovered+1);
                    }
                }
            }
        //System.out.print(numberUncovered);
        //for loop through list for uncovered values

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
        model[i][j].click();
    }
     
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){

        for (int i=0; i<widthOfGame; i++){
            for(int j=0; j<heigthOfGame; j++){
                model[i][j].uncover();
            }
        }

    }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
        return numberOfSteps;

    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
        return model[i][j];

    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
        numberOfSteps=numberOfSteps+1;

    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
        if (numberUncovered == (widthOfGame*heigthOfGame)-numberOfMines){
            return true;
        }
        else if (numberUncovered == (widthOfGame*heigthOfGame)){
            return true;
        }

        else {
            return false;
        }
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    // public String toString(){
        
    // }
}
