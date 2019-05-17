package ejercicios2;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Ahorcado {
	
	private static String PALABRA="cristalera";
	private String respuesta="";
	private int totalVidas;
	private int vidasRest;
	private GestorAhorcado ga;
	
	public Ahorcado()
	{
		for(int i=0;i<PALABRA.length();i++)
		{
			respuesta+="-";
		}
		totalVidas=PALABRA.length()/2;
		vidasRest=totalVidas;
	}
	public Ahorcado(int cantLetras)
	{
		ga=new GestorAhorcado();
		
		ga.parsear();
		PALABRA=ga.generarPalabra(PALABRA,cantLetras);
		
		for(int i=0;i<PALABRA.length();i++)
		{
			respuesta+="-";
		}
		totalVidas=PALABRA.length()/2;
		vidasRest=totalVidas;
		
		
	}
	
	public boolean tirar(char letra)
	{
		boolean acertado=false;
		String palabraAux=PALABRA.toUpperCase();
		int ultimaLetra=0;
		for(int i=0;i<palabraAux.length();i++)
		{
			if(palabraAux.charAt(i)==letra)
			{
				if(ultimaLetra>i)
				{
					respuesta=respuesta.substring(0,i);
					respuesta+=letra;
					for(int i3=respuesta.charAt(ultimaLetra);i3<PALABRA.length();i3++)
					{
						respuesta+="-";
					}
				}
				else
				{
					for(int i2=respuesta.length();i2<PALABRA.length();i2++)
					{
						respuesta+="-";
					}
					ultimaLetra=i;
				}
				
				acertado=true;
			}
		}
		if(acertado==false)
		{
			vidasRest--;
		}
		return acertado;
	}
	
	public boolean completo()
	{
		if(respuesta.equals(PALABRA))
			return true;
		else
			return false;
	}
	
	public String respuestaToString()
	{
		String respuestaString="";
		for(int i=0;i<respuesta.length();i++)
		{
			respuestaString+=respuesta.charAt(i)+" ";
		}
		return respuestaString;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ga == null) ? 0 : ga.hashCode());
		result = prime * result
				+ ((respuesta == null) ? 0 : respuesta.hashCode());
		result = prime * result + totalVidas;
		result = prime * result + vidasRest;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ahorcado other = (Ahorcado) obj;
		if (ga == null) {
			if (other.ga != null)
				return false;
		} else if (!ga.equals(other.ga))
			return false;
		if (respuesta == null) {
			if (other.respuesta != null)
				return false;
		} else if (!respuesta.equals(other.respuesta))
			return false;
		if (totalVidas != other.totalVidas)
			return false;
		if (vidasRest != other.vidasRest)
			return false;
		return true;
	}
	public static String getPALABRA() {
		return PALABRA;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public int getVidasRest() {
		return vidasRest;
	}
	public int getTotalVidas() {
		return totalVidas;
	}
	public static void main(String[] args) {
		
		/* Pruebas de los metodos---->Todo Ok
		Ahorcado a1=new Ahorcado(6);
		a1.tirar('a');
		System.out.println(a1.completo());
		System.out.println(a1.respuestaToString());*/
	}

}
