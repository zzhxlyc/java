package algorithm;

import java.util.Comparator;
import java.util.List;

public class SortUtils {
	
	public static <T> void quicksort(List<T> list, Comparator<T> C) {
	}
	
	public static <T> void quicksort(T x[], Comparator<T> C) {
		quicksort(x, C, 0, x.length);
	}
	
	public static <T> void quicksort(T x[], Comparator<T> C, int off, int len) {
		// Insertion sort on smallest arrays
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && C.compare(x[j - 1], x[j]) > 0; j--)
					swap(x, j, j - 1);
			return;
		}

		// Choose a partition element, v
		int m = off + (len >> 1); // Small arrays, middle element
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) { // Big arrays, pseudomedian of 9
				int s = len / 8;
				l = med3(x, C, l, l + s, l + 2 * s);
				m = med3(x, C, m - s, m, m + s);
				n = med3(x, C, n - 2 * s, n - s, n);
			}
			m = med3(x, C, l, m, n); // Mid-size, med of 3
		}
		T v = x[m];

		// Establish Invariant: v* (<v)* (>v)* v*
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && C.compare(x[b], v) <= 0) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && C.compare(x[c], v) >= 0) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}

		// Swap partition elements back to middle
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);

		// Recursively sort non-partition-elements
		if ((s = b - a) > 1)
			quicksort(x, C, off, s);
		if ((s = d - c) > 1)
			quicksort(x, C, n - s, s);
	}
	
	public static <T extends Comparable<T>> void quicksort(T x[]) {
		quicksort(x, 0, x.length);
	}

	public static <T extends Comparable<T>> void quicksort(T x[], int off,
			int len) {
		// Insertion sort on smallest arrays
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1].compareTo(x[j]) > 0; j--)
					swap(x, j, j - 1);
			return;
		}

		// Choose a partition element, v
		int m = off + (len >> 1); // Small arrays, middle element
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) { // Big arrays, pseudomedian of 9
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n); // Mid-size, med of 3
		}
		T v = x[m];

		// Establish Invariant: v* (<v)* (>v)* v*
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b].compareTo(v) <= 0) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c].compareTo(v) >= 0) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}

		// Swap partition elements back to middle
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);

		// Recursively sort non-partition-elements
		if ((s = b - a) > 1)
			quicksort(x, off, s);
		if ((s = d - c) > 1)
			quicksort(x, n - s, s);
	}
	
	private static <T> int med3(T x[], Comparator<T> C, int a, int b, int c) {
		return (C.compare(x[a], x[b]) < 0 ? (C.compare(x[b], x[c]) < 0 ? b : 
				C.compare(x[a], x[c]) < 0 ? c : a) : (C.compare(x[b], x[c]) > 0 ? b
		         : C.compare(x[a], x[c]) > 0 ? c : a));
	}

	private static <T extends Comparable<T>>int med3(T x[], int a, int b, int c) {
		return (x[a].compareTo(x[b]) < 0 ? (x[b].compareTo(x[c]) < 0 ? b : x[a]
				.compareTo(x[c]) < 0 ? c : a) : (x[b].compareTo(x[c]) > 0 ? b
				: x[a].compareTo(x[c]) > 0 ? c : a));
	}

	private static <T> void vecswap(T x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}

	/**
	 * Swaps x[a] with x[b].
	 */
	private static <T> void swap(T x[], int a, int b) {
		T t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

}
