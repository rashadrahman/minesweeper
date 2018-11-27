import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

     private DotButton[][] board;
     private javax.swing.JLabel nbreOfStepsLabel;
     private GameModel gameModel;
     private GameController gameController;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        super("Minesweeper");
        this.gameModel=gameModel;
        this.gameController = gameController;
        //setSize(28*gameModel.getWidth(), 35*gameModel.getHeigth());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel grid = new JPanel(new GridLayout(gameModel.getHeigth(), gameModel.getWidth()));
        //grid.setSize(28*gameModel.getWidth(), 28*gameModel.getHeigth());
        // add(grid);
        //setLayout(new GridLayout(gameModel.getHeigth(), gameModel.getWidth()));
        board = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];
        this.setVisible(true);
        for (int row = 0; row < gameModel.getHeigth(); row++){
            for (int column = 0; column < gameModel.getWidth(); column++){
                board[column][row] = new DotButton(column, row, 11);
                board[column][row].addActionListener(gameController);
                grid.add(board[column][row]);
            }
        }

        add(grid);
        
        JButton restart = new JButton("Reset");
        restart.addActionListener(gameController);
        JButton leave = new JButton("Quit");
        leave.addActionListener(gameController);
        Label steps= new Label();
        steps.setText("Number of steps : "+gameModel.getNumberOfSteps());
        JPanel control = new JPanel();
        control.setBackground(Color.WHITE);
        control.add(steps);
        control.add(restart);
        control.add(leave);
        add(control, BorderLayout.SOUTH);
        pack();
        

    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update() {
        int b;
        b = 11;
        for (int a = 0; a< gameModel.getWidth(); a++){
            for (int c = 0; c < gameModel.getHeigth(); c++){
                if (gameModel.isCovered(a,c)){
                    b=11;
                }
                else if (gameModel.isMined(a,c)){
                    b = 10;
                }
                else if (gameModel.isFinished() && gameModel.isMined(a,c)){
                    b = 9;
                }
                else {
                    b = gameModel.getNeighbooringMines(a,c);
                }
                board[a][c].setIconNumber(b);
            }
        }
        
        repaint();

    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
        if (gameModel.isCovered(i,j)){
            return 11;
        }
        else if (gameModel.isMined(i,j)){
            return 10;
            
        }
        else if (gameModel.isFinished() && gameModel.isMined(i,j)){
            return 9;
        }
        else if (gameModel.getNeighbooringMines(i,j)<9){
            return gameModel.getNeighbooringMines(i,j);
        }

        return 11;
    }

}
