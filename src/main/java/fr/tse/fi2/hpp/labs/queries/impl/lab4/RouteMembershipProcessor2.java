package fr.tse.fi2.hpp.labs.queries.impl.lab4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;


public class RouteMembershipProcessor2 extends AbstractQueryProcessor {

	public static BitSet listeHashRoute = new BitSet(14378);
	private static DebsRecord lastRec;

	public RouteMembershipProcessor2(QueryProcessorMeasure measure) {
		super(measure);
	}

	@Override
	protected void process(DebsRecord record) {

		String result = getStringConca(record);
		
		//On Hash "result" 10 fois avec une valeur differante à chaque fois
		for(int i =0 ; i<10 ; i++){
			String resultHash = result + i;
			
			BigInteger bigIndex = new BigInteger(SHA3Util.digest(resultHash), 16);
			BigInteger modulo = new BigInteger("14378");
			
			int index = bigIndex.mod(modulo).intValue();
			
			listeHashRoute.set( index );
		}
		lastRec = record;
	}

	public static DebsRecord getRec(){ return lastRec; }
	
	public static String getStringConca (DebsRecord record){

		String result = null;
		result += record.getPickup_longitude();
		result += record.getPickup_latitude();
		result += record.getDropoff_longitude();
		result += record.getDropoff_latitude();
		result += record.getHack_license();
		
		return result;
	}
	
	
	public static boolean checkroute(DebsRecord rec){
		String result = getStringConca(rec);
		
		for(int i =0 ; i<10 ; i++){
			String resultHash = result + i;
			
			BigInteger bigIndex = new BigInteger(SHA3Util.digest(resultHash), 16);
			BigInteger modulo = new BigInteger("14378");
			int index = bigIndex.mod(modulo).intValue();
			
			if(listeHashRoute.get(index) == false){
				return false;	
			}
		}
		
		return true;		
	}
}