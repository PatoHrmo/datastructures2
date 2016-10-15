package main;

import bsttree.BstTree;
import pojo.Car;

public class Main {

	public static void main(String[] args) {
		Car auto1 = new Car(5, "bugina");
		Car auto2 = new Car(8, "bugsdafsadina");
		Car auto3 = new Car(-50, "buasgina");
		Car auto5 = new Car(44, "bugasdina");
		Car auto6 = new Car(552, "bugsadasdina");
		Car auto7 = new Car(120, "sadf");
		Car auto8 = new Car(4538, "bugsdfasina");
		Car auto9 = new Car(786, "bugisdafsadna");
		Car auto4 = new Car(-88, "bugsaasdfina");
		Car auto10 = new Car(-54, "bugsadfina");
		Car auto11 = new Car(-78, "bugisasdfasna");
		Car auto12 = new Car(542876, "busadfgina");
		Car auto13 = new Car(-7897, "skodovecka");
		BstTree<Integer,Car> strom = new BstTree<>();
		strom.insert(auto1.getEC(),auto1);
		strom.insert(auto2.getEC(),auto2);
		strom.insert(auto3.getEC(),auto3);
		strom.insert(auto4.getEC(),auto4);
		strom.insert(auto5.getEC(),auto5);
		strom.insert(auto6.getEC(),auto6);
		strom.insert(auto7.getEC(),auto7);
		strom.insert(auto8.getEC(),auto8);
		strom.insert(auto9.getEC(),auto9);
		strom.insert(auto10.getEC(),auto10);
		strom.insert(auto11.getEC(),auto11);
		strom.insert(auto12.getEC(),auto12);
		strom.insert(auto13.getEC(),auto13);
		
		System.out.println(strom.find(-7897));
		//strom.inOrder();

	}

}
