package ejercicios2;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Ejercicio1 {
	
	
	public static void main(String[] args) {
		
		Element eTabla=new Element("tabla");
		Document doc=new Document(eTabla);
		
		for(int i=1;i<11;i++)
		{
			Element eFactor=new Element("factor");
			eFactor.setAttribute("f",String.valueOf(i));
			Element eResultado=new Element("resultado");
			int producto=Integer.parseInt(args[0]);
			eResultado.setText(String.valueOf(producto*i));
			eFactor.addContent(eResultado);
			eTabla.addContent(eFactor);
		}
		
		
		//Serializar a archivo
		
				try 
				{
					XMLOutputter seralizador=new XMLOutputter(Format.getPrettyFormat());
					seralizador.output(doc,new FileWriter("tablaMulti.xml"));
				} 
				catch (IOException e) 
				{
					System.out.println("Fichero invalido");
				}
	}

}
