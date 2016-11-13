package gui;

import javax.swing.JDialog;

import main.KniznicnySoftware;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RuseniePobocky extends JDialog {
	private KniznicnySoftware kniznica;
	private JTextField textFieldRusena;
	private JTextField textFieldNova;
	
	public RuseniePobocky(KniznicnySoftware ks) {
		setTitle("Ru\u0161enie pobo\u010Dky");
		kniznica = ks;
		setBounds(100, 100, 535, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("N\u00E1zov pobo\u010Dky ktor\u00E1 sa ru\u0161\u00ED");
		lblNewLabel.setBounds(12, 17, 163, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("N\u00E1zov pobo\u010Dky do ktorej sa presunie agenda");
		lblNewLabel_1.setBounds(185, 17, 291, 14);
		getContentPane().add(lblNewLabel_1);
		
		textFieldRusena = new JTextField();
		textFieldRusena.setBounds(10, 42, 86, 20);
		getContentPane().add(textFieldRusena);
		textFieldRusena.setColumns(10);
		
		textFieldNova = new JTextField();
		textFieldNova.setBounds(185, 42, 86, 20);
		getContentPane().add(textFieldNova);
		textFieldNova.setColumns(10);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setBounds(10, 153, 499, 14);
		getContentPane().add(lblInfo);
		
		JButton btnZrusAPresun = new JButton("Zru\u0161 pobo\u010Dku");
		btnZrusAPresun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rusena = textFieldRusena.getText();
				String nova = textFieldNova.getText();
				String odpoved;
				odpoved = kniznica.presunPobockuAVymazJu(rusena, nova);
				lblInfo.setText(odpoved);
			}
		});
		btnZrusAPresun.setBounds(10, 86, 138, 23);
		getContentPane().add(btnZrusAPresun);
	}

}
