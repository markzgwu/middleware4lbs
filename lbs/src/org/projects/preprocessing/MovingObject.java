package org.projects.preprocessing;

public final class MovingObject {
	final String Object_Id;
	final String Timestamp;
	final String Type;
	final String Lat;
	final String Lng;

	public MovingObject(final String Object_Id,
			final String Timestamp,
			final String Type,
			final String Lat,
			final String Lng){
		this.Object_Id = Object_Id;
		this.Timestamp = Timestamp;
		this.Type = Type;
		this.Lat = Lat;
		this.Lng = Lng;
	}
	
	public MovingObject(final String oneline){
		String[] e = oneline.split(" ");
		this.Object_Id = e[0];
		this.Timestamp = e[1];
		this.Type = e[2];
		this.Lat = e[3];
		this.Lng = e[4];	
	}	
	
	public String getObject_Id() {
		return Object_Id;
	}

	public String getTimestamp() {
		return Timestamp;
	}

	public String getType() {
		return Type;
	}

	public String getLat() {
		return Lat;
	}

	public String getLng() {
		return Lng;
	}
	
}
