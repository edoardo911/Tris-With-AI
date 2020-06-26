package com.edo.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class EntryPoint implements Runnable
{
	private int WIDTH = 1280, HEIGHT = 720;
	private boolean FULLSCREEN = false;
	private boolean running = true;
	private JFrame frame;
	private Canvas canvas = new Canvas();
	private static EntryPoint instance;
	private Game game;
	private Mouse mouse = new Mouse();
	
	public EntryPoint()
	{
		instance = this;
		
		initFrame();
		game = new Game();
		
		new Thread(this).start();
	}
	
	public static void main(String[] args) { new EntryPoint(); }
	
	private void initFrame()
	{
		frame = new JFrame("Tris - Demo");
		
		if(FULLSCREEN)
			frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		else
			frame.setSize(WIDTH, HEIGHT);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(canvas);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		
		frame.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				super.componentResized(e);
				resize();
			}
		});
		
		frame.setUndecorated(FULLSCREEN);
		frame.setResizable(true);
		frame.setVisible(true);
		
		canvas.setFocusable(false);
	}
	
	private void update()
	{
		game.update();
	}
	
	private void render()
	{
		BufferStrategy buffer = canvas.getBufferStrategy();
		
		if(buffer == null)
		{
			canvas.createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		game.render(g);
		
		g.dispose();
		buffer.show();
	}
	
	public void resize()
	{
		if(game != null)
			game.resize();
	}
	
	public int getWidth() { return canvas.getWidth(); }
	public int getHeight() { return canvas.getHeight(); }
	
	public static EntryPoint getInstance() { return instance; }
	
	public Mouse getMouse() { return mouse; }

	@Override
	public void run()
	{
		while(running)
		{
			update();
			render();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
