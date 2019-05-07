package ejercicios;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Ejercicio1 extends JFrame implements MouseListener{
	
	private JList ciudades;
	private JLabel titulo1;
	private DefaultListModel ciudad;
	
	public Ejercicio1()
	{
		setTitle("COMPRA DE BILLETES");
		

		anyadeLista();
		ciudades=new JList(ciudad);
		JScrollPane scroll=new JScrollPane(ciudades);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		titulo1=new JLabel("HAZ DOBLE CLIC EN EL DESTINO DESEADO");

		this.getContentPane().add(titulo1, "North");
		this.getContentPane().add(scroll, "Center");
		
		eventos();
		
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void eventos()
	{
		ciudades.addMouseListener(this);
	}
	
	private void anyadeLista()
	{
		gestorVuelos gv=new gestorVuelos("vuelos.xml");
		ArrayList lista=gv.destinos();
		ciudad=new DefaultListModel();
		for(int i=0;i<lista.size();i++)
		{
			ciudad.addElement(lista.get(i));
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		if(e.getClickCount()==2)
		{
			DialogoEjercicio1 de=new DialogoEjercicio1((String) ciudades.getSelectedValue());
		}
		
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {	
	}
	
	
	
	
	public JList getCiudades() {
		return ciudades;
	}

	public void setCiudades(JList ciudades) {
		this.ciudades = ciudades;
	}

	public static void main(String[] args) {
		
		new Ejercicio1();
	}
	

}
