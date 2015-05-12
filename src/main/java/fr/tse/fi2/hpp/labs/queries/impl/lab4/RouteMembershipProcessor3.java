package fr.tse.fi2.hpp.labs.queries.impl.lab4;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

import fr.tse.fi2.hpp.labs.beans.DebsRecord;
import fr.tse.fi2.hpp.labs.beans.measure.QueryProcessorMeasure;
import fr.tse.fi2.hpp.labs.queries.AbstractQueryProcessor;

public class RouteMembershipProcessor3 extends AbstractQueryProcessor {

	private static DebsRecord lastRec;

	public RouteMembershipProcessor3(QueryProcessorMeasure measure) {
		super(measure);
		// TODO Auto-generated constructor stub
	}

	static Funnel<DebsRecord> personFunnel = new Funnel<DebsRecord>() {
		@Override
		public void funnel(DebsRecord person, PrimitiveSink into) {
			into.putFloat(person.getPickup_latitude())
			.putFloat(person.getPickup_longitude())
			.putFloat(person.getDropoff_latitude())
			.putFloat(person.getDropoff_longitude())
			.putString(person.getHack_license(), Charsets.UTF_8);
		}
	};

	private static BloomFilter<DebsRecord> rec = BloomFilter.create(personFunnel, 1000, 0.001);
	
	@Override
	protected void process(DebsRecord record) {
		// TODO Auto-generated method stub
		
		rec.put(record);
		
		lastRec = record;
	}
	
	public static DebsRecord getRec(){ return lastRec; }

	public static boolean checkroute(DebsRecord recherche)
	{
		if (rec.mightContain(recherche)) {
			return true;
		}
		return false;
	}

}