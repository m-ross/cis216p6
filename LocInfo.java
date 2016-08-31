package lab06;

public class LocInfo {
	private String zip;
	private double price;

	public LocInfo (String s, double d/*lol*/) {
		zip = s;
		price = d;
	}

	public void addLoc(String s, double d) {
		zip = s;
		price = d;
	}

	public String getZip() {
		return zip;
	}

	public double getPrice() {
		return price;
	}
}