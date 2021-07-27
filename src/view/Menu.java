package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Dices;
import model.GameConfig;


public class Menu extends JPanel {

    /* serial id */
    private static final long serialVersionUID = 1L;

    /* let's play the game */
    private JButton play = new JButton("PLAY/STOP");

    /* rotate dice */
    /*  private JButton rotate = new JButton("ROTATE"); */

    /* dice random */
    private JLabel dice1 =  new JLabel(Dices.getInstance().getDiceIcon(7));
    private JLabel dice2 = new JLabel(Dices.getInstance().getDiceIcon(7));
    private JPanel dices = new JPanel();

    public Menu() {
        	
        /* init JPanel */
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(GameConfig.MARGIN_MENU, GameConfig.MARGIN_MENU, GameConfig.MARGIN_MENU, GameConfig.MARGIN_MENU));
        this.setBackground(new Color(51,25,0));
        /* init dice */

        setIcon(dice1);
        setIcon(dice2);
        
        dices.setLayout(new BoxLayout(dices,BoxLayout.X_AXIS));
        dices.setBackground(new Color(51,25,0));
        
        dices.add(dice1);
    	dices.add(dice2);
        
        /*when there are two dices*/
        dices.setLayout(new BoxLayout(dices, BoxLayout.X_AXIS));

        /* init JButton */
        play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        play.setBackground(new Color(19, 77, 0));
        play.setForeground(Color.WHITE);
       /*  rotate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); */

        /* add Components to JPanel */
        this.add(dices,BorderLayout.PAGE_START);
        this.add(play, BorderLayout.CENTER);
        /* this.add(rotate, BorderLayout.EAST); */
        

    }
    
    private void setIcon(JLabel x) {
    	x.setBorder(BorderFactory.createEmptyBorder(GameConfig.MARGIN_MENU, GameConfig.MARGIN_MENU, GameConfig.MARGIN_MENU, GameConfig.MARGIN_MENU));
        x.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public JLabel getDice() {
        return dice1;
    }

    public void setDice(ImageIcon icon) {
    	dice2.setIcon(null);
    	dice1.setIcon(icon);

    }

    public JButton getPlay() {
        return play;
    }

    public void setPlay(JButton play) {
        this.play = play;
    }
    
    public void setDices(ImageIcon x, ImageIcon y) {
    	
    	dice1.setIcon(x);
    	dice2.setIcon(y);
    	
    }
    
    
}
