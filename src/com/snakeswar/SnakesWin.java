package com.snakeswar;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import com.clientUI.Client;
import com.frame.AllConstValue;
import com.frame.Lock;

public class SnakesWin extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	
	public SnakesWin() {
		this.addKeyListener(this);
		new Thread(new snakesThread()).start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!Client.startFlag) return ;
		if(Client.id == 0) {
			synchronized (Lock.clientSnake) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if(Client.snake0.getLength() != 1 && Client.snake0.getDir() == AllConstValue.Down) break;
					Client.snake0.changeDir(AllConstValue.Up);
					break;
				case KeyEvent.VK_DOWN:
					if(Client.snake0.getLength() != 1 && Client.snake0.getDir() == AllConstValue.Up) break;
					Client.snake0.changeDir(AllConstValue.Down);
					break;
				case KeyEvent.VK_LEFT:
					if(Client.snake0.getLength() != 1 && Client.snake0.getDir() == AllConstValue.Right) break;
					Client.snake0.changeDir(AllConstValue.Left);
					break;
				case KeyEvent.VK_RIGHT:
					if(Client.snake0.getLength() != 1 && Client.snake0.getDir() == AllConstValue.Left) break;
					Client.snake0.changeDir(AllConstValue.Right);
					break;
				}
			}
		}
		else {
			synchronized (Lock.clientSnake) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if(Client.snake1.getLength() != 1 && Client.snake1.getDir() == AllConstValue.Down) break;
					Client.snake1.changeDir(AllConstValue.Up);
					break;
				case KeyEvent.VK_DOWN:
					if(Client.snake1.getLength() != 1 && Client.snake1.getDir() == AllConstValue.Up) break;
					Client.snake1.changeDir(AllConstValue.Down);
					break;
				case KeyEvent.VK_LEFT:
					if(Client.snake1.getLength() != 1 && Client.snake1.getDir() == AllConstValue.Right) break;
					Client.snake1.changeDir(AllConstValue.Left);
					break;
				case KeyEvent.VK_RIGHT:
					if(Client.snake1.getLength() != 1 && Client.snake1.getDir() == AllConstValue.Left) break;
					Client.snake1.changeDir(AllConstValue.Right);
					break;
				}
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
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //刷新界面
		g.setColor(Color.black);
		g.drawRect(AllConstValue.GameLocX, AllConstValue.GameLocY, 
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //绘制边界
		synchronized (Lock.clientSnake0) {	//绘制蛇
			g.setColor(Color.black);
			for(int i = 0; i < Client.snake0.getLength(); ++i) {
				g.fillRect(Client.snake0.getNode(i).getX(), Client.snake0.getNode(i).getY(), 10, 10);
			}
		}
		synchronized (Lock.clientSnake1) {	//绘制蛇
			g.setColor(Color.green);
			for(int i = 0; i < Client.snake1.getLength(); ++i) {
				g.fillRect(Client.snake1.getNode(i).getX(), Client.snake1.getNode(i).getY(), 10, 10);
			}
		}
	}
	class snakesThread implements Runnable
	{
		public void run() {
			int num = 0, speed = 0; // 计数，每隔100轮，蛇的长度增加1，速度增加1
			while(true) {
				try {
					if(num == 100) {speed++; num = 0;}
					Thread.sleep((100 - speed > 0 ? 100 - speed : 0));
					repaint();
					if(Client.startFlag) {
						if(Client.id == 0) {
							synchronized (Lock.clientSnake0) {
								Client.snake0.move(++num);
							}
						}
						else {
							synchronized (Lock.clientSnake1) {
								Client.snake1.move(++num);
							}
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
