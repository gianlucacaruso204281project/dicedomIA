package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Matrix extends JPanel {

	
	private static final long serialVersionUID = -7038157275821816013L;

	private int rows;
	private int columns;

	private ArrayList<JLabel> dices = new ArrayList<JLabel>();
	
	public Matrix(int rows, int columns) {
		setBackground(new Color(70, 128, 64));
		/* init */
		this.setLayout(new GridLayout(rows,columns,1,1));

		/* margin */
		this.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		
		/* sizeX/sizeY */
		this.rows = rows;
		this.columns = columns;

		/* init  matrix game*/
		for (int j = 0; j < (rows*columns); j++) {
			
			JLabel dice = new JLabel();
			dice.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			/* add in array position */
			dices.add(dice);
			/* add in root */
			this.add(dice);
		}
		
	}

	public void setDice(ImageIcon diceImageIcon, int index) {
		
		System.out.println(index);
		dices.get(index).setIcon(diceImageIcon);

	}

	public void clearDice(int index) {
		dices.get(index).setIcon(null);
	}



	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}


	public ArrayList<JLabel> getDices() {
		return dices;
	}



	
}
