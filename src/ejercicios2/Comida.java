package ejercicios2;

public class Comida {
	
	private String nombre;
	private float precio;
	private String descripcion;
	private String calorias;
	
	
	public Comida(String nombre, float precio, String descripcion,String calorias) {
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.calorias=calorias;
	}


	public String getNombre() {
		return nombre;
	}


	public float getPrecio() {
		return precio;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public String getCalorias() {
		return calorias;
	}
	
	

}
