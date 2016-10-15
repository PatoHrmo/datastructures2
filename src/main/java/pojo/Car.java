package pojo;

public class Car implements Comparable<Car> {
	private int EC;
	private String nazov;
	public Car (int ec, String nazov) {
		this.EC = ec;
		this.nazov = nazov;
	}
	@Override
	public int compareTo(Car o) {
		if (this.EC < o.EC) return -1;
		if (this.EC > o.EC) return 1;
		return 0;
	}
	@Override
	public String toString() {
		return EC+" "+nazov;
	}
	public int getEC(){
		return this.EC;
	}
}
