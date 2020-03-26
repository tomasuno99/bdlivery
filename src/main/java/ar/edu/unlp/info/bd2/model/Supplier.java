package ar.edu.unlp.info.bd2.model;

public class Supplier {
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
	public Supplier createSupplier(	String name, String cuil, String adress, Float coordx, Float coordy) {
		this.name= name;
		this.cuil = cuil;
		this.address = adress;
		this.coordX = coordx;
		this.coordY = coordy;
		
		return this;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public String getAdress() {
		return address;
	}
	public void setAdress(String adress) {
		this.address = adress;
	}
	public Float getCoordx() {
		return coordX;
	}
	public void setCoordx(Float coordx) {
		this.coordX = coordx;
	}
	public Float getCoordy() {
		return coordY;
	}
	public void setCoordy(Float coordy) {
		this.coordY = coordy;
	}
	
	
	
}
