package com.frame;

import javax.swing.JFrame;

public class subJFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public subJFrame() {}
	public subJFrame(String title) {
		super(title);
	}
	protected void frameInit() {
		super.frameInit();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
