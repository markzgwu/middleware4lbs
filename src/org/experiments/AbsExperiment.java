package org.experiments;

import java.util.ArrayList;

public abstract class AbsExperiment {
	protected final StringBuffer outputMessages = new StringBuffer();
	protected final ArrayList<String> outputResults = new ArrayList<String>();
	protected void output(String mode){
		switch (mode){
		case "1":
			for(String result:outputResults){
				System.out.println(result);
			}
			break;
		case "2":
			System.out.print(outputMessages);
			break;
		}
		
	}
}
