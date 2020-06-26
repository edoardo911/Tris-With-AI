package com.edo.main;

import java.awt.Graphics2D;

import com.edo.main.objects.Grid;

public class Game
{
	private Grid grid;
	
	public Game()
	{
		grid = new Grid((int) (EntryPoint.getInstance().getWidth() * 0.1));
	}
	
	public void update()
	{
		grid.update();
	}
	
	public void render(Graphics2D g)
	{
		grid.render(g);
	}
	
	public void resize()
	{
		grid.setCellW((int) (EntryPoint.getInstance().getWidth() * 0.1));
		grid.resize();
	}
}
