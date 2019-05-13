package ejercicios2;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Ejercicio3 extends JFrame{
	
	public Ejercicio3()
	{
		setTitle("Consultas medicas");
		
		int opcion=JOptionPane.showConfirmDialog(null, "¿Desea resetear fichero de medicos?");
		if(opcion==1)
		{
			crearXML();
		}
		
		setSize(300,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	private void crearXML() {


		Element eConsultas=new Element("consultas");
		Document doc=new Document(eConsultas);
		String[] arrayNombres={"Dr Saez","Dra Artea","Dr Cabeza","Dra Kholn"};
		
		for(int i=0;i<arrayNombres.length;i++)
		{
			Element eMedico=new Element("medico");
			eMedico.setAttributes(arrayNombres[0]);
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


	public static void main(String[] args) {
	
		new Ejercicio3();

	}

}
