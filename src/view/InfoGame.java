package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoGame extends JPanel{

   
    private static final long serialVersionUID = 1L;

    private JLabel points;

    public InfoGame() {
    	setBackground(new Color(51,25,0));
    	
        points = new JLabel("POINTS: "+0);
        points.setForeground(Color.WHITE);
        this.add(points, JPanel.CENTER_ALIGNMENT);
    }

    public void setPoints(int points) {

        this.points.setText("POINTS: "+points);
    }


    
}
