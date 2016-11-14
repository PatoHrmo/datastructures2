package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.awt.event.ActionEvent;

public class Generovanie extends JDialog {
	private JTextField textFieldPocetCit;
	private JTextField textFieldPocetPob;
	private JTextField textFieldKnih;
	private KniznicnySoftware kniznica;
	public Generovanie(KniznicnySoftware ks) {
		this.kniznica = ks;
		setTitle("Generovanie \u00FAdajov");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblPoetUvateov = new JLabel("Po\u010Det \u010Ditate\u013Eov");
		lblPoetUvateov.setBounds(8, 11, 122, 14);
		getContentPane().add(lblPoetUvateov);
		
		textFieldPocetCit = new JTextField();
		textFieldPocetCit.setBounds(8, 30, 86, 20);
		getContentPane().add(textFieldPocetCit);
		textFieldPocetCit.setColumns(10);
		
		JLabel lblPoetPoboiek = new JLabel("Po\u010Det pobo\u010Diek");
		lblPoetPoboiek.setBounds(122, 11, 96, 14);
		getContentPane().add(lblPoetPoboiek);
		
		textFieldPocetPob = new JTextField();
		textFieldPocetPob.setBounds(122, 30, 86, 20);
		getContentPane().add(textFieldPocetPob);
		textFieldPocetPob.setColumns(10);
		
		textFieldKnih = new JTextField();
		textFieldKnih.setBounds(240, 30, 86, 20);
		getContentPane().add(textFieldKnih);
		textFieldKnih.setColumns(10);
		
		JLabel lblPoetKnhNa = new JLabel("Po\u010Det kn\u00EDh na pobo\u010Dku");
		lblPoetKnhNa.setBounds(240, 11, 135, 14);
		getContentPane().add(lblPoetKnhNa);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setBounds(16, 108, 272, 14);
		getContentPane().add(lblInfo);
		
		JButton btnGeneruj = new JButton("Generuj");
		btnGeneruj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kniznica.generuj(textFieldPocetCit.getText(),
						textFieldPocetPob.getText(), textFieldKnih.getText());
				lblInfo.setText("Hotovo");
			}
		});
		btnGeneruj.setBounds(8, 61, 89, 23);
		getContentPane().add(btnGeneruj);
		
		

	}
}
