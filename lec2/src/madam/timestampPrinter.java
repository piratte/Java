package src.madam;

class timestampPrinter extends genericPrinter implements printer {
	public void print(String msg) {
		System.out.println(new java.util.Date());
	}
}