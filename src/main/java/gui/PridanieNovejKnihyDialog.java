package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.KniznicnySoftware;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PridanieNovejKnihyDialog extends JDialog {
	private JTextField textFieldNazovKnihy;
	private JTextField textFieldAutor;
	private JTextField textFieldISBN;
	private JTextField textFieldEAN;
	private JTextField textFieldZaner;
	private JTextField textFieldPoplatok;
	private JTextField textFieldPobocka;
	private JTextField textFieldDoba;
	private JLabel lblInfo;
	private KniznicnySoftware kniznica;
	

	public PridanieNovejKnihyDialog(KniznicnySoftware kniznica) {
		setTitle("Pridanie novej knihy");
		this.kniznica = kniznica;
		setBounds(100, 100, 450, 338);
		getContentPane().setLayout(null);
		
		JLabel lblNzovKnihy = new JLabel("N\u00E1zov knihy:");
		lblNzovKnihy.setBounds(10, 11, 78, 14);
		getContentPane().add(lblNzovKnihy);
		
		textFieldNazovKnihy = new JTextField();
		textFieldNazovKnihy.setBounds(10, 31, 139, 20);
		getContentPane().add(textFieldNazovKnihy);
		textFieldNazovKnihy.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 62, 46, 14);
		getContentPane().add(lblAutor);
		
		textFieldAutor = new JTextField();
		textFieldAutor.setBounds(10, 87, 139, 20);
		getContentPane().add(textFieldAutor);
		textFieldAutor.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN :");
		lblIsbn.setBounds(10, 126, 46, 14);
		getContentPane().add(lblIsbn);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setBounds(10, 151, 139, 20);
		getContentPane().add(textFieldISBN);
		textFieldISBN.setColumns(10);
		
		JLabel lblEan = new JLabel("EAN:");
		lblEan.setBounds(10, 182, 46, 14);
		getContentPane().add(lblEan);
		
		textFieldEAN = new JTextField();
		textFieldEAN.setBounds(10, 207, 139, 20);
		getContentPane().add(textFieldEAN);
		textFieldEAN.setColumns(10);
		
		JLabel lblner = new JLabel("\u017D\u00E1ner:");
		lblner.setBounds(180, 11, 46, 14);
		getContentPane().add(lblner);
		
		textFieldZaner = new JTextField();
		textFieldZaner.setBounds(180, 31, 86, 20);
		getContentPane().add(textFieldZaner);
		textFieldZaner.setColumns(10);
		
		JLabel lblPoplatokZaDe = new JLabel("Poplatok za de\u0148 ome\u0161kania:");
		lblPoplatokZaDe.setBounds(180, 62, 217, 14);
		getContentPane().add(lblPoplatokZaDe);
		
		textFieldPoplatok = new JTextField();
		textFieldPoplatok.setBounds(180, 87, 86, 20);
		getContentPane().add(textFieldPoplatok);
		textFieldPoplatok.setColumns(10);
		
		JLabel lblNzovPoboky = new JLabel("N\u00E1zov pobo\u010Dky:");
		lblNzovPoboky.setBounds(180, 126, 151, 14);
		getContentPane().add(lblNzovPoboky);
		
		textFieldPobocka = new JTextField();
		textFieldPobocka.setBounds(180, 151, 86, 20);
		getContentPane().add(textFieldPobocka);
		textFieldPobocka.setColumns(10);
		
		JLabel lblVpoinDoba = new JLabel("V\u00FDpo\u017Ei\u010Dn\u00E1 doba:");
		lblVpoinDoba.setBounds(180, 182, 129, 14);
		getContentPane().add(lblVpoinDoba);
		
		textFieldDoba = new JTextField();
		textFieldDoba.setBounds(180, 207, 86, 20);
		getContentPane().add(textFieldDoba);
		textFieldDoba.setColumns(10);
		
		JButton btnPridajKnihu = new JButton("Pridaj Knihu");
		btnPridajKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String vysledok = kniznica.pridajKnihu(textFieldPobocka.getText(), textFieldNazovKnihy.getText(),
						textFieldAutor.getText(), textFieldISBN.getText(), textFieldEAN.getText(),
						textFieldZaner.getText(), textFieldPoplatok.getText(), textFieldDoba.getText());
				lblInfo.setText(vysledok);
				
			}
		});
		btnPridajKnihu.setBounds(10, 238, 104, 23);
		getContentPane().add(btnPridajKnihu);
		
		lblInfo = new JLabel("");
		lblInfo.setBounds(124, 238, 300, 14);
		getContentPane().add(lblInfo);

	}
}
