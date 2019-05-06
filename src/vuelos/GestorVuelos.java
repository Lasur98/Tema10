package vuelos;

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

public class GestorVuelos {
	
	private Document doc;
	private String nomFich;
	
	
	public GestorVuelos(String nomFich)
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
	
	public ArrayList<String> destinosDesde(String origen)
	{
	
		ArrayList<String> destinos=new ArrayList<String>();
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		Iterator it=lstVuelos.iterator();
		while(it.hasNext())
		{
			Element eVuelo=(Element) it.next();
			Element eOrigen=eVuelo.getChild("origen");
			if(eOrigen.getText().equals(origen))
			{
				destinos.add(eVuelo.getChildText("destino"));
			}
		}
		
		
		return destinos;
	}
	
	
	private Vuelo crearVuelo(Element eVuelo)
	{
		Vuelo v=new Vuelo();
		v.setId(eVuelo.getAttributeValue("id"));
		v.setOrigen(eVuelo.getChildText("origen"));
		v.setDestino(eVuelo.getChildText("destino"));
		int[] hora=new int[2];
		
		String strHora=eVuelo.getChildText("hora");
		String[] partes=strHora.split(":");
		hora[0]=Integer.parseInt(partes[0]);
		hora[1]=Integer.parseInt(partes[1]);
		v.setHora(hora);
		
		return v;
	}
	
	public List<Vuelo> vuelosPosterioresA(String horaDesde)
	{
		int horaDesdeInt=str_to_timestamp(horaDesde);
		
		ArrayList<Vuelo> vuelos=new ArrayList<Vuelo>();
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		for(Element eVuelo:lstVuelos)
		{
			String strHora=eVuelo.getChildText("hora");
			if(str_to_timestamp(strHora)>horaDesdeInt)
			{
				vuelos.add(crearVuelo(eVuelo));
			}
		}
		
		
		return vuelos;
	}
	
	private static int str_to_timestamp(String strHora)
	{
		//hora:minutos
		String[] partes=strHora.split(":");
		int h=Integer.parseInt(partes[0]);
		int m=Integer.parseInt(partes[1]);
		
		return (h*60+m);
	}
	
	public void nuevoVuelo(Vuelo v)
	{
		Element eVuelos=doc.getRootElement();
		
		Element eVuelo=new Element("vuelo");
		eVuelo.setAttribute(new Attribute("id",v.getId()));
		
		Element eOrigen=new Element("origen");
		eOrigen.setText(v.getOrigen());
		
		Element eDestino=new Element("destino");
		eDestino.setText(v.getDestino());
		
		Element eHora=new Element("hora");
		String strHora=(v.getHora()[0]<10)?("0"+v.getHora()[0]):v.getHora()[0]+":"+
		((v.getHora()[1]<10)?("0"+v.getHora()[1]):(""+v.getHora()[1]));
		
		eHora.setText(strHora);
		
		eVuelo.addContent(eOrigen);
		eVuelo.addContent(eDestino);
		eVuelos.addContent(eVuelo);
		
		
		grabar();
		
	}

	public boolean borrarVuelo(String id)
	{
		ArrayList<Vuelo> vuelos=new ArrayList<Vuelo>();
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		for(Element eVuelo:lstVuelos)
		{
			if(eVuelo.getAttributeValue("id").equals(id))
			{
				//Borrar eVuelo
				eVuelos.removeContent(eVuelo);
				grabar();
				return true;
			}
		}
		return false;
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
	
	private void verFich()
	{
		try 
		{
			XMLOutputter serializador=new XMLOutputter(Format.getPrettyFormat());
			serializador.output(doc,System.out);
		} 
		catch (IOException e) 
		{
			System.err.println("Fichero invalido");
		}
	}
	
	public void crearDocVuelosA(String nomFichNuevo,String destino)
	{
		Element eVuelos2=new Element("vuelos");
		
		Element eVuelos1=doc.getRootElement();
		List<Element> lstVuelos=eVuelos1.getChildren();
		for(Element eVuelo1:lstVuelos)
		{
			if(eVuelo1.getChildText("destino").equals(destino))
			{
				//colgar eVuelo1 de eVuelo2
				Element eVuelo2=eVuelo1.clone();
				eVuelos2.addContent(eVuelo2);
			}
			
		}
		Document doc2=new Document(eVuelos2);
		//Grabar documento nuevo
		try 
		{
			XMLOutputter serializador=new XMLOutputter(Format.getPrettyFormat());
			serializador.output(doc2,new FileWriter(nomFichNuevo));
		} 
		catch (IOException e) 
		{
			System.err.println("Fichero invalido");
		}
	}
	
	//Modificar el xml original para que la hora pase a ser un atributo
	//<vuelo id="" hora="09:30">
	
	public void cambioXML()
	{
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		for(Element eVuelo:lstVuelos)
		{
			Element eHora=eVuelo.getChild("hora");
			String strHora=eHora.getText();
			
			//Crear atributo y añadirselo a <vuelo>
			Attribute aHora=new Attribute("hora", strHora);
			eVuelo.setAttribute(aHora);
			eVuelo.removeContent(eHora);
			
			grabar();
			//verFich();
		}
	}
	
	public void transpasarInfoACompanyias(String nomFichCompanyias)
	{
		Element eVuelos=doc.getRootElement();
		List<Element> lstVuelos=eVuelos.getChildren();
		for(Element eVuelo:lstVuelos)
		{
			Attribute aId=new Attribute("id",eVuelo.getAttributeValue("id"));
			String strId=aId.toString();
		}
	}
	
	public static void main(String[] args) {
		
		GestorVuelos gv=new GestorVuelos("vuelos.xml");
		/*
		List<String> destinos=gv.destinosDesde("Miranda");
		
		if(destinos.size()==0)
		{
			System.out.println("Sin vuelos");
		}
		else
		{
			System.out.println(destinos); //Como prueba
		}
		
		List<Vuelo> vuelos=gv.vuelosPosterioresA("12:30");
		if(vuelos.size()==0)
		{
			System.out.println("Sin vuelos");
		}
		else
		{
			for(Vuelo v:vuelos)
			{
				System.out.println(v);
			}
		}
		
		gv.nuevoVuelo(new Vuelo("ib4000","Miranda","Madrid",new int[]{10,45}));
		gv.nuevoVuelo(new Vuelo("ib4000","Miranda","Madrid",new int[]{10,45}));
		gv.nuevoVuelo(new Vuelo("ib4000","Boston","New York",new int[]{3,50}));
		
		if(!gv.borrarVuelo("ib1000"))
			System.out.println("No hay vuelo a borrar");
		else
		{
			gv.verFich();
		}
		
		gv.crearDocVuelosA("vuelosAMadrid.xml","Madrid");
		
		gv.cambioXML();
		
		*/
		gv.transpasarInfoACompanyias("companyas.xml");
	}
	
}
