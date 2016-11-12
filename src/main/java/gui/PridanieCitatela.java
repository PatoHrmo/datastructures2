package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PridanieCitatela extends JDialog {
	private JTextField textFieldMeno;
	private JTextField textFieldPriezvisko;
	private KniznicnySoftware kniznica;
	

	
	public PridanieCitatela(KniznicnySoftware k) {
		kniznica = k;
		setTitle("Pridanie nov\u00E9ho \u010Ditate\u013Ea");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajMenoNovho = new JLabel("Zadaj meno nov\u00E9ho \u010Ditate\u013Ea");
		lblZadajMenoNovho.setBounds(10, 11, 204, 14);
		getContentPane().add(lblZadajMenoNovho);
		
		textFieldMeno = new JTextField();
		textFieldMeno.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldMeno);
		textFieldMeno.setColumns(10);
		
		JLabel lblZadajPriezviskoNovho = new JLabel("Zadaj priezvisko nov\u00E9ho \u010Ditate\u013Ea");
		lblZadajPriezviskoNovho.setBounds(10, 67, 245, 14);
		getContentPane().add(lblZadajPriezviskoNovho);
		
		textFieldPriezvisko = new JTextField();
		textFieldPriezvisko.setBounds(10, 92, 86, 20);
		getContentPane().add(textFieldPriezvisko);
		textFieldPriezvisko.setColumns(10);
		
		JButton btnPridajCitatela = new JButton("Pridaj \u010Ditate\u013Ea");
		btnPridajCitatela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kniznica.pridajCitatela(textFieldMeno.getText(), textFieldPriezvisko.getText());
				textFieldMeno.setText("");
				textFieldPriezvisko.setText("");
			}
		});
		btnPridajCitatela.setBounds(10, 138, 118, 23);
		getContentPane().add(btnPridajCitatela);

	}

}
