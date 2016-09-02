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
import com.snakeswar.*;

class snakeSnakeThread implements Runnable {
	
	int id;          		// ���̵߳�id��Ҳ��������߳�ͨ�ŵĿͻ��˵�id
	Socket client;   		// ����߳�ͨ�ŵĿͻ��˵��׽���
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	DataInputStream is = null;
	DataOutputStream os = null;

	public snakeSnakeThread(Socket tClient, int tid) {
		client = tClient;
		id = tid;
	}
	@Override
	public void run() {
		try {
			is = new DataInputStream(client.getInputStream());
			os = new DataOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			os.writeInt(SelectMode.selectMode);     // ���߿ͻ��˵�ǰ��Ϸģʽ
			os.writeInt(id);						// ���߿ͻ�������id 
			while(!Server.startFlag) {				// ����ȵ�startFlagΪtrue�����ܿ�ʼ�������У���������
				Thread.sleep(10);
			}				
			os.writeInt(Server.cnt);  				// ���߿ͻ��˵�ǰ����������
			init();   								// ��ͻ��˿�ʼͨ����Ҫ����һЩ��ʼ������ӦClient��init
			if(id == 0) {							// ��ʾ�ͻ��˵Ľ��棬����ֻ��ʾһ��
				snakesWinDisplay();
			}
			                     
			
			/*
			 * ��Ϸ��ʼ����ͻ��˵�ͨ�ţ�����������ҵĵ�ǰλ����Ϣ��������Щ��Ϣ��
			 * �����͸�������ҡ���Ҫע����ǣ�����Ҫ�ڽ��յ�������Ϣ֮�󣬲��ܽ��з���
			 * vis��ҪӦ���ڴ�
			 */
			while(true) {   	
				Object obj = in.readObject();
				if(id == 0) Server.snake0 = (Snakes) obj;
				else Server.snake1 = (Snakes) obj;
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
				out.writeObject(Server.snake0);
				out.writeObject(Server.snake1);
				Thread.sleep(10);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	void init() throws Exception {
		out.writeObject(Server.snake0);          // ���߸��ͻ���������ҵĳ�ʼλ����Ϣ
		out.writeObject(Server.snake1);
					
		os.writeBoolean(Server.startFlag);       // ���߿ͻ��ˣ���Ϸ���Կ�ʼ��
	}
	void snakesWinDisplay() {
		JFrame frame;
		frame = new subJFrame("�������ߴ���ս");
		frame.setSize(AllConstValue.FrameWidth, AllConstValue.FrameHeight);
		frame.setLocation(AllConstValue.FrameLocX, AllConstValue.FrameLocY);
		frame.setVisible(true);
		ShowSnakesWin snakesWin = new ShowSnakesWin();
		frame.add(snakesWin);
		snakesWin.requestFocus();
	}
}