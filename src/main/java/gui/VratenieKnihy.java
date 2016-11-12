package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class VratenieKnihy extends JDialog {
	private JTextField textFieldIDknihy;
	private JTextField textFieldPobocka;
	private JTextField textFieldIDCitatela;
	private KniznicnySoftware kniznica;
	public VratenieKnihy(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("Vr\u00E1tenie knihy");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblIdKnihyKtor = new JLabel("ID knihy ktor\u00FA treba vr\u00E1ti\u0165");
		lblIdKnihyKtor.setBounds(10, 11, 182, 14);
		getContentPane().add(lblIdKnihyKtor);
		
		textFieldIDknihy = new JTextField();
		textFieldIDknihy.setBounds(10, 36, 86, 20);
		getContentPane().add(textFieldIDknihy);
		textFieldIDknihy.setColumns(10);
		
		JLabel lblPobokaNaktorejJe = new JLabel("pobo\u010Dka naktorej je kniha vracan\u00E1");
		lblPobokaNaktorejJe.setBounds(160, 11, 264, 14);
		getContentPane().add(lblPobokaNaktorejJe);
		
		textFieldPobocka = new JTextField();
		textFieldPobocka.setBounds(160, 36, 86, 20);
		getContentPane().add(textFieldPobocka);
		textFieldPobocka.setColumns(10);
		
		JLabel lblsloitateskhoPreukazu = new JLabel("\u010C\u00EDslo \u010Ditate\u013Esk\u00E9ho preukazu");
		lblsloitateskhoPreukazu.setBounds(10, 67, 168, 14);
		getContentPane().add(lblsloitateskhoPreukazu);
		
		textFieldIDCitatela = new JTextField();
		textFieldIDCitatela.setBounds(10, 92, 86, 20);
		getContentPane().add(textFieldIDCitatela);
		textFieldIDCitatela.setColumns(10);
		
		JTextArea textAreaInfo = new JTextArea();
		textAreaInfo.setBounds(10, 147, 414, 78);
		getContentPane().add(textAreaInfo);
		
		JButton btnVrat = new JButton("Vr\u00E1\u0165 knihu");
		btnVrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String info = kniznica.vratKnihu(textFieldIDCitatela.getText(),
						textFieldIDknihy.getText(), textFieldPobocka.getText());
				textAreaInfo.setText(info);
			}
		});
		btnVrat.setBounds(157, 92, 89, 23);
		getContentPane().add(btnVrat);
		

	}
}
