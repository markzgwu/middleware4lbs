package org.assistants.dbsp.jsonprocessor;

public abstract class AbsContent {
	public final String OriginalContent;
	public AbsContent(final String json_input_str){
		OriginalContent = json_input_str;
	}
}
