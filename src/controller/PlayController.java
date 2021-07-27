package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameIA;

public class PlayController implements ActionListener {
    
    private static PlayController instance = null;
    

    private GameIA gameIA;

 

    private PlayController(){}

    public static PlayController getInstance(){
        if (instance == null)
            instance = new PlayController();

        return instance;
    }

    public void setComponents(GameIA gameIA){
        this.gameIA = gameIA;

    } 
	
	@Override
	public void actionPerformed(ActionEvent e) {
        
		if (!gameIA.isPlay()) {
			gameIA.setPlay(true);
			Thread t = new Thread(gameIA);
			t.start();
		}
		else {
			gameIA.setPlay(false);
		}
		
	}
    
}
