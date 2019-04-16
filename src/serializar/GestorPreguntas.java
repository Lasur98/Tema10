package serializar;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class GestorPreguntas {
	
	private final static Pregunta[] PREGUNTAS={new Pregunta("2+2",new String[]{"22","5","4"},2),
		
		new Pregunta("Capital de Libano",new String[]{"Acra","Beirut"},1),
		new Pregunta("Director de psicosis",new String[]{"Hitchcock","Newman","Ford"},0),
		new Pregunta("Factorial de 1",new String[]{"1","2","-1","0"},0)};
		
	
	private String nomFich;
	
	public GestorPreguntas(String nomFich)
	{
		this.nomFich=nomFich;
	}
	
	public void generarXML()
	{
		Element eConcurso=new Element("concurso");
		Document doc=new Document(eConcurso);
		
		for(int i=0;i<PREGUNTAS.length;i++)
		{
			Element ePregunta=new Element("pregunta");
			Element eTexto=new Element("texto");
			eTexto.setText(PREGUNTAS[i].getPregunta());
			Element eRespuestas=new Element("respuestas");
			String[] respuestas=PREGUNTAS[i].getRespuestas();
			for(int i2=0;i2<respuestas.length;i2++)
			{
				Element eRespuesta=new Element("respuesta");
				eRespuesta.setText(respuestas[i2]);
				eRespuestas.addContent(eRespuesta);
			}
			
			ePregunta.addContent(eTexto);
			ePregunta.addContent(eRespuestas);
			eConcurso.addContent(ePregunta);
		}
		
		//Serializar a archivo
		
				try 
				{
					XMLOutputter seralizador=new XMLOutputter(Format.getPrettyFormat());
					//seralizador.output(doc, System.out);
					seralizador.output(doc,new FileWriter(nomFich));
				} 
				catch (IOException e) 
				{
					System.out.println("Fichero invalido");
				}
		
	}
	
	public static void main(String[] args) {
		
		GestorPreguntas gp=new GestorPreguntas("preguntas.xml");
		gp.generarXML();
		
	}

}
