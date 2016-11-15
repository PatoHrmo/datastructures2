package pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;;

public class Vypozicka {
	private Pobocka pobocka;
	private Kniha kniha;
	private Citatel citatel;
	private LocalDate datumDoKedySaMaVratit;
	private LocalDate datumZapozicania;
	private LocalDate datumKedyBolaVratena;
	private String nazovPobockyKdeBolaVratena;
	public Vypozicka(Pobocka pobocka, Kniha kniha, Citatel citatel, LocalDate datumDoKedySaMaVratit, LocalDate datumZapozicania) {
		this.pobocka = pobocka;
		this.kniha = kniha;
		this.citatel = citatel;
		this.datumDoKedySaMaVratit = datumDoKedySaMaVratit;
		this.datumZapozicania = datumZapozicania;
		this.datumKedyBolaVratena = null;
		this.nazovPobockyKdeBolaVratena = null;
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
	public String getNazovPobocky() {
		return pobocka.getNazov();
	}
	public LocalDate getDatumZapozicania() {
		return datumZapozicania;
	}
	public LocalDate getDatumDoKedySaMaVratit() {
		return datumDoKedySaMaVratit;
	}
	public double getPoplatokZaDenOmeskania() {
		return this.kniha.getPoplatokZaDenOmeskania();
	}
	public Pobocka getPobocka() {
		return this.pobocka;
	}
	public void setDatumKedyBolaVratena(LocalDate datumKedyBolaVratena) {
		this.datumKedyBolaVratena = datumKedyBolaVratena;
	}
	/**
	 * zist� �i t�to v�po�i�ka bola vr�ten� na�as
	 * @return true ak t�to v�po�i�ka me�kala s vr�ten�m, inak false
	 */
	public boolean bolaVratenaNeskor() {
		if(datumKedyBolaVratena==null) return false;
		if(datumKedyBolaVratena.isAfter(datumDoKedySaMaVratit)) {
			return true;
		}
		return false;
	}
	public Citatel getCitatela () {
		return this.citatel;
	}
	/** 
	 * zist� po�et dn� me�kania pri vr�ten� tejto v�po�i�ky
	 * @return po�et dn� me�kania pri vr�ten� tejto v�po�i�ky, alebo 0 ak e�te nebola vr�ten� alebo neme�kala
	 */
	public int getPocetDniMeskania() {
		if(!bolaVratenaNeskor())
			return 0;
		return (int)ChronoUnit.DAYS.between(datumDoKedySaMaVratit, datumKedyBolaVratena);
	}
	/**
	 * zist� �i v�po�i�ka me�k� s vr�ten�m
	 * @param aktualnyDatum d�tum ku ktor�mu zis�ujeme me�kanie
	 * @return true ak t�to v�o�i�ka me�k� s vr�ten�m
	 */
	public boolean meskaSVratenim(LocalDate aktualnyDatum) {
		if(datumDoKedySaMaVratit.isBefore(aktualnyDatum)) {
			return true;
		}
		return false;
	}
	public LocalDate getDatumKedySaVratila() {
		return datumKedyBolaVratena;
	}
	/**
	 * zak�e po�i�iava� knihy tomuto �itate�ovi na dobu jedn�ho roka od vr�tenia knihy
	 */
	public void blokniCitatela() {
		this.citatel.setDatumOdblokovania(datumKedyBolaVratena.plusYears(1));
		
	}
	public void setNazovPobockyKdeBolaVratena(String nazov) {
		nazovPobockyKdeBolaVratena = nazov;
	}
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		String pozicana = datumZapozicania.format(formatter);
		String doKedy = datumDoKedySaMaVratit.format(formatter);
		
		String info =  citatel.getMeno()+" "+kniha.getNazov()+" "+pobocka.getNazov()+System.lineSeparator()+
				"po�i�an� d�a: "+pozicana+" kedy sa m� vr�ti�: "+doKedy;
		if(datumKedyBolaVratena!=null) {
			String vratena = datumKedyBolaVratena.format(formatter);
			info+=" bola vr�ten�: "+vratena;
		}
		if(bolaVratenaNeskor()) {
			info+=" po�et dn� me�kania "+getPocetDniMeskania()+".";
		}
		return info;
	}
	public String toID() {
		String udaje = getNazovKnihy()+getCitatela().getCisloPreukazu()+
				datumZapozicania.toString()+datumKedyBolaVratena.toString();
		return udaje;
	}
	/**
	 * z�ska �daje potrebn� pri zapisovan� tejto v�po�i�ky do s�boru
	 * @return string reprezentuj�ci �daje o tejto v�po�i�ke
	 */
	public String getSuboroveUdaje() {
		String udaje=pobocka.getNazov()+","+kniha.getID()+","+citatel.getCisloPreukazu()+","
				+datumZapozicania.toString()+","+datumDoKedySaMaVratit.toString()+","+datumKedyBolaVratena+","
				+nazovPobockyKdeBolaVratena;
		return udaje;
	}
	public void setPobocka(Pobocka pobockaDo) {
		this.pobocka = pobockaDo;
		
	}
	
	
}
