package com.frame;

import java.io.Serializable;

public class Node implements Serializable{

	private static final long serialVersionUID = 1L;
	private int x, y;
	
	public Node(int tx, int ty) {x = tx; y = ty;}
	public Node(Node tmp) {x = tmp.x; y = tmp.y;}
	public int getX() {return x;}
	public int getY() {return y;}
}
