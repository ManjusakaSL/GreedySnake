package com.serverUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;


public class SelectMode extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton button_1 = null;
	private	JButton button_2 = null;
	private JButton button_3 = null;
	//public static boolean startServer = false;
	public static int selectMode = -1;

	public SelectMode() {
		// 框框
		this.setLayout(null);
		this.setBounds(520, 170, 300, 450);

		// 标题
		this.setTitle("模式选择");

		// 按钮
		button_1 = new JButton("蛇点大作战");
		button_1.setBounds(0, 0, 300, 150);
		button_1.setFont(new Font("宋体", Font.BOLD, 30));
		this.add(button_1);

		button_2 = new JButton("蛇蛇大作战");
		button_2.setBounds(0, 150, 300, 150);
		button_2.setFont(new Font("宋体", Font.BOLD, 30));
		this.add(button_2);

		button_3 = new JButton("霸道生化模式");
		button_3.setBounds(0, 300, 300, 150);
		button_3.setFont(new Font("宋体", Font.BOLD, 30));
		this.add(button_3);

		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//startServer = true;
				selectMode = 0;
				dispose();
				new CreateRoom_1();
			}
		});
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectMode = 1;
				dispose();
				new CreateRoom_1();
			}
		});
		button_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectMode = 2;
				dispose();
				new CreateRoom_1();
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭
		this.setResizable(false);// 设置窗口大小不可拖动
		this.setVisible(true);
	}
}