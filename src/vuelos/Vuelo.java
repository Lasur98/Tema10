package vuelos;

import java.util.Arrays;

public class Vuelo {
	
	private String id;
	private String origen;
	private String destino;
	private int[] hora;
	
	public Vuelo(String id, String origen, String destino, int[] hora) 
	{

		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.hora = hora;
	}
	
	public Vuelo()
	{
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int[] getHora() {
		return hora;
	}

	public void setHora(int[] hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "Vuelo [id=" + id + ", origen=" + origen + ", destino="
				+ destino + ", hora=" + Arrays.toString(hora) + "]";
	}
	
	

}
