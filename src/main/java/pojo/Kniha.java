package pojo;

import java.time.Period;
import java.util.Date;

public class Kniha {
	private String autor;
	private String ISBN;
	private String EAN;
	private Zaner zaner;
	private Pobocka aktualnePriradenaPobocka;
	private double poplatokZaDenOmeskania;
	private int IDvytlacku;
	private Period vypozicnaDoba;
	private Date datumZapozicania;
	private Date datumDoKedySaMaVratit;
	public Kniha() {
		
	}
}
