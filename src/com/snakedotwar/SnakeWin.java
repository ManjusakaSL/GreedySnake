package com.snakedotwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import com.clientUI.Client;
import com.frame.AllConstValue;
import com.frame.Lock;

public class SnakeWin extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	
	public SnakeWin() {
		this.addKeyListener(this);
		new Thread(new snakeThread()).start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!Client.startFlag) return ;
		synchronized (Lock.clientSnake) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if(Client.snake.getLength() != 1 && Client.snake.getDir() == AllConstValue.Down) break;
				Client.snake.changeDir(AllConstValue.Up);
				break;
			case KeyEvent.VK_DOWN:
				if(Client.snake.getLength() != 1 && Client.snake.getDir() == AllConstValue.Up) break;
				Client.snake.changeDir(AllConstValue.Down);
				break;
			case KeyEvent.VK_LEFT:
				if(Client.snake.getLength() != 1 && Client.snake.getDir() == AllConstValue.Right) break;
				Client.snake.changeDir(AllConstValue.Left);
				break;
			case KeyEvent.VK_RIGHT:
				if(Client.snake.getLength() != 1 && Client.snake.getDir() == AllConstValue.Left) break;
				Client.snake.changeDir(AllConstValue.Right);
				break;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(AllConstValue.GameLocX, AllConstValue.GameLocY, 
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //ˢ�½���
		g.setColor(Color.black);
		g.drawRect(AllConstValue.GameLocX, AllConstValue.GameLocY, 
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //���Ʊ߽�
		synchronized (Lock.clientMdot) {	//���Ƶ�
			g.setColor(Color.green);
			g.fillRect(Client.dot.getDot().getX(), Client.dot.getDot().getY(), 10, 10);
		}
		synchronized (Lock.clientSnake) {	//������
			g.setColor(Color.black);
			for(int i = 0; i < Client.snake.getLength(); ++i) {
				g.fillRect(Client.snake.getNode(i).getX(), Client.snake.getNode(i).getY(), 10, 10);
			}
		}
	}
	class snakeThread implements Runnable
	{
		public void run() {
			int num = 0, speed = 0; 	// ������ÿ��100�֣��ߵĳ�������1���ٶ�����1
			while(true) {
				try {
					if(num == 100) {speed++; num = 0;}
					Thread.sleep((100 - speed > 0 ? 100 - speed : 0));
					repaint();
					if(Client.startFlag) {
						synchronized (Lock.clientSnake) {
							Client.snake.move(++num);
						}
					}
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
