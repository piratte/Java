package lec9.src.madam;

public class Item {
	public int priority;
	public String what;

	public Item(String what, int priority) {
		this.what = what;
		this.priority = priority;
	}

	public String toString() {
		return what + " " + priority;
	}
}