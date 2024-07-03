package it.polito.tdp.crimes.model;

import java.util.Objects;

public class Arco {
	private String t1;
	private String t2;
	private int peso;
	public Arco(String t1, String t2, int peso) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.peso = peso;
	}
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(peso, t1, t2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return peso == other.peso && Objects.equals(t1, other.t1) && Objects.equals(t2, other.t2);
	}
	@Override
	public String toString() {
		return  t1 + " <---> " + t2 + "     " + peso ;
	}
	

}
