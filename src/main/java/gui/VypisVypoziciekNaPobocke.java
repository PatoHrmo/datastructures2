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

public class VypisVypoziciekNaPobocke extends JDialog {

	private JTextField nazovPobockytextField;
	private KniznicnySoftware kniznica;
	private JTextArea pobockytextArea;
	
	
	public VypisVypoziciekNaPobocke(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("V\u00FDpis v\u00FDpo\u017Ei\u010Diek na pobo\u010Dke");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajNzovPoboky = new JLabel("Zadaj n\u00E1zov pobo\u010Dky");
		lblZadajNzovPoboky.setBounds(10, 11, 193, 14);
		getContentPane().add(lblZadajNzovPoboky);
		
		nazovPobockytextField = new JTextField();
		nazovPobockytextField.setBounds(10, 36, 86, 20);
		getContentPane().add(nazovPobockytextField);
		nazovPobockytextField.setColumns(10);
		
		JButton btnHladaj = new JButton("H\u013Eadaj");
		btnHladaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nazovPobocky = nazovPobockytextField.getText();
				String infoOVypozickach = new String();
				for(String info : kniznica.getInfoOVypozickachNaPobocke(nazovPobocky)){
					infoOVypozickach+=info+System.lineSeparator();
				}
				pobockytextArea.setText(infoOVypozickach);
				if(infoOVypozickach.equals("")) {
					pobockytextArea.setText("na tejto poboèke nie sú výpožièky, alebo poboèka neexistuje");
				}
			}
		});
		btnHladaj.setBounds(7, 67, 89, 23);
		getContentPane().add(btnHladaj);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 103, 414, 148);
		getContentPane().add(scrollPane);
		
		pobockytextArea = new JTextArea();
		scrollPane.setViewportView(pobockytextArea);

	}

}
