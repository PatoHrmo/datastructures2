package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

public class VyradenieCitatela extends JDialog {

	private JTextField textFieldID;
	private KniznicnySoftware kniznica;
	
	
	public VyradenieCitatela(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("Vyradenie \u010Ditate\u013Ea");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajNzovPoboky = new JLabel("Zadaj ID preukazu \u010Ditate\u013Ea");
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
				String IDcitatela = textFieldID.getText();
				lblInfo.setText(kniznica.vymazCitatela(IDcitatela));
			}
		});
		btnVymaz.setBounds(7, 67, 89, 23);
		getContentPane().add(btnVymaz);

	}

}
