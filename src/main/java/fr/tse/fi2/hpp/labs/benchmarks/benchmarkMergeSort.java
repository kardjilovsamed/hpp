package fr.tse.fi2.hpp.labs.benchmarks;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import fr.tse.fi2.hpp.labs.utils.MergeSortMono;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class benchmarkMergeSort {

	int[] liste;
	
	@Param({"100000", "1000000", "10000000"})
	private int taille;

	
	@Setup
	public void init(){
		liste = MergeSortMono.genererAleatoire(taille);
	}

	@Benchmark
	public void testMethod1() {
		int[] listeTrie = MergeSortMono.trier(liste);
		System.out.println("Fin du tri");
	}
	
	@Benchmark
	public void testMethod2() {
		int[] listeTrie = MergeSortMono.trierInsertionSort(liste);
		System.out.println("Fin du tri");
	}
	
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
		.include(benchmarkMergeSort.class.getSimpleName())
		.build();
		new Runner(opt).run();
	}

}
