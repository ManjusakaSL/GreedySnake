package com.serverUI;

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
import com.snakedotwar.Snake;

class snakeDotThread implements Runnable {
	/*
	 * 规定玩蛇的玩家的id为0，玩点的玩家的id从1开始依次递增
	 */
	int id;          	// 该线程的id，也代表与该线程通信的客户端的id
	Socket client;   	// 与该线程通信的客户端的套接字
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	DataInputStream is = null;
	DataOutputStream os = null;
	
	snakeDotThread(Socket tclient, int tid) {
		client = tclient; 
		id = tid;
	}
	public void run() {
		try {
			is = new DataInputStream(client.getInputStream());
			os = new DataOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			os.writeInt(SelectMode.selectMode);     // 告诉客户端游戏模式
			os.writeInt(id);						// 告诉客户端它的id 
			while(!Server.startFlag) {				// 必须等到startFlag为true，才能开始往下运行，传递数据
				Thread.sleep(10);
			}				
			os.writeInt(Server.cnt);  				// 告诉客户端当前参与的玩家数
			init();   								// 与客户端开始通信需要做的一些初始化，对应Client的init
			if(id == 0) {							// 显示客户端的界面，并且只显示一个
				snakeDotWinDisplay();                  
			}
			
			
			/*
			 * 游戏开始后与客户端的通信：接收所有玩家的当前位置信息，汇总这些信息，
			 * 并发送给所有玩家。需要注意的是，必须要在接收到所有信息之后，才能进行发送
			 * vis主要应用在此
			 */
			while(true) {   	
				Object obj = in.readObject();
				if(id == 0) {
					synchronized (Lock.serverSnake) {
						Server.snake = (Snake) obj;
					}
				}
				else {
					synchronized (Lock.serverMdot) {
						Server.dot = (Dot) obj;
					}
				}
				synchronized (Lock.serverVis) {
					Server.vis[id] = true;
				}
				for(int i = 0; i < Server.cnt; ) {
					Thread.sleep(10);
					if(Server.vis[i]) ++i;
				}
				synchronized (Lock.serverVis) {
					Server.vis[id] = false;
				}
				out.writeObject(Server.snake);
				out.writeObject(Server.dot);
				Thread.sleep(10);
			}
		}
		catch(Exception e) {
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
	void init() throws Exception {
		out.writeObject(Server.snake);          // 告诉各客户端所有玩家的初始位置信息
		out.writeObject(Server.dot);
					
		os.writeBoolean(Server.startFlag);      // 告诉客户端，游戏可以开始了
	}
	void snakeDotWinDisplay() {
		JFrame frame;
		frame = new subJFrame("清真蛇点大作战");
		frame.setSize(AllConstValue.FrameWidth, AllConstValue.FrameHeight);
		frame.setLocation(AllConstValue.FrameLocX, AllConstValue.FrameLocY);
		frame.setVisible(true);
		ShowSnakeDotWin snakeDotWin = new ShowSnakeDotWin();
		frame.add(snakeDotWin);
		snakeDotWin.requestFocus();
	}
}