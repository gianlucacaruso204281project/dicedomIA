package model;

import view.InfoGame;
import view.Matrix;
import view.Menu;

public class GameIA implements Runnable {
	
	private boolean play;
	private Matrix matrix;
	private InfoGame infogame;
	private Menu menu;
	private int prec = 0;
	
	
	public GameIA(Matrix matrix,InfoGame infoGame,Menu menu) {
		play = false;
		this.matrix = matrix;
		this.menu = menu;
		this.setInfogame(infoGame);
	}
	
	
	@Override
	public void run() {
		while (play) {
			
			
			System.out.println("In running...");
	        MainClass.getInstance().play();
	        
	        System.out.println("Points: "+MainClass.getInstance().getPoints());
	        
	        /* In cell */
	        int row = MainClass.getInstance().getLastCell().getRow();
	        int column = MainClass.getInstance().getLastCell().getColumn();
	        int value = MainClass.getInstance().getLastCell().getValue();
	        int size = MainClass.getInstance().getSize();
	        int points = MainClass.getInstance().getPoints();
	        
	        int countMerge = MainClass.getInstance().getCountMerge();
	        int countMerge2 = MainClass.getInstance().getCountMerge2();
	       
        	if (prec == 0 || prec != value && MainClass.getInstance().isOneDice())
        		menu.setDice(Dices.getInstance().getDiceIcon(value - countMerge));
        	
	        		
	        	
			prec = value;
			
			try {
				Thread.sleep(800);
				System.out.println("SLEEP");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        int position = (row*size) + column;
	       
			if (MainClass.getInstance().isMerge()){
	            int mat[][] = MainClass.getInstance().getMatrix();
	            for (int i = 0; i < size; i++){
	                for (int j = 0; j <size; j++){
	                    position = (i*size) + j;
	                    if (mat[i][j] != 0)
	                    	 matrix.setDice(Dices.getInstance().getDiceIcon(mat[i][j]),position);
	                    else
	                    	matrix.clearDice(position);
	                }
	            }
	        }
			 else {
				 if (!MainClass.getInstance().isOneDice()) {
					 In secondDice = MainClass.getInstance().getLastCell2();
					 menu.setDices(Dices.getInstance().getDiceIcon(value-countMerge), Dices.getInstance().getDiceIcon(secondDice.getValue()-countMerge2));
					 matrix.setDice(Dices.getInstance().getDiceIcon(secondDice.getValue()),((secondDice.getRow()*size)+secondDice.getColumn()));
				 }
				
				 matrix.setDice(Dices.getInstance().getDiceIcon(value),position);
			 }
		        
			
			infogame.setPoints(points);
			
		}
		System.out.println("stop game");
		
	}


	public boolean isPlay() {
		return play;
	}


	public void setPlay(boolean play) {
		this.play = play;
	}


	public Matrix getMatrix() {
		return matrix;
	}


	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}


	public InfoGame getInfogame() {
		return infogame;
	}


	public void setInfogame(InfoGame infogame) {
		this.infogame = infogame;
	}
	
	
	
	
	

}
