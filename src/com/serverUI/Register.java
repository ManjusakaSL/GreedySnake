package com.serverUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton register, back;
	private JLabel showName, showPasswd, showVerify;
	private JTextField username, passwd, verifyPasswd;
	Statement statement;
	
	public Register() {
		this.setLayout(null);
		this.setBounds(400, 170, 300, 350);
		this.setTitle("注册界面");
		
		// 标签
		showName = new JLabel("用户名");
		showPasswd = new JLabel("密码");
		showVerify = new JLabel("确认密码");
		showName.setFont(new Font("楷体", Font.BOLD, 18));
		showPasswd.setFont(new Font("楷体", Font.BOLD, 18));
		showVerify.setFont(new Font("楷体", Font.BOLD, 18));
		showName.setBounds(20, 20, 300, 200);
		showPasswd.setBounds(20, 50, 300, 200);
		showVerify.setBounds(20, 80, 300, 200);
		this.add(showName);
		this.add(showPasswd);
		this.add(showVerify);
		
		// 单行文本
		username = new JTextField();
		passwd = new JPasswordField();
		verifyPasswd = new JPasswordField();
		username.setBounds(120, 110, 150, 30);
		passwd.setBounds(120, 140, 150, 30);
		verifyPasswd.setBounds(120, 170, 150, 30);
		this.add(username);
		this.add(passwd);
		this.add(verifyPasswd);
		
		// 按钮
		back = new JButton("返回");
		register = new JButton("注册");
		back.setBounds(40, 250, 100, 30);
		register.setBounds(160, 250, 100, 30);
		this.add(back);
		this.add(register);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 设置关闭
		this.setResizable(false);								// 设置窗口大小不可拖动
		this.setVisible(true);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序  
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/greedySnake","root","qwe123");
			statement = connect.createStatement();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
		});
		
		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText();
				String password = passwd.getText();
				String verify = verifyPasswd.getText();
				if(name.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入用户名", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(password.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入密码", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(verify.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入确认密码", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(!password.equals(verify)) {
					JOptionPane.showMessageDialog(null, "确认密码失败，请重新输入", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "注册成功", "Message", JOptionPane.ERROR_MESSAGE);
					try {
						System.out.println("insert into User values(\"" + name + "\", \"" + password + "\")");
						statement.executeUpdate("insert into User values(\"" + name + "\", \"" + password + "\")");
						
					}
					catch(Exception ee) {}
				}
			}
		});
	}
}
