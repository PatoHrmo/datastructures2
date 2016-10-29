package structures.bsttree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import pojo.Car;
import structures.bsttree.BstTree;

public class BstTreeTest {

	

	@Test
	public void testAllOperations() {
		for(int i = 0; i< 5000;i++) {
			System.out.println("aktualny seed je: "+i);
			Random generator = new Random(i);
			BstTree<Integer, Car> strom = new BstTree<>();
			List<Car> auta = new ArrayList<>();
			int size = 0;
				for(int j=0; j<10000;j++) {
					
					if(generator.nextInt(1000)<500) {
						Car auto = new Car(generator.nextInt(), generator.nextInt()+"");
						if(strom.insert(auto.getEC(), auto)) {
							auta.add(auto);
							size++;
						}
					} else {
						int pocetPrvkov = auta.size();
						if(pocetPrvkov==0) continue;
						Car auto = auta.remove(0);
						strom.delete(auto.getEC());
						size--;
					}
				}
				
				assertEquals("nie su rovako velke", size, strom.getSize());
				auta.sort(new Comparator<Car>() {

					@Override
					public int compare(Car o1, Car o2) {
						if(o1.getEC()==o2.getEC()) return 0;
						if(o1.getEC()<o2.getEC()) return 1;
						else return -1;
					}
				});
				Car autaExpected[] = new Car[auta.size()];
				auta.toArray(autaExpected);
				Car autaActual[] = new Car[strom.getSize()];
				strom.toArray(autaActual);
				assertArrayEquals("nie su rovnake", autaExpected, autaActual);
		}
	}

}
