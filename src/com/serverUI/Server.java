package com.serverUI;

import com.snakedotwar.Dot;
import com.snakedotwar.Snake;	
import java.net.ServerSocket;
import java.net.Socket;
import com.snakeswar.*;

public class Server {
	
	static int cnt;							// ��ǰ���ӵļ����������
	public static boolean startFlag;		// ��Ϸ��ʼ��־
	static boolean[] vis;					// ֻ��vis��0~cnt-1λΪtrue���ű�ʾ���������������пͻ��˷�������Ϣ
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
					snake = new Snake(); 			// ��Ϸ��ʼǰ���ɷ�������ʼ������ҵ�λ����Ϣ
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
