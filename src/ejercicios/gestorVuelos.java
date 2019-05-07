package ejercicios;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class gestorVuelos {

	private Document doc;
	private String nomFich;
	
	
	public gestorVuelos(String nomFich)
	{
		this.nomFich=nomFich;
		try {
			SAXBuilder builder=new SAXBuilder();
			doc=builder.build(nomFich);
		} catch (JDOMException e) 
		{
			System.err.println("");
		} catch (IOException e) 
		{
			System.err.println("Fichero invalido");
		}
				
	}
	
	public ArrayList<String> destinos()
	{
	
		ArrayList<String> destinos=new ArrayList<String>();
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		Iterator it=lstVuelos.iterator();
		while(it.hasNext())
		{
			Element eVuelo=(Element) it.next();
			if(!destinos.contains(eVuelo.getChildText("destino")))
				destinos.add(eVuelo.getChildText("destino"));
		}
		
		
		return destinos;
	}

	public ArrayList<String> origenes(String destino)
	{
		ArrayList<String> origenes=new ArrayList<String>();
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		Iterator it=lstVuelos.iterator();
		while(it.hasNext())
		{
			Element eVuelo=(Element) it.next();
			if(eVuelo.getChildText("destino").equals(destino))
				origenes.add(eVuelo.getAttributeValue("id")+": "+eVuelo.getChildText("origen")+" "+
						eVuelo.getChildText("hora"));
		}
		
		return origenes;
	
	}
	
	private void grabar()
	{
		
		try 
		{
			XMLOutputter serializador=new XMLOutputter(Format.getPrettyFormat());
			serializador.output(doc,new FileWriter(nomFich));
		} 
		catch (IOException e) 
		{
			System.err.println("Fichero invalido");
		}
	}
	
	public void compraBillete(String id)
	{
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		for(Element eVuelo:lstVuelos)
		{
			if(eVuelo.getAttribute("id").equals(id))
			{
				Element eHora=eVuelo.getChild("vendidos");
				int strVenta=Integer.parseInt(eHora.getText())+1;
				String strVendido=String.valueOf(strVenta);
				
				//Modificar atributo de vendidos
				Attribute aVen=new Attribute("vendidos", strVendido);
				eVuelo.setAttribute(aVen);
				
				grabar();
			}
			break;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doc == null) ? 0 : doc.hashCode());
		result = prime * result + ((nomFich == null) ? 0 : nomFich.hashCode());
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
		gestorVuelos other = (gestorVuelos) obj;
		if (doc == null) {
			if (other.doc != null)
				return false;
		} else if (!doc.equals(other.doc))
			return false;
		if (nomFich == null) {
			if (other.nomFich != null)
				return false;
		} else if (!nomFich.equals(other.nomFich))
			return false;
		return true;
	}

	public static void main(String[] args) {
		
		gestorVuelos gv=new gestorVuelos("vuelos.xml");
		System.out.println(gv.origenes("Madrid"));
	}
}
