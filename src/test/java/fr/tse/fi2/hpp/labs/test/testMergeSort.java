package fr.tse.fi2.hpp.labs.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

import fr.tse.fi2.hpp.labs.utils.MergeSortMono;
import fr.tse.fi2.hpp.labs.utils.MergeSortMulti;

public class testMergeSort {

	int[] tab;
	
	public testMergeSort() {
		tab = MergeSortMono.genererAleatoire(10000000);
	}

	@Test
	public void test() {
		
		int[] tabCopy;
		int[] tabCopy2;
		
		tabCopy = tab;
		tabCopy2 = tab;
		
		Arrays.sort(tabCopy);
		
		int[] listeTrieFusion = MergeSortMono.trier(tabCopy2);
		
		assertArrayEquals(tabCopy, listeTrieFusion);
	}
	
	@Test
	public void test2() {
		
		int[] tabCopy;
		int[] tabCopy2;
		
		tabCopy = tab;
		tabCopy2 = tab;
		
		Arrays.sort(tabCopy);
		
		int[] listeTrieFusion = MergeSortMono.trierInsertionSort(tabCopy2);
		
		assertArrayEquals(tabCopy, listeTrieFusion);
	}
	
	@Test
	public void test3() {
		
		int[] tabCopy;
		int[] tabCopy2;
		
		tabCopy = tab;
		tabCopy2 = tab;
		
		Arrays.sort(tabCopy);
		
		MergeSortMulti tri1 = new MergeSortMulti(tabCopy2);
		int cores = Runtime.getRuntime().availableProcessors();
		ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
		
		int[] listeTrieFusion = forkJoinPool.invoke(tri1);
		
		assertArrayEquals(tabCopy, listeTrieFusion);
	}

}
