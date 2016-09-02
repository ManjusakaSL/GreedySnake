package com.serverUI;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.frame.AllConstValue;
import com.frame.Lock;

public class ShowSnakesWin extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ShowSnakesWin() {
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
		synchronized (Lock.serverSnake0) {	//���Ƶ�
			g.setColor(Color.green);
			for(int i = 0; i < Server.snake0.getLength(); ++i) {
				g.fillRect(Server.snake0.getNode(i).getX(), Server.snake0.getNode(i).getY(), 10, 10);
			}
		}
		synchronized (Lock.serverSnake1) {	//������
			g.setColor(Color.black);
			for(int i = 0; i < Server.snake1.getLength(); ++i) {
				g.fillRect(Server.snake1.getNode(i).getX(), Server.snake1.getNode(i).getY(), 10, 10);
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
