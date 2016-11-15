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
	 * zistí èi táto vıpoièka bola vrátená naèas
	 * @return true ak táto vıpoièka meškala s vrátením, inak false
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
	 * zistí poèet dní meškania pri vrátení tejto vıpoièky
	 * @return poèet dní meškania pri vrátení tejto vıpoièky, alebo 0 ak ešte nebola vrátená alebo nemeškala
	 */
	public int getPocetDniMeskania() {
		if(!bolaVratenaNeskor())
			return 0;
		return (int)ChronoUnit.DAYS.between(datumDoKedySaMaVratit, datumKedyBolaVratena);
	}
	/**
	 * zistí èi vıpoièka mešká s vrátením
	 * @param aktualnyDatum dátum ku ktorému zisujeme meškanie
	 * @return true ak táto vıoièka mešká s vrátením
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
	 * zakáe poièiava knihy tomuto èitate¾ovi na dobu jedného roka od vrátenia knihy
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
				"poièaná dòa: "+pozicana+" kedy sa má vráti: "+doKedy;
		if(datumKedyBolaVratena!=null) {
			String vratena = datumKedyBolaVratena.format(formatter);
			info+=" bola vrátená: "+vratena;
		}
		if(bolaVratenaNeskor()) {
			info+=" poèet dní meškania "+getPocetDniMeskania()+".";
		}
		return info;
	}
	public String toID() {
		String udaje = getNazovKnihy()+getCitatela().getCisloPreukazu()+
				datumZapozicania.toString()+datumKedyBolaVratena.toString();
		return udaje;
	}
	/**
	 * získa údaje potrebné pri zapisovaní tejto vıpoièky do súboru
	 * @return string reprezentujúci údaje o tejto vıpoièke
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
