package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VyhladanieKnihyDialog extends JDialog {
	private JTextField menoPobockyTextField;
	private JTextField IDknihyTextField;
	private KniznicnySoftware kniznica;
	JTextArea InfoOkniheTxtArea;
	
	
	public VyhladanieKnihyDialog(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("Vyh\u013Ead\u00E1vanie knihy");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajMenoPoboky = new JLabel("Zadaj meno pobo\u010Dky");
		lblZadajMenoPoboky.setBounds(10, 11, 178, 14);
		getContentPane().add(lblZadajMenoPoboky);
		
		menoPobockyTextField = new JTextField();
		menoPobockyTextField.setBounds(10, 30, 86, 20);
		getContentPane().add(menoPobockyTextField);
		menoPobockyTextField.setColumns(10);
		
		JLabel lblZadajId = new JLabel("Zadaj identifika\u010Dn\u00FD k\u00F3d knihy");
		lblZadajId.setBounds(10, 61, 195, 14);
		getContentPane().add(lblZadajId);
		
		IDknihyTextField = new JTextField();
		IDknihyTextField.setBounds(10, 86, 86, 20);
		getContentPane().add(IDknihyTextField);
		IDknihyTextField.setColumns(10);
		
		JButton HladajKnihuBtn = new JButton("h\u013Eadaj knihu");
		HladajKnihuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoOkniheTxtArea.setText(kniznica.najdiKnihuPodlaID(
						IDknihyTextField.getText(), menoPobockyTextField.getText()));
			}
		});
		HladajKnihuBtn.setBounds(7, 117, 106, 23);
		getContentPane().add(HladajKnihuBtn);
		
		InfoOkniheTxtArea = new JTextArea();
		InfoOkniheTxtArea.setBounds(10, 151, 414, 67);
		getContentPane().add(InfoOkniheTxtArea);

	}
}
