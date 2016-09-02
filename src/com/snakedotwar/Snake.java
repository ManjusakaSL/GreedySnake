package com.snakedotwar;

import java.io.Serializable;
import javax.swing.JOptionPane;

import com.frame.AllConstValue;
import com.frame.Node;

public class Snake implements Serializable {

	private static final long serialVersionUID = 1L;
	private int curDir, cnt;
	private Node[] snake;
	
	public Snake() {
		curDir = 3;
		cnt = 0;
		snake = new Node[AllConstValue.Maxn];
//		snake[cnt++] = new Node((int)(Math.random() * (AllConstValue.GameWidth - 10) + AllConstValue.GameLocX), 
//			(int)(Math.random() * (AllConstValue.GameHeight - 10) + AllConstValue.GameLocY));
		snake[cnt++] = new Node(350, 250);
	}
	public Node getNode(int i) {return snake[i];}
	public int getLength() {return cnt;}
	public int getDir() {return this.curDir;}
	boolean isDead() {
		//×²Ç½
		if(snake[0].getX() > AllConstValue.GameLocX + AllConstValue.GameWidth - 2 * AllConstValue.Size) {
			return true;
		}	
		if(snake[0].getX() < AllConstValue.GameLocX + AllConstValue.Size) {
			return true;
		}	
		if(snake[0].getY() > AllConstValue.GameLocY + AllConstValue.GameHeight - 2 * AllConstValue.Size) {
			return true;
		}	
		if(snake[0].getY() < AllConstValue.GameLocY + AllConstValue.Size) {
			return true;
		}	
		for(int i = 1; i < cnt; ++i) { // ³Ô×Ô¼º
			if(snake[0].getX() == snake[i].getX() && snake[0].getY() == snake[i].getY()) 
				return true;
		}
		return false;
	}
	void move(int num) {
		Node newnode = new Node(snake[cnt-1]);
		Node newHead = new Node(snake[0].getX() + AllConstValue.Size * AllConstValue.Dir[curDir][0], 
			snake[0].getY() + AllConstValue.Size * AllConstValue.Dir[curDir][1]);
		for(int i = cnt - 1; i >= 0; --i) {
			if(i == 0) {snake[0] = newHead; continue;}
			snake[i] = snake[i-1];
		}
		if(num == 100) {
			snake[cnt++] = newnode;
		}
		if(isDead()) {
			JOptionPane.showMessageDialog(null, "Snake dead", "Message", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	 void changeDir(int tDir) {curDir = tDir;}
}
