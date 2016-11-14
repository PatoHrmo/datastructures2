package main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		if (!citatel.jeMoznePozicat(aktualnyDatum)) {
			return "citatel nie je svedomity, kniha nebola pozicana";
		}
		new Vypozicka(pobocka, kniha, citatel, aktualnyDatum.plus(kniha.getVypozicnaDoba()),
				aktualnyDatum);
		return "kniha bola zapo�i�an�";
	}

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
			info.add("kniha "+vypozicka.getNazovKnihy()+" bola vratena "
					+vypozicka.getDatumKedySaVratila().format(formatter)+
					" vratit sa mala najneskor "+vypozicka.getDatumDoKedySaMaVratit().format(formatter)+
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
		return "Pobo�ka "+pobockaZs+" bola vymazan� a jej agenda presunut� do pobo�ky "+pobockaDos;
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
		citateliaPodlaMena.find(citatel.getPriezvisko()+citatel.getMeno()).delete(Integer.parseInt(id));
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
	public String vymazKnihu(String nazovPobocky, String IDKnihy) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if(pobocka==null) {
			return "pobocka neexistuje";
		}
		return pobocka.vymazKnihu(IDKnihy);
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
		return "kniha bola pridan�";
	}

	public void pridajPobocku(String nazovPobocky) {
		pobockyPodlaMena.insert(nazovPobocky, new Pobocka(nazovPobocky));
	}

	public String getMenoCitatela(String IDpreukazu) {
		Citatel citatel = getCitatela(Integer.parseInt(IDpreukazu));
		if (citatel == null) {
			return "�itate� neexistuje";
		}
		String info = citatel.getMeno() + " " + citatel.getPriezvisko()+" "+citatel.getDatumOdblokovania();
		return info;

	}

	public void setAktualnyDatum(String datum) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		aktualnyDatum = LocalDate.parse(datum, formatter);
	}

	public String getDatum() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		return aktualnyDatum.format(formatter);
	}
	private String randomString(int pocetPismenok, Random rnd){
		String pismenka= "abcdefghijklmnopqrstuvwxyz";
		String randomString = "";
		for(int i = 0; i<pocetPismenok;i++) {
			randomString += pismenka.charAt(rnd.nextInt(pismenka.length()));
		}
		return randomString;
	}
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
				+pocetKnih+System.lineSeparator()+vsetkyKnihyString
				+pocetVypoziciek+System.lineSeparator()+vsetkyVypozickyString;
		try {
			Files.write(Paths.get(nazovSuboru), vsetkyUdaje.getBytes());
			return "kni�nica bola ulo�en� do s�boru "+nazovSuboru;
		} catch (IOException e) {
			e.printStackTrace();
			return "kni�nica Nebola ulo�en� do s�boru "+nazovSuboru;
		}
		
	}

	public String nacitajZoSuboru(String nazovSuboru) {
		// TODO Auto-generated method stub
		return "kni�nica bola na��tan� zo s�boru "+nazovSuboru;
	}

}
