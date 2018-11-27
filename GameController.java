import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {

        gameModel = new GameModel(width, height, numberOfMines);
        gameView = new GameView(gameModel, this);
        ///gameView.update();
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals("Reset")){
            reset();
            System.out.print("update");
        }

        if (e.getActionCommand().equals("Quit")){
            System.exit(0);
        }

        if (e.getSource() instanceof DotButton){
            DotButton b;
            b = (DotButton) e.getSource();
            if(gameModel.isCovered(b.getColumn(), b.getRow())) {
                play(b.getColumn(), b.getRow());
            
            }
            

        }
    }

    /**
     * resets the game
     */
    private void reset(){

        gameModel.reset();
        gameView.update();

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){
        gameModel.step();
        //System.out.print(gameModel.getNumberOfSteps());
        gameModel.click(width, heigth);
        gameModel.uncover(width, heigth);
        if (gameModel.isMined(width,heigth)){
            //System.out.print("this is a mine");
            gameModel.uncoverAll();
            gameView.update();
            JOptionPane.showMessageDialog(gameView, "Ouch you lost in "+gameModel.getNumberOfSteps()+" steps, would you like to play again?");
        }

        // else if (gameModel.isBlank(width, heigth)){
        //     clearZone(gameModel.get(width, heigth));
        //     gameView.update();
        // }
        else {
            gameView.update();
            //System.out.print(gameModel.getNumberOfSteps());
        }
        
        if (gameModel.isFinished()){
            
            //JOptionPane.showOptionDialog(gameView, "a");
            gameView.update();
        }
        //System.out.print(width+ "," +heigth);
        //System.out.print(gameModel.getNeighbooringMines(width, heigth));

    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot){
        Stack<DotInfo>  stack = new GenericArrayStack<DotInfo>(gameModel.getWidth()*gameModel.getHeigth());
        stack.push(initialDot);

        DotInfo dotInfo;
        while(!stack.isEmpty()){
            dotInfo = stack.pop();
            for(int i = Math.max(dotInfo.getX()-1, 0); i <= Math.min(dotInfo.getX()+1, gameModel.getWidth()-1); i++) {
                for(int j = Math.max(dotInfo.getY()-1, 0); j <= Math.min(dotInfo.getY()+1, gameModel.getHeigth()-1); j++) {
                    if(gameModel.isCovered(i,j)) {
                        gameModel.uncover(i,j);
                        if(gameModel.isBlank(i,j)) {
                            stack.push(gameModel.get(i, j));
                        }
                    }
                }
            }
        }

    }
}
