package pojo;

import java.util.Date;

public class Vypozicka {
	Pobocka pobocka;
	Kniha kniha;
	Citatel citatel;
	Date datumDoKedySaMaVratit;
	Date datumZapozicania;
	public Vypozicka(Pobocka pobocka, Kniha kniha, Citatel citatel, Date datumDoKedySaMaVratit, Date datumZapozicania) {
		this.pobocka = pobocka;
		this.kniha = kniha;
		this.citatel = citatel;
		this.datumDoKedySaMaVratit = datumDoKedySaMaVratit;
		this.datumZapozicania = datumZapozicania;
	}
	
}
