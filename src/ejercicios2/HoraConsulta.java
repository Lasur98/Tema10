package ejercicios2;

public class HoraConsulta {
	
	private int hora;
	private int minutos;
	
	public HoraConsulta(int hora, int minutos) {
		
		this.hora = hora;
		this.minutos = minutos;
	}

	@Override
	public String toString() {
		return hora+":"+minutos;
	}
	
	

}
