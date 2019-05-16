package ejercicios2;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JuegoAhorcado extends JFrame{
	
	private JButton jugar;
	private JLabel[] fallos;
	private JLabel palabra;
	private JButton[] letrasAbecedario;
	
	
	public JuegoAhorcado()
	{
		setTitle("AHORCADO");
		setLayout(null);
		
		//Creacion del boton jugar e instanciado
		jugar=new JButton("Jugar");
		jugar.setBounds(250, 20, 100, 30);
		this.getContentPane().add(jugar);
		
		
		//Creacion de los botones con las letras del abecedario e instanciado
		JPanel panelSur=new JPanel(new GridLayout(2, 0));
		String abc="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		letrasAbecedario=new JButton[abc.length()];
		for(int i=0;i<letrasAbecedario.length;i++)
		{
			JButton letra=new JButton(abc.charAt(i)+"");
			letrasAbecedario[i]=letra;
			panelSur.add(letrasAbecedario[i]);
		}
		//panelSur.add(letrasAbecedario);
		this.getContentPane().add(panelSur, "South");
		
		
		
		
		setSize(600, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		
		new JuegoAhorcado();

	}

}
