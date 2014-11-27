package src.adammar;

public class test {
	public static void main(String[] args) {
		MyString ms = new MyString("Tonda rullez");
		ms.insert(5,", ale Rob vic, ");
		ms.append('!');
		System.out.println(ms);
		for (char ch : ms) {
			System.out.println(ch);
		}
	}
}