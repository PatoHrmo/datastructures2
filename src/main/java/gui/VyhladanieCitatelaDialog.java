package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VyhladanieCitatelaDialog extends JDialog {
	private JTextField cisloPreukazuTxtDield;
	private KniznicnySoftware kniznica;
	JTextArea textAreaPozicaneKnihy;
	
	
	public VyhladanieCitatelaDialog(KniznicnySoftware kniznica) {
		setTitle("Vyhladanie \u010Ditate\u013Ea");
		this.kniznica = kniznica;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajsloPreukazu = new JLabel("Zadaj \u010D\u00EDslo preukazu \u010Ditate\u013Ea");
		lblZadajsloPreukazu.setBounds(10, 11, 207, 14);
		getContentPane().add(lblZadajsloPreukazu);
		
		cisloPreukazuTxtDield = new JTextField();
		cisloPreukazuTxtDield.setBounds(11, 27, 86, 20);
		getContentPane().add(cisloPreukazuTxtDield);
		cisloPreukazuTxtDield.setColumns(10);
		
		JLabel menoCitatelaLabel = new JLabel("");
		menoCitatelaLabel.setBounds(10, 105, 334, 14);
		getContentPane().add(menoCitatelaLabel);
		
		JButton hladajKnihyCitatelaBtn = new JButton("hladaj");
		hladajKnihyCitatelaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = cisloPreukazuTxtDield.getText();
				String knihyCitatelov = new String();
				menoCitatelaLabel.setText(kniznica.getMenoCitatela(id));
				for(String kniha : kniznica.getinfoOKnihachCitatela(id)) {
					knihyCitatelov+= kniha+System.lineSeparator();
				}
				textAreaPozicaneKnihy.setText(knihyCitatelov);
				if(knihyCitatelov.isEmpty()) {
					textAreaPozicaneKnihy.setText("nie sú požièané žiadne knihy");
				}
			}
		});
		hladajKnihyCitatelaBtn.setBounds(10, 57, 89, 23);
		getContentPane().add(hladajKnihyCitatelaBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 130, 414, 121);
		getContentPane().add(scrollPane);
		
		textAreaPozicaneKnihy = new JTextArea();
		scrollPane.setViewportView(textAreaPozicaneKnihy);

	}

}
