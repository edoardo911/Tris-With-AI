package com.edo.main.objects.symbols;

import java.awt.Graphics2D;

public abstract class Symbol
{
	public enum SYMBOL
	{
		CROSS, CIRCLE
	};
	
	protected int x, y, width, height;
	protected SYMBOL symbol;
	
	public Symbol(int x, int y, int width, int height, SYMBOL symbol)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.symbol = symbol;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g);
	public abstract void resize();
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public SYMBOL getSymbol() { return symbol; }
	
	public static boolean compare(Symbol s1, Symbol s2)
	{
		if(s1 == null || s2 == null)
			return false;
		
		return s1.symbol == s2.symbol;
	}
}
