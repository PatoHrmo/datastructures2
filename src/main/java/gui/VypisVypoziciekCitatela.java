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

public class VypisVypoziciekCitatela extends JDialog {

	private JTextField cisloPreukazuTxtDield;
	private KniznicnySoftware kniznica;
	JTextArea textAreaPozicaneKnihy;
	
	
	public VypisVypoziciekCitatela(KniznicnySoftware kniznica) {
		setTitle("V\u00FDpis v\u00FDpo\u017Ei\u010Diek \u010Ditate\u013Ea");
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
		
		JButton hladajKnihyCitatelaBtn = new JButton("vyp\u00ED\u0161");
		hladajKnihyCitatelaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = cisloPreukazuTxtDield.getText();
				String vypozicky = new String();
				menoCitatelaLabel.setText(kniznica.getMenoCitatela(id));
				for(String vypozicka : kniznica.getHistoriaVypoziciekCitatela(id)) {
					vypozicky+= vypozicka+System.lineSeparator();
				}
				textAreaPozicaneKnihy.setText(vypozicky);
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
