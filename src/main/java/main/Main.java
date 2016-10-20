package main;

import java.util.ArrayList;
import java.util.Comparator;
import pojo.Car;

public class Main {

	public static void main(String[] args) {
		ArrayList<Car> auta = new ArrayList<>(10);
		auta.add(new Car(1,"as"));
		auta.add(new Car(5, "ads"));
		auta.add(new Car(7, "aads"));
		auta.add(new Car(4, "aa5ds"));
		auta.sort(Comparator.comparing(Car::getEC));
		for(Car auto : auta) {
			System.out.println(auto);
		}
	}

}
