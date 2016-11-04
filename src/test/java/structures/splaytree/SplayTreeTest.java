package structures.splaytree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.Test;


import pojo.Car;
import structures.splaytree.SplayTree;

public class SplayTreeTest {
	@Test
	public void testIfnotFoundParentIsRoot() {
		SplayTree<Integer, Car> auta = new SplayTree<>();
		auta.insert(20, new Car(20,"skodovka"));
		auta.insert(8, new Car(8,"skodovka"));
		auta.insert(22, new Car(22,"skodovka"));
		auta.insert(4, new Car(4,"skodovka"));
		auta.find(1);
		assertEquals(4, auta.getRoot().getEC());
		
	}

	@Test
	public void testAllOperations() {
		for(int i = 0; i< 5;i++) {
			System.out.println("aktualny seed je: "+i);
			Random generator = new Random(i);
			SplayTree<Integer, Car> strom = new SplayTree<>();
			List<Car> auta = new ArrayList<>();
			int size = 0;
				for(int j=0; j<100000;j++) {
					//System.out.println("aktualna iteracia je: "+j);
					//System.out.println("pocet prvkov v strome je: "+strom.getSize());
					//System.out.println();
					//strom.SkontrolujStromCezLevelOrder();
					
					if(generator.nextInt(1000)<900) {
						Car auto = new Car(generator.nextInt(), generator.nextInt()+"");
						//System.out.println("vkladam "+ auto.getEC());
						if(strom.insert(auto.getEC(), auto)) {
							
							auta.add(auto);
							size++;
						}
					} else {
						int pocetPrvkov = auta.size();
						if(pocetPrvkov==0) continue;
						Car auto = auta.get(generator.nextInt(pocetPrvkov));
						assertEquals("auta nie su rovnake",auto, strom.find(auto.getEC()));
						auto = auta.remove(generator.nextInt(pocetPrvkov));
						//System.out.println("mazem "+ auto.getEC());
						strom.delete(auto.getEC());
						
						size--;
					}
				}
				
				assertEquals("nie su rovako velke", size, strom.getSize());
				auta.sort(new Comparator<Car>() {

					@Override
					public int compare(Car o1, Car o2) {
						if(o1.getEC()==o2.getEC()) return 0;
						if(o1.getEC()>o2.getEC()) return 1;
						else return -1;
					}
				});
				Car autaExpected[] = new Car[auta.size()];
				auta.toArray(autaExpected);
				Car autaActual[] = new Car[strom.getSize()];
				strom.inOrder(autaActual);
				assertArrayEquals("nie su rovnake", autaExpected, autaActual);
		}
	}

}
