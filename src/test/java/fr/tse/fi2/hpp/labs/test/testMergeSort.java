package fr.tse.fi2.hpp.labs.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import fr.tse.fi2.hpp.labs.utils.MergeSortMono;

public class testMergeSort {

	@Test
	public void test() {
		
		int[] tab;
		int[] tab2;
		
		tab = MergeSortMono.genererAleatoire(10000000);
		tab2 = tab;
		
		Arrays.sort(tab);
		
		int[] listeTrieFusion = MergeSortMono.trier(tab2);
		
		assertArrayEquals(tab, listeTrieFusion);
	}
	
	@Test
	public void test2() {
		
		int[] tab;
		int[] tab2;
		
		tab = MergeSortMono.genererAleatoire(10000000);
		tab2 = tab;
		
		Arrays.sort(tab);
		
		int[] listeTrieFusion = MergeSortMono.trierInsertionSort(tab2);
		
		assertArrayEquals(tab, listeTrieFusion);
	}

}
