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

		// ���
		this.setLayout(null);
		this.setBounds(400, 170, 650, 500);

		// ����
		this.setTitle("����");
		label_1 = new JLabel("��Ϸ����1��һ���ߣ�����㣬�ɲ�ͬ��ҿ���");
		label_1.setFont(new Font("����", Font.BOLD, 18));
		label_1.setBounds(0, 20, 650, 30);
		this.add(label_1);
		label_2 = new JLabel("2������ʱ����������߱䳤���Ѷ����󡣳Ե����е㣬��ʤ��");
		label_2.setFont(new Font("����", Font.BOLD, 18));
		label_2.setBounds(0, 60, 650, 30);
		this.add(label_2);
		label_3 = new JLabel("3�����ϵ������������ʤ��");
		label_3.setFont(new Font("����", Font.BOLD, 18));
		label_3.setBounds(0, 100, 650, 30);
		this.add(label_3);
		label_4 = new JLabel("4�������Ƿ���ǽ�����ж���");
		label_4.setFont(new Font("����", Font.BOLD, 18));
		label_4.setBounds(0, 140, 650, 30);
		this.add(label_4);
		label_5 = new JLabel("5������Դ����ߵ����塣");
		label_5.setFont(new Font("����", Font.BOLD, 18));
		label_5.setBounds(0, 180, 650, 30);
		this.add(label_5);
		
		button_1 = new JButton("����");
		button_1.setBounds(180, 240, 100, 50);
		this.add(button_1);
		
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new logIn();
				dispose();
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// ���ùر�
		this.setResizable(false);								// ���ô��ڴ�С�����϶�
		this.setVisible(true);
	}
}
