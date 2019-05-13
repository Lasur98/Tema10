package ejercicios2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class GestionMenu {
	
	private String nomFich;
	private Document doc;
	
	public GestionMenu(String nomFich)
	{
		this.nomFich=nomFich;
	}
	
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
	
	public void ver()
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

	public String consultarPlato(String plato)
	{
		String comida="";
		Element eMenu=doc.getRootElement();
		List<Element> lstComidas=eMenu.getChildren();
		Iterator it=lstComidas.iterator();
		while(it.hasNext())
		{
			Element eComida=(Element) it.next();
			Element eNombre=eComida.getChild("nombre");
			if(eNombre.getText().equals(plato))
				comida+=eComida.getChildText("descripcion")+" con un precio de "+eComida.getChildText("precio")+
				"€ y "+eComida.getChildText("calorias")+" calorias";
		}

		return comida;
	}
	
	public ArrayList<String> consultaPorCalorias(int calorias)
	{
		ArrayList<String> comidas=new ArrayList<String>();
		Element eMenu=doc.getRootElement();
		List<Element> lstComidas=eMenu.getChildren();
		Iterator it=lstComidas.iterator();
		while(it.hasNext())
		{
			Element eComida=(Element) it.next();
			Element eCaloria=eComida.getChild("calorias");
			int caloria_plato=Integer.parseInt(eCaloria.getText());
			if(caloria_plato<calorias)
				comidas.add(eComida.getChildText("nombre"));
		}
		
		return comidas;
	}

	public void platosEconomicos(float limiteInf,float limiteSup)
	{
		System.out.println("Que nombre desea poner al nuevo documento: ");
		String nomDocu=Consola.leeString();
		
		Element eMenu=new Element("menu");
		Document docu=new Document(eMenu);
		
		Element eMenu2=doc.getRootElement();
		List<Element> lstComidas=eMenu2.getChildren();
		Iterator it=lstComidas.iterator();
		while(it.hasNext())
		{
			Element eComida=(Element) it.next();
			float precio=Float.parseFloat(eComida.getChildText("precio"));
			if(precio>=limiteInf && precio<=limiteSup)
			{	
				Element eComida2=new Element("comida");
				Element eNombre=new Element("nombre");
				eNombre.setText(eComida.getChildText("nombre"));
				eNombre.setAttribute("precio",eComida.getChildText("precio"));
				Element eDescripcion=new Element("descripcion");
				eDescripcion.setText(eComida.getChildText("descripcion"));
				eComida2.addContent(eNombre);
				eComida2.addContent(eDescripcion);
				eMenu.addContent(eComida2);
			}
		}
		

		//Serializar a archivo
		try 
		{
			XMLOutputter serializador=new XMLOutputter(Format.getPrettyFormat());
			serializador.output(docu,new FileWriter(nomDocu));
		} 
		catch (IOException e) 
		{
			System.err.println("Fichero invalido");
		}
		
	}
	
	public void nuevaComida(Comida comida)
	{
		Element eMenu=doc.getRootElement();
		
		Element eComida=new Element("comida");
		
		Element eNombre=new Element("nombre");
		eNombre.setText(comida.getNombre());
		eComida.addContent(eNombre);
		
		Element ePrecio=new Element("precio");
		ePrecio.setText(String.valueOf(comida.getPrecio()));
		eComida.addContent(ePrecio);
		
		Element eDescripcion=new Element("descripcion");
		eDescripcion.setText(comida.getDescripcion());
		eComida.addContent(eDescripcion);
		
		Element eCalorias=new Element("calorias");
		eCalorias.setText(comida.getCalorias());
		eComida.addContent(eCalorias);
		
		eMenu.addContent(eComida);
		
		grabar();
		
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
	
	public void eliminaComidaMasCara()
	{
		
		Element eMenu=doc.getRootElement();
		List<Element> lstComidas=eMenu.getChildren();
		for(Element eComida:lstComidas)
		{
			float precio=Float.parseFloat(eComida.getChildText("precio"));
			if(precio==comidaMasCara())
			{
				eMenu.removeContent(eComida);
				grabar();
				break;
			}
		}
		
	}
	private float comidaMasCara()
	{
		float comidaCara=0;
		Element eMenu=doc.getRootElement();
		List<Element> lstComidas=eMenu.getChildren();
		Iterator it=lstComidas.iterator();
		while(it.hasNext())
		{
			Element eComida=(Element) it.next();
			float precio=Float.parseFloat(eComida.getChildText("precio"));
			if(precio>comidaCara)
				comidaCara=precio;	
		}
		
		return comidaCara;
	}
	public static void main(String[] args) {
		
		GestionMenu gm=new GestionMenu("menu.xml");
		
		//Prueba metodo parsear y ver
		gm.parsear();
		//gm.ver();
		
		
		//Prueba metodo consultarPlato
		if(!gm.consultarPlato("Arroz").equals(""))
			System.out.println(gm.consultarPlato("Fabada"));
		else
			System.out.println("El plato no se encuentra en el menu");
		
		//Prueba metodo consultaPorCalorias
		ArrayList<String> platos=gm.consultaPorCalorias(550);
		for(int i=0;i<platos.size();i++)
		{
			System.out.println(platos.get(i));
		}
		
		//Prueba metodo platoEconomicos
		//gm.platosEconomicos(1.50f, 6.50f);
		
		
		//Prueba metodo nuevaComida
		//gm.nuevaComida(new Comida("Patatas",2.50f,"Patatas fritas","200"));
		
		//Prueba metodo eliminaComidaMasCara
		gm.eliminaComidaMasCara();
	}
}
