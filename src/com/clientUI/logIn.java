package com.clientUI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
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
import javax.swing.JTextField;

import com.frame.Report;

public class logIn extends JFrame {
	/*
	 * 客户端的第一个界面
	 */
	public static String adr;			// 用于存储转发制定的IP地址
	public static boolean startClient = false;
	private static final long serialVersionUID = 1L;
	private JButton button_1 = null;
	private JButton button_2 = null;
	private JButton button_3 = null;
	private JButton button_4 = null;
	private JLabel label_1 = null;
	private JTextField text = null;

	public AudioClip ac;                
	private int musicFlag = 1;

	public logIn() {
		// 框框
		this.setLayout(null);
		this.setBounds(520, 170, 350, 250);
		this.setTitle("logIn");

		// lable
		label_1 = new JLabel("服务器IP地址");
		label_1.setFont(new Font("楷体", Font.BOLD, 14));
		label_1.setBounds(120, 40, 100, 30);
		label_1.setForeground(Color.orange);
		this.add(label_1);
		// 按钮
		button_1 = new JButton("登录");
		button_1.setBounds(80, 130, 80, 40);
		button_1.setFont(new Font("宋体", Font.BOLD, 15));
		this.add(button_1);

		button_2 = new JButton("退出");
		button_2.setBounds(180, 130, 80, 40);
		button_2.setFont(new Font("宋体", Font.BOLD, 15));
		this.add(button_2);

		button_3 = new JButton("M");
		button_3.setBounds(80, 180,40, 40);
		button_3.setFont(new Font("宋体", Font.BOLD, 10));
		this.add(button_3);

		button_4 = new JButton("?");
		button_4.setBounds(130, 180, 40, 40);
		button_4.setFont(new Font("宋体", Font.ITALIC,12));
		this.add(button_4);
		// 文本框
		text = new JTextField();
		text.setBounds(70, 80, 200, 40);
		text.setFont(new Font("宋体", Font.BOLD, 12));
		this.add(text);
		// 背景
		String path = System.getProperty("user.dir") + "\\src\\com\\resource\\LingEr.jpg";
		ImageIcon backGround = new ImageIcon(path);
		JLabel label = new JLabel(backGround);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		JPanel ImagePanel = (JPanel) this.getContentPane();
		ImagePanel.setOpaque(false);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

		// 音乐
		try {
			File file=new File(System.getProperty("user.dir") + "\\src\\com\\resource\\huiMengYouXian.wav");
			URL url=file.toURI().toURL();
			ac= Applet.newAudioClip(url);
			ac.loop();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adr = text.getText();
				startClient = true;
				dispose();
				// 等待其他玩家加入
				final Report r1 = new Report("                             等待其他玩家加入...");
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							while (true) {
								Thread.sleep(10);
								if (Client.connectWR) {
									r1.dispose();
									Report r2 = new Report("                          不存在ip，请重新启动客户端");
									Thread.sleep(1000);
									r2.dispose();
									ac.stop();
									break;
								}
								if (Client.startFlag) {
									r1.dispose();
									ac.stop();
									break;
								}
							}
						} catch (Exception e) {
						}
					}
				}).start();
			}
		});
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ac.stop();
				dispose();

			}
		});
		button_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				musicFlag++;
				if (musicFlag % 2 == 0) {
					ac.stop();
				} else {
					ac.play();
				}
			}
		});
		button_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ac.stop();
				new Help();
				dispose();
				
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭
		this.setResizable(false);// 设置窗口大小不可拖动
		this.setVisible(true);

	}
}
