package fr.tse.fi2.hpp.labs.queries.impl.lab4;

import java.util.ArrayList;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class RouteMembershipProcessor extends AbstractQueryProcessor {

	private static ArrayList<DebsRecord> listeRoute = new ArrayList<DebsRecord>();
	private static DebsRecord recTest;
	private int count = 0;

	public RouteMembershipProcessor(QueryProcessorMeasure measure) {
		super(measure);
	}

	@Override
	protected void process(DebsRecord record) {
		listeRoute.add(record);

		count++;
		
		if(count==15){
			recTest = record;
		}
		/*System.out.println("x1: " + record.getPickup_longitude()  + " /y1: " + record.getPickup_latitude() + 
				" /x2: " + record.getDropoff_longitude() + " /y2: " + record.getDropoff_latitude() + " /li: " + record.getHack_license());
		 */
	}

	public static DebsRecord getRec(){ return recTest; }

	public static boolean checkroute(DebsRecord rec){

		for(int i=0; i<listeRoute.size(); i++){
			if(        (listeRoute.get(i).getPickup_longitude() 	== rec.getPickup_longitude())
					&& (listeRoute.get(i).getPickup_latitude()		== rec.getPickup_latitude())
					&& (listeRoute.get(i).getDropoff_longitude() 	== rec.getDropoff_longitude())
					&& (listeRoute.get(i).getDropoff_latitude() 	== rec.getDropoff_latitude())
					&& (listeRoute.get(i).getHack_license().equals(rec.getHack_license()))	)
			{
				return true;
			} 
		}
		return false;
	}

}