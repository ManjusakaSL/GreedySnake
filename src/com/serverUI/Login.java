package com.serverUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton login, register;
	private JLabel username_jl, passwd_jl;
	private JTextField username_jf, passwd_jf;
	ResultSet rel;
	
	public Login() {
		this.setLayout(null);
		this.setBounds(400, 170, 300, 350);
		this.setTitle("��½����");
		
		// ��ǩ
		username_jl = new JLabel("�û���");
		passwd_jl = new JLabel("����");
		username_jl.setFont(new Font("����", Font.BOLD, 18));
		passwd_jl.setFont(new Font("����", Font.BOLD, 18));
		username_jl.setBounds(20, 20, 300, 200);
		passwd_jl.setBounds(20, 50, 300, 200);
		this.add(username_jl);
		this.add(passwd_jl);
		
		// �����ı�
		username_jf = new JTextField();
		passwd_jf = new JPasswordField();
		username_jf.setBounds(120, 110, 150, 30);
		passwd_jf.setBounds(120, 140, 150, 30);
		this.add(username_jf);
		this.add(passwd_jf);
		
		// ��ť
		login = new JButton("��½");
		register = new JButton("ע��");
		login.setBounds(40, 250, 100, 30);
		register.setBounds(160, 250, 100, 30);
		this.add(login);
		this.add(register);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// ���ùر�
		this.setResizable(false);								// ���ô��ڴ�С�����϶�
		this.setVisible(true);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");     //����MYSQL JDBC��������  
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/greedySnake","root","qwe123");
			Statement statement = connect.createStatement();
		    rel = statement.executeQuery("select * from User");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = username_jf.getText();
				String passwd = passwd_jf.getText();
				
				try {
					boolean isRight = false;
					while(rel.next()) {
				        if(username.equals(rel.getString("name")) && passwd.equals(rel.getString("password"))) {
				        	dispose();
				        	new SelectMode();
				        	isRight = true;
				        	break;
				        }
				    }
					if(!isRight) {
						JOptionPane.showMessageDialog(null, "�û����������������������", "Message", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		
		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Register();
			}
		});
	}
}
