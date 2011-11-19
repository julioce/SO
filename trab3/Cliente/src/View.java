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
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8464482670221083273L;

	/* Cria tudo: Janela, entidades, label, botão... */
	public static JFrame window = new JFrame("Cliente - Trabalho 3 - SO");
	
	public static JLabel labelTitulo = new JLabel();
	
	public static JLabel labelHost = new JLabel();
	public static JTextField hostField = new JTextField();
	public static JButton connectButton = new JButton();
	
	public static JButton listButton = new JButton();
	public static JButton disconnectButton = new JButton();
	
	public static JTextArea outputTextArea = new JTextArea();
	
	public View(){
		/* Label do trabalho */
		labelTitulo.setText("Cliente de Arquivos");
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		labelTitulo.setBounds((Configuracoes.WIDTH/2)-105, 10, (Configuracoes.WIDTH/2)+105, 30);
		
		/* Campo de host */
		labelHost.setText("Host:");
		labelHost.setBounds(10, 50, 40, 25);
		hostField.setText("");
		hostField.setBounds(50, 50, 140, 25);
		connectButton.addActionListener(this);
		connectButton.setText("Conectar");
		connectButton.setActionCommand("connect");
		connectButton.setToolTipText("Clique aqui para conectar ao servidor");
		connectButton.setBounds(190, 50, 120, 25);
		
		/* List button */
		listButton.addActionListener(this);
		listButton.setText("Listar");
		listButton.setActionCommand("ls");
		listButton.setToolTipText("Clique aqui para listar os arquivos no Servidor");
		listButton.setBounds(10, 110, 120, 25);

		/* Disconnect button */
		disconnectButton.addActionListener(this);
		disconnectButton.setText("Desconectar");
		disconnectButton.setActionCommand("disconnect");
		disconnectButton.setToolTipText("Clique aqui para desconectar do Servidor");
		disconnectButton.setBounds(10, 140, 120, 25);
		
		/* outputTextArea */
		outputTextArea.setEditable(false);
		outputTextArea.setAutoscrolls(true);
		outputTextArea.setToolTipText("Resposta enviada pelo Servidor");
		outputTextArea.setBounds(140, 100, 620, 440);
		
		/* Adiciona tudo a janela */
		window.add(labelTitulo);
		window.add(labelHost);
		window.add(hostField);
		window.add(connectButton);
		window.add(listButton);
		window.add(disconnectButton);
		window.add(outputTextArea);
		switchButtons(false);
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
		
		// Comando de conectar
		if(arg0.getActionCommand().equals("connect")) {
			if(!View.hostField.getText().isEmpty()){
				// Configura os parâmetros
				Cliente.setHost(View.hostField.getText());
				Cliente.setPortNumber(2222);
				
				// Inicia de fato o cliente
				Cliente.startCliente();
				
				// Desabilita o input de host
				switchButtons(true);
			}
		}
		
		// Comando de listar
		if(arg0.getActionCommand().equals("ls")){
			Cliente.execute("ls");
		}
		
		// Comando de desconectar
		if(arg0.getActionCommand().equals("disconnect")){
			Cliente.execute("disconnect");
			
			// Habilita o input de host
			switchButtons(false);
		}
	}
	
	private void switchButtons(boolean status){
		listButton.setEnabled(status);
		disconnectButton.setEnabled(status);
		
		labelHost.setEnabled(!status);
		hostField.setEnabled(!status);
		connectButton.setEnabled(!status);
		if(status){
			connectButton.setText("Conectado");
		}else{
			connectButton.setText("Conectar");
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
