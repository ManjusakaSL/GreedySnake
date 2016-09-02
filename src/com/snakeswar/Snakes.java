package com.snakeswar;

import java.io.Serializable;
import javax.swing.JOptionPane;
import com.clientUI.Client;
import com.frame.AllConstValue;
import com.frame.Node;

public class Snakes implements Serializable {

	private static final long serialVersionUID = 1L;
	private int curDir, cnt;
	private Node[] snake;
	
	public Snakes() {
		curDir = 3;
		cnt = 0;
		snake = new Node[AllConstValue.Maxn];
		snake[cnt++] = new Node((int)(Math.random() * (AllConstValue.GameWidth - 50) + AllConstValue.GameLocX), 
			(int)(Math.random() * (AllConstValue.GameHeight - 50) + AllConstValue.GameLocY));
		for(int i = 0; i < 10; ++i) {
			Node newnode = new Node(snake[cnt-1]);
			Node newHead = new Node(snake[0].getX() + AllConstValue.Size * AllConstValue.Dir[curDir][0], 
				snake[0].getY() + AllConstValue.Size * AllConstValue.Dir[curDir][1]);
			for(int j = cnt - 1; j >= 0; --j) {
				if(j == 0) {snake[0] = newHead; continue;}
				snake[j] = snake[j-1];
			}
			snake[cnt++] = newnode;
		}
	}
	public Node getNode(int i) {return snake[i];}
	public int getLength() {return cnt;}
	public int getDir() {return this.curDir;}
	boolean isDead() {
		//撞墙
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
		
		if(cnt <= 3) return true; 		// 当蛇的长度小于3设定蛇死亡
		
		// 蛇头若是相撞，设定死亡
		if(Client.id == 0 && Math.abs(Client.snake1.getNode(0).getX() - snake[0].getX()) < 10 
				&& Math.abs(Client.snake1.getNode(0).getY() - snake[0].getY())< 10) {
			return true;
		}
		if(Client.id == 1 && Math.abs(Client.snake0.getNode(0).getX() - snake[0].getX()) < 10 
				&& Math.abs(Client.snake0.getNode(0).getY() - snake[0].getY())< 10) {
			return true;
		}
		
		for(int i = 1; i < cnt; ++i) {   // 吃自己
			if(snake[0].getX() == snake[i].getX() && snake[0].getY() == snake[i].getY()) 
				return true;
		}
		return false;
	}
	int remainLength() {
		if(Client.id == 0) {
			for(int i = 1; i < cnt; ++i) {   // 自己的身体若是碰掉另一条蛇的蛇头了，长度就会变短
				if(Math.abs(Client.snake1.getNode(0).getX() - snake[i].getX()) < 10 
						&& Math.abs(Client.snake1.getNode(0).getY() - snake[i].getY())< 10)
					return i; 
			}
		}
		else {
			for(int i = 1; i < cnt; ++i) {   // 自己的身体若是碰掉另一条蛇的蛇头了，长度就会变短
				if(Math.abs(Client.snake0.getNode(0).getX() - snake[i].getX()) < 10 
						&& Math.abs(Client.snake0.getNode(0).getY() - snake[i].getY())< 10)
					return i; 
			}
		}
		return cnt;
	}
	void move(int num) {   // 每走num步，蛇的长度自动增加1
		Node newnode = new Node(snake[cnt-1]);
		Node newHead = new Node(snake[0].getX() + AllConstValue.Size * AllConstValue.Dir[curDir][0], 
			snake[0].getY() + AllConstValue.Size * AllConstValue.Dir[curDir][1]);
		for(int i = cnt - 1; i >= 0; --i) {
			if(i == 0) {snake[0] = newHead; continue;}
			snake[i] = snake[i-1];
		}
		if(num == 50) {
			snake[cnt++] = newnode;
		}
		if(isDead()) {
			JOptionPane.showMessageDialog(null, "Snake" + Client.id + "dead", "Message", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		cnt = remainLength();
	}
	void changeDir(int tDir) {curDir = tDir;}
}
