package gui;

import javax.swing.JDialog;

import main.KniznicnySoftware;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ZapozicanieKnihy extends JDialog {
	private KniznicnySoftware kniznica;
	private JTextField textFieldIDknihy;
	private JTextField textFieldIDCitatela;
	private JTextField textFieldNazovPobocky;
	private JLabel labelInfo;
	
	public ZapozicanieKnihy(KniznicnySoftware kniznica) {
		setTitle("Zapo\u017Ei\u010Danie knihy");
		this.kniznica = kniznica;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID knihy");
		lblNewLabel.setBounds(10, 10, 108, 14);
		getContentPane().add(lblNewLabel);
		
		textFieldIDknihy = new JTextField();
		textFieldIDknihy.setBounds(10, 35, 86, 20);
		getContentPane().add(textFieldIDknihy);
		textFieldIDknihy.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u010D\u00EDslo preukazu \u010Ditate\u013Ea");
		lblNewLabel_1.setBounds(128, 10, 133, 14);
		getContentPane().add(lblNewLabel_1);
		
		textFieldIDCitatela = new JTextField();
		textFieldIDCitatela.setBounds(128, 35, 86, 20);
		getContentPane().add(textFieldIDCitatela);
		textFieldIDCitatela.setColumns(10);
		
		JLabel lblNzovPoboky = new JLabel("N\u00E1zov pobo\u010Dky");
		lblNzovPoboky.setBounds(10, 66, 108, 14);
		getContentPane().add(lblNzovPoboky);
		
		textFieldNazovPobocky = new JTextField();
		textFieldNazovPobocky.setBounds(10, 91, 86, 20);
		getContentPane().add(textFieldNazovPobocky);
		textFieldNazovPobocky.setColumns(10);
		
		JButton btnPozicaj = new JButton("Po\u017Ei\u010Daj");
		btnPozicaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String info = kniznica.zapozicajKnihu(textFieldNazovPobocky.getText()
						, textFieldIDknihy.getText(), textFieldIDCitatela.getText());
				labelInfo.setText(info);
			}
		});
		btnPozicaj.setBounds(128, 90, 89, 23);
		getContentPane().add(btnPozicaj);
		
		labelInfo = new JLabel("");
		labelInfo.setBounds(10, 146, 414, 14);
		getContentPane().add(labelInfo);

	}

}
