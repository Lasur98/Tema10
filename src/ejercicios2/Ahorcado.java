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
	private final static String nomFich="palabras.xml";
	private Document doc;
	
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
		try 
		{
			SAXBuilder builder=new SAXBuilder();
			doc=builder.build(nomFich);
		} 
		catch (JDOMException e) 
		{
			System.out.println("Documento xml erroneo");
		} 
		catch (IOException e) 
		{
			System.out.println("Fichero invalido");
		}
		
		Element eAhorcado=doc.getRootElement();
		List lstGrupos=eAhorcado.getChildren();
		Iterator it=lstGrupos.iterator();
		while(it.hasNext())
		{
			Element eGrupo=(Element) it.next();
			if(Integer.parseInt(eGrupo.getAttributeValue("numletras"))==cantLetras)
			{
				List<Element> cantPalabras=eGrupo.getChildren("pal");
				int aleatorio=(int)(1+Math.random()*cantPalabras.size());
				for(int i=1;i<cantPalabras.size();i++)
				{
					if(aleatorio==i)
					{
						Element palabra=cantPalabras.get(i-1);
						PALABRA=palabra.getText();
					}
				}
			}
			
		}
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
		for(int i=0;i<PALABRA.length();i++)
		{
			if(PALABRA.charAt(i)==letra)
			{
				respuesta=respuesta.substring(0,i);
				respuesta+=letra;
				for(int i2=respuesta.length();i2<PALABRA.length();i2++)
				{
					respuesta+="-";
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
		result = prime * result + ((doc == null) ? 0 : doc.hashCode());
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
		if (doc == null) {
			if (other.doc != null)
				return false;
		} else if (!doc.equals(other.doc))
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
	public static void main(String[] args) {
		
		/* Pruebas de los metodos---->Todo Ok
		Ahorcado a1=new Ahorcado(6);
		a1.tirar('a');
		System.out.println(a1.completo());
		System.out.println(a1.respuestaToString());*/
	}

}
