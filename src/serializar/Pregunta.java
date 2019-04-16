package serializar;

import java.util.Arrays;

public class Pregunta {
	
	private String pregunta;
	private String[] respuestas;
	private int iCorrecta;
	
	
	public Pregunta(String pregunta, String[] respuestas, int iCorrecta) {
		this.pregunta = pregunta;
		this.respuestas = respuestas;
		this.iCorrecta = iCorrecta;
	}


	@Override
	public String toString() {
		return pregunta;
	}


	public String getPregunta() {
		return pregunta;
	}


	public String[] getRespuestas() {
		return respuestas;
	}


	public int getiCorrecta() {
		return iCorrecta;
	}
	
	

}
