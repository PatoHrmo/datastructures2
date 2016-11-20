package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.KniznicnySoftware;

public class VypisOneskorenychVrateniPreCitatela extends JDialog {

	private JTextField cisloPreukazuTxtDield;
	private KniznicnySoftware kniznica;
	JTextArea textAreaPozicaneKnihy;
	private JTextField textFieldod;
	private JTextField textFielddo;
	
	
	public VypisOneskorenychVrateniPreCitatela(KniznicnySoftware kniznica) {
		setTitle("V\u00FDpis onekoren\u00FDch vr\u00E1ten\u00FDch kn\u00EDh pre \u010Ditate\u013Ea");
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
				String oneskoreneVypozickyCitatela = new String();
				menoCitatelaLabel.setText(kniznica.getMenoCitatela(id));
				for(String vypozicka : kniznica.getinfoOOmesaknychVypozickachCitatela(
						id, textFieldod.getText(), textFielddo.getText())) {
					oneskoreneVypozickyCitatela+= vypozicka+System.lineSeparator()+System.lineSeparator();
				}
				textAreaPozicaneKnihy.setText(oneskoreneVypozickyCitatela);
			}
		});
		hladajKnihyCitatelaBtn.setBounds(10, 57, 89, 23);
		getContentPane().add(hladajKnihyCitatelaBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 130, 414, 121);
		getContentPane().add(scrollPane);
		
		textAreaPozicaneKnihy = new JTextArea();
		scrollPane.setViewportView(textAreaPozicaneKnihy);
		
		JLabel lblOd = new JLabel("Od:");
		lblOd.setBounds(192, 11, 46, 14);
		getContentPane().add(lblOd);
		
		JLabel lblDo = new JLabel("Do:");
		lblDo.setBounds(284, 11, 46, 14);
		getContentPane().add(lblDo);
		
		textFieldod = new JTextField();
		textFieldod.setBounds(174, 27, 86, 20);
		getContentPane().add(textFieldod);
		textFieldod.setColumns(10);
		
		textFielddo = new JTextField();
		textFielddo.setBounds(284, 27, 86, 20);
		getContentPane().add(textFielddo);
		textFielddo.setColumns(10);

	}

}
