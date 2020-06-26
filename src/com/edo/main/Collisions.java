package com.edo.main;

import java.awt.Point;
import java.awt.Rectangle;

public class Collisions
{
	public static boolean collide(Rectangle rect, Point p) { return rect.contains(p); }
}
