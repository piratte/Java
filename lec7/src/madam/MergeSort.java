package lec7.src.madam;

import java.util.Random;
import java.lang.*;

public class MergeSort {

	static class Worker extends Thread {
		int[] data;

		Worker(int[] data) {
			this.data = data;
		}

		public void run() {
			data = mergeSort(data);
		}

		public int[] getData() {
			return data;
		}
	}

	public static void main(String[] args) {
		final int SIZE = 100000000;
		Random rand = new Random();
		int[] pole = new int[SIZE];
		int[] out1 = new int[SIZE];
		int[] out2 = new int[SIZE];
		for (int i=0; i<SIZE; ++i) {
			pole[i] = rand.nextInt(999999);
		}

		long start = System.currentTimeMillis();
		out1 = mergeSort(pole);
		long split = System.currentTimeMillis();
		out2 = parallel(pole);
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (split - start) + "ms, in parallel: " + (end -split) + "ms");
	}

	static int getIndMin(int[] pole) {
		int minInd = 0;
		int min = pole[minInd];
		for (int i=1; i<pole.length; ++i) {
			if(min > pole[i]){
				min = pole[i];
				minInd = i;
			}
		}
		return minInd;
	}

	static int[] parallel(int[] pole) {
		int cut = Runtime.getRuntime().availableProcessors();
		int len = pole.length/cut;
		int preb = len % cut;
		int data[] = new int[len+1];
		int[][] ret = new int[cut][];
		int[] indexy = new int[cut];
		Worker[] prac = new Worker[cut];
		long start, split1, split2, end;

		start = System.currentTimeMillis();
		// prideleni podilu pole
		for (int i=0; i<cut; ++i) {
			for (int j=0; j<len; ++j) {
				try {
					data[j] = pole[j+(i*len)];
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("deleni poli");
					System.exit(1);
				}
			}
			if (preb-- > 0) {
				data[len+1] = pole[len+1+(i*len)];
			}
			prac[i] = new Worker(data);
		}

		split1 =  System.currentTimeMillis();
		// spusteni pracantu
		for (Worker w : prac) {
			w.start();
		}

		split2 = System.currentTimeMillis();
		// cut-cestny merge
		for (int i=0; i<cut; ++i) {
			ret[i] = prac[i].getData();
		}

		int[] minima = new int[cut];
		int minInd = -1;
		for (int i=0; i<cut; ++i) {
			minima[i] = ret[i][0];
		}
		for (int i=0; i<pole.length; ++i) {
			minInd = getIndMin(minima);
			pole[i] = minima[minInd];
			try {
				minima[minInd] = ret[minInd][indexy[i]++];
			} catch (ArrayIndexOutOfBoundsException e) {
				minima[minInd] = Integer.MAX_VALUE;
			}
		}
		end = System.currentTimeMillis();
		System.out.println("thread count: " + cut);
		System.out.println("Splitting: "+(split1 - start)+"ms, working: "+(split2 - split1)+"ms, merging: "+(end-split2)+"ms");
		return pole;
	}

	static int[] mergeSort(int[] pole) {
		if (pole.length == 1)
			return pole;
		int lichy = 0;
		int size = pole.length;
		int half = size/2;
		if (size % 2 != 0)
			lichy = 1;
		int[] levy = new int[half];
		int[] pravy = new int[half+lichy];
		//System.err.println("size " + size + " leve: " + levy.length + " prave " + pravy.length + " lichy " + lichy);
		for (int i=0; i<half; ++i) {
			//System.err.println("Li " + i + " pi " + (i+half+lichy));
			levy[i] = pole[i];
			pravy[i] = pole[i+half+lichy];
		}
		if (lichy == 1) 
			pravy[half] = pole[half];

		levy = mergeSort(levy);
		pravy = mergeSort(pravy);
		return merge(levy,pravy);
	}

	static int[] merge(int[] levy, int[] pravy) {
		int left = levy.length; int li = 0;
		int right = pravy.length; int ri = 0;
		int[] res = new int[left + right];
		int i = 0;

		try {
			for (i=0; i<left + right; ++i) {
				if (levy[li] < pravy[ri]) 
					res[i] = levy[li++];
				else
					res[i] = pravy[ri++];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			if (li == left) {
				res[i] = pravy[ri++];
				while (++i < left+right)
					res[i] = pravy[ri++];
			} else {
				res[i] = levy[li++];
				while (++i < left+right)
					res[i] = levy[li++];
			}
		}
		return res;
	}

	static boolean checkSorted (int[] pole) {
		if (pole.length < 2)
			return true;
		for (int i=0; i<pole.length-1 ; ++i) {
			if (pole[i] > pole[i+1])
				return false;
		}
		return true;
	}
	
}