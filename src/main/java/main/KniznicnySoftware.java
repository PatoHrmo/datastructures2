package main;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
	// 'priezvisko+meno'
	private SplayTree<String, SplayTree<Integer, Citatel>> citateliaPodlaMena;

	public KniznicnySoftware() {
		aktualnyDatum = LocalDate.now();
		citateliaPodlaID = new SplayTree<>();
		pobockyPodlaMena = new SplayTree<>();
		citateliaPodlaMena = new SplayTree<>();
		this.pridajCitatela("Patrik", "Hrmo");
		this.pridajPobocku("p1");
		this.pridajPobocku("p2");
		this.pridajKnihu("p1", "Java 8", "Jan mak", "5465", "645",
				"romantika", "1", "30");
	}

	public String najdiKnihuPodlaID(String IDknihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if (pobocka == null)
			return "poboèka " + NazovPobocky + " nebola nájdená";
		Kniha kniha = pobocka.najdiKnihuPodlaID(Integer.parseInt(IDknihy));
		if (kniha == null) {
			return "kniha s ID " + IDknihy + " nebola nájdená na poboèke " + NazovPobocky;
		}
		return kniha.toString();
	}

	public List<String> najdiKnihyPodlaNazvu(String NazovKnihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if (pobocka == null)
			return new ArrayList<>();
		List<Kniha> knihy = pobocka.najdiKnihyPodlaNazvu(NazovKnihy);
		if (!knihy.isEmpty()) {
			List<String> udaje = new ArrayList<>();
			for (Kniha kniha : knihy) {
				udaje.add(kniha.toString());
			}
			return udaje;
		} else {
			List<Kniha> nasledovneKnihy = pobocka.dajDalsieKnihyPodlaAbecedy(5);
			List<String> udaje = new ArrayList<>();
			for (Kniha kniha : nasledovneKnihy) {
				udaje.add(kniha.toString());
			}
			return udaje;

		}

	}

	public String zapozicajKnihu(String nazovPobocky, String IDknihy, String cisloPreukazu) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if (pobocka == null) {
			return "pobocka " + nazovPobocky + " neexistuje";
		}
		Kniha kniha = pobocka.najdiKnihuPodlaID(Integer.parseInt(IDknihy));
		if (kniha == null) {
			return "kniha s ID " + IDknihy + " na tejto pobocke neexistuje";
		}
		if (kniha.jePozicana()) {
			return "kniha s ID " + IDknihy + " je uz pozicana";
		}
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(cisloPreukazu));
		if (citatel == null) {
			return "Citatel s ID " + cisloPreukazu + " neexistuje";
		}
		if (citatel.jeMoznePozicat(aktualnyDatum)) {
			return "citatel nie je svedomity, kniha nebola pozicana";
		}
		new Vypozicka(pobocka, kniha, citatel, aktualnyDatum.plus(kniha.getVypozicnaDoba()),
				aktualnyDatum);
		return "kniha bola zapožièaná";
	}

	public String vratKnihu(String IDCitatela, String IDKnihy, String nazovPobocky) {
		boolean vratenieNaInejPobocke = false;
		boolean oneskoreneVratenie = false;
		double poplatokZaOmeskanie = 0;

		Pobocka pobockaDoKtorejSaVracia = pobockyPodlaMena.find(nazovPobocky);
		if (pobockaDoKtorejSaVracia == null) {
			return "Poboèka " + nazovPobocky + " nebola nájdená";
		}
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(IDCitatela));
		if (citatel == null) {
			return "Èitatel s preukazom " + IDCitatela + " neexistuje";
		}
		Vypozicka vypozicka = citatel.getVypozickuPodlaID(Integer.parseInt(IDKnihy));
		if (vypozicka == null) {
			return "Citatel nema pozicanu knihu s id " + IDKnihy;
		}
		
		vypozicka.setDatumKedyBolaVratena(this.aktualnyDatum);
		if (vypozicka.bolaVratenaNeskor()) {
			long pocetDniMeskania =  vypozicka.getPocetDniMeskania();
			if(pocetDniMeskania>60) {
				vypozicka.blokniCitatela();
			}
			poplatokZaOmeskanie = pocetDniMeskania * vypozicka.getPoplatokZaDenOmeskania();
			oneskoreneVratenie = true;
		}
        if(vypozicka.getPobocka()!=pobockaDoKtorejSaVracia) {
        	vratenieNaInejPobocke = true;
        }
		vypozicka.getPobocka().vymazVypozicku(vypozicka);
		vypozicka.getKniha().setAktualnaVypozicka(null);
		citatel.vymazVypozicku(vypozicka);

		vypozicka.getKniha().setPriradenaPobocka(pobockaDoKtorejSaVracia);
		if(vratenieNaInejPobocke) {
			pobockaDoKtorejSaVracia.pridajKnihu(vypozicka.getKniha());
			vypozicka.getPobocka().vymazKnihu(vypozicka.getKniha());
		}
		
		
		String spravaOVrateni = "kniha bola vrátená ";
		if(vratenieNaInejPobocke) {
			spravaOVrateni+=System.lineSeparator()+" poplatok za vrátenie na inej poboèke: 15 eur.";
		}
		if(oneskoreneVratenie) {
			spravaOVrateni+=System.lineSeparator()+" poplatok za omeškanie: "+poplatokZaOmeskanie+" eur.";
		}
		
		return spravaOVrateni;
	}
	public List<String> getInfoOVypozickachNaPobocke(String nazov) {
		Pobocka pobocka = pobockyPodlaMena.find(nazov);
		if(pobocka == null) {
			return new ArrayList<String>();
		}
		List<String> infoOvypozickach = new ArrayList<>();
		for(Vypozicka vypozicka : pobocka.getVypozicky()) {
			infoOvypozickach.add(vypozicka.toString());
		}
		return infoOvypozickach;
	}
	public List<String> getKnihyKtoreMeskajuSvratenim(String nazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		List<String> infoOVypozickach = new ArrayList<>();
		if(pobocka==null) {
			return infoOVypozickach;
		}
		for(Vypozicka vypozicka: pobocka.getVypozickyKtoreMeskaju(aktualnyDatum)){
			infoOVypozickach.add(vypozicka.getKniha().toString());
		}
		return infoOVypozickach;
	}
	public List<String> getinfoOOmesaknychVypozickachCitatela(String idCitatela, String datumOds, String datumDos) {
		List<String> info = new ArrayList<>();
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(idCitatela));
		if(citatel == null) {
			return info;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		LocalDate datumOd = LocalDate.parse(datumOds, formatter);
		LocalDate datumDo = LocalDate.parse(datumDos, formatter);
		
		for(Vypozicka vypozicka: citatel.oneskoreneVypozicky(datumOd, datumDo)) {
			info.add("kniha "+vypozicka.getNazovKnihy()+" bola vratena "+vypozicka.getDatumKedySaVratila()+
					" vratit sa mala najneskor "+vypozicka.getDatumDoKedySaMaVratit()+
					" to je "+vypozicka.getPocetDniMeskania()+" dni meskania");
		}
		return info;
		
	}
	public List<String> getHistoriaVypoziciekCitatela(String idCitatela) {
		List<String> info = new ArrayList<>();
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(idCitatela));
		if (citatel == null) {
			return info;
		}
		for(Kniha kniha : citatel.getKnihyPozicaneVMinulosti()) {
			info.add(kniha.getNazov()+" "+kniha.getID());
		}
		return info;
	}
	public String presunPobockuAVymazJu(String pobockaZs, String pobockaDos) {
		Pobocka pobockaZ = pobockyPodlaMena.find(pobockaZs);
		if(pobockaZ==null) {
			return "pobocka "+pobockaZs+" neexistuje";
		}
		Pobocka pobockaDo = pobockyPodlaMena.find(pobockaDos);
		if(pobockaDo==null) {
			return "pobocka "+pobockaDos+" neexistuje";
		}
		for(Kniha kniha : pobockaZ.getVsetkyKnihyNaPobocke()) {
			kniha.setPriradenaPobocka(pobockaDo);
			pobockaDo.pridajKnihu(kniha);
		}
		for(Vypozicka vypozicka: pobockaZ.getVypozicky()) {
			pobockaDo.pridajNovuVypozicku(vypozicka);
		}
		pobockyPodlaMena.delete(pobockaZs);
		return "Poboèka "+pobockaZs+" bola vymazaná a jej agenda presunutá do poboèky "+pobockaDos;
	}
	
	public String vymazCitatela(String id) {
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(id));
		if (citatel == null) {
			return "citatel neexistuje";
		}
		if(citatel.maPozicaneKnihy()) {
			return "citatel ma pozicane knihy, neda sa vymazat";
		}
		citateliaPodlaID.delete(Integer.parseInt(id));
		return "citatel bol vymazany";
	}

	private Citatel getCitatela(int id) {
		Citatel citatel = citateliaPodlaID.find(id);
		return citatel;
	}

	public List<String> getinfoOKnihachCitatela(String IDpreukazuCitatela) {
		Citatel citatel = getCitatela(Integer.parseInt(IDpreukazuCitatela));
		if (citatel == null)
			return new ArrayList<>();
		List<Kniha> knihyCitatela = citatel.getPozicaneKnihy();
		List<String> infoOknihach = new ArrayList<>();
		for (Kniha k : knihyCitatela) {
			infoOknihach.add(k.getNazov() + " " + k.getID());
		}
		// TODO zistit blokovanie
		return infoOknihach;
	}

	public List<String> getNazvyPobociek() {
		List<Pobocka> pobocky = pobockyPodlaMena.toList();
		List<String> nazvy = new ArrayList<>();
		for (Pobocka pobocka : pobocky) {
			nazvy.add(pobocka.getNazov());
		}
		return nazvy;
	}

	public List<String> getInfoOknihachNaPobocke(String nazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if (pobocka == null) {
			return new ArrayList<>();
		}
		List<Kniha> knihyNaPobocke = pobocka.getVsetkyKnihyNaPobocke();
		List<String> infoOknihach = new ArrayList<>();
		for (Kniha kniha : knihyNaPobocke) {
			infoOknihach.add(kniha.getNazov() + " " + kniha.getID());

		}
		return infoOknihach;
	}

	public void pridajCitatela(String meno, String priezvisko) {
		Citatel citatel = new Citatel(meno, priezvisko);
		citateliaPodlaID.insert(citatel.getCisloPreukazu(), citatel);
		SplayTree<Integer, Citatel> citateliaPodlanovehoMena = citateliaPodlaMena.find(priezvisko + meno);
		if (citateliaPodlanovehoMena == null) {
			citateliaPodlanovehoMena = new SplayTree<>();
			citateliaPodlaMena.insert(priezvisko + meno, citateliaPodlanovehoMena);
		}
		citateliaPodlanovehoMena.insert(citatel.getCisloPreukazu(), citatel);
	}

	public List<String> getMenaCitatelovSID() {
		List<Citatel> citateliaVSysteme = new ArrayList<>();
		List<SplayTree<Integer, Citatel>> zoznamyCitatelovSRovnakymMenom = citateliaPodlaMena.toList();
		for (SplayTree<Integer, Citatel> zoznam : zoznamyCitatelovSRovnakymMenom) {
			citateliaVSysteme.addAll(zoznam.toList());
		}
		List<String> infoOCitateloch = new ArrayList<>();
		for (Citatel citatel : citateliaVSysteme) {
			infoOCitateloch.add(citatel.getPriezvisko() + " " + citatel.getMeno() + " " + citatel.getCisloPreukazu());
		}
		return infoOCitateloch;
	}

	public String pridajKnihu(String nazovPobocky, String nazov, String autor, String iSBN, String eAN, String zaner,
			String poplatokZaDenOmeskania, String vypozicnaDoba) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if (pobocka == null) {
			return "pobocka neexistuje";
		}
		Kniha kniha = new Kniha(nazov, autor, iSBN, eAN, zaner, pobocka, Double.parseDouble(poplatokZaDenOmeskania),
				Period.ZERO.plusDays(Integer.parseInt(vypozicnaDoba)));
		pobocka.pridajKnihu(kniha);
		return "kniha bola pridaná";
	}

	public void pridajPobocku(String nazovPobocky) {
		pobockyPodlaMena.insert(nazovPobocky, new Pobocka(nazovPobocky));
	}

	public String getMenoCitatela(String IDpreukazu) {
		Citatel citatel = getCitatela(Integer.parseInt(IDpreukazu));
		if (citatel == null) {
			return "èitate¾ neexistuje";
		}
		return citatel.getMeno() + " " + citatel.getPriezvisko();

	}

	public void setAktualnyDatum(String datum) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		aktualnyDatum = LocalDate.parse(datum, formatter);
	}

	public String getDatum() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		return aktualnyDatum.format(formatter);
	}

}
