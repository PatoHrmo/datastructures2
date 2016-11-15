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
	 * Vr�ti textov� reprezent�ciu dannej knihy 
	 * @param IDknihy jedine�n� ��slo v�tla�ku knihy
	 * @param NazovPobocky n�zov pobo�ky v ktorej sa kniha nach�dza
	 * @return textov� reprezent�ciu knihy
	 */
	public String najdiKnihuPodlaID(String IDknihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if (pobocka == null)
			return "pobo�ka " + NazovPobocky + " nebola n�jden�";
		Kniha kniha = pobocka.najdiKnihuPodlaID(Integer.parseInt(IDknihy));
		if (kniha == null) {
			return "kniha s ID " + IDknihy + " nebola n�jden� na pobo�ke " + NazovPobocky;
		}
		return kniha.toString();
	}
	/**
	 * vr�ti textov� reprezent�ciu kn�h so zadan�m n�zvom
	 * @param NazovKnihy nazov knihy ktor� chceme vyh�ada�
	 * @param NazovPobocky nazov pobocky na ktorej sa bude vyh�ad�va�
	 * @return zoznam kn�h na tejto pobo�ke so zadan�m n�zvom, alebo nasledovn� knihy v abecednom porad�
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
	 * Zapo�i�ia knihu dann�mu �itate�ovi
	 * @param nazovPobocky nazov pobo�ky z ktorej sa po�i�iava
	 * @param IDknihy id v�tla�ku knihy ktor� sa m� po�i�a�
	 * @param cisloPreukazu ��slo preukazu �itate�a
	 * @return spr�vu o stave v�po�i�ky
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
		return "kniha bola zapo�i�an�";
	}
	/**
	 * Zapo�i�ia knihu dann�mu �itate�ovi - pou��van� len pri na��tavn� datab�zy zo s�boru
	 * @param nazovPobocky nazov pobo�ky z ktorej sa po�i�iava
	 * @param IDknihy id v�tla�ku knihy ktor� sa m� po�i�a�
	 * @param cisloPreukazu ��slo preukazu �itate�a
	 * @param datumPozickyS d�tum kedy sa uskuto�nila v�po�i�ka
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
	 * Vr�ti knihu na dann� pobo�ku
	 * @param IDCitatela ��slo preukazu �itate�a
	 * @param IDKnihy id v�tla�ku knihy ktor� sa m� vr�ti�
	 * @param nazovPobocky nazov pobo�ky do ktorej �itate� vracia 
	 * @return spr�vu o vr�ten�
	 */
	public String vratKnihu(String IDCitatela, String IDKnihy, String nazovPobocky) {
		boolean vratenieNaInejPobocke = false;
		boolean oneskoreneVratenie = false;
		double poplatokZaOmeskanie = 0;

		Pobocka pobockaDoKtorejSaVracia = pobockyPodlaMena.find(nazovPobocky);
		if (pobockaDoKtorejSaVracia == null) {
			return "Pobo�ka " + nazovPobocky + " nebola n�jden�";
		}
		Citatel citatel = citateliaPodlaID.find(Integer.parseInt(IDCitatela));
		if (citatel == null) {
			return "�itatel s preukazom " + IDCitatela + " neexistuje";
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
		
		
		String spravaOVrateni = "kniha bola vr�ten� ";
		if(vratenieNaInejPobocke) {
			spravaOVrateni+=System.lineSeparator()+" poplatok za vr�tenie na inej pobo�ke: 15 eur.";
		}
		if(oneskoreneVratenie) {
			spravaOVrateni+=System.lineSeparator()+" poplatok za ome�kanie: "+poplatokZaOmeskanie+" eur.";
		}
		
		return spravaOVrateni;
	}
	/**
	 * Vr�ti knihu na dann� pobo�ku - pou��va sa pri na��tan� datab�zy zo s�boru
	 * @param IDCitatela ��slo preukazu �itate�a
	 * @param IDKnihy id v�tla�ku knihy ktor� sa m� vr�ti�
	 * @param nazovPobocky nazov pobo�ky do ktorej �itate� vracia 
	 * @param datumVrateniaS d�tum kedy bola kniha vr�ten�
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
	 * vr�ti inform�cie o v�po�i�k�ch na pobo�ke
	 * @param nazov nazov pobo�ky z ktroej chceme v�pis
	 * @return list stringov obsahuj�ci inform�cie o v�po�i�k�ch
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
	 * vr�ti inform�cie o knih�ch, pri ktor�ch �itatelia me�kaj� s vr�ten�m
	 * @param nazovPobocky n�zov pobo�ky z ktorej chceme v�pis
	 * @return list stringov obsahuj�ci inform�cie o knih�ch, pri ktor�ch �itatelia me�kaj� s vr�ten�m
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
	 * z�ska inform�cie o v�po�i�k�ch �itate�a ktor� boli odovzdan� neskoro v dannom �asovom rozmedz�
	 * @param idCitatela ��slo preukazu �itate�a 
	 * @param datumOds d�tum od kedy chceme v�pis p��i�iek
	 * @param datumDos  d�tum do kedy chceme v�pis p��i�iek
	 * @return inform�cie o v�po�i�k�ch �itate�a ktor� boli odovzdan� neskoro v dannom �asovom rozmedz�
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
	 * z�ska hist�riu v�po�i�iek kn�h �itate�a
	 * @param idCitatela ��slo preukazu �itate�a
	 * @return hist�ria v�po�i�iek kn�h �itate�a
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
	 * presunie cel� agendu z jednej pobo�ky do druhej
	 * @param pobockaZs pobo�ka z ktorej pres�vame agendu, a ktor� bude vymazan�
	 * @param pobockaDos pobo�ka do ktorej pres�vame agendu
	 * @return spr�va o �spe�nosti prenosu
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
		return "Pobo�ka "+pobockaZs+" bola vymazan� a jej agenda presunut� do pobo�ky "+pobockaDos;
	}
	/**
	 * Vyma�e pobo�ku zo syst�mu
	 * @param nazov nazov pobocky ktor� sa m� vymaza�
	 * @return spr�va o �spe�nosti zru�enia pobo�ky
	 */
	public String zrusPobocku(String nazov) {
		Pobocka pobocka = pobockyPodlaMena.find(nazov);
		if (pobocka == null) return "pobo�ka neexistuje";
		if(pobocka.jePrazdna()) {
			pobockyPodlaMena.delete(nazov);
			return "pobo�ka "+nazov+" bola odstr�nen�";
		}
		return "v pobo�ke sa nach�dzaj� knihy alebo v�po�i�ky, ned� sa odstr�ni�";
	}
	/**
	 * Vyma�e z datab�zy dann�ho �itate�a
	 * @param id ��slo preukazu �itate�a ktor�ho chceme vymaza�
	 * @return spr�vu o �spe�nosti oper�cie
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
	 * vr�ti objekt reprezentuj�ci �itate�a
	 * @param id ��slo preukazu �itate�a ktor�ho chceme vymaza�
	 * @return cital so zvolen�m id
	 */
	private Citatel getCitatela(int id) {
		Citatel citatel = citateliaPodlaID.find(id);
		return citatel;
	}
	/**
	 * Vr�ti inform�cie o aktu�lne po�i�an�ch knih�ch �itate�a
	 * @param IDpreukazuCitatela ��slo preukazu �itate�a ktor�ho p��i�ky chceme z�ska�
	 * @return list stringov obsahuj�ci inform�cie o moment�lne po�i�an�ch knih�ch
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
	 * vyma�e dann� knihu z evidencie na pobo�ke
	 * @param nazovPobocky n�zov pobo�ky z ktorej chceme vymaza� knihu
	 * @param IDKnihy id v�tla�ku knihy ktor� chceme vyradi�
	 * @return spr�va o dokon�en� oper�cie vymazania
	 */
	public String vymazKnihu(String nazovPobocky, String IDKnihy) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if(pobocka==null) {
			return "pobocka neexistuje";
		}
		return pobocka.vymazKnihu(IDKnihy);
	}
	/**
	 * z�ska n�zvy v�etk�ch pobo�iek v syst�me
	 * @return list stringov n�zvov pobo�iek
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
	 * vr�ti inform�cie o knih�ch na dannej pobo�ke
	 * @param nazovPobocky n�zov pobo�ky z ktorej chceme urobi� v�pis
	 * @return list stringov obsahuj�ci inform�cie o knih�ch na zvolenej pobo�ke
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
	 * prid� do syst�mu nov�ho �itate�a
	 * @param meno meno nov�ho �itate�a
	 * @param priezvisko priezvisko nov�ho �itate�a
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
	 * prid� do syst�mu nov�ho �itate�a - pou��va sa pri na��tavan� datab�zy zo syst�mu
	 * @param citatel objekt reprezentuj�ci �itate�a 
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
	 * z�ska men� �itate�ov spolu s ich ��slamy �itate�sk�ch preukazov
	 * @return list stringov obsahuj�ci men� �itate�ov spolu s ich ��slamy �itate�sk�ch preukazov
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
	 * Prid� do syst�mu nov� knihu
	 * @param nazovPobocky n�zov pobo�ky do ktorej sa prid� t�to kniha
	 * @param nazov n�zov knihy
	 * @param autor autor knihy
	 * @param iSBN isbn ��slo tejto knihy
	 * @param eAN ean ��slo tejto knihy
	 * @param zaner zaner tejto knihy
	 * @param poplatokZaDenOmeskania poplatok za de� ome�kania pri vr�ten� knihy
	 * @param vypozicnaDoba v�po�i�n� doba tejto knihy
	 * @return spr�vu o �spe�nosti oper�cie prid�vania
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
		return "kniha bola pridan�";
	}
	
	/**
	 * prid� do syst�mu nov� pobo�ku
	 * @param nazovPobocky n�zov novej pobo�ky
	 */
	public void pridajPobocku(String nazovPobocky) {
		pobockyPodlaMena.insert(nazovPobocky, new Pobocka(nazovPobocky));
	}
	/**
	 * z�ska meno �itate�a
	 * @param IDpreukazu ��slo preukazu �itate�a ktor�ho meno chceme zisti�
	 * @return meno �itate�a s dann�m �islom preukazu
	 */
	public String getMenoCitatela(String IDpreukazu) {
		Citatel citatel = getCitatela(Integer.parseInt(IDpreukazu));
		if (citatel == null) {
			return "�itate� neexistuje";
		}
		String info = citatel.getMeno() + " " + citatel.getPriezvisko()+" "+citatel.getDatumOdblokovania();
		return info;

	}
	/**
	 * nastav� v kni�nici d�tum
	 * @param datum na ktor� sa m� nastavi� kni�nica
	 */
	public void setAktualnyDatum(String datum) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		aktualnyDatum = LocalDate.parse(datum, formatter);
	}
	/**
	 * vr�ti aktu�lny d�tum v kni�nici
	 * @return d�tum na ktor� je nastaven� kni�nica
	 */
	public String getDatum() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		return aktualnyDatum.format(formatter);
	}
	/**
	 * vr�ti n�hodn� string obsahuj�ci iba p�smenk� anglickej abecedy
	 * @param pocetPismenok d�ka textov�ho re�azca ktor� sa m� vygenerova�
	 * @param rnd gener�tor n�hodn�ch ��sel
	 * @return n�hodn� string
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
	 * vygeneruje n�hodn� d�ta pre t�to kni�nicu
	 * @param pocetUzivs po�et u��vate�ov ktor� sa maj� vygenerova�
	 * @param pocetPobocieks po�et pobo�iek ktor� sa maj� vygenerova�
	 * @param pocetKnihVkazdejPobockes po�et kn�h v ka�dej pobo�ke ktor� sa maj� vygenerova�
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
	 * ulo�� cel� datab�zu do s�boru
	 * @param nazovSuboru n�zov s�boru do ktor�ho sa ulo�ia d�ta
	 * @return spr�vu o v�sledku �spe�nosti ukladania �dajov do s�boru
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
			return "kni�nica bola ulo�en� do s�boru "+nazovSuboru;
		} catch (IOException e) {
			e.printStackTrace();
			return "kni�nica Nebola ulo�en� do s�boru "+nazovSuboru;
		}
		
	}
	/**
	 * na��ta datab�zu kni�nice zo s�boru
	 * @param nazovSuboru n�zov s�boru z ktor�ho sa na��taj� d�ta
	 * @return spr�vu o na��tan� kni�nice zo s�boru
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
			return "kni�nica bola na��tan� zo s�boru "+nazovSuboru;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "s�bor "+nazovSuboru+" neexistuje";
		}
		
	}
	

}
