package com.serverUI;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.frame.AllConstValue;
import com.frame.Lock;

public class  ShowSnakeDotWin extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ShowSnakeDotWin() {
		new Thread(new showThread()).start();
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(AllConstValue.GameLocX, AllConstValue.GameLocY, 
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //ˢ�½���
		g.setColor(Color.black);
		g.drawRect(AllConstValue.GameLocX, AllConstValue.GameLocY, 
				AllConstValue.GameWidth, AllConstValue.GameHeight);  //���Ʊ߽�
		synchronized (Lock.serverMdot) {	//���Ƶ�
			g.setColor(Color.green);
			g.fillRect(Server.dot.getDot().getX(), Server.dot.getDot().getY(), 10, 10);
		}
		synchronized (Lock.serverSnake) {	//������
			g.setColor(Color.black);
			for(int i = 0; i < Server.snake.getLength(); ++i) {
				g.fillRect(Server.snake.getNode(i).getX(), Server.snake.getNode(i).getY(), 10, 10);
			}
		}
	}
	
	class showThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				}
				catch(Exception e) {}
			}
		}
	}
}
