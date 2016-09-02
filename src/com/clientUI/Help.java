package com.clientUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Help extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel label_1 = null;
	private JLabel label_2 = null;
	private JLabel label_3 = null;
	private JLabel label_4 = null;
	private JLabel label_5 = null;
	private JButton button_1 = null;
	
	public Help() {

		// 框框
		this.setLayout(null);
		this.setBounds(400, 170, 650, 500);

		// 标题
		this.setTitle("规则");
		label_1 = new JLabel("游戏规则：1、一条蛇，多个点，由不同玩家控制");
		label_1.setFont(new Font("楷体", Font.BOLD, 18));
		label_1.setBounds(0, 20, 650, 30);
		this.add(label_1);
		label_2 = new JLabel("2、随着时间的增长，蛇变长，难度增大。吃掉所有点，蛇胜。");
		label_2.setFont(new Font("楷体", Font.BOLD, 18));
		label_2.setBounds(0, 60, 650, 30);
		this.add(label_2);
		label_3 = new JLabel("3、点拖到蛇死亡，点获胜。");
		label_3.setFont(new Font("楷体", Font.BOLD, 18));
		label_3.setBounds(0, 100, 650, 30);
		this.add(label_3);
		label_4 = new JLabel("4、四周是否有墙，待判定。");
		label_4.setFont(new Font("楷体", Font.BOLD, 18));
		label_4.setBounds(0, 140, 650, 30);
		this.add(label_4);
		label_5 = new JLabel("5、点可以穿过蛇的身体。");
		label_5.setFont(new Font("楷体", Font.BOLD, 18));
		label_5.setBounds(0, 180, 650, 30);
		this.add(label_5);
		
		button_1 = new JButton("返回");
		button_1.setBounds(180, 240, 100, 50);
		this.add(button_1);
		
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new logIn();
				dispose();
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 设置关闭
		this.setResizable(false);								// 设置窗口大小不可拖动
		this.setVisible(true);
	}
}
