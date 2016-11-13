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

public class VypisKnihNaPobockeKdeCitateliaMeskaju extends JDialog {

	private JTextField nazovPobockytextField;
	private KniznicnySoftware kniznica;
	private JTextArea pobockytextArea;
	
	
	public VypisKnihNaPobockeKdeCitateliaMeskaju(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("V\u00FDpis kn\u00EDh na pobo\u010Dke kde \u010Ditatelia me\u0161kaj\u00FA s vr\u00E1ten\u00EDm");
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
				String infoOknihach = new String();
				for(String info : kniznica.getKnihyKtoreMeskajuSvratenim(nazovPobocky)){
					infoOknihach+=info+System.lineSeparator();
				}
				pobockytextArea.setText(infoOknihach);
				if(infoOknihach.equals("")) {
					pobockytextArea.setText("na tejto poboèke nie sú omeškané výpožièky,"
							+ " alebo poboèka neexistuje");
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
