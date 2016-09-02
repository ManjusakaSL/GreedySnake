package com.serverUI;

import com.snakedotwar.Dot;
import com.snakedotwar.Snake;	
import java.net.ServerSocket;
import java.net.Socket;
import com.snakeswar.*;

public class Server {
	
	static int cnt;							// 当前连接的计算机的数量
	public static boolean startFlag;		// 游戏开始标志
	static boolean[] vis;					// 只有vis中0~cnt-1位为true，才表示服务器汇总了所有客户端发来的信息
	public static boolean clientGetIn;
	static Snake snake;
	static Snakes snake0, snake1;
	static Dot dot;
	ServerSocket server = null;
	Socket client = null;
	int port = 8080;
	
	Server() {
		cnt = 0;
		startFlag = false;
		clientGetIn = false;
		vis = new boolean[10];
		try {
			//new SelectMode();
			new Login();
			while(SelectMode.selectMode == -1) {Thread.sleep(10);}
			server = new ServerSocket(port);
			new Thread(new serverThread()).start();
			while(true) {
				Thread.sleep(10);
				if(CreateRoom_1.startGame) {  			
					snake = new Snake(); 			// 游戏开始前，由服务器初始化各玩家的位置信息
					dot = new Dot();
					snake0 = new Snakes();
					snake1 = new Snakes();
					startFlag = true;
					break;
				}
			}
			while(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	class serverThread implements Runnable {

		public void run() {
			try {
				while(true) {	
					client = server.accept();
					clientGetIn = true;
					switch(SelectMode.selectMode) {
					case 0:
						new Thread(new snakeDotThread(client, cnt++)).start();
						break;
					case 1:
						new Thread(new snakeSnakeThread(client, cnt++)).start();
						break;
					case 2:
						new Thread(new biochemistryThread(client, cnt++)).start();
						break;
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {	
		new Server();
	}
}
