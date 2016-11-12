package gui;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextArea;

import main.KniznicnySoftware;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VypisPobociekDialog extends JDialog {
	private KniznicnySoftware kniznica;
	private JTextArea pobockyTxtArea;

	
	public VypisPobociekDialog(KniznicnySoftware kniznica) {
		setTitle("V\u00FDpis pobo\u010Diek");
		this.kniznica = kniznica;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton vypisPobockybtn = new JButton("Vyp\u00ED\u0161 pobo\u010Dky");
		vypisPobockybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pobocky = new String();
				for(String menoPobocky : kniznica.getNazvyPobociek()) {
					pobocky+= menoPobocky+System.lineSeparator();
				}
				pobockyTxtArea.setText(pobocky);
			}
		});
		vypisPobockybtn.setBounds(10, 11, 142, 23);
		getContentPane().add(vypisPobockybtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 414, 166);
		getContentPane().add(scrollPane);
		
		pobockyTxtArea = new JTextArea();
		scrollPane.setViewportView(pobockyTxtArea);

	}
}
