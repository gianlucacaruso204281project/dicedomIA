package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("adiacente")
public class Adiacente {


	@Param(0)
	private int row;
	@Param(1)
	private int column;
	@Param(2)
	private int value;
    @Param(3)
	private int row1;
	@Param(4)
	private int column1;
	
	public Adiacente(int r,int c,int v,int r1, int c1){
		this.row=r;
		this.column=c;
		this.value=v;
        this.row1=r1;
        this.column1=c1;
	}
	
	public Adiacente() {
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
    public int getRow1() {
		return row1;
	}

	public void setRow1(int row) {
		this.row1 = row;
	}

	public int getColumn1() {
		return column1;
	}

	public void setColumn1(int column) {
		this.column1 = column;
	}

    @Override
    public String toString() {
        return "row: " + row + ", column: " + column+", value: " + value + " row1: " + row1+" column1: " + column1;
    }
}