package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.KniznicnySoftware;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VypisCitatelovDialog extends JDialog {
	private KniznicnySoftware kniznica;
	private JTextArea textAreaCitatelia;
	public VypisCitatelovDialog(KniznicnySoftware kniznica) {
		this.kniznica = kniznica;
		setTitle("V\u00FDpis \u010Ditate\u013Eov");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnVypisCitatelov = new JButton("Vyp\u00ED\u0161 \u010Ditate\u013Eov");
		btnVypisCitatelov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String info = new String();
				for(String data : kniznica.getMenaCitatelovSID()) {
					info+=data+System.lineSeparator();
					
				}
				textAreaCitatelia.setText(info);
				
			}
		});
		btnVypisCitatelov.setBounds(10, 11, 131, 23);
		getContentPane().add(btnVypisCitatelov);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 414, 192);
		getContentPane().add(scrollPane);
		
		textAreaCitatelia = new JTextArea();
		scrollPane.setViewportView(textAreaCitatelia);

	}

}
