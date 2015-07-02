package org.projects.providers;

public final class ProviderMYSQL extends AbsProvider implements IDBProvider{
	final WorkerMysql worker;
	public ProviderMYSQL(WorkerMysql worker){
		this.worker = worker;
	}
	@Override
	public String retrieval(String cellid) {
		String thiscellid = "123dbaffdfd667a7233bf210";
		String output = "Data("+cellid+")="+worker.read(thiscellid);
		/*
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println(output);
		return output;
	}

}
