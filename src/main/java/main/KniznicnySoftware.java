package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
	}
	/**
	 * Vráti textovú reprezentáciu dannej knihy 
	 * @param IDknihy jedineèné èíslo vıtlaèku knihy
	 * @param NazovPobocky názov poboèky v ktorej sa kniha nachádza
	 * @return textovú reprezentáciu knihy
	 */
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
	/**
	 * vráti textovú reprezentáciu kníh so zadanım názvom
	 * @param NazovKnihy nazov knihy ktorú chceme vyh¾ada
	 * @param NazovPobocky nazov pobocky na ktorej sa bude vyh¾adáva
	 * @return zoznam kníh na tejto poboèke so zadanım názvom, alebo nasledovné knihy v abecednom poradí
	 */
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
	/**
	 * Zapoièia knihu dannému èitate¾ovi
	 * @param nazovPobocky nazov poboèky z ktorej sa poièiava
	 * @param IDknihy id vıtlaèku knihy ktorá sa má poièa
	 * @param cisloPreukazu èíslo preukazu èitate¾a
	 * @return správu o stave vıpoièky
	 */
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
		if (!citatel.jeMoznePozicat(aktualnyDatum)) {
			return "citatel nie je svedomity, kniha nebola pozicana";
		}
		new Vypozicka(pobocka, kniha, citatel, aktualnyDatum.plus(kniha.getVypozicnaDoba()),
				aktualnyDatum);
		return "kniha bola zapoièaná";
	}
	/**
	 * Zapoièia knihu dannému èitate¾ovi - pouívaná len pri naèítavní databázy zo súboru
	 * @param nazovPobocky nazov poboèky z ktorej sa poièiava
	 * @param IDknihy id vıtlaèku knihy ktorá sa má poièa
	 * @param cisloPreukazu èíslo preukazu èitate¾a
	 * @param datumPozickyS dátum kedy sa uskutoènila vıpoièka
	 */
	private void zapozicajKnihu(String nazovPobocky, String IDknihy, String cisloPreukazu, String datumPozickyS) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		Kniha kniha = pobocka.najdiKnihuPodlaID(Integer.parseInt(IDknihy));
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(cisloPreukazu));
		LocalDate datumPozicky = LocalDate.parse(datumPozickyS);
		
		new Vypozicka(pobocka, kniha, citatel, datumPozicky.plus(kniha.getVypozicnaDoba()),
				datumPozicky);
	}
	/**
	 * Vráti knihu na dannú poboèku
	 * @param IDCitatela èíslo preukazu èitate¾a
	 * @param IDKnihy id vıtlaèku knihy ktorá sa má vráti
	 * @param nazovPobocky nazov poboèky do ktorej èitate¾ vracia 
	 * @return správu o vrátení
	 */
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
        	vypozicka.setNazovPobockyKdeBolaVratena(pobockaDoKtorejSaVracia.getNazov());
        } else {
        	vypozicka.setNazovPobockyKdeBolaVratena(vypozicka.getPobocka().getNazov());
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
	/**
	 * Vráti knihu na dannú poboèku - pouíva sa pri naèítaní databázy zo súboru
	 * @param IDCitatela èíslo preukazu èitate¾a
	 * @param IDKnihy id vıtlaèku knihy ktorá sa má vráti
	 * @param nazovPobocky nazov poboèky do ktorej èitate¾ vracia 
	 * @param datumVrateniaS dátum kedy bola kniha vrátená
	 */
	private void vratKnihu(String IDCitatela, String IDKnihy, String nazovPobocky,String datumVrateniaS) {
		boolean vratenieNaInejPobocke = false;
		LocalDate datumVratenia = LocalDate.parse(datumVrateniaS);
		Pobocka pobockaDoKtorejSaVracia = pobockyPodlaMena.find(nazovPobocky);
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(IDCitatela));
		Vypozicka vypozicka = citatel.getVypozickuPodlaID(Integer.parseInt(IDKnihy));
		
		vypozicka.setDatumKedyBolaVratena(datumVratenia);
		if (vypozicka.bolaVratenaNeskor()) {
			long pocetDniMeskania =  vypozicka.getPocetDniMeskania();
			if(pocetDniMeskania>60) {
				vypozicka.blokniCitatela();
			}
		}
        if(vypozicka.getPobocka()!=pobockaDoKtorejSaVracia) {
        	vratenieNaInejPobocke = true;
        	vypozicka.setNazovPobockyKdeBolaVratena(pobockaDoKtorejSaVracia.getNazov());
        } else {
        	vypozicka.setNazovPobockyKdeBolaVratena(vypozicka.getPobocka().getNazov());
        }
		vypozicka.getPobocka().vymazVypozicku(vypozicka);
		vypozicka.getKniha().setAktualnaVypozicka(null);
		citatel.vymazVypozicku(vypozicka);

		vypozicka.getKniha().setPriradenaPobocka(pobockaDoKtorejSaVracia);
		if(vratenieNaInejPobocke) {
			pobockaDoKtorejSaVracia.pridajKnihu(vypozicka.getKniha());
			vypozicka.getPobocka().vymazKnihu(vypozicka.getKniha());
		}
	}
	/**
	 * vráti informácie o vıpoièkách na poboèke
	 * @param nazov nazov poboèky z ktroej chceme vıpis
	 * @return list stringov obsahujúci informácie o vıpoièkách
	 */
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
	/**
	 * vráti informácie o knihách, pri ktorıch èitatelia meškajú s vrátením
	 * @param nazovPobocky názov poboèky z ktorej chceme vıpis
	 * @return list stringov obsahujúci informácie o knihách, pri ktorıch èitatelia meškajú s vrátením
	 */
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
	/**
	 * získa informácie o vıpoièkách èitate¾a ktoré boli odovzdané neskoro v dannom èasovom rozmedzí
	 * @param idCitatela èíslo preukazu èitate¾a 
	 * @param datumOds dátum od kedy chceme vıpis pôièiek
	 * @param datumDos  dátum do kedy chceme vıpis pôièiek
	 * @return informácie o vıpoièkách èitate¾a ktoré boli odovzdané neskoro v dannom èasovom rozmedzí
	 */
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
			info.add("kniha "+vypozicka.getNazovKnihy()+" bola vratena "
					+vypozicka.getDatumKedySaVratila().format(formatter)+
					" vratit sa mala najneskor "+vypozicka.getDatumDoKedySaMaVratit().format(formatter)+
					" to je "+vypozicka.getPocetDniMeskania()+" dni meskania");
		}
		return info;
		
	}
	/**
	 * získa históriu vıpoièiek kníh èitate¾a
	 * @param idCitatela èíslo preukazu èitate¾a
	 * @return história vıpoièiek kníh èitate¾a
	 */
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
	/**
	 * presunie celú agendu z jednej poboèky do druhej
	 * @param pobockaZs poboèka z ktorej presúvame agendu, a ktorá bude vymazaná
	 * @param pobockaDos poboèka do ktorej presúvame agendu
	 * @return správa o úspešnosti prenosu
	 */
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
			vypozicka.setPobocka(pobockaDo);
		}
		pobockyPodlaMena.delete(pobockaZs);
		return "Poboèka "+pobockaZs+" bola vymazaná a jej agenda presunutá do poboèky "+pobockaDos;
	}
	/**
	 * Vymae poboèku zo systému
	 * @param nazov nazov pobocky ktorá sa má vymaza
	 * @return správa o úspešnosti zrušenia poboèky
	 */
	public String zrusPobocku(String nazov) {
		Pobocka pobocka = pobockyPodlaMena.find(nazov);
		if (pobocka == null) return "poboèka neexistuje";
		if(pobocka.jePrazdna()) {
			pobockyPodlaMena.delete(nazov);
			return "poboèka "+nazov+" bola odstránená";
		}
		return "v poboèke sa nachádzajú knihy alebo vıpoièky, nedá sa odstráni";
	}
	/**
	 * Vymae z databázy danného èitate¾a
	 * @param id èíslo preukazu èitate¾a ktorého chceme vymaza
	 * @return správu o úspešnosti operácie
	 */
	public String vymazCitatela(String id) {
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(id));
		if (citatel == null) {
			return "citatel neexistuje";
		}
		if(citatel.maPozicaneKnihy()) {
			return "citatel ma pozicane knihy, neda sa vymazat";
		}
		citateliaPodlaID.delete(Integer.parseInt(id));
		citateliaPodlaMena.find(citatel.getPriezvisko()+citatel.getMeno()).delete(Integer.parseInt(id));
		return "citatel bol vymazany";
		
	}
	/**
	 * vráti objekt reprezentujúci èitate¾a
	 * @param id èíslo preukazu èitate¾a ktorého chceme vymaza
	 * @return cital so zvolenım id
	 */
	private Citatel getCitatela(int id) {
		Citatel citatel = citateliaPodlaID.find(id);
		return citatel;
	}
	/**
	 * Vráti informácie o aktuálne poièanıch knihách èitate¾a
	 * @param IDpreukazuCitatela èíslo preukazu èitate¾a ktorého pôièky chceme získa
	 * @return list stringov obsahujúci informácie o momentálne poièanıch knihách
	 */
	public List<String> getinfoOKnihachCitatela(String IDpreukazuCitatela) {
		Citatel citatel = getCitatela(Integer.parseInt(IDpreukazuCitatela));
		if (citatel == null)
			return new ArrayList<>();
		List<Kniha> knihyCitatela = citatel.getPozicaneKnihy();
		List<String> infoOknihach = new ArrayList<>();
		for (Kniha k : knihyCitatela) {
			infoOknihach.add(k.getNazov() + " " + k.getID());
		}
		return infoOknihach;
	}
	/**
	 * vymae dannú knihu z evidencie na poboèke
	 * @param nazovPobocky názov poboèky z ktorej chceme vymaza knihu
	 * @param IDKnihy id vıtlaèku knihy ktorú chceme vyradi
	 * @return správa o dokonèení operácie vymazania
	 */
	public String vymazKnihu(String nazovPobocky, String IDKnihy) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if(pobocka==null) {
			return "pobocka neexistuje";
		}
		return pobocka.vymazKnihu(IDKnihy);
	}
	/**
	 * získa názvy všetkıch poboèiek v systéme
	 * @return list stringov názvov poboèiek
	 */
	public List<String> getNazvyPobociek() {
		List<Pobocka> pobocky = pobockyPodlaMena.toList();
		List<String> nazvy = new ArrayList<>();
		for (Pobocka pobocka : pobocky) {
			nazvy.add(pobocka.getNazov());
		}
		return nazvy;
	}
	/**
	 * vráti informácie o knihách na dannej poboèke
	 * @param nazovPobocky názov poboèky z ktorej chceme urobi vıpis
	 * @return list stringov obsahujúci informácie o knihách na zvolenej poboèke
	 */
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
	/**
	 * pridá do systému nového èitate¾a
	 * @param meno meno nového èitate¾a
	 * @param priezvisko priezvisko nového èitate¾a
	 */
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
	/**
	 * pridá do systému nového èitate¾a - pouíva sa pri naèítavaní databázy zo systému
	 * @param citatel objekt reprezentujúci èitate¾a 
	 */
	private void pridajCitatela(Citatel citatel) {
		citateliaPodlaID.insert(citatel.getCisloPreukazu(), citatel);
		SplayTree<Integer, Citatel> citateliaPodlanovehoMena = citateliaPodlaMena.find(citatel.getPriezvisko()+citatel.getMeno());
		if (citateliaPodlanovehoMena == null) {
			citateliaPodlanovehoMena = new SplayTree<>();
			citateliaPodlaMena.insert(citatel.getPriezvisko()+citatel.getMeno(), citateliaPodlanovehoMena);
		}
		citateliaPodlanovehoMena.insert(citatel.getCisloPreukazu(), citatel);
	}
	/**
	 * získa mená èitate¾ov spolu s ich èíslamy èitate¾skıch preukazov
	 * @return list stringov obsahujúci mená èitate¾ov spolu s ich èíslamy èitate¾skıch preukazov
	 */
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
	/**
	 * Pridá do systému novú knihu
	 * @param nazovPobocky názov poboèky do ktorej sa pridá táto kniha
	 * @param nazov názov knihy
	 * @param autor autor knihy
	 * @param iSBN isbn èíslo tejto knihy
	 * @param eAN ean èíslo tejto knihy
	 * @param zaner zaner tejto knihy
	 * @param poplatokZaDenOmeskania poplatok za deò omeškania pri vrátení knihy
	 * @param vypozicnaDoba vıpoièná doba tejto knihy
	 * @return správu o úspešnosti operácie pridávania
	 */
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
	
	/**
	 * pridá do systému novú poboèku
	 * @param nazovPobocky názov novej poboèky
	 */
	public void pridajPobocku(String nazovPobocky) {
		pobockyPodlaMena.insert(nazovPobocky, new Pobocka(nazovPobocky));
	}
	/**
	 * získa meno èitate¾a
	 * @param IDpreukazu èíslo preukazu èitate¾a ktorého meno chceme zisti
	 * @return meno èitate¾a s dannım èislom preukazu
	 */
	public String getMenoCitatela(String IDpreukazu) {
		Citatel citatel = getCitatela(Integer.parseInt(IDpreukazu));
		if (citatel == null) {
			return "èitate¾ neexistuje";
		}
		String info = citatel.getMeno() + " " + citatel.getPriezvisko()+" "+citatel.getDatumOdblokovania();
		return info;

	}
	/**
	 * nastavı v kninici dátum
	 * @param datum na ktorı sa má nastavi kninica
	 */
	public void setAktualnyDatum(String datum) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		aktualnyDatum = LocalDate.parse(datum, formatter);
	}
	/**
	 * vráti aktuálny dátum v kninici
	 * @return dátum na ktorı je nastavená kninica
	 */
	public String getDatum() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		return aktualnyDatum.format(formatter);
	}
	/**
	 * vráti náhodnı string obsahujúci iba písmenká anglickej abecedy
	 * @param pocetPismenok dåka textového reazca ktorı sa má vygenerova
	 * @param rnd generátor náhodnıch èísel
	 * @return náhodnı string
	 */
	private String randomString(int pocetPismenok, Random rnd){
		String pismenka= "abcdefghijklmnopqrstuvwxyz";
		String randomString = "";
		for(int i = 0; i<pocetPismenok;i++) {
			randomString += pismenka.charAt(rnd.nextInt(pismenka.length()));
		}
		return randomString;
	}
	/**
	 * vygeneruje náhodné dáta pre túto kninicu
	 * @param pocetUzivs poèet uívate¾ov ktorı sa majú vygenerova
	 * @param pocetPobocieks poèet poboèiek ktoré sa majú vygenerova
	 * @param pocetKnihVkazdejPobockes poèet kníh v kadej poboèke ktoré sa majú vygenerova
	 */
	public void generuj(String pocetUzivs, String pocetPobocieks, String pocetKnihVkazdejPobockes) {
		String meno;
		String priezvisko;
		String zaner;
		String menoPobocky;
		String Isbn;
		int cislaDoisbn[] = new int[13];
		cislaDoisbn[0] = 9;
		cislaDoisbn[1] = 7;
		cislaDoisbn[2] = 8;
		int sumaI;
		Random rnd = new Random();
		byte bity[]  = new byte[5];
		int pocetUziv = Integer.parseInt(pocetUzivs);
		int pocetPobociek = Integer.parseInt(pocetPobocieks);
		int pocetKnihNaPobocku = Integer.parseInt(pocetKnihVkazdejPobockes);
		for(int i = 0;i<pocetUziv;i++) {
			rnd.nextBytes(bity);
			meno = randomString(5, rnd);;
			rnd.nextBytes(bity);
			priezvisko = randomString(5, rnd);;
			pridajCitatela(meno, priezvisko);
		}
		for(int i=0;i<pocetPobociek;i++) {
			rnd.nextBytes(bity);
			menoPobocky = randomString(5, rnd);
			pridajPobocku(menoPobocky);
			for(int j=0;j<pocetKnihNaPobocku;j++){
				rnd.nextBytes(bity);
				meno = randomString(5, rnd);
				rnd.nextBytes(bity);
				priezvisko = randomString(5, rnd);;
				rnd.nextBytes(bity);
				zaner = randomString(5, rnd);;
				sumaI= 9+ 3*7 +8;
				Isbn="978";
				for(int k =3;k<cislaDoisbn.length-1;k++) {
					cislaDoisbn[k] = rnd.nextInt(10);
					Isbn+=cislaDoisbn[k];
					if(k%2==0) {
						sumaI+=cislaDoisbn[k];
					} else {
						sumaI = cislaDoisbn[k]*3;
					}
				}
				if(sumaI%10==0) {
					cislaDoisbn[12]=0;
				} else {
					cislaDoisbn[12]=10-sumaI%10;
				}
				Isbn+=cislaDoisbn[12];
				
				pridajKnihu(menoPobocky, meno, priezvisko, Isbn, Isbn,
						zaner, String.valueOf(rnd.nextInt(5)), String.valueOf(rnd.nextInt(60)));
			}
		}
	}
	/**
	 * uloí celú databázu do súboru
	 * @param nazovSuboru názov súboru do ktorého sa uloia dáta
	 * @return správu o vısledku úspešnosti ukladania údajov do súboru
	 */
	public String ulozDoSuboru(String nazovSuboru) {
		String vsetciCitateliaString="";
		String vsetkyPobockyString ="";
		String vsetkyKnihyString="";
		String vsetkyVypozickyString="";
		int pocetPobociek;
		int pocetKnih;
		int pocetVypoziciek;
		int pocetCitatelov = citateliaPodlaID.getSize();
		SplayTree<Integer,Kniha> knihy = new SplayTree<>();
		SplayTree<String,Pobocka> pobocky = new SplayTree<>();
		List<Vypozicka> vypozicky = new ArrayList<>();
		for(Citatel citatel: citateliaPodlaID.toListLevelOrder()){
			vsetciCitateliaString+=citatel.getSuboroveUdaje()+System.lineSeparator();
			for(Vypozicka vypozicka : citatel.getVypozickyVMinulosti()) {
				pobocky.insert(vypozicka.getNazovPobocky(), vypozicka.getPobocka());
				knihy.insert(vypozicka.getIDKnihy(), vypozicka.getKniha());
				vypozicky.add(vypozicka);
			}
			for(Vypozicka vypozicka : citatel.getAktualneVypozicky()){
				vypozicky.add(vypozicka);
			}
		}
		
		for(Pobocka pobocka : pobockyPodlaMena.toListLevelOrder()) {
			pobocky.insert(pobocka.getNazov(), pobocka);
			for(Kniha k : pobocka.getVsetkyKnihyNaPobocke()) {
				knihy.insert(k.getID(), k);
			}
		}
		pocetKnih = knihy.getSize();
		for(Kniha kniha : knihy.toListLevelOrder()) {
			if(kniha.bolaVymazana()) {
				vsetkyKnihyString+= kniha.getSuboroveUdaje()+",n"+System.lineSeparator();
			} else {
				vsetkyKnihyString+= kniha.getSuboroveUdaje()+",e"+System.lineSeparator();
			}
			
		}
		pocetPobociek = pobocky.getSize();
		for(Pobocka pobocka : pobocky.toListLevelOrder()) {
			if(pobockyPodlaMena.find(pobocka.getNazov())==null){
				vsetkyPobockyString+=pobocka.getSuboroveUdaje()+",n"+System.lineSeparator();
			} else {
				vsetkyPobockyString+=pobocka.getSuboroveUdaje()+",e"+System.lineSeparator();
			}
			
		}
		pocetVypoziciek = vypozicky.size();
		for(Vypozicka vypozicka : vypozicky) {
			vsetkyVypozickyString+= vypozicka.getSuboroveUdaje()+System.lineSeparator();
		}
		String vsetkyUdaje = 
				pocetCitatelov+","+Citatel.getNextCisloPreukazu()
				+System.lineSeparator()+vsetciCitateliaString
				+pocetPobociek+System.lineSeparator()+vsetkyPobockyString
				+pocetKnih+","+Kniha.getNextID()+System.lineSeparator()+vsetkyKnihyString
				+pocetVypoziciek+System.lineSeparator()+vsetkyVypozickyString;
		try {
			Files.write(Paths.get(nazovSuboru), vsetkyUdaje.getBytes());
			return "kninica bola uloená do súboru "+nazovSuboru;
		} catch (IOException e) {
			e.printStackTrace();
			return "kninica Nebola uloená do súboru "+nazovSuboru;
		}
		
	}
	/**
	 * naèíta databázu kninice zo súboru
	 * @param nazovSuboru názov súboru z ktorého sa naèítajú dáta
	 * @return správu o naèítaní kninice zo súboru
	 */
	public String nacitajZoSuboru(String nazovSuboru) {
		try {
			Scanner sc = new Scanner(new File(nazovSuboru));
			int pocetCitatelov;
			int nextIDCitatela;
			String poctyCitatelov = sc.nextLine();
			pocetCitatelov = Integer.parseInt(poctyCitatelov.split(",")[0]);
			nextIDCitatela = Integer.parseInt(poctyCitatelov.split(",")[1]);
			String udaje[];
			Citatel.setNextCisloPreukazu(nextIDCitatela);
			for(int i = 0; i<pocetCitatelov;i++) {
				 udaje = sc.nextLine().split(",");
				 Citatel citatel = new Citatel(udaje[0], udaje[1],Integer.parseInt(udaje[2]));
				 this.pridajCitatela(citatel);
			}
			int pocetPobociek = Integer.parseInt(sc.nextLine());
			ArrayList<String> nazvyVymazanychPobociek = new ArrayList<>();
			for(int i = 0; i< pocetPobociek;i++) {
				udaje = sc.nextLine().split(",");
				if(udaje[1].equals("n")){
					nazvyVymazanychPobociek.add(udaje[0]);
				}
				this.pridajPobocku(udaje[0]);	
			}
		    String poctyKnih = sc.nextLine();
		    int pocetKnih = Integer.parseInt(poctyKnih.split(",")[0]);
		    int idDalsejKnihy = Integer.parseInt(poctyKnih.split(",")[1]);
		    Kniha.setNextID(idDalsejKnihy);
		    ArrayList<String[]> idAPobockaVymazanychKnih = new ArrayList<>();
		    for(int i=0; i<pocetKnih;i++) {
		    	udaje = sc.nextLine().split(",");
		    	Pobocka pobocka = pobockyPodlaMena.find(udaje[5]);
		    	Kniha kniha = new Kniha(udaje[0],udaje[1],udaje[2],udaje[3],udaje[4],
		    			pobocka,Double.parseDouble(udaje[6]),
		    					Period.ZERO.plusDays(Integer.parseInt(udaje[7])),
		    					Integer.parseInt(udaje[8]));
		    	if(udaje[9].equals("n")) {
		    		String idApobocka[] = new String[2];
		    		idApobocka[0] = udaje[8];
		    		idApobocka[1] = udaje[5];
		    		idAPobockaVymazanychKnih.add(idApobocka);
		    	}
		    	pobocka.pridajKnihu(kniha);
		    }
		    int pocetVypoziciek = Integer.parseInt(sc.nextLine());
		    ArrayList<String[]> udajeOvratenychVypozickach = new ArrayList<>();
		    ArrayList<String[]> udajeOnevratenychVypozickach = new ArrayList<>();
		    for(int i = 0; i< pocetVypoziciek;i++) {
		    	udaje = sc.nextLine().split(",");
		    	if(udaje[5].equals("null")) {
		    		udajeOnevratenychVypozickach.add(udaje);
		    	} else {
		    		udajeOvratenychVypozickach.add(udaje);
		    	}
		    }
		    for(String[] u : udajeOvratenychVypozickach) {
		    	this.zapozicajKnihu(u[0], u[1], u[2], u[3]);
		    	this.vratKnihu(u[2], u[1], u[6], u[5]);
		    }
		    for(String[] u : udajeOnevratenychVypozickach) {
		    	this.zapozicajKnihu(u[0], u[1], u[2], u[3]);
		    }
		    for(String[] idApob : idAPobockaVymazanychKnih) {
		    	this.vymazKnihu(idApob[1], idApob[0]);
		    }
		    for(String nazovVymazanejPobocky : nazvyVymazanychPobociek) {
		    	pobockyPodlaMena.delete(nazovVymazanejPobocky);
		    }
			sc.close();
			return "kninica bola naèítaná zo súboru "+nazovSuboru;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "súbor "+nazovSuboru+" neexistuje";
		}
		
	}
	

}
