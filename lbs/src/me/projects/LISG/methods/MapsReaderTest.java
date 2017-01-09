package me.projects.LISG.methods;

public final class MapsReaderTest {

	public static void main(String[] args) {
		{
		MapsReaderInterface m = new MapsReaderV1();
		m.worker();
		String cid = "0000";
		String catalog = "others";
		System.out.println(m.queryrisk(cid, catalog));
		}
		{
		MapsReaderInterface m = new MapsReaderV2();
		m.worker();
		String cid = "0000";
		String catalog = "others";
		System.out.println(m.queryrisk(cid, catalog));
		}
	}

}
