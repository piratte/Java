package src.madam;

public class test {
	public static void main(String[] args) {
		MyColl m = new MyColl(2);
		m.add(new Integer(1));
		m.add(new Integer(2));
		m.add(new Integer(3));
		String s = "Tonda";
		m.add(s);
		for (Object a : m) {
			System.out.println(a.toString());
		}
		m.remove(2);
		for (Object a : m) {
			System.out.println(a.toString());
		}
		m.remove(s);
		System.out.println(m.get(1));
		// vyhodi vyjimku
		System.out.println(m.get(2));
	}
}