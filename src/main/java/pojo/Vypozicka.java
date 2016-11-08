package pojo;

import java.time.LocalDate;;

public class Vypozicka {
	private static int nextID = 1;
	private int ID;
	private Pobocka pobocka;
	private Kniha kniha;
	private Citatel citatel;
	private LocalDate datumDoKedySaMaVratit;
	private LocalDate datumZapozicania;
	public Vypozicka(Pobocka pobocka, Kniha kniha, Citatel citatel, LocalDate datumDoKedySaMaVratit, LocalDate datumZapozicania) {
		this.pobocka = pobocka;
		this.kniha = kniha;
		this.citatel = citatel;
		this.datumDoKedySaMaVratit = datumDoKedySaMaVratit;
		this.datumZapozicania = datumZapozicania;
		this.ID = nextID;
		nextID++;
		this.kniha.setAktualnaVypozicka(this);
		this.citatel.pridajNovuVypozicku(this);
		this.pobocka.pridajNovuVypozicku(this);
	}
	public String getNazovKnihy() {
		return this.kniha.getNazov();
	}
	public int getIDKnihy() {
		return this.kniha.getID();
	}
	public Kniha getKniha() {
		return this.kniha;
	}
	
}
