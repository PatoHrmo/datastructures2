package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PridaniePobocky extends JDialog {
	private JTextField textFieldNazovPobocky;
	private KniznicnySoftware kniznica;
	JLabel lblInfo;
	

	
	public PridaniePobocky(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("Pridanie novej pobo\u010Dky");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNzovPoboky = new JLabel("N\u00E1zov pobo\u010Dky");
		lblNzovPoboky.setBounds(10, 11, 150, 14);
		getContentPane().add(lblNzovPoboky);
		
		textFieldNazovPobocky = new JTextField();
		textFieldNazovPobocky.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldNazovPobocky);
		textFieldNazovPobocky.setColumns(10);
		
		JButton btnPridajPoboku = new JButton("Pridaj pobo\u010Dku");
		btnPridajPoboku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kniznica.pridajPobocku(textFieldNazovPobocky.getText());
				lblInfo.setText("Poboèka "+textFieldNazovPobocky.getText()+" bola pridaná");
				textFieldNazovPobocky.setText("");
			}
		});
		btnPridajPoboku.setBounds(10, 67, 134, 23);
		getContentPane().add(btnPridajPoboku);
		
		lblInfo = new JLabel("");
		lblInfo.setBounds(10, 101, 179, 14);
		getContentPane().add(lblInfo);

	}

}
