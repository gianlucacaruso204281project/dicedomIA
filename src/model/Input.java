package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("Input")
public class Input {
	
	@Param(0)
	private int x;
	@Param(1)
	private int y;
	@Param(2)
	private int v;
	@Param(3)
	private int x1;
	@Param(4)
	private int y1;
	@Param(5)
	private int v1;

	public Input() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Input(int x, int y, int v, int x1, int y1, int v1) {
		super();
		this.x = x;
		this.y = y;
		this.v = v;
		this.x1 = x1;
		this.y1 = y1;
		this.v1 = v1;
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getV1() {
		return v1;
	}

	public void setV1(int v1) {
		this.v1 = v1;
	}
	
	@Override
	public String toString() {
		return "InputTwoDicesAfterPosition [x=" + x + ", y=" + y + ", v=" + v + ", x1=" + x1 + ", y1=" + y1 + ", v1="
				+ v1 + "]";
	}
}
