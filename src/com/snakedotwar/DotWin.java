package com.snakedotwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import com.clientUI.Client;
import com.frame.AllConstValue;
import com.frame.Lock;

public class DotWin extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	
	public DotWin() {
		this.addKeyListener(this);
		new Thread(new dotThread()).start();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(!Client.startFlag) return ;
		synchronized (Lock.clientMdot) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				//Client.mDot[Client.id].changeDir(AllConstValue.Up);
				Client.dot.changeDir(AllConstValue.Up);
				break;
			case KeyEvent.VK_DOWN:
				//Client.mDot[Client.id].changeDir(AllConstValue.Down);
				Client.dot.changeDir(AllConstValue.Down);
				break;
			case KeyEvent.VK_LEFT:
				//Client.mDot[Client.id].changeDir(AllConstValue.Left);
				Client.dot.changeDir(AllConstValue.Left);
				break;
			case KeyEvent.VK_RIGHT:
				//Client.mDot[Client.id].changeDir(AllConstValue.Right);
				Client.dot.changeDir(AllConstValue.Right);
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
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //刷新界面
		g.setColor(Color.black);
		g.drawRect(AllConstValue.GameLocX, AllConstValue.GameLocY, 
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //绘制边界
		synchronized (Lock.clientMdot) { 	//绘制点
			g.setColor(Color.green);
			g.fillRect(Client.dot.getDot().getX(), Client.dot.getDot().getY(), 10, 10);
		}
		synchronized (Lock.clientSnake) { 	//绘制蛇
			g.setColor(Color.black);
			for(int i = 0; i < Client.snake.getLength(); ++i) {
				g.fillRect(Client.snake.getNode(i).getX(), Client.snake.getNode(i).getY(), 10, 10);
			}
		}
	}
	class dotThread implements Runnable
	{
		public void run() {
			while(true) {
				try {
					Thread.sleep(100);
					repaint();
					if(Client.startFlag) {
						synchronized (Lock.clientMdot) {
							Client.dot.move();
							//Client.mDot[Client.id].move();
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
