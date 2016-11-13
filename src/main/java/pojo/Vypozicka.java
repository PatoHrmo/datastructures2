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
	public Vypozicka(Pobocka pobocka, Kniha kniha, Citatel citatel, LocalDate datumDoKedySaMaVratit, LocalDate datumZapozicania) {
		this.pobocka = pobocka;
		this.kniha = kniha;
		this.citatel = citatel;
		this.datumDoKedySaMaVratit = datumDoKedySaMaVratit;
		this.datumZapozicania = datumZapozicania;
		this.datumKedyBolaVratena = null;
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
	public int getPocetDniMeskania() {
		if(datumKedyBolaVratena==null) return 0;
		return (int)ChronoUnit.DAYS.between(datumDoKedySaMaVratit, datumKedyBolaVratena);
	}
	public boolean meskaSVratenim(LocalDate aktualnyDatum) {
		if(datumDoKedySaMaVratit.isBefore(aktualnyDatum)) {
			return true;
		}
		return false;
	}
	public LocalDate getDatumKedySaVratila() {
		return datumKedyBolaVratena;
	}
	public void blokniCitatela() {
		this.citatel.setDatumOdblokovania(datumKedyBolaVratena.plusYears(1));
		
	}
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		String pozicana = datumZapozicania.format(formatter);
		String doKedy = datumDoKedySaMaVratit.format(formatter);
		
		String info =  citatel.getMeno()+" "+kniha.getNazov()+" "+pobocka.getNazov()+System.lineSeparator()+
				"poûiËan· dÚa: "+pozicana+" kedy sa m· vr·tiù: "+doKedy;
		if(datumKedyBolaVratena!=null) {
			String vratena = datumKedyBolaVratena.format(formatter);
			info+=" bola vr·ten·: "+vratena;
		}
		if(bolaVratenaNeskor()) {
			info+=" poËet dnÌ meökania "+getPocetDniMeskania()+".";
		}
		return info;
	}
	
	
}
