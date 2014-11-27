package lec2.src.madam;

import java.util.Arrays;
import java.util.Iterator;

public class MyColl implements MyCollection {
	private Object[] data;
	private int size;
	private final int defSize = 4;

	public MyColl() {
		data = new Object[defSize];
		size = 0;
	}

	public MyColl(int capacity) {
		if (capacity < defSize)
			capacity = defSize;
		data = new Object[capacity];
		size = 0;
	}
	
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

	public void add(Object o) {
		enoughCap(size + 1);
		data[size++] = o;
	}

	public Object get(int i) { 
		rangeCheck(i);
		return data[i];
	}

	public void remove(Object o) {
		boolean in = false;
		int i;
		for (i=0; i<size; i++) {
			if (o == data[i]) {
				in = true;
				break;
			}
		}
		if (in == true)
			remove(i);
	}
	public void remove(int i) {
		rangeCheck(i);
		//data[i] remove
		System.out.println("Size:" + size + "i: " + i);
		data[i] = data[size-1];
		size--;
	}

	public int size() {
		return size;
	}

	public Iterator iterator() {
		return new Iterator() {
			private int index = 0;

			public boolean hasNext() {
				return index < size;
			}

			public Object next() {
				rangeCheck(index);
				return data[index++];
			}

			public void remove() {}
		};
	}
}