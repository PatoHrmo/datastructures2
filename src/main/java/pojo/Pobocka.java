package pojo;

import structures.splaytree.SplayTree;

public class Pobocka {
	SplayTree<Integer, Citatel> knihyPodlaID;
	SplayTree<String,SplayTree<Integer, Kniha>> knihyPodlaNazvu;
	SplayTree<String,SplayTree<Integer, Vypozicka>> vypozickyPodlaNazvu;
	String nazov;
	public Pobocka(String nazov) {
		this.nazov = nazov;
		knihyPodlaID = new SplayTree<>();
		knihyPodlaNazvu = new SplayTree<>();
		vypozickyPodlaNazvu = new SplayTree<>();
	}
	
}
