package serializar;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



public class GestorDptos {
	
	private final static String[] DPTOS={"Informatica","Compras","Nominas"};
	private final static long[] FACTURACIONES={1200000,30000000,400000000};
	private String nomFich;
	
	public GestorDptos(String nomFich)
	{
		this.nomFich=nomFich;
	}
	
	public void generarXml()
	{
		//Crear arbol/documento en memoria
		Element eDepartamentos=new Element("departamentos");
		Document doc=new Document(eDepartamentos);
		
		for(int i=0;i<DPTOS.length;i++)
		{
			Element eDepartamento=new Element("departamento");
			eDepartamento.setAttribute("nombre",DPTOS[i]);
			Element eFacturacion=new Element("facturacion");
			eFacturacion.setText(FACTURACIONES[i]+"");
			
			eDepartamento.addContent(eFacturacion);
			eDepartamentos.addContent(eDepartamento);
			
		}
		
		//Serializar a archivo
		
		try 
		{
			XMLOutputter seralizador=new XMLOutputter(Format.getPrettyFormat());
			//seralizador.output(doc, System.out);
			seralizador.output(doc, new FileWriter(nomFich));
		} 
		catch (IOException e) 
		{
			System.out.println("Fichero invalido");
		}
	}
	
	public static void main(String[] args) {
		
		GestorDptos gd=new GestorDptos("dptos.xml");
		gd.generarXml();
	}

}
