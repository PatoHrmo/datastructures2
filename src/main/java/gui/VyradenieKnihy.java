package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.KniznicnySoftware;

public class VyradenieKnihy extends JDialog {

	private JTextField textFieldID;
	private KniznicnySoftware kniznica;
	private JTextField textFieldNayzovPobocky;
	
	
	public VyradenieKnihy(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("Vyradenie knihy");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajNzovPoboky = new JLabel("Zadaj ID v\u00FDtla\u010Dku knihy");
		lblZadajNzovPoboky.setBounds(10, 11, 193, 14);
		getContentPane().add(lblZadajNzovPoboky);
		
		textFieldID = new JTextField();
		textFieldID.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldID);
		textFieldID.setColumns(10);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setBounds(10, 112, 358, 14);
		getContentPane().add(lblInfo);
		
		JButton btnVymaz = new JButton("Vyma\u017E");
		btnVymaz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IDknihy = textFieldID.getText();
				String nazovPobocky = textFieldNayzovPobocky.getText();
				lblInfo.setText(kniznica.vymazKnihu(nazovPobocky, IDknihy));
				textFieldID.setText("");
				textFieldNayzovPobocky.setText("");
				
			}
		});
		btnVymaz.setBounds(7, 67, 89, 23);
		getContentPane().add(btnVymaz);
		
		JLabel lblZadajNzovPoboky_1 = new JLabel("Zadaj n\u00E1zov pobo\u010Dky");
		lblZadajNzovPoboky_1.setBounds(187, 11, 165, 14);
		getContentPane().add(lblZadajNzovPoboky_1);
		
		textFieldNayzovPobocky = new JTextField();
		textFieldNayzovPobocky.setBounds(185, 31, 167, 20);
		getContentPane().add(textFieldNayzovPobocky);
		textFieldNayzovPobocky.setColumns(10);

	}
}
