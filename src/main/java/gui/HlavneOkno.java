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
	private UkladanieDoSuboru ukladanieDoSuboru;
	private Generovanie generovanie;
	private RuseniePobocky ruseniePobocky;
	private VyradenieCitatela vyradenieCitatela;
	private VypisVypoziciekCitatela vypisVypoziciekCitatela;
	private VypisOneskorenychVrateniPreCitatela vypisOneskorenychVrateniPreCitatela;
	private VypisVypoziciekNaPobocke vypisVypoziciekNaPobocke;
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
	private VypisKnihNaPobockeKdeCitateliaMeskaju vypisKnihNaPobockeKdeCitateliaMeskaju; 
	private VyradenieKnihy vyradenieKnihy;
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
		setBounds(100, 100, 604, 423);
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
		btnPridanieCitatela.setBounds(10, 52, 170, 23);
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
		btnPridaniePobocky.setBounds(10, 123, 170, 23);
		contentPane.add(btnPridaniePobocky);
		
		JButton btnVyhladanieCitatela = new JButton("Vyh\u013Eadanie \u010Ditatela");
		btnVyhladanieCitatela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyhladanieCitatelaDialog.setVisible(true);
			}
		});
		btnVyhladanieCitatela.setBounds(318, 260, 144, 23);
		contentPane.add(btnVyhladanieCitatela);
		
		JButton btnVyhladanieKnihy = new JButton("Vyh\u013Eadanie knihy");
		btnVyhladanieKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyhladanieKnihyDialog.setVisible(true);
			}
		});
		btnVyhladanieKnihy.setBounds(10, 260, 144, 23);
		contentPane.add(btnVyhladanieKnihy);
		
		JButton btnVypisCitatelov = new JButton("V\u00FDpis \u010Ditate\u013Eov");
		btnVypisCitatelov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisCitatelovDialog.setVisible(true);
			}
		});
		btnVypisCitatelov.setBounds(387, 52, 170, 23);
		contentPane.add(btnVypisCitatelov);
		
		JButton btnVypisKnih = new JButton("V\u00FDpis kn\u00EDh na pobo\u010Dke");
		btnVypisKnih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisKnihNaPobockeDialog.setVisible(true);
			}
		});
		btnVypisKnih.setBounds(387, 88, 170, 23);
		contentPane.add(btnVypisKnih);
		
		JButton btnVpisPoboiek = new JButton("V\u00FDpis pobo\u010Diek");
		btnVpisPoboiek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisPobociekDialog.setVisible(true);
			}
		});
		btnVpisPoboiek.setBounds(387, 122, 170, 23);
		contentPane.add(btnVpisPoboiek);
		
		JButton btnVyhladanieKnih = new JButton("Vyh\u013Eadanie kn\u00EDh");
		btnVyhladanieKnih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyhladanieKnih.setVisible(true);
			}
		});
		btnVyhladanieKnih.setBounds(164, 260, 144, 23);
		contentPane.add(btnVyhladanieKnih);
		
		JButton btnZapoianieKnihy = new JButton("Zapo\u017Ei\u010Danie knihy");
		btnZapoianieKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zapozicanieKnih.setVisible(true);
			}
		});
		btnZapoianieKnihy.setBounds(10, 315, 146, 23);
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
		btnVrtenieKnihy.setBounds(164, 315, 184, 23);
		contentPane.add(btnVrtenieKnihy);
		
		JButton btnVpisVpoiiekNa = new JButton("V\u00FDpis v\u00FDpo\u017Ei\u010Diek na pobo\u010Dke");
		btnVpisVpoiiekNa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisVypoziciekNaPobocke.setVisible(true);
			}
		});
		btnVpisVpoiiekNa.setBounds(10, 220, 237, 23);
		contentPane.add(btnVpisVpoiiekNa);
		
		JButton btnVpisOmekanchKnh = new JButton("V\u00FDpis kn\u00EDh kde \u010Ditatelia me\u0161kaj\u00FA s vr\u00E1ten\u00EDm");
		btnVpisOmekanchKnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisKnihNaPobockeKdeCitateliaMeskaju.setVisible(true);
			}
		});
		btnVpisOmekanchKnh.setBounds(271, 220, 306, 23);
		contentPane.add(btnVpisOmekanchKnh);
		
		JButton btnVyradenieKnihy = new JButton("Vyradenie knihy");
		btnVyradenieKnihy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyradenieKnihy.setVisible(true);
			}
		});
		btnVyradenieKnihy.setBounds(190, 88, 182, 23);
		contentPane.add(btnVyradenieKnihy);
		
		JButton btnVpisOmekanchVrten = new JButton("V\u00FDpis ome\u0161kan\u00FDch vr\u00E1ten\u00ED pre \u010Ditate\u013Ea");
		btnVpisOmekanchVrten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisOneskorenychVrateniPreCitatela.setVisible(true);
			}
		});
		btnVpisOmekanchVrten.setBounds(271, 187, 306, 23);
		contentPane.add(btnVpisOmekanchVrten);
		
		JButton btnVpisVpoiiekNa_1 = new JButton("V\u00FDpis star\u00FDch v\u00FDpo\u017Ei\u010Diek \u010Ditate\u013Ea");
		btnVpisVpoiiekNa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisVypoziciekCitatela.setVisible(true);
			}
		});
		btnVpisVpoiiekNa_1.setBounds(10, 187, 237, 23);
		contentPane.add(btnVpisVpoiiekNa_1);
		
		JButton btnVyradenieitatea = new JButton("Vyradenie \u010Ditate\u013Ea");
		btnVyradenieitatea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vyradenieCitatela.setVisible(true);
			}
		});
		btnVyradenieitatea.setBounds(190, 52, 182, 23);
		contentPane.add(btnVyradenieitatea);
		
		JButton btnNewButton = new JButton("Zru\u0161enie pobo\u010Dky");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruseniePobocky.setVisible(true);
			}
		});
		btnNewButton.setBounds(191, 123, 182, 23);
		contentPane.add(btnNewButton);
		
		JButton btnGenerovanie = new JButton("Generovanie");
		btnGenerovanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generovanie.setVisible(true);
			}
		});
		btnGenerovanie.setBounds(10, 351, 144, 23);
		contentPane.add(btnGenerovanie);
		
		JButton btnUkladanienatanie = new JButton("Ukladanie/na\u010D\u00EDtanie");
		btnUkladanienatanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ukladanieDoSuboru.setVisible(true);
			}
		});
		btnUkladanienatanie.setBounds(164, 349, 182, 23);
		contentPane.add(btnUkladanienatanie);
		pridanieCitatelaDialog = new PridanieCitatela(kniznica);
		pridanieNovejKnihyDialog = new PridanieNovejKnihyDialog(kniznica);
		pridaniePobocky = new PridaniePobocky(kniznica);
		vyhladanieCitatelaDialog = new VyhladanieCitatelaDialog(kniznica);
		vyhladanieKnihyDialog = new VyhladanieKnihyDialog(kniznica);
		vypisCitatelovDialog = new VypisCitatelovDialog(kniznica);
		vypisKnihNaPobockeDialog = new VypisKnihNaPobockeDialog(kniznica);
		vypisPobociekDialog = new VypisPobociekDialog(kniznica);
		vyhladanieKnih = new VyhldanieKnih(kniznica);
		zapozicanieKnih = new ZapozicanieKnihy(kniznica);
		vrateniaKnih = new VratenieKnihy(kniznica);
		vypisVypoziciekNaPobocke = new VypisVypoziciekNaPobocke(kniznica);
		vypisKnihNaPobockeKdeCitateliaMeskaju = new VypisKnihNaPobockeKdeCitateliaMeskaju(kniznica);
		vyradenieKnihy = new VyradenieKnihy(kniznica);
		vypisOneskorenychVrateniPreCitatela = new VypisOneskorenychVrateniPreCitatela(kniznica);
		vypisVypoziciekCitatela  = new VypisVypoziciekCitatela(kniznica);
		vyradenieCitatela = new VyradenieCitatela(kniznica);
		ruseniePobocky = new RuseniePobocky(kniznica);
		generovanie = new Generovanie(kniznica);
		ukladanieDoSuboru = new UkladanieDoSuboru(kniznica);
	}
}
