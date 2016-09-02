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
		// ���
		this.setLayout(null);
		this.setBounds(520, 170, 300, 450);

		// ����
		this.setTitle("ģʽѡ��");

		// ��ť
		button_1 = new JButton("�ߵ����ս");
		button_1.setBounds(0, 0, 300, 150);
		button_1.setFont(new Font("����", Font.BOLD, 30));
		this.add(button_1);

		button_2 = new JButton("���ߴ���ս");
		button_2.setBounds(0, 150, 300, 150);
		button_2.setFont(new Font("����", Font.BOLD, 30));
		this.add(button_2);

		button_3 = new JButton("�Ե�����ģʽ");
		button_3.setBounds(0, 300, 300, 150);
		button_3.setFont(new Font("����", Font.BOLD, 30));
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���ùر�
		this.setResizable(false);// ���ô��ڴ�С�����϶�
		this.setVisible(true);
	}
}