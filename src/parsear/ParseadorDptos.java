package parsear;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ParseadorDptos {
	
	private String nomFich;
	private Document doc;
	
	public ParseadorDptos(String nomFich)
	{
		this.nomFich=nomFich;
		
		//Parsear
		
		
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
	
	public int numDptos1000()
	{
		//Cantidad de departamentos que han facturado mas de 1000 euros
		int cont=0;
		Element eDepartamento=doc.getRootElement();
		List<Element> lstDptos=eDepartamento.getChildren();
		for(Element eDpto:lstDptos)
		{
			String txtFact=eDpto.getChildText("facturacion");
			long fact=Long.parseLong(txtFact);
			if(fact>1000)
			{
				cont++;
			}
		}
		return cont;
	}
	
	public void subirFacturacion(int porc)
	{
		//Si porc=10, incrementa facturacion de todos los dptos un 10%
		Element eDepartamento=doc.getRootElement();
		List<Element> lstDptos=eDepartamento.getChildren();
		for(Element eDpto:lstDptos)
		{
			Element eFacturacion=eDpto.getChild("facturacion");
			String txtFact=eFacturacion.getText();
			long fact=Long.parseLong(txtFact);
			
			long factDespues=fact+(fact*porc/100);
			eFacturacion.setText(factDespues+"");
		}
		
		grabar();
	}
	
	private void grabar() {
		
		
		try 
		{
			XMLOutputter serializador=new XMLOutputter(Format.getPrettyFormat());
			serializador.output(doc, new FileWriter(nomFich));
		} 
		catch (IOException e) 
		{
			System.err.println("Fichero invalido");
		}
		
	}

	public static void main(String[] args) {
		
		ParseadorDptos dp=new ParseadorDptos("dptos.xml");
		
		System.out.println("Dptos con mas de 1000: "+dp.numDptos1000());
		dp.subirFacturacion(50);
	}

}
