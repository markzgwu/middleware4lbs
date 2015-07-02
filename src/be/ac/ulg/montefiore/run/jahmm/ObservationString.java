package be.ac.ulg.montefiore.run.jahmm;

import java.text.NumberFormat;
import java.util.*;

//自定义的类

/**
 * This class implements observations whose values are taken out of a finite
 * set implemented as an enumeration.
 */
public class ObservationString
extends Observation
{
	/**
	 * This observation value.
	 */
	public final ArrayList<String> value;
	
	
	public ObservationString(ArrayList<String> value)
	{
		this.value = value;
	}
	
	
	public String toString()
	{
		return value.toString();
	}
	
	
	public String toString(NumberFormat nf)
	{
		return toString();
	}
}
