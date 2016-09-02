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
	 * �涨���ߵ���ҵ�idΪ0��������ҵ�id��1��ʼ���ε���
	 */
	int id;          	// ���̵߳�id��Ҳ��������߳�ͨ�ŵĿͻ��˵�id
	Socket client;   	// ����߳�ͨ�ŵĿͻ��˵��׽���
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
			os.writeInt(SelectMode.selectMode);     // ���߿ͻ�����Ϸģʽ
			os.writeInt(id);						// ���߿ͻ�������id 
			while(!Server.startFlag) {				// ����ȵ�startFlagΪtrue�����ܿ�ʼ�������У���������
				Thread.sleep(10);
			}				
			os.writeInt(Server.cnt);  				// ���߿ͻ��˵�ǰ����������
			init();   								// ��ͻ��˿�ʼͨ����Ҫ����һЩ��ʼ������ӦClient��init
			if(id == 0) {							// ��ʾ�ͻ��˵Ľ��棬����ֻ��ʾһ��
				snakeDotWinDisplay();                  
			}
			
			
			/*
			 * ��Ϸ��ʼ����ͻ��˵�ͨ�ţ�����������ҵĵ�ǰλ����Ϣ��������Щ��Ϣ��
			 * �����͸�������ҡ���Ҫע����ǣ�����Ҫ�ڽ��յ�������Ϣ֮�󣬲��ܽ��з���
			 * vis��ҪӦ���ڴ�
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
		out.writeObject(Server.snake);          // ���߸��ͻ���������ҵĳ�ʼλ����Ϣ
		out.writeObject(Server.dot);
					
		os.writeBoolean(Server.startFlag);      // ���߿ͻ��ˣ���Ϸ���Կ�ʼ��
	}
	void snakeDotWinDisplay() {
		JFrame frame;
		frame = new subJFrame("�����ߵ����ս");
		frame.setSize(AllConstValue.FrameWidth, AllConstValue.FrameHeight);
		frame.setLocation(AllConstValue.FrameLocX, AllConstValue.FrameLocY);
		frame.setVisible(true);
		ShowSnakeDotWin snakeDotWin = new ShowSnakeDotWin();
		frame.add(snakeDotWin);
		snakeDotWin.requestFocus();
	}
}