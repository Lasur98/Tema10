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

import vuelos.Vuelo;

public class GestorVuelos2 {

	private Document doc;
	private String nomFich;
	
	public GestorVuelos2(String nomFich)
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
	
	public ArrayList<String> origenes()
	{
		ArrayList<String> origenes=new ArrayList<String>();
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		Iterator it=lstVuelos.iterator();
		while(it.hasNext())
		{
			Element eVuelo=(Element) it.next();
			if(!origenes.contains(eVuelo.getChildText("origen")))
				origenes.add(eVuelo.getChildText("origen"));
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
	
	public boolean anyadeVuelo(String origen,String destino,String id,String hora)
	{
		Element eVuelos=doc.getRootElement();
		
		Element eVuelo=new Element("vuelo");
		eVuelo.setAttribute(new Attribute("id",id));
		
		Element eOrigen=new Element("origen");
		eOrigen.setText(origen);
		
		Element eDestino=new Element("destino");
		eDestino.setText(destino);
		
		Element eHora=new Element("hora");
		String strHora=(hora);
		
		eHora.setText(strHora);
		
		eVuelo.addContent(eOrigen);
		eVuelo.addContent(eDestino);
		eVuelos.addContent(eVuelo);
		
		if(!id.equals(eVuelo.getAttributeValue("id")) || (!origen.equals(eVuelo.getChildText("origen"))
				&& !destino.equals(eVuelo.getChildText("destino")) && !hora.equals(eVuelo.getChildText("hora"))))
		{
			grabar();
			return true;
		}
		else
			return false;
		
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
		GestorVuelos2 other = (GestorVuelos2) obj;
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
	
	
	
}
