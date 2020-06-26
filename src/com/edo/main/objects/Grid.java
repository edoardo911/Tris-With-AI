package com.edo.main.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.edo.main.Collisions;
import com.edo.main.EntryPoint;
import com.edo.main.Mouse;
import com.edo.main.objects.symbols.Circle;
import com.edo.main.objects.symbols.Cross;
import com.edo.main.objects.symbols.Symbol;

public class Grid
{
	private int x, y, cellW;
	private Symbol[][] elements;
	private Rectangle[][] bounds;
	private boolean yourTurn, endgame;
	
	public Grid(int cellW)
	{
		this.cellW = cellW;
		
		elements = new Symbol[3][3];
		bounds = new Rectangle[3][3];
		yourTurn = true;
		endgame = false;
		
		x = (EntryPoint.getInstance().getWidth() - cellW * 3) / 2;
		y = (EntryPoint.getInstance().getHeight() - cellW * 3) / 2;
		
		bounds[0][0] = new Rectangle((int) (cellW * 0.02) + x, (int) (cellW * 0.02) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[0][1] = new Rectangle((int) (cellW * 1.03) + x, (int) (cellW * 0.02) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[0][2] = new Rectangle((int) (cellW * 2.04) + x, (int) (cellW * 0.02) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		
		bounds[1][0] = new Rectangle((int) (cellW * 0.02) + x, (int) (cellW * 1.03) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[1][1] = new Rectangle((int) (cellW * 1.03) + x, (int) (cellW * 1.03) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[1][2] = new Rectangle((int) (cellW * 2.04) + x, (int) (cellW * 1.03) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		
		bounds[2][0] = new Rectangle((int) (cellW * 0.02) + x, (int) (cellW * 2.04) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[2][1] = new Rectangle((int) (cellW * 1.03) + x, (int) (cellW * 2.04) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[2][2] = new Rectangle((int) (cellW * 2.04) + x, (int) (cellW * 2.04) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
	}
	
	public void update()
	{
		if(!endgame)
		{
			if(yourTurn)
			{	
				for(int y = 0; y < 3; y++)
				{
					for(int x = 0; x < 3; x++)
					{
						if(elements[y][x] != null)
							elements[y][x].update();
						
						if(Collisions.collide(bounds[y][x], EntryPoint.getInstance().getMouse().getMousePos()))
						{
							if(EntryPoint.getInstance().getMouse().isButtonDown(Mouse.MOUSE_LEFT))
							{
								if(elements[y][x] == null)
								{
									elements[y][x] = new Circle(bounds[y][x].x, bounds[y][x].y, bounds[y][x].width, bounds[y][x].height);
									
									if(!check())
										yourTurn = false;
									else
										endgame = true;
								}
							}
						}
					}
				}
			}
			else
			{
				Random rand = new Random();
				
				int x = 0;
				int y = 0;
				boolean counterStrategy = false;
				
				for(int j = 0; j < 3 && !counterStrategy; j++)
				{
					for(int i = 0; i < 3; i++)
					{
						if(elements[j][i] == null)
						{
							elements[j][i] = new Circle(0, 0, 0, 0);

							if(check())
							{
								x = i;
								y = j;
								counterStrategy = true;
								break;
							}
							else
								elements[j][i] = null;
						}
					}
				}
				
				if(!counterStrategy)
				{
					do
					{
						x = rand.nextInt(3);
						y = rand.nextInt(3);
					} while(elements[y][x] != null);
				}
				
				elements[y][x] = new Cross(bounds[y][x].x, bounds[y][x].y, bounds[y][x].width, bounds[y][x].height);
				
				if(!check())
					yourTurn = true;
				else
					endgame = true;
			}
		}
	}
	
	private boolean check()
	{
		return  (Symbol.compare(elements[0][0], elements[0][1]) && Symbol.compare(elements[0][0], elements[0][2])) ||
				(Symbol.compare(elements[1][0], elements[1][1]) && Symbol.compare(elements[1][0], elements[1][2])) ||
				(Symbol.compare(elements[2][0], elements[2][1]) && Symbol.compare(elements[2][0], elements[2][2])) ||
				(Symbol.compare(elements[0][0], elements[1][0]) && Symbol.compare(elements[1][0], elements[2][0])) ||
				(Symbol.compare(elements[0][1], elements[1][1]) && Symbol.compare(elements[0][1], elements[2][1])) ||
				(Symbol.compare(elements[0][2], elements[1][2]) && Symbol.compare(elements[0][2], elements[2][2])) ||
				(Symbol.compare(elements[0][0], elements[1][1]) && Symbol.compare(elements[0][0], elements[2][2])) ||
				(Symbol.compare(elements[0][2], elements[1][1]) && Symbol.compare(elements[0][2], elements[2][0]));
	}
	
	public void render(Graphics2D g)
	{
		g.setColor(Color.RED);
		
		g.drawLine(cellW + x, y, x + cellW, y + (cellW * 3));
		g.drawLine(x + (cellW * 2), y, x + (cellW * 2), y + (cellW * 3));
		
		g.drawLine(x, y + cellW, x + (cellW * 3), y + cellW);
		g.drawLine(x, y + (cellW * 2), x + (cellW * 3), y + (cellW * 2));
				
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				if(elements[y][x] != null)
					elements[y][x].render(g);
			}
		}
	}
	
	public void setCellW(int cellW) { this.cellW = cellW; }
	
	public void resize()
	{	
		x = (EntryPoint.getInstance().getWidth() - cellW * 3) / 2;
		y = (EntryPoint.getInstance().getHeight() - cellW * 3) / 2;
		
		bounds[0][0] = new Rectangle((int) (cellW * 0.02) + x, (int) (cellW * 0.02) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[0][1] = new Rectangle((int) (cellW * 1.03) + x, (int) (cellW * 0.02) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[0][2] = new Rectangle((int) (cellW * 2.04) + x, (int) (cellW * 0.02) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		
		bounds[1][0] = new Rectangle((int) (cellW * 0.02) + x, (int) (cellW * 1.03) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[1][1] = new Rectangle((int) (cellW * 1.03) + x, (int) (cellW * 1.03) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[1][2] = new Rectangle((int) (cellW * 2.04) + x, (int) (cellW * 1.03) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		
		bounds[2][0] = new Rectangle((int) (cellW * 0.02) + x, (int) (cellW * 2.04) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[2][1] = new Rectangle((int) (cellW * 1.03) + x, (int) (cellW * 2.04) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		bounds[2][2] = new Rectangle((int) (cellW * 2.04) + x, (int) (cellW * 2.04) + y, (int) (cellW * 0.96), (int) (cellW * 0.96));
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				if(elements[y][x] != null)
				{
					elements[y][x].setX(bounds[y][x].x);
					elements[y][x].setY(bounds[y][x].y);
					elements[y][x].setWidth(bounds[y][x].width);
					elements[y][x].setHeight(bounds[y][x].height);
				}
			}
		}
	}
}
