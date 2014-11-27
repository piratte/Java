package src.adammar;

import java.util.Iterator;
import java.util.Arrays;

public class MyString implements Iterable<Character>{
	private char[] data; 
	private int size;
	private final int defSize = 4;

	private void enoughCap(int wanted) {
		int cap = data.length;
		if (wanted > cap) {
			int newcap = cap + (wanted - cap) * 2;
			data = Arrays.copyOf(data, newcap);
		}
	}

	private void rangeCheck(int ind) {
		if((ind >= size) || (size == 0))
			throw new IndexOutOfBoundsException("Index: " + ind + ", Size: " + size);
	}


	MyString() {
		data = new char[defSize];
		size = 0;
	}
	MyString(String s) {
		int len = s.length();
		data = new char[len];
		for (int i=0; i < len ; i++ ) {
			data[i] = s.charAt(i);
		}
		size = len;
	}

	void append(String s) {
		int addition = s.length();
		enoughCap(size + addition);
		for (int i=0; i < addition ; i++ ) {
			data[size+i] = s.charAt(i);
		}
		size += addition;
	}
	void append(char ch) {
		enoughCap(size++);
		data[size-1] = ch;
	}

	void insert(int pos, String s) {
		rangeCheck(pos);
		int addition = s.length();
		enoughCap(size + addition);
		for (int i=size-1;  i>pos ; i--) {
			data[i+addition] = data[i];
		}
		for (int i=0; i<addition ; i++) {
			data[pos+i] = s.charAt(i);
		}
		size += addition;
	}
	void insert(int pos, char ch) {
		rangeCheck(pos);
		enoughCap(size++);
		for (int i=size-1;  i>pos ; i--) {
			data[i+1] = data[i];
		}
		data[pos] = ch;
		size += 1;
	}

	void delete(int pos, int len) {
		rangeCheck(pos);

		size -= len;
	}

	public String toString() {
		return size == 0 ? new String() : new String(data);
	}

	public Iterator<Character> iterator() {
		return new Iterator<Character>() {
			private int index = 0;

			public boolean hasNext() {
				return index < size;
			}

			public Character next() {
				rangeCheck(index);
				return data[index++];
			}

			public void remove() {}
		};
	}
}
