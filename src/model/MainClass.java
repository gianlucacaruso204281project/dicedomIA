package model;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;


public class MainClass {

	public static enum Modalità{oneDice,twoDice,mixDice};
	
	private static MainClass instance = null;
	
	
	public static MainClass getInstance(){
		if (instance == null)
			instance = new MainClass();
		return instance;
	}

	
	private boolean merge = false;
	
	private ArrayList<Adiacente> adiacenti = new ArrayList<Adiacente>();
	private ArrayList<Cell> cells = new ArrayList<Cell>();
	private ArrayList<Input> inputs = new ArrayList<Input>();
	
	private boolean OneDice = true;
	
	private Modalità mod;
	
	
	private int numAdiacenti = 0;
	private int numAdiacenti2 = 0;

	/* points game */
	private int points = 0;

	/* the last cell dice position */
	private  In lastCell;
	private In lastCell2;


	/* init Matrix */
	private  int[][] matrix = { 
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0}};
			
	
	/* size Matrix */
	private int N=5;
	
	private int countMerge = 0;
	private int countMerge2 = 0;
	
	/* upload encoding dlv file content dicedom one dice */
	private String encodingResource1="encodings/dicedom";
	private String encodingResource2="encodings/dicedomre";
	
	/* upload encoding dlv file content dicedom two dice */
	private String twodices ="encodings/twodice";
	
	private Handler handler;

	private MainClass(){}
	
	public boolean isOneDice() {
		return OneDice;
	}
	
	public void setMode(Modalità mode) {
		this.mod = mode;
	}

	public void play(){
		
		countMerge = 0;
		countMerge2 = 0;
		numAdiacenti = 0;
		numAdiacenti2 = 0;
		merge = false;	
		
		Modalità prec = null;
		
		AnswerSet a = null;
		
		ArrayList<Integer> out = new ArrayList<Integer>();
		OneDice = isOneDice(out);
		System.out.println(OneDice);
		
		try {
			if (OneDice) 
				a = positionOneDice(out.get(0)).getOptimalAnswerSets().get(0);
			else {
				try {
					
					a = positionTwoDices2(out);
					if (prec != null)
						mod = prec;
				}catch (Exception e) {
					if (mod == null || mod.equals(Modalità.mixDice))
						prec = mod;
						mod = Modalità.oneDice;
						
				}
			}	
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,
				    "IA ha perso!!",
				    "GAME OVER",
				    JOptionPane.ERROR_MESSAGE);
		}
		
		
		try {
			for(Object obj:a.getAtoms()){
				if(!(obj instanceof In) && !(obj instanceof Input) ) {
					if (obj instanceof Adiacente) {
						Adiacente ad = (Adiacente) obj;
						adiacenti.add(ad);
					}
					else if (obj instanceof Cell && !OneDice) {
						cells.add((Cell)obj);
					}
					continue;
				}
				
				if(OneDice) { 
					lastCell = (In) obj;
					System.out.println(lastCell.toString());
				}
				else {
					Input cellInput = (Input) obj;
					System.out.println(cellInput.toString());
					lastCell = new In(cellInput.getX(), cellInput.getY(), cellInput.getV());
					lastCell2 = new In(cellInput.getX1(), cellInput.getY1(), cellInput.getV1());
					matrix[lastCell2.getRow()][lastCell2.getColumn()] = lastCell2.getValue();
				}
				
				matrix[lastCell.getRow()][lastCell.getColumn()] = lastCell.getValue();
				
				
			}
		} catch (Exception e) {}
		
		for (Adiacente adiacente : adiacenti) {
			if(adiacente.getRow() == lastCell.getRow() && adiacente.getColumn() == lastCell.getColumn()) {
				numAdiacenti+=1;
			}
			if(!OneDice &&(adiacente.getRow() == lastCell2.getRow() && adiacente.getColumn() == lastCell2.getColumn())) {
				numAdiacenti2+=1;
			}
			
		}
		
		System.out.println("counter 1:"+numAdiacenti+"counter 2:"+numAdiacenti2);
		
		if((numAdiacenti >= 3 && OneDice) || ( !OneDice && (numAdiacenti >= 2 || numAdiacenti2 >= 2))){
			merge = true;
			merge(OneDice);
		}
		
		displayMatrix();
		
	}
	

	/*random number for round*/
	private int getRandomNumberDice() {
		
		//get max numer in cells
		int max = maxCell();
		int number;
		
		if (max <= 1) {
			max = 1;
			number = max;
		}
		else
			number = ThreadLocalRandom.current().nextInt(1, max+1);
		
		return number;
	}
	
	/* check 2 o 1 dice and add number/s in out*/
	private boolean isOneDice(ArrayList<Integer> out){
		
		int max = maxCell();
		
				
		if (max >= 2 || mod.equals(Modalità.twoDice)) {
			Random rand = new Random();
			
			int number = rand.nextInt(2);
			
			if (mod.equals(Modalità.oneDice))
				number = 1;
			else if (mod.equals(Modalità.twoDice))
				number = 2;
			
			if (number == 1) {
				out.add(getRandomNumberDice());
				return true;
			}
			else {
				int x = getRandomNumberDice();
				int y = getRandomNumberDice();
				if (x == y) {
					y=x+1;
				}
						
				out.add(x);
				out.add(y);
			}
		}
		else {
			out.add(getRandomNumberDice());
			return true;
		}
		
		return false;
		
	}
	
	
	private void displayMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void merge(boolean oneDice){
		
		
		if (!oneDice) {		
			
			for (Adiacente adiacente : adiacenti) {
				
				
				if(adiacente.getRow() == lastCell.getRow() && adiacente.getColumn() == lastCell.getColumn() && numAdiacenti >= 2) {
					matrix[adiacente.getRow1()][adiacente.getColumn1()] = 0;
					this.points +=(lastCell.getValue());
				}
				
				if(adiacente.getRow() == lastCell2.getRow() && adiacente.getColumn() == lastCell2.getColumn() && numAdiacenti2 >= 2) {
					matrix[adiacente.getRow1()][adiacente.getColumn1()] = 0;
					this.points +=(lastCell2.getValue());
				}
			}
			
			if (numAdiacenti >= 2) {
				countMerge+=1;
				points+=lastCell.getValue();
				lastCell.setValue(lastCell.getValue()+1);
				matrix[lastCell.getRow()][lastCell.getColumn()] = lastCell.getValue();
			}
			
			if (numAdiacenti2 >= 2) {
				countMerge2+=1;
				points+=lastCell2.getValue();
				lastCell2.setValue(lastCell2.getValue()+1);
				matrix[lastCell2.getRow()][lastCell2.getColumn()] = lastCell2.getValue();
			}
		}
			
		else {
			countMerge+=1;
			for (Adiacente adiacente : adiacenti) {
				if (adiacente.getRow1() == lastCell.getRow() && adiacente.getColumn1() == lastCell.getColumn()
						&& adiacente.getRow() == lastCell.getRow() && adiacente.getColumn() == lastCell.getColumn()) {
					points+=lastCell.getValue();
					lastCell.setValue(lastCell.getValue()+1);
					matrix[lastCell.getRow()][lastCell.getColumn()] = lastCell.getValue();
					continue;
				}
				if(adiacente.getRow() == lastCell.getRow() && adiacente.getColumn() == lastCell.getColumn()) {
					matrix[adiacente.getRow1()][adiacente.getColumn1()] = 0;
					this.points +=(lastCell.getValue());
				}
			}
		}
			
		if(lastCell.getValue() == 8)
			explotion(lastCell);
		
		if (!oneDice && lastCell2.getValue() == 8)
			explotion(lastCell2);
		
			
		adiacenti.clear();
		
		boolean secondDice = false;
		
		if (numAdiacenti2 > 0)
			secondDice = true;
		
		numAdiacenti = 0;
		numAdiacenti2 = 0;
		
		if (!oneDice && secondDice)
				retrigger(lastCell2,false,oneDice);
		if (oneDice)
			retrigger(lastCell,true,oneDice);
		
	}
	

	
	
	public void explotion(In cellX) {
		
		int i  = cellX.getRow();
		int j = cellX.getColumn();
		
		matrix[i][j] = 0;
		
		if (i-1 >= 0)
			matrix[i-1][j] = 0;
		
		if (i+1 < matrix.length)
			matrix[i+1][j] = 0;
		
		if (j-1 >= 0)
			matrix[i][j-1] = 0;
		
		if (j+1 < matrix.length)
			matrix[i][j+1] = 0;
		
		if (i+1 < matrix.length && j+1 < matrix.length)
			matrix[i+1][j+1] = 0;
		
		if (i+1 < matrix.length && j-1 >= 0)
			matrix[i+1][j-1] = 0;
		
		if (i-1 >= 0 && j-1 >= 0)
			matrix[i-1][j-1] = 0;
		
		if (i-1 >= 0 && j+1 < matrix.length)
			matrix[i-1][j+1] = 0;
	}
	
	/*position one dice*/
	private AnswerSets positionOneDice(int number) {
		
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		
		try {
			
			ASPMapper.getInstance().registerClass(Cell.class);
			ASPMapper.getInstance().registerClass(Number.class);
			ASPMapper.getInstance().registerClass(In.class);
			ASPMapper.getInstance().registerClass(Adiacente.class);
			
		}
		catch (ObjectNotValidException | IllegalAnnotationException e1) {return null;}
		InputProgram facts= new ASPInputProgram();
		
		addCellMatrixInput(facts);
		
		try {facts.addObjectInput(new Number(number));}
		catch (Exception e1) {return null;}
		
		handler.addProgram(facts);
		InputProgram encoding= new ASPInputProgram();
		encoding.addFilesPath(encodingResource1);
		handler.addProgram(encoding);
		Output o =  handler.startSync();
		AnswerSets answersets = (AnswerSets) o;
		
		return answersets;
		
	}
	
	private AnswerSet positionTwoDices2(ArrayList<Integer> input) {
		
		Handler handlerTwoDicesSel = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		
		try {
			ASPMapper.getInstance().registerClass(Number.class);
			ASPMapper.getInstance().registerClass(Cell.class);
			ASPMapper.getInstance().registerClass(Input.class);
			ASPMapper.getInstance().registerClass(Adiacente.class);
	
		}
		catch (ObjectNotValidException | IllegalAnnotationException e1) {}
		
		InputProgram facts= new ASPInputProgram();
		
		for (Integer x : input) {
			try {facts.addObjectInput(new Number(x));} 
			catch (Exception e) {}
		}
		
		addCellMatrixInput(facts);
		
		handlerTwoDicesSel.addProgram(facts);
		InputProgram encoding= new ASPInputProgram();
		encoding.addFilesPath(twodices);
		handlerTwoDicesSel.addProgram(encoding);
		Output o =  handlerTwoDicesSel.startSync();
		AnswerSets answersets = (AnswerSets) o;
		
		return answersets.getOptimalAnswerSets().get(0);
		
	}

	
	
	/*copy matrix in input for dlv*/
	private boolean addCellMatrixInput(InputProgram facts) {
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(matrix[i][j]!=0){
					try {
						Cell cell = new Cell(i, j, matrix[i][j]);
						facts.addObjectInput(cell);
					} catch (Exception e) {
						return false;
					}
				}	
			}
		}
		return true;
	}
	
	
	public void retrigger(In cellX, boolean firstDice, boolean oneDice) {
		
		Handler handler1 = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		
		try {
			ASPMapper.getInstance().registerClass(Number.class);
			ASPMapper.getInstance().registerClass(Cell.class);
			ASPMapper.getInstance().registerClass(In.class);
			ASPMapper.getInstance().registerClass(Adiacente.class);
			
		}
		catch (ObjectNotValidException | IllegalAnnotationException e1) {}
		
		displayMatrix();
		
		InputProgram facts= new ASPInputProgram();
		
		int mc= 0;
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(matrix[i][j]!=0){
					try {
						Cell cell = new Cell(i, j, matrix[i][j]);
						facts.addObjectInput(cell);
						mc++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}			
		}
		
		
		handler1.addProgram(facts);
		InputProgram encoding= new ASPInputProgram();
		encoding.addFilesPath(encodingResource2);
		handler1.addProgram(encoding);
		Output o =  handler1.startSync();
		AnswerSets answersets = (AnswerSets) o;
		
		if (answersets.getAnswersets().isEmpty() || mc <= 1)
			return ;
		
		mc = 0;
		
		AnswerSet a = answersets.getAnswersets().get(0);
		

		try {
			for(Object obj:a.getAtoms()) {
				if (obj instanceof Adiacente) { 
					Adiacente adiacente = (Adiacente) obj;
					if(adiacente.getRow() == cellX.getRow() && adiacente.getColumn() == cellX.getColumn()) {
						if (firstDice)
							numAdiacenti+=1;
						else
							numAdiacenti2+=1;
						adiacenti.add(adiacente);
					}
				}
			}
		}
		catch (Exception e) {return ;}
		
		if (!firstDice && numAdiacenti2 >= 2) {
			points+=lastCell2.getValue();
			lastCell2.setValue(lastCell2.getValue()+1);
			matrix[lastCell2.getRow()][lastCell2.getColumn()] = lastCell2.getValue();
			merge(oneDice);
		}
			
		
		if(numAdiacenti >= 2) {
			points+=lastCell.getValue();
			lastCell.setValue(lastCell.getValue()+1);
			matrix[lastCell.getRow()][lastCell.getColumn()] = lastCell.getValue();
			merge(oneDice);
		}
		
	}
	
	/* check all cell for find the max number in cells*/
	public int maxCell() {
		int max = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (max < matrix[i][j])
					max = matrix[i][j];
			}
		}
		return max;
	}
	
	
	public boolean isMerge() {
		return merge;
	}

	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public In getLastCell() {
		return lastCell;
	}
	
	public In getLastCell2() {
		return lastCell2;
	}


	public int[][] getMatrix() {
		return matrix;
	}

	public int getMatrixValue(int i, int j) {
		return matrix[i][j];
	}

	public void setMatrix(int i, int j,int value) {
		matrix[i][j] = value;
	}

	public int getSize() {
		return N;
	}


	public int getCountMerge() {
		return countMerge;
	}

	
	public int getCountMerge2() {
		return countMerge2;
	}

}

