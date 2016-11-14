package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;

import main.KniznicnySoftware;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UkladanieDoSuboru extends JDialog {
	
	private KniznicnySoftware kniznica;
	private JTextField textFieldNazovSuboru;
	
	public UkladanieDoSuboru(KniznicnySoftware kniznica) {
		setTitle("Ukladanie do s\u00FAboru");
		this.kniznica = kniznica;
		setBounds(100, 100, 450, 174);
		getContentPane().setLayout(null);
		
		JLabel lblNzovSboru = new JLabel("N\u00E1zov s\u00FAboru");
		lblNzovSboru.setBounds(10, 12, 163, 14);
		getContentPane().add(lblNzovSboru);
		
		textFieldNazovSuboru = new JTextField();
		textFieldNazovSuboru.setBounds(10, 37, 86, 20);
		getContentPane().add(textFieldNazovSuboru);
		textFieldNazovSuboru.setColumns(10);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setBounds(114, 40, 310, 14);
		getContentPane().add(lblInfo);
		
		JButton btnUlo = new JButton("Ulo\u017E");
		btnUlo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String odpoved = kniznica.ulozDoSuboru(textFieldNazovSuboru.getText());
				lblInfo.setText(odpoved);
			}
		});
		btnUlo.setBounds(10, 68, 89, 23);
		getContentPane().add(btnUlo);
		
		JButton btnNataj = new JButton("Na\u010D\u00EDtaj");
		btnNataj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String odpoved = kniznica.nacitajZoSuboru(textFieldNazovSuboru.getText());
				lblInfo.setText(odpoved);
			}
		});
		btnNataj.setBounds(109, 68, 89, 23);
		getContentPane().add(btnNataj);

	}
}
