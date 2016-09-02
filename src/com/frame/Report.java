package com.frame;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class Report {
	JDialog jDialog = null;
	public Report(String info) {
		jDialog = new JDialog();
		jDialog.add(new JLabel(info));
		
		jDialog.setSize(300, 100);
		jDialog.setLocationRelativeTo(null);
		
		jDialog.setVisible(true);
	}
	public void dispose() {
		jDialog.dispose();
	}
}
