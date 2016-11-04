package main;

import java.util.Date;

import pojo.Citatel;
import pojo.Pobocka;
import structures.splaytree.SplayTree;

public class KniznicnySoftware {
	private Date aktualnyDatum;
	private SplayTree<Integer, Citatel> citateliaPodlaID;
	private SplayTree<String, Pobocka> pobockyPodlaMena;
	private SplayTree<String, SplayTree<Integer,Citatel>> citateliaPodlaMena;
	
}
