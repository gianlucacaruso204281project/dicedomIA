package model;

import controller.PlayController;
import model.MainClass.Modalità;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.InfoGame;
import view.Matrix;
import view.Menu;

public class Main {
    public static void main(String[] args) {
    	
    	
    	String[] options = {"1 dado","2 dadi","mix dadi"};
    	
		int mode = JOptionPane.showOptionDialog(null,
			"Seleziona la modalità di gioco?",
			"Dicedom",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[2]);
			
			
		
		MainClass.getInstance().setMode(Modalità.values()[mode]);
		
        /* begin init Frame */
    	
        JFrame frame = new JFrame("Dice Doom");
        frame.setSize(540, 700);

        /* end init */

        /* Info Game begin */

        InfoGame infoGame = new InfoGame();

        frame.add(infoGame, BorderLayout.PAGE_START);

        /* Info Game end */

        /* Matrix Game */
        Matrix matrix = new Matrix(GameConfig.ROWS, GameConfig.COLUMNS);

	      

        frame.add(matrix, BorderLayout.CENTER);

        /* Matrix Game end */
        
        /* Menu begin */

        Menu menu = new Menu();

        /* init Controller */
        GameIA gameIA = new GameIA(matrix,infoGame,menu);
        
        Thread ia = new Thread(gameIA);
        
        ia.start();
        
        PlayController.getInstance().setComponents(gameIA);

        /* add listener */
        menu.getPlay().addActionListener(PlayController.getInstance());

        frame.add(menu, BorderLayout.PAGE_END);

        /* Menu end */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
