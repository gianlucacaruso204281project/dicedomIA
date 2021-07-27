package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("number")
public class Number {

	@Param(0)
	private int value;

	public Number(int value) {
		this.value = value;
	}
	
	public Number() {
		
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
}
