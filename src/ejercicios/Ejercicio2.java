package ejercicios;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ejercicio2 extends JFrame implements ActionListener{
	
	private JLabel origen,destino,id,hora,titulo,descripcionVuelo;
	private JComboBox<String[]> origenes;
	private JTextField destination,id2,hour;
	private JButton anyadeVuelo;
	private GestorVuelos2 gv2=new GestorVuelos2("vuelos.xml");
	
	public Ejercicio2()
	{
		setTitle("AÑADIR VUELOS");
		
		
		//Panel Norte con el la etiqueta de nuevo vuelo
		JPanel panelNorte=new JPanel();
		titulo=new JLabel("NUEVO VUELO");
		titulo.setFont(new Font("Arial",Font.BOLD,16));
		panelNorte.add(titulo);
		
		
		//Panel Oeste
		JPanel panelOeste=new JPanel();
		panelOeste.setLayout(new GridLayout(4, 2));
		
		//Columna 1
		origen=new JLabel("ORIGEN");
		destino=new JLabel("DESTINO");
		id=new JLabel("ID");
		hora=new JLabel("HORA");
		
		//Columna2
		origenes=new JComboBox(cargar());
		destination=new JTextField(8);
		id2=new JTextField(8);
		hour=new JTextField(8);
		
		panelOeste.add(origen);
		panelOeste.add(origenes);
		panelOeste.add(destino);
		panelOeste.add(destination);
		panelOeste.add(id);
		panelOeste.add(id2);
		panelOeste.add(hora);
		panelOeste.add(hour);
		
		//Panel Centro
		anyadeVuelo=new JButton("Añade vuelo");
		anyadeVuelo.addActionListener(this);

		
		
		//Panel Sur
		JPanel panelSur=new JPanel();
		descripcionVuelo=new JLabel();
		descripcionVuelo.setText("<html>");
		panelSur.add(descripcionVuelo);
		
		
		
		
		
		//Añadir los paneles a la ventana
		this.getContentPane().add(panelNorte,"North");
		this.getContentPane().add(panelOeste, "West");
		this.getContentPane().add(anyadeVuelo, "Center");
		this.getContentPane().add(panelSur, "South");
		
		
		
		
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	
	
	private ComboBoxModel cargar() 
	{
		ArrayList<String> origenes=gv2.origenes();
		DefaultComboBoxModel<String> combo=new DefaultComboBoxModel<String>();
		for(int i=0;i<origenes.size();i++)
		{
			combo.addElement(origenes.get(i));
		}
		
		return combo;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		boolean funciona=gv2.anyadeVuelo((String) origenes.getSelectedItem(),destino.getText(),id.getText(),hora.getText());
		if(funciona)
		{
			descripcionVuelo.setText(descripcionVuelo.getText()+"<br>Vuelo añadido: "+origenes.getSelectedItem()+"-"+
					destino.getText()+"("+hora.getText()+")</br>");
		}
		else
		{
			descripcionVuelo.setText(descripcionVuelo.getText()+"<br>No se puede añadir ese vuelo</br>");
		}
	}



	public static void main(String[] args) {
		
		new Ejercicio2();
	}





	

}
