package pojo;

import structures.splaytree.SplayTree;

public class Citatel {
	private String meno;
	private String priezvisko;
	private int cisloPreukazu;
	private SplayTree<Integer, Kniha> aktualnePozicaneKnihy;
	private SplayTree<Integer, Kniha> knihyPozicaneVMinulosti;
	private SplayTree<Integer, Kniha> oneskoreneVrateneKnihy;
	
}
