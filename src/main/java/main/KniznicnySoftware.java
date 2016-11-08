package main;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import pojo.Citatel;
import pojo.Kniha;
import pojo.Pobocka;
import pojo.Vypozicka;
import structures.splaytree.SplayTree;

public class KniznicnySoftware {
	private LocalDate aktualnyDatum;
	private SplayTree<Integer, Citatel> citateliaPodlaID;
	private SplayTree<String, Pobocka> pobockyPodlaMena;
	                 //'priezvisko+meno'
	private SplayTree<String, SplayTree<Integer,Citatel>> citateliaPodlaMena;
	
	public KniznicnySoftware() {
		aktualnyDatum = LocalDate.now();
		citateliaPodlaID  =new SplayTree<>();
		pobockyPodlaMena = new SplayTree<>();
		citateliaPodlaMena = new SplayTree<>();
	}
	
	public String najdiKnihuPodlaID(int IDknihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if(pobocka == null) return null;
		Kniha kniha = pobocka.najdiKnihuPodlaID(IDknihy);
		return kniha.toString();
	}
	public String[] najdiKnihyPodlaNazvu(String NazovKnihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if(pobocka==null) return null;
		List<Kniha> knihy = pobocka.najdiKnihyPodlaNazvu(NazovKnihy);
		if(!knihy.isEmpty()) {
			String[] udaje = new String[knihy.size()];
			int i = 0;
			for(Kniha kniha : knihy) {
				udaje[i] = kniha.toString();
				i++;
			}
			return udaje;
		} else {
			List<Kniha> nasledovneKnihy = pobocka.dajDalsieKnihyPodlaAbecedy(5);
			String[] udaje = new String[nasledovneKnihy.size()];
			int i = 0;
			for(Kniha kniha : nasledovneKnihy) {
				udaje[i] = kniha.getNazov();
				i++;
			}
			return udaje;
			
		}
		
	}
	public String zapozicajKnihu(String nazovPobocky, int IDknihy, int cisloPreukazu) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if(pobocka==null) {
			return "pobocka "+nazovPobocky+" neexistuje";
		}
		Kniha kniha = pobocka.najdiKnihuPodlaID(IDknihy);
		if(kniha == null) {
			return "kniha s ID "+IDknihy+" na tejto pobocke neexistuje";
		}
		if(kniha.jePozicana()) {
			return "kniha s ID "+IDknihy+" je uz pozicana";
		}
		Citatel citatel = citateliaPodlaID.find(cisloPreukazu);
		if (citatel == null) {
			return "Citatel s ID "+cisloPreukazu+" neexistuje";
		}
		//TODO kontrola ci je citatel svedomity
		Vypozicka vypozicka = new Vypozicka(pobocka, kniha, citatel,
				aktualnyDatum.plus(kniha.getVypozicnaDoba()), aktualnyDatum);
		return "kniha bola zapožièaná";
	}
	public Citatel getCitatela(int id) {
		Citatel citatel = citateliaPodlaID.find(id);
		if (citatel==null) {
			return null;
		}
		return citatel;
	}
	public String[] getinfoOKnihachCitatela(Citatel citatel){
		if(citatel == null)
			return null;
		List<Kniha> knihyCitatela = citatel.getPozicaneKnihy();
		String [] infoOknihach = new String[knihyCitatela.size()];
		int i = 0;
		for(Kniha k : knihyCitatela) {
			infoOknihach[i] = k.getNazov()+" "+k.getID();
			i++;
		}
		//TODO zistit blokovanie
		return infoOknihach;
	}
	public String[] getNazvyPobociek() {
		List<Pobocka> pobocky = pobockyPodlaMena.toList();
		String nazvy[] = new String[pobocky.size()];
		int i = 0;
		for(Pobocka pobocka : pobocky) {
			nazvy[i] = pobocka.getNazov();
			i++;
		}
		return nazvy;
	}
	public String[] getInfoOknihachNaPobocke(String nazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if (pobocka==null) {
			return null;
		}
		List<Kniha> knihyNaPobocke = pobocka.getKnihyMomentalneNaPobocke();
		String infoOknihach[] = new String[knihyNaPobocke.size()];
		int i = 0;
		for(Kniha kniha : knihyNaPobocke) {
			infoOknihach[i] = kniha.getNazov()+" "+kniha.getID();
			i++;
		}
		return infoOknihach;
	}
	public void pridajCitatela(String meno, String priezvisko) {
		Citatel citatel = new Citatel(meno, priezvisko);
		citateliaPodlaID.insert(citatel.getCisloPreukazu(), citatel);
		SplayTree<Integer, Citatel> citateliaPodlanovehoMena = citateliaPodlaMena.find(priezvisko+meno);
		if(citateliaPodlanovehoMena == null) {
			citateliaPodlanovehoMena = new SplayTree<>();
			citateliaPodlaMena.insert(priezvisko+meno, citateliaPodlanovehoMena);
		}
		citateliaPodlanovehoMena.insert(citatel.getCisloPreukazu(), citatel);
	}
	
	public String[] getMenaCitatelov() {
		List<Citatel> citateliaVSysteme = new ArrayList<>();
		List<SplayTree<Integer, Citatel>> zoznamyCitatelovSRovnakymMenom =
				citateliaPodlaMena.toList();
		for(SplayTree<Integer, Citatel> zoznam : zoznamyCitatelovSRovnakymMenom) {
			citateliaVSysteme.addAll(zoznam.toList());
		}
		String infoOcitateloch[]  = new String[citateliaVSysteme.size()];
		int i = 0;
		for(Citatel citatel : citateliaVSysteme) {
			infoOcitateloch[i] = citatel.getPriezvisko()+" "+citatel.getMeno()+" "+citatel.getCisloPreukazu();
			i++;
		}
		return infoOcitateloch;
	}
	
	public String pridajKnihu(String nazovPobocky, String nazov, String autor, String iSBN,
			String eAN, String zaner, Pobocka aktualnePriradenaPobocka,
			String poplatokZaDenOmeskania, String vypozicnaDoba) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if (pobocka==null) {
			return "pobocka neexistuje";
		}
		Kniha kniha = new Kniha(nazov,autor,iSBN,eAN,zaner,pobocka,Double.parseDouble(poplatokZaDenOmeskania),
				Period.ZERO.plusDays(Integer.parseInt(vypozicnaDoba)));
		pobocka.pridajKnihu(kniha);
		return "kniha bola pridaná";
	}
	
	
}
