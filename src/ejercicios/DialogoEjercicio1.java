package ejercicios;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DialogoEjercicio1 extends JDialog{
	
	private String respuesta;
	private JRadioButton[] radiosOrigen;
	private JLabel titulo;
	private JButton compra;
	
	public DialogoEjercicio1(String respuesta)
	{
		this.respuesta=respuesta;
		setTitle("Eleccion de origen");
		setModal(true);
		
		JPanel panelCentro=new JPanel();
		titulo=new JLabel("VUELOS HACIA "+respuesta.toUpperCase()+" desde");
		compra=new JButton("COMPRAR BILLETE");
		panelCentro.add(titulo);
		ButtonGroup bg=new ButtonGroup();
		gestorVuelos gv=new gestorVuelos("vuelos.xml");
		ArrayList<String> origen=gv.origenes(respuesta);
		radiosOrigen=new JRadioButton[origen.size()];
		for(int i=0;i<origen.size();i++)
		{
			JRadioButton boton=new JRadioButton(origen.get(i));
			boton.isSelected();
			radiosOrigen[i]=boton;
			bg.add(boton);
			panelCentro.add(boton);
		}
		panelCentro.add(compra);
		this.getContentPane().add(panelCentro, "Center");
		
		eventos();
		
		
		
		
		setSize(250,250);
		setVisible(true);
	}
	
	private void eventos()
	{
		compra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gestorVuelos gv=new gestorVuelos("vuelos.xml");
				ArrayList<String> array=gv.origenes(respuesta);
				String id ="";
				String[] arraySplit;
				for(int i=0;i<radiosOrigen.length;i++)
				{
					arraySplit=array.get(i).split(":");
					id=arraySplit[0];
					String[] id2=radiosOrigen[i].getText().split(":");
					if(radiosOrigen[i].isSelected() && id.equals(id2[0]))	
						break;
				}
				
				gv.compraBillete(id);
				setVisible(false);
				
			}
		});
	}

}
