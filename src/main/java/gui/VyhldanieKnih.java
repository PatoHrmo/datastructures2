package gui;


import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class VyhldanieKnih extends JDialog {
	private JTextField textFieldNazovKnihy;
	private JTextField textFieldNazovPobocka;
	private JTextArea textAreaInfo;
	private KniznicnySoftware kniznica;
	public VyhldanieKnih(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("Vyh\u013Ead\u00E1vanie kn\u00EDh pod\u013Ea n\u00E1zvu");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblZadajNzovKnihy = new JLabel("Zadaj n\u00E1zov knihy");
		lblZadajNzovKnihy.setBounds(10, 11, 168, 14);
		getContentPane().add(lblZadajNzovKnihy);
		
		textFieldNazovKnihy = new JTextField();
		textFieldNazovKnihy.setBounds(10, 36, 141, 20);
		getContentPane().add(textFieldNazovKnihy);
		textFieldNazovKnihy.setColumns(10);
		
		JLabel lblZadajNzovPoboky = new JLabel("Zadaj n\u00E1zov pobo\u010Dky");
		lblZadajNzovPoboky.setBounds(161, 11, 219, 14);
		getContentPane().add(lblZadajNzovPoboky);
		
		textFieldNazovPobocka = new JTextField();
		textFieldNazovPobocka.setBounds(161, 36, 141, 20);
		getContentPane().add(textFieldNazovPobocka);
		textFieldNazovPobocka.setColumns(10);
		
		JButton btnHladaj = new JButton("H\u013Eadaj");
		btnHladaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> infoOknihach = kniznica.najdiKnihyPodlaNazvu(
						textFieldNazovKnihy.getText(), textFieldNazovPobocka.getText());
				String udaje = new String();
				for(String s : infoOknihach) {
					udaje+=s+System.lineSeparator();
				}
				textAreaInfo.setText(udaje);
				if(udaje.equals("")) {
					textAreaInfo.setText("kniha s týmto názvom nebola nájdená,"
							+ " ani ïalšie v abecednom poradí");
				}
			}
		});
		btnHladaj.setBounds(10, 67, 89, 23);
		getContentPane().add(btnHladaj);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 109, 414, 142);
		getContentPane().add(scrollPane);
		
		textAreaInfo = new JTextArea();
		scrollPane.setViewportView(textAreaInfo);

	}
}
