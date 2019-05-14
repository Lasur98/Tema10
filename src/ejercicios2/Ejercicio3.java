package ejercicios2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 * @author Aitor
 *
 */
public class Ejercicio3 extends JFrame implements ActionListener{
	
	private JList<HoraConsulta> lsthoras;
	private JComboBox<String> medicos;
	private JTextField txtnombre;
	private JLabel nombrePaciente,eligeHora,eligeMedico;
	private JButton anyadir;
	private GestorConsultas gc;
	private DefaultComboBoxModel nombresMedicos;
	
	public Ejercicio3()
	{
		setTitle("Consultas medicas");
		setLayout(null);
		
		int opcion=JOptionPane.showConfirmDialog(null, "¿Desea resetear fichero de medicos?");
		if(opcion==0)
		{
			crearXML();
		}
		if(opcion==1)
		{
			comprobarExisteXml();
		}
		
		//Creacion de la etiqueta eligeHora y de la lista de objetos
		eligeHora=new JLabel("Elija una o mas horas");
		eligeHora.setBounds(20, 2, 150, 20);
		//Creamos el array de objetos HoraConsulta
		HoraConsulta[] horas_consulta={new HoraConsulta(9, 00),new HoraConsulta(9, 30),new HoraConsulta(10, 00),new HoraConsulta(10, 30),
				new HoraConsulta(11, 00),new HoraConsulta(11, 30),new HoraConsulta(12, 00),new HoraConsulta(12, 30),
				new HoraConsulta(13, 00),new HoraConsulta(13, 30),new HoraConsulta(14, 00),new HoraConsulta(14, 30),
				new HoraConsulta(15, 00),new HoraConsulta(15, 30)};
		
		lsthoras=new JList(horas_consulta);
		JScrollPane scroll=new JScrollPane(lsthoras,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(20, 30, 100, 200);
		
		//Creacion de la etiqueta eligeMedico y el combo de medicos
		eligeMedico=new JLabel("Elija un médico");
		eligeMedico.setBounds(200, 30, 100, 20);
		//Creamos el array con los nombres de los medicos y la añadimos al combo
		anyadeMedicos();
		medicos=new JComboBox(nombresMedicos);
		medicos.setBounds(200, 55, 150, 30);
		
		//Creacion de la etiqueta nombrePaciente y el textField
		nombrePaciente=new JLabel("Nombre paciente:");
		nombrePaciente.setBounds(200, 200, 100, 20);
		txtnombre=new JTextField(10);
		txtnombre.setBounds(200, 220, 150, 30);
		
		//Creacion del boton añadir
		anyadir=new JButton("AÑADIR");
		anyadir.setBounds(120, 280, 100, 35);
		anyadir.addActionListener(this);
		
		//Añadimos los componentes a la ventana principal
		this.getContentPane().add(eligeHora);
		this.getContentPane().add(scroll);
		this.getContentPane().add(eligeMedico);
		this.getContentPane().add(medicos);
		this.getContentPane().add(nombrePaciente);
		this.getContentPane().add(txtnombre);
		this.getContentPane().add(anyadir);
		
		
		setSize(400,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	private void anyadeMedicos() 
	{
		nombresMedicos=new DefaultComboBoxModel();
		ArrayList medicos=gc.verMedicos();
		for(int i=0;i<medicos.size();i++)
		{
			nombresMedicos.addElement(medicos.get(i));
		}
		
	}


	private void comprobarExisteXml() 
	{
		File f=new File("consultas.xml");
		//Si no existe lo creamos con la informacion basica
		if(!f.exists())
		{
			Element eConsultas=new Element("consultas");
			Document doc=new Document(eConsultas);
			final String[] arrayNombres={"Dr Saez","Dra Artea","Dr Cabeza","Dra Kholn"};
			
			for(int i=0;i<arrayNombres.length;i++)
			{
				Element eMedico=new Element("medico");
				eMedico.setAttribute("nombre",arrayNombres[i]);
				eConsultas.addContent(eMedico);
			}
			
			
			//Serializar a archivo
			try 
			{
				XMLOutputter seralizador=new XMLOutputter(Format.getPrettyFormat());
				seralizador.output(doc,new FileWriter("consultas.xml"));
			} 
			catch (IOException e) 
			{
				System.out.println("Fichero invalido");
			}
		}
		gc=new GestorConsultas("consultas.xml");
		
		
	}


	private void crearXML() {


		Element eConsultas=new Element("consultas");
		Document doc=new Document(eConsultas);
		final String[] arrayNombres={"Dr Saez","Dra Artea","Dr Cabeza","Dra Kholn"};
		
		for(int i=0;i<arrayNombres.length;i++)
		{
			Element eMedico=new Element("medico");
			eMedico.setAttribute("nombre",arrayNombres[i]);
			eConsultas.addContent(eMedico);
		}
		
		//Serializar a archivo
		
		try 
		{
			XMLOutputter seralizador=new XMLOutputter(Format.getPrettyFormat());
			seralizador.output(doc,new FileWriter("consultas.xml"));
		} 
		catch (IOException e) 
		{
			System.out.println("Fichero invalido");
		}
		gc=new GestorConsultas("consultas.xml");
	}


	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		HoraConsulta hora=lsthoras.getSelectedValue();
		boolean estado=gc.anyadirConsulta(lsthoras.getSelectedValue(), txtnombre.getText(),(String) medicos.getSelectedItem());
		if(estado==true)
		{
			
			JOptionPane.showMessageDialog(null, "CONSULTA ASIGNADA: "+txtnombre.getText()+"-"+(String) medicos.getSelectedItem()+
					","+hora.toString());
		}
		else
		{
			JOptionPane.showMessageDialog(null,(String) medicos.getSelectedItem()+" ya tiene asignada la hora "+hora.toString()+"\t"+
					"Elija otra");
		}
	}
	
	
	public static void main(String[] args) {
	
		new Ejercicio3();

	}


	

}
