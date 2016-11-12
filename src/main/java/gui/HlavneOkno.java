package gui;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.KniznicnySoftware;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HlavneOkno extends JFrame {
	private JPanel contentPane;
	private KniznicnySoftware kniznica;
	private PridanieCitatela pridanieCitatelaDialog;
	private PridanieNovejKnihyDialog pridanieNovejKnihyDialog;
	private PridaniePobocky pridaniePobocky;
	private VyhladanieCitatelaDialog vyhladanieCitatelaDialog;
	private VyhladanieKnihyDialog vyhladanieKnihyDialog;
	private VypisCitatelovDialog vypisCitatelovDialog;
	private VypisKnihNaPobockeDialog vypisKnihNaPobockeDialog;
	private VypisPobociekDialog vypisPobociekDialog;
	private VyhldanieKnih vyhladanieKnih;
	private ZapozicanieKnihy zapozicanieKnih;
	private VratenieKnihy vrateniaKnih;
	private JTextField textFieldDatum;
	private JLabel lblDatum;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HlavneOkno frame = new HlavneOkno();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HlavneOkno() {
		setTitle("Kni\u017Enica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblDatum = new JLabel("");
		JButton btnPridanieCitatela = new JButton("Pridanie \u010Ditate\u013Ea");
		btnPridanieCitatela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pridanieCitatelaDialog.setVisible(true);
			}
		});
		btnPridanieCitatela.setBounds(10, 52, 144, 23);
		contentPane.add(btnPridanieCitatela);
		
		JButton btnPridanieNovejKnihy = new JButton("Pridanie novej knihy");
		btnPridanieNovejKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pridanieNovejKnihyDialog.setVisible(true);
			}
		});
		btnPridanieNovejKnihy.setBounds(10, 88, 170, 23);
		contentPane.add(btnPridanieNovejKnihy);
		
		JButton btnPridaniePobocky = new JButton("Pridanie pobocky");
		btnPridaniePobocky.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pridaniePobocky.setVisible(true);
			}
		});
		btnPridaniePobocky.setBounds(10, 122, 144, 23);
		contentPane.add(btnPridaniePobocky);
		
		JButton btnVyhladanieCitatela = new JButton("Vyhladanie Citatela");
		btnVyhladanieCitatela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyhladanieCitatelaDialog.setVisible(true);
			}
		});
		btnVyhladanieCitatela.setBounds(413, 52, 144, 23);
		contentPane.add(btnVyhladanieCitatela);
		
		JButton btnVyhladanieKnihy = new JButton("Vyhladanie knihy");
		btnVyhladanieKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyhladanieKnihyDialog.setVisible(true);
			}
		});
		btnVyhladanieKnihy.setBounds(413, 88, 144, 23);
		contentPane.add(btnVyhladanieKnihy);
		
		JButton btnVypisCitatelov = new JButton("V\u00FDpis \u010Ditate\u013Eov");
		btnVypisCitatelov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisCitatelovDialog.setVisible(true);
			}
		});
		btnVypisCitatelov.setBounds(209, 52, 144, 23);
		contentPane.add(btnVypisCitatelov);
		
		JButton btnVypisKnih = new JButton("V\u00FDpis kn\u00EDh na pobo\u010Dke");
		btnVypisKnih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisKnihNaPobockeDialog.setVisible(true);
			}
		});
		btnVypisKnih.setBounds(209, 122, 170, 23);
		contentPane.add(btnVypisKnih);
		
		JButton btnVpisPoboiek = new JButton("V\u00FDpis pobo\u010Diek");
		btnVpisPoboiek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisPobociekDialog.setVisible(true);
			}
		});
		btnVpisPoboiek.setBounds(209, 86, 132, 23);
		contentPane.add(btnVpisPoboiek);
		
		JButton btnVyhladanieKnih = new JButton("Vyhladanie knih");
		btnVyhladanieKnih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyhladanieKnih.setVisible(true);
			}
		});
		btnVyhladanieKnih.setBounds(413, 122, 144, 23);
		contentPane.add(btnVyhladanieKnih);
		
		JButton btnZapoianieKnihy = new JButton("Zapo\u017Ei\u010Danie knihy");
		btnZapoianieKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zapozicanieKnih.setVisible(true);
			}
		});
		btnZapoianieKnihy.setBounds(10, 178, 146, 23);
		contentPane.add(btnZapoianieKnihy);
		lblDatum.setBounds(10, 15, 144, 14);
		contentPane.add(lblDatum);
		
		textFieldDatum = new JTextField();
		textFieldDatum.setBounds(172, 12, 86, 20);
		contentPane.add(textFieldDatum);
		textFieldDatum.setColumns(10);
		
		JButton btnNastavDtum = new JButton("Nastav d\u00E1tum");
		btnNastavDtum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String datumString = textFieldDatum.getText();
				kniznica.setAktualnyDatum(datumString);
				lblDatum.setText("Dnes je: "+datumString);
			}
		});
		btnNastavDtum.setBounds(271, 11, 137, 23);
		contentPane.add(btnNastavDtum);
		
		
		
		kniznica = new KniznicnySoftware();
		lblDatum.setText("Dnes je: "+kniznica.getDatum());
		
		JButton btnVrtenieKnihy = new JButton("Vr\u00E1tenie knihy");
		btnVrtenieKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vrateniaKnih.setVisible(true);
			}
		});
		btnVrtenieKnihy.setBounds(169, 178, 184, 23);
		contentPane.add(btnVrtenieKnihy);
		pridanieCitatelaDialog = new PridanieCitatela(kniznica);
		pridanieNovejKnihyDialog = new PridanieNovejKnihyDialog(kniznica);
		pridaniePobocky = new PridaniePobocky(kniznica);
		vyhladanieCitatelaDialog = new VyhladanieCitatelaDialog(kniznica);
		vyhladanieKnihyDialog = new VyhladanieKnihyDialog(kniznica);
		vypisCitatelovDialog = new VypisCitatelovDialog(kniznica);
		vypisKnihNaPobockeDialog = new VypisKnihNaPobockeDialog(kniznica);
		vypisPobociekDialog = new VypisPobociekDialog(kniznica);
		pridanieCitatelaDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		vyhladanieKnih = new VyhldanieKnih(kniznica);
		zapozicanieKnih = new ZapozicanieKnihy(kniznica);
		vrateniaKnih = new VratenieKnihy(kniznica);
	}
}
