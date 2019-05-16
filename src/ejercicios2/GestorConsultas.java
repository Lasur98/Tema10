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

public class GestorConsultas {
	
	private String nomFich;
	private Document doc;
	
	public GestorConsultas(String nomFich)
	{
		this.nomFich=nomFich;
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
	
	public ArrayList verMedicos()
	{
		ArrayList medicos=new ArrayList();
		Element eConsultas=doc.getRootElement();
		List<Element> lstMedicos=eConsultas.getChildren();
		Iterator it=lstMedicos.iterator();
		while(it.hasNext())
		{
			Element eMedico=(Element) it.next();
			medicos.add(eMedico.getAttributeValue("nombre"));
		}
		
		return medicos;
	}
	
	public boolean anyadirConsulta(HoraConsulta hora,String nombrePaciente,String medico)
	{
		Element eConsultas=doc.getRootElement();
		List<Element> lstMedicos=eConsultas.getChildren();
		Iterator it=lstMedicos.iterator();
		boolean anyadido=false;
		while(it.hasNext())
		{
			Element eMedico=(Element) it.next();
			if(eMedico.getAttributeValue("nombre").equals(medico))
			{
				try 
				{
					String hora2=eMedico.getChild("consulta").getChildText("hora");
					Element eHoraComparar=eMedico.getChild("consulta").getChild("hora");
					String horaComparar=eHoraComparar.getText();
					if(!horaComparar.equals(hora.toString()))
					{
						Element eConsulta=new Element("consulta");
						Element eHora=new Element("hora");
						eHora.addContent(hora.toString());
						Element ePaciente=new Element("paciente");
						ePaciente.addContent(nombrePaciente);
						eConsulta.addContent(eHora);
						eConsulta.addContent(ePaciente);
						eMedico.addContent(eConsulta);
						anyadido=true;
					}
					break;
				} 
				catch (NullPointerException e) 
				{
					Element eConsulta=new Element("consulta");
					Element eHora=new Element("hora");
					eHora.addContent(hora.toString());
					Element ePaciente=new Element("paciente");
					ePaciente.addContent(nombrePaciente);
					eConsulta.addContent(eHora);
					eConsulta.addContent(ePaciente);
					eMedico.addContent(eConsulta);	
					anyadido=true;
				}
			}
		}
		grabar();
		return anyadido;
		
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
}
