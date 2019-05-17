package ejercicios2;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class GestorAhorcado {
	
	private final static String nomFich="palabras.xml";
	private Document doc;
	
	
	
	
	public void parsear()
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
	}

	public String generarPalabra(String palabra,int cantLetras)
	{
		Element eAhorcado=doc.getRootElement();
		List lstGrupos=eAhorcado.getChildren();
		Iterator it=lstGrupos.iterator();
		while(it.hasNext())
		{
			Element eGrupo=(Element) it.next();
			if(Integer.parseInt(eGrupo.getAttributeValue("numletras"))==cantLetras)
			{
				List<Element> cantPalabras=eGrupo.getChildren("pal");
				int aleatorio=(int)(Math.random()*cantPalabras.size());
				for(int i=0;i<cantPalabras.size();i++)
				{
					if(aleatorio==i)
					{
						Element palabra2=cantPalabras.get(i);
						palabra=palabra2.getText();
					}
				}
			}
			
		}
		
		return palabra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doc == null) ? 0 : doc.hashCode());
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
		GestorAhorcado other = (GestorAhorcado) obj;
		if (doc == null) {
			if (other.doc != null)
				return false;
		} else if (!doc.equals(other.doc))
			return false;
		return true;
	}
	
	
	
}
