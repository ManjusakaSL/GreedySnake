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
	
	public static boolean startFlag = false;				// 游戏开始的标志   
	public static int id, cnt;								// 该客户端的id，总的玩家数目
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
			selectMode = is.readInt();  				// 客户端知道当前玩的是哪种模式
			id = is.readInt();                   	    // 客户端得到自己的id
			cnt = is.readInt();                         // 客户端得到所有参与游戏的玩家数目
			
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
		initSnakeDot();                   		// 游戏开始前的初始化信息，和Server的init对应
		snakeDotDisplay();		  				// 游戏开始后，各玩家玩游戏的窗口
		
		/*
		 * 游戏开始后与服务器的通信：每个玩家都向服务器报告自己的位置信息，
		 * 并从服务器接收其它所有玩家的信息
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
		 * 游戏开始后与服务器的通信：每个玩家都向服务器报告自己的位置信息，
		 * 并从服务器接收其它所有玩家的信息
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
		Object obj = in.readObject();               // 客户端从服务器得到所有玩家的初始位置信息
		snake = (Snake) obj;
		obj = in.readObject();
		dot = (Dot) obj;
		startFlag = is.readBoolean();               // game begin 
	}
	void initSnakeSnake() throws Exception {
		Object obj = in.readObject(); 				// 客户端从服务器得到所有玩家的初始位置信息
		snake0 = (Snakes) obj;
		obj = in.readObject();
		snake1 = (Snakes) obj;
		startFlag = is.readBoolean();               // game begin 
	}
	void snakeDotDisplay() {
		JFrame frame;
		SnakeWin snakeWin;
		DotWin dotWin;
		frame = new subJFrame("清真蛇点大作战");
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
		frame = new subJFrame("清真蛇蛇大作战");
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
