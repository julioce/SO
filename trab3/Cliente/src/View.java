import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8464482670221083273L;

	/* Cria tudo: Janela, entidades, label, botão... */
	public static JFrame window = new JFrame("Cliente - Trabalho 3 - SO");
	
	public static JLabel labelTitulo = new JLabel();
	
	public static JLabel labelHost = new JLabel();
	public static JTextField hostField = new JTextField();
	public static JButton connectButton = new JButton();
	
	public static JButton listServerButton = new JButton();
	public static JButton listClientButton = new JButton();
	public static JButton disconnectButton = new JButton();
	
	public static JLabel labelServer = new JLabel();
	public static DefaultListModel ServerFileList = new DefaultListModel();
	public static JList ServerList = new JList(ServerFileList);
	
	public static JLabel labelClient = new JLabel();
	public static DefaultListModel ClientFileList = new DefaultListModel();
	public static JList ClientList = new JList(ClientFileList);
	
	public View(){
		/* Label do trabalho */
		labelTitulo.setText("Cliente de Arquivos");
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		labelTitulo.setBounds((800/2)-105, 10, (600/2)+105, 30);
		
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
		
		/* List Server Button */
		listServerButton.addActionListener(this);
		listServerButton.setText("Listar Servidor");
		listServerButton.setActionCommand("ls");
		listServerButton.setToolTipText("Clique aqui para listar os arquivos no Servidor");
		listServerButton.setBounds(10, 130, 120, 25);
		
		/* List Client Button */
		listClientButton.addActionListener(this);
		listClientButton.setText("Listar Cliente");
		listClientButton.setActionCommand("client");
		listClientButton.setToolTipText("Clique aqui para listar os arquivos Locais");
		listClientButton.setBounds(10, 160, 120, 25);

		/* Disconnect button */
		disconnectButton.addActionListener(this);
		disconnectButton.setText("Desconectar");
		disconnectButton.setActionCommand("disconnect");
		disconnectButton.setToolTipText("Clique aqui para desconectar do Servidor");
		disconnectButton.setBounds(10, 540, 120, 25);
		
		/* Server File List */
		labelServer.setText("Servidor");
		labelServer.setBounds(140, 100, 70, 25);
		ServerList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ServerList.setLayoutOrientation(JList.VERTICAL);
		ServerList.setVisibleRowCount(-1);
		ServerList.setBounds(140, 135, 300, 430);
		
		/* Client File List */
		labelClient.setText("Local");
		labelClient.setBounds(470, 100, 50, 25);
		ClientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ClientList.setLayoutOrientation(JList.VERTICAL);
		ClientList.setVisibleRowCount(-1);
		ClientList.setBounds(470, 135, 300, 430);
		
		/* Adiciona tudo a janela */
		window.add(labelTitulo);
		window.add(labelHost);
		window.add(hostField);
		window.add(connectButton);
		
		window.add(listServerButton);
		window.add(listClientButton);
		window.add(disconnectButton);
		
		window.add(labelServer);
		window.add(ServerList);
		window.add(labelClient);
		window.add(ClientList);
		switchButtons(false);
		window.add(this);
		window.add(new Canvas());
		
		/* Amarra tudo e exibe a janela */
		window.setPreferredSize(new Dimension(800, 600));
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
		
		// Comando de listar Servidor
		if(arg0.getActionCommand().equals("ls")){
			Cliente.execute("ls");
		}
		
		// Comando de listar local
		if(arg0.getActionCommand().equals("client")){
			Cliente.runLocalCommand("ls");
		}
		
		// Comando de desconectar
		if(arg0.getActionCommand().equals("disconnect")){
			Cliente.execute("disconnect");
			
			// Habilita o input de host
			switchButtons(false);
		}
	}
	
	private void switchButtons(boolean status){

		labelHost.setEnabled(!status);
		hostField.setEnabled(!status);
		connectButton.setEnabled(!status);
		if(status){
			connectButton.setText("Conectado");
		}else{
			connectButton.setText("Conectar");
		}
		
		listServerButton.setEnabled(status);
		listClientButton.setEnabled(status);
		disconnectButton.setEnabled(status);
		
		labelServer.setEnabled(status);
		ServerList.setEnabled(status);
		labelClient.setEnabled(status);
		ClientList.setEnabled(status);
	}

}

class Canvas extends JComponent {
	private static final long serialVersionUID = 5703217428905757134L;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* Desenha pequenas Bordas */
		g2.setPaint(new Color(120, 120, 120));
		g2.drawRect(139, 134, 301, 431);
		g2.drawRect(469, 134, 301, 431);
	}
}
