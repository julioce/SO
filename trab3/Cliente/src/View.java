import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8464482670221083273L;

	/* Cria tudo: Janela, entidades, label, botão... */
	public static JFrame window = new JFrame("Cliente - Trabalho 3 - SO");
	
	public static JLabel labelTitulo = new JLabel();
	
	public static JLabel labelHost = new JLabel();
	public static JTextField hostField = new JTextField();
	
	public static JButton connectButton = new JButton();
	
	public View(){
		// Construtor
	}
	
	public void startView(){
		/* Label do trabalho */
		labelTitulo.setText("Cliente de Arquivos");
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		labelTitulo.setBounds((Configuracoes.WIDTH/2)-105, 10, (Configuracoes.WIDTH/2)+105, 30);
		
		/* Campo de host */
		labelHost.setText("Host:");
		labelHost.setBounds(10, 40, 40, 30);
		hostField.setText("");
		hostField.setBounds(50, 40, 140, 30);
		connectButton.addActionListener(this);
		connectButton.setText("Conectar");
		connectButton.setActionCommand("conectar");
		connectButton.setToolTipText("Clique aqui para conectar ao servidor");
		connectButton.setBounds(190, 40, 200, 30);
		
		/* Adiciona tudo a janela */
		window.add(labelHost);
		window.add(hostField);
		window.add(connectButton);
		window.add(labelTitulo);
		window.add(this);
		window.add(new Canvas());
		
		/* Amarra tudo e exibe a janela */
		window.setPreferredSize(new Dimension(Configuracoes.WIDTH, Configuracoes.HEIGHT));
		window.setFocusTraversalKeysEnabled(true);
		window.setResizable(false);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("conectar")) {
			Main.createClient(hostField.getText());
		}
	}

}

class Canvas extends JComponent {
	private static final long serialVersionUID = 5703217428905757134L;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
