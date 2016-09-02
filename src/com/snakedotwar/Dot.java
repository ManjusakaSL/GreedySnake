package com.snakedotwar;

import java.io.Serializable;
import javax.swing.JOptionPane;
import com.clientUI.Client;
import com.frame.AllConstValue;
import com.frame.Node;

public class Dot implements Serializable{

	private static final long serialVersionUID = 1L;
	private Node dot;
	private int curDir;
	
	public Dot() {
		curDir = 3;
		dot = new Node((int)(Math.random() * (AllConstValue.GameWidth - 10) + AllConstValue.GameLocX), 
				(int)(Math.random() * (AllConstValue.GameHeight - 10) + AllConstValue.GameLocY));
	}
	boolean isDead() {
		if(Math.abs(Client.snake.getNode(0).getX() - dot.getX()) < 10 
				&& Math.abs(Client.snake.getNode(0).getY() - dot.getY()) < 10) {  // ±»³Ô
			return true;
		}
		// ×²Ç½
		if(dot.getX() > AllConstValue.GameLocX + AllConstValue.GameWidth - 2 * AllConstValue.Size) {
			return true;
		}	
		if(dot.getX() < AllConstValue.GameLocX + AllConstValue.Size) {
			return true;
		}	
		if(dot.getY() > AllConstValue.GameLocY + AllConstValue.GameHeight - 2 * AllConstValue.Size) {
			return true;
		}	
		if(dot.getY() < AllConstValue.GameLocY + AllConstValue.Size) {
			return true;
		}
		return false;
	}
	void move() {
		dot = new Node(dot.getX() + AllConstValue.Size * AllConstValue.Dir[curDir][0], 
			dot.getY() + AllConstValue.Size * AllConstValue.Dir[curDir][1]);
		if(isDead()) {
			JOptionPane.showMessageDialog(null, "dot dead", "Message", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	void changeDir(int tDir) {curDir = tDir;}
	public Node getDot() {return dot;}
}
