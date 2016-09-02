package com.clientUI;


import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;


import javax.swing.JFrame;
import com.frame.AllConstValue;
import com.frame.Lock;
import com.frame.subJFrame;
import com.snakedotwar.Dot;
import com.snakedotwar.DotWin;
import com.snakedotwar.Snake;
import com.snakedotwar.SnakeWin;
import com.snakeswar.Snakes;
import com.snakeswar.SnakesWin;

public class Client {	
	
	public static boolean startFlag = false;				// ��Ϸ��ʼ�ı�־   
	public static int id, cnt;								// �ÿͻ��˵�id���ܵ������Ŀ
	public static Snake snake = null;
	public static Snakes snake0 = null, snake1 = null;
	public static Dot dot = null;
	public static boolean connectWR = false , connectAC = false;
	int selectMode = -1;
	Socket client = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	DataInputStream is = null;
	DataOutputStream os = null;
	
	Client() {
		try {
			new logIn();
			while(!logIn.startClient) {Thread.sleep(10);}
			client = new Socket(logIn.adr, 8080);
			connectAC = true;
			is = new DataInputStream(client.getInputStream());
			os = new DataOutputStream(client.getOutputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			selectMode = is.readInt();  				// �ͻ���֪����ǰ���������ģʽ
			id = is.readInt();                   	    // �ͻ��˵õ��Լ���id
			cnt = is.readInt();                         // �ͻ��˵õ����в�����Ϸ�������Ŀ
			
			switch(selectMode) {
			case 0:
				snakeDotMode();
				break;
			case 1:
				snakeSnakeMode();
				break;
			case 2:
				//biochmistryMode();
				break;
			} 
		}
		catch(java.net.ConnectException e) {
			connectWR = true;
			e.printStackTrace();
		}
		catch(java.net.UnknownHostException e) {
			connectWR = true;
			e.printStackTrace();
		}
		catch(java.net.SocketException e) {
			connectWR = true;
			e.printStackTrace();
		}
		catch(Exception e) {
			connectWR = true;
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
				os.close();
				in.close();
				out.close();
				client.close();
			}
			catch(Exception e) {}
		}
	}
	void snakeDotMode() throws Exception {
		initSnakeDot();                   		// ��Ϸ��ʼǰ�ĳ�ʼ����Ϣ����Server��init��Ӧ
		snakeDotDisplay();		  				// ��Ϸ��ʼ�󣬸��������Ϸ�Ĵ���
		
		/*
		 * ��Ϸ��ʼ�����������ͨ�ţ�ÿ����Ҷ�������������Լ���λ����Ϣ��
		 * ���ӷ�������������������ҵ���Ϣ
		 */                                                                                                                                                                                                                                                        
		while(true) {
			if(id == 0) out.writeObject(snake);
			else out.writeObject(dot);
			synchronized (Lock.clientSnake) {
				Object obj = in.readObject();
				snake = (Snake) obj;
			}
			synchronized (Lock.clientMdot) {
				Object obj = in.readObject();
				dot = (Dot) obj;
			}
			Thread.sleep(10);
		}
	}
	void snakeSnakeMode() throws Exception{
		initSnakeSnake();
		snakeSnakeDisplay();
		
		/*
		 * ��Ϸ��ʼ�����������ͨ�ţ�ÿ����Ҷ�������������Լ���λ����Ϣ��
		 * ���ӷ�������������������ҵ���Ϣ
		 */                                                                                                                                                                                                                                                        
		while(true) {
			if(id == 0) out.writeObject(snake0);
			else out.writeObject(snake1);
			synchronized (Lock.clientSnake0) {
				Object obj = in.readObject();
				snake0 = (Snakes) obj;
			}
			synchronized (Lock.clientSnake1) {
				Object obj = in.readObject();
				snake1 = (Snakes) obj;
			}
			Thread.sleep(10);
		}
	}
	void initSnakeDot() throws Exception {
		Object obj = in.readObject();               // �ͻ��˴ӷ������õ�������ҵĳ�ʼλ����Ϣ
		snake = (Snake) obj;
		obj = in.readObject();
		dot = (Dot) obj;
		startFlag = is.readBoolean();               // game begin 
	}
	void initSnakeSnake() throws Exception {
		Object obj = in.readObject(); 				// �ͻ��˴ӷ������õ�������ҵĳ�ʼλ����Ϣ
		snake0 = (Snakes) obj;
		obj = in.readObject();
		snake1 = (Snakes) obj;
		startFlag = is.readBoolean();               // game begin 
	}
	void snakeDotDisplay() {
		JFrame frame;
		SnakeWin snakeWin;
		DotWin dotWin;
		frame = new subJFrame("�����ߵ����ս");
		frame.setSize(AllConstValue.FrameWidth, AllConstValue.FrameHeight);
		frame.setLocation(AllConstValue.FrameLocX, AllConstValue.FrameLocY);
		frame.setVisible(true);
		if(id == 0) {
			snakeWin = new SnakeWin();
			frame.add(snakeWin);
			snakeWin.requestFocus();
		}
		else {
			dotWin = new DotWin();
			frame.add(dotWin);
			dotWin.requestFocus();
		}
	}
	void snakeSnakeDisplay() {
		JFrame frame;
		SnakesWin snakesWin;
		frame = new subJFrame("�������ߴ���ս");
		frame.setSize(AllConstValue.FrameWidth, AllConstValue.FrameHeight);
		frame.setLocation(AllConstValue.FrameLocX, AllConstValue.FrameLocY);
		frame.setVisible(true);
		snakesWin = new SnakesWin();
		frame.add(snakesWin);
		snakesWin.requestFocus();
	}
	public static void main(String[] args) {
		new Client();
	}
}
