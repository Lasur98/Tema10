package ejercicios2;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JuegoAhorcado extends JFrame implements ActionListener{
	
	private JButton jugar;
	private JLabel[] fallos;
	private JLabel palabra;
	private JButton[] letrasAbecedario;
	private Ahorcado a1;
	private JPanel panelOeste,panelCentro;
	
	
	public JuegoAhorcado()
	{
		setTitle("AHORCADO");
		
		
		//Panel norte
		JPanel panelNorte=new JPanel();
		jugar=new JButton("Jugar");
		panelNorte.add(jugar);
		
		
		
		//Panel Sur
		JPanel panelSur=new JPanel(new GridLayout(2, 0));
		String abc="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		letrasAbecedario=new JButton[abc.length()];
		for(int i=0;i<letrasAbecedario.length;i++)
		{
			JButton letra=new JButton(abc.charAt(i)+"");
			letrasAbecedario[i]=letra;
			letrasAbecedario[i].addActionListener(this);
			panelSur.add(letrasAbecedario[i]);
		}
		
		
		
		//Panel Centro
		panelCentro=new JPanel();
		palabra=new JLabel();
		palabra.setVisible(false);
		panelCentro.add(palabra);
		
		
		//Adicion de los paneles a la ventana principal
		this.getContentPane().add(panelNorte, "North");
		this.getContentPane().add(panelSur, "South");
		this.getContentPane().add(panelCentro, "Center");
		
		
		eventos();
		
		setSize(600, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void eventos() {
		 jugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int letras=Integer.parseInt(JOptionPane.showInputDialog(null, "Elija numero de letras:"));
				a1=new Ahorcado(letras);
				
				//Instanciar etiquetas de la palabra a adivinar y los fallos
				palabra.setText(a1.respuestaToString());
				palabra.setFont(new Font("Arial",Font.ROMAN_BASELINE, 40));
				palabra.setVisible(true);
				panelCentro.revalidate();
				
				panelOeste=new JPanel();
				fallos=new JLabel[a1.getTotalVidas()];
				for(int i=0;i<fallos.length;i++)
				{
					JLabel fallo=new JLabel(redim("aspa_roja.png"));
					fallos[i]=fallo;
					fallos[i].setVisible(false);
					panelOeste.add(fallos[i]);
				}
				
				add(panelOeste, "West");
				panelOeste.revalidate();
				jugar.setVisible(false);
				
			}
		});
		 
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		char letra=e.getActionCommand().charAt(0);
		boolean tirada=a1.tirar(letra);
		if(tirada==true)
		{
			palabra.setText(a1.respuestaToString());
		}
		else
		{
			if(a1.getVidasRest()>=0)
			{
				for(int i=0;i<fallos.length-a1.getVidasRest();i++)
				{
					fallos[i].setVisible(true);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Se acabaron tus vidas.Palabra: "+a1.getPALABRA());
			}
		}
		
	}

	private ImageIcon redim (String fichImag)
	{
	            ImageIcon imIcon=new ImageIcon(fichImag);

	            Image im=imIcon.getImage();

	            Image im2= im.getScaledInstance(40, 40, 0);

	            return new ImageIcon(im2);


	}
	
	public static void main(String[] args) {
		
		new JuegoAhorcado();

	}

	

}
