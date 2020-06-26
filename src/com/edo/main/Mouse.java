package com.edo.main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Mouse implements MouseMotionListener, MouseListener
{
	public static final int MOUSE_LEFT = MouseEvent.BUTTON1;
	
	private Point mousePos;
	private ArrayList<Integer> buffer;
	
	public Mouse()
	{
		mousePos = new Point(0, 0);
		buffer = new ArrayList<Integer>();
	}
	
	public boolean isButtonDown(int button)
	{
		return buffer.contains(new Integer(button));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(!buffer.contains(new Integer(e.getButton())))
			buffer.add(new Integer(e.getButton()));
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		buffer.remove(new Integer(e.getButton()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mousePos.x = e.getX();
		mousePos.y = e.getY();
	}
	
	public Point getMousePos() { return mousePos; }
}
