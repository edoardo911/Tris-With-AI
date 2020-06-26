package com.edo.main.objects.symbols;

import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Symbol
{
	public Circle(int x, int y, int width, int height)
	{
		super(x, y, width, height, SYMBOL.CIRCLE);
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics2D g)
	{
		g.setColor(Color.RED);
		g.drawOval(x, y, width, height);
	}
	
	@Override
	public void resize() {}
}
