package fr.tse.fi2.hpp.labs.benchmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.dispatcher.LoadFirstDispatcher;
import fr.tse.fi2.hpp.labs.main.MainNonStreaming;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;
import fr.tse.fi2.hpp.labs.queries.impl.lab4.RouteMembershipProcessor;
import fr.tse.fi2.hpp.labs.queries.impl.lab4.RouteMembershipProcessor2;


@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class MyBenchmark {

	//x1: -73.98715 /y1: 40.732376 /x2: -73.996155 /y2: 40.75883 /li: FDAE3E515EB1419900CF1D5E83073FB2
	private float x1 = (float) -73.98715;
	private float y1= (float) 40.732376;
	private float x2= (float) -73.996155;
	private float y2= (float) 40.75883;
	private String l1= "FDAE3E515EB1419900CF1D5E83073FB2";

	private DebsRecord recordTestFaux = new DebsRecord("", "", 4, 4, 4, 4, 4, 4, 4, 4, "", 4, 4, 4, 4, 4, 4, false);
	private DebsRecord recordTestVrai = new DebsRecord("", l1, 4, 4, 4, 4, x1, y1, x2, y2, "", 4, 4, 4, 4, 4, 4, false);
	final static Logger logger = LoggerFactory
			.getLogger(MainNonStreaming.class);

	@Setup
	public void init(){
		// Init query time measure
		QueryProcessorMeasure measure = new QueryProcessorMeasure();
		// Init dispatcher and load everything
		LoadFirstDispatcher dispatch = new LoadFirstDispatcher(
				"src/main/resources/data/1000Records.csv");
		logger.info("Finished parsing");
		// Query processors
		List<AbstractQueryProcessor> processors = new ArrayList<>();

		// Add you query processor here
		//processors.add(new SimpleQuerySumEvent(measure));
		//processors.add(new NaiveAverage(measure));
		//processors.add(new IncrementalAverage(measure));	
		processors.add(new RouteMembershipProcessor2(measure));	


		// Register query processors
		for (AbstractQueryProcessor queryProcessor : processors) {
			dispatch.registerQueryProcessor(queryProcessor);
		}
		// Initialize the latch with the number of query processors
		CountDownLatch latch = new CountDownLatch(processors.size());
		// Set the latch for every processor
		for (AbstractQueryProcessor queryProcessor : processors) {
			queryProcessor.setLatch(latch);
		}
		for (AbstractQueryProcessor queryProcessor : processors) {
			Thread t = new Thread(queryProcessor);
			t.setName("QP" + queryProcessor.getId());
			t.start();
		}
		// Start everything dispatcher first, not as a thread
		dispatch.run();
		logger.info("Finished Dispatching");
		// Wait for the latch
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("Error while waiting for the program to end", e);
		}
		// Output measure and ratio per query processor
		measure.setProcessedRecords(dispatch.getRecords());
		measure.outputMeasure();
	}


	@Benchmark
	public void testMethod1() {
		System.out.println("Route find : " + RouteMembershipProcessor2.checkroute(recordTestVrai));
	}

	//    @Benchmark
	//    public float testMethod2() {
	//       long sum=0L;
	//       for(Integer integer : liste2){
	//    	   sum += integer;
	//       }
	//       float mean = sum / n;
	//       return mean;
	//    }

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
		.include(MyBenchmark.class.getSimpleName())
		.build();
		new Runner(opt).run();
	}

}
