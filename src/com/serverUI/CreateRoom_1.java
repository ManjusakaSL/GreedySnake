package com.serverUI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.frame.Report;

public class CreateRoom_1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel label_1 = null;
	private JLabel label_2 = null;
	private JButton button_1 = null;
	private JButton button_2 = null;
	public static boolean startGame = false;

	public CreateRoom_1() {

		// 框框
		this.setLayout(null);
		this.setBounds(400, 170, 500, 500);

		// 标题
		this.setTitle("等待");
		
		// 标签：点 ，蛇
		label_1 = new JLabel("等待其他玩家加入即可开始游戏!");
		switch(SelectMode.selectMode) {
		case 0:
			this.add(label_1);
			label_2 = new JLabel("清真蛇点大作战模式已启动");
			break;
		case 1:
			this.add(label_1);
			label_2 = new JLabel("清真蛇蛇大作战模式已启动");
			break;
		case 2:
			label_2 = new JLabel("霸道生化模式敬请期待");
			break;
		}
		label_1.setFont(new Font("楷体", Font.BOLD, 18));
		label_2.setFont(new Font("楷体", Font.BOLD, 18));
		label_1.setBounds(120, 40, 300, 200);
		label_2.setBounds(120, 40, 300, 150);
		this.add(label_2);

		// 开始按钮
		button_1 = new JButton("开始游戏");
		button_1.setBounds(180, 280, 100, 30);
		this.add(button_1);

		// 上一步按钮
		button_2 = new JButton("上一步");
		button_2.setBounds(180, 320, 100, 30);
		this.add(button_2);
		
		// 背景
		String path = System.getProperty("user.dir") + "\\src\\com\\resource\\LingEr.jpg";
		ImageIcon backGround=new ImageIcon(path);
		JLabel label=new JLabel(backGround);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		JPanel ImagePanel=(JPanel)this.getContentPane();
		ImagePanel.setOpaque(false);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		try { 			// 播放音乐
			File file = new File(System.getProperty("user.dir") + "\\src\\com\\resource\\linWeiBianDiao.wav");
			URL url = file.toURI().toURL();
			AudioClip ac = Applet.newAudioClip(url);
			ac.loop();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 设置关闭
		this.setResizable(false);								// 设置窗口大小不可拖动
		this.setVisible(true);
		
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				startGame = true;
			}
		});
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Server.clientGetIn) {
					dispose();
					new SelectMode();
				}
				else {
					Report rp = new Report("                      已有客户端连接，不能更换模式");
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(1000);
								rp.dispose();
							} catch (Exception e) {}
						}
					}).start();
				}
			}
		});
	}
}
