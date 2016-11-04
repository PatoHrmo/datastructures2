package pojo;

import java.time.Period;
import java.util.Date;

public class Kniha {
	private static int nextID = 1;
	private String autor;
	private String ISBN;
	private String EAN;
	private String zaner;
	private Pobocka aktualnePriradenaPobocka;
	private double poplatokZaDenOmeskania;
	private int IDvytlacku;
	private Period vypozicnaDoba;
	private Vypozicka vypozickaVKtorejJeTatoKniha;
	public Kniha(String autor, String iSBN, String eAN, String zaner, Pobocka aktualnePriradenaPobocka,
			double poplatokZaDenOmeskania, Period vypozicnaDoba) {
		super();
		this.autor = autor;
		ISBN = iSBN;
		EAN = eAN;
		this.zaner = zaner;
		this.aktualnePriradenaPobocka = aktualnePriradenaPobocka;
		this.poplatokZaDenOmeskania = poplatokZaDenOmeskania;
		this.vypozicnaDoba = vypozicnaDoba;
		IDvytlacku = nextID;
		nextID++;
	}
	
}
