package com.edo.main.objects.symbols;

import java.awt.Color;
import java.awt.Graphics2D;

public class Cross extends Symbol
{
	public Cross(int x, int y, int width, int height)
	{
		super(x, y, width, height, SYMBOL.CROSS);
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics2D g)
	{
		g.setColor(Color.RED);
		
		g.drawLine(x, y, x + width, y + height);
		g.drawLine(x + width, y, x, y + height);
	}
	
	@Override
	public void resize() {}
}
