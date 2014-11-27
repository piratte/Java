package lec2.src.madam;

public interface MyCollection extends Iterable{
	public void add(Object o);
	public Object get(int i);
	public void remove(Object o);
	public void remove(int i);
}