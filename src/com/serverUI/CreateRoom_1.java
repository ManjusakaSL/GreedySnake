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

		// ���
		this.setLayout(null);
		this.setBounds(400, 170, 500, 500);

		// ����
		this.setTitle("�ȴ�");
		
		// ��ǩ���� ����
		label_1 = new JLabel("�ȴ�������Ҽ��뼴�ɿ�ʼ��Ϸ!");
		switch(SelectMode.selectMode) {
		case 0:
			this.add(label_1);
			label_2 = new JLabel("�����ߵ����սģʽ������");
			break;
		case 1:
			this.add(label_1);
			label_2 = new JLabel("�������ߴ���սģʽ������");
			break;
		case 2:
			label_2 = new JLabel("�Ե�����ģʽ�����ڴ�");
			break;
		}
		label_1.setFont(new Font("����", Font.BOLD, 18));
		label_2.setFont(new Font("����", Font.BOLD, 18));
		label_1.setBounds(120, 40, 300, 200);
		label_2.setBounds(120, 40, 300, 150);
		this.add(label_2);

		// ��ʼ��ť
		button_1 = new JButton("��ʼ��Ϸ");
		button_1.setBounds(180, 280, 100, 30);
		this.add(button_1);

		// ��һ����ť
		button_2 = new JButton("��һ��");
		button_2.setBounds(180, 320, 100, 30);
		this.add(button_2);
		
		// ����
		String path = System.getProperty("user.dir") + "\\src\\com\\resource\\LingEr.jpg";
		ImageIcon backGround=new ImageIcon(path);
		JLabel label=new JLabel(backGround);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		JPanel ImagePanel=(JPanel)this.getContentPane();
		ImagePanel.setOpaque(false);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		try { 			// ��������
			File file = new File(System.getProperty("user.dir") + "\\src\\com\\resource\\linWeiBianDiao.wav");
			URL url = file.toURI().toURL();
			AudioClip ac = Applet.newAudioClip(url);
			ac.loop();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// ���ùر�
		this.setResizable(false);								// ���ô��ڴ�С�����϶�
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
					Report rp = new Report("                      ���пͻ������ӣ����ܸ���ģʽ");
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
