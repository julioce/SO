import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8464482670221083273L;

	/* Cria tudo: Janela, entidades, label, botão... */
	public static JFrame window = new JFrame("Cliente de Arquivos- Trabalho 3 - SO");
	
	public static JLabel labelHost = new JLabel();
	public static JTextField hostField = new JTextField();
	public static JButton connectButton = new JButton();
	public static JButton disconnectButton = new JButton();
	
	public static JButton listServerButton = new JButton();
	public static JButton fileInfoServerButton = new JButton();
	public static JButton uploadFileToServerButton = new JButton();
	public static JButton deleteServerFileButton = new JButton();
	
	public static JButton listClientButton = new JButton();
	public static JButton fileInfoClientButton = new JButton();
	public static JButton downloadFileFromServerButton = new JButton();
	public static JButton deleteLocalFileButton = new JButton();
	
	public static JLabel labelServer = new JLabel();
	public static DefaultListModel ServerFileList = new DefaultListModel();
	public static JList ServerList = new JList(ServerFileList);
	
	public static JLabel labelClient = new JLabel();
	public static DefaultListModel ClientFileList = new DefaultListModel();
	public static JList ClientList = new JList(ClientFileList);
	
	
	public View(){
		/* Campo de host */
		labelHost.setText("Host");
		labelHost.setBounds(15, 25, 40, 25);
		hostField.setText("localhost");
		hostField.setBounds(50, 25, 140, 25);
		connectButton.addActionListener(this);
		connectButton.setText("Conectar");
		connectButton.setActionCommand("connect");
		connectButton.setToolTipText("Clique aqui para conectar ao servidor");
		connectButton.setBounds(190, 25, 120, 25);
		
		/* Disconnect button */
		disconnectButton.addActionListener(this);
		disconnectButton.setText("Desconectar");
		disconnectButton.setActionCommand("disconnect");
		disconnectButton.setToolTipText("Clique aqui para desconectar do Servidor");
		disconnectButton.setBounds(660, 25, 125, 25);
		
		/* List Server Button */
		listServerButton.addActionListener(this);
		listServerButton.setText("Listar");
		listServerButton.setActionCommand("serverList");
		listServerButton.setToolTipText("Clique aqui para listar os arquivos no Servidor");
		listServerButton.setBounds(10, 95, 125, 25);
		
		/* Server Info Button */
		fileInfoServerButton.addActionListener(this);
		fileInfoServerButton.setText("Informações");
		fileInfoServerButton.setActionCommand("infoServer");
		fileInfoServerButton.setToolTipText("Clique aqui para mais informações do Servidor");
		fileInfoServerButton.setBounds(10, 135, 125, 25);
		
		/* Download File from Server Button */
		downloadFileFromServerButton.addActionListener(this);
		downloadFileFromServerButton.setText("Download");
		downloadFileFromServerButton.setActionCommand("receiveFileFromServer");
		downloadFileFromServerButton.setToolTipText("Clique aqui para receber o arquivo selecionado do Servidor");
		downloadFileFromServerButton.setBounds(10, 175, 125, 25);
		
		/* Delete File from Client Button */
		deleteServerFileButton.addActionListener(this);
		deleteServerFileButton.setText("Deletar arquivo");
		deleteServerFileButton.setActionCommand("deleteServerFile");
		deleteServerFileButton.setToolTipText("Clique aqui para deletar o arquivo no Servidor");
		deleteServerFileButton.setBounds(10, 215, 125, 25);
		
		/* Server File List */
		labelServer.setText("Servidor");
		labelServer.setBounds(180, 75, 70, 25);
		ServerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ServerList.setLayoutOrientation(JList.VERTICAL);
		ServerList.setVisibleRowCount(-1);
		ServerList.setBounds(140, 100, 245, 440);
		
		/* List Client Button */
		listClientButton.addActionListener(this);
		listClientButton.setText("Listar");
		listClientButton.setActionCommand("clientList");
		listClientButton.setToolTipText("Clique aqui para listar os arquivos Locais");
		listClientButton.setBounds(660, 95, 125, 25);

		/* Client Info Button */
		fileInfoClientButton.addActionListener(this);
		fileInfoClientButton.setText("Informações");
		fileInfoClientButton.setActionCommand("infoClient");
		fileInfoClientButton.setToolTipText("Clique aqui para mais informações do Cliente");
		fileInfoClientButton.setBounds(660, 135, 125, 25);
		
		/* Client File List */
		labelClient.setText("Local");
		labelClient.setBounds(580, 75, 50, 25);
		ClientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ClientList.setLayoutOrientation(JList.VERTICAL);
		ClientList.setVisibleRowCount(-1);
		ClientList.setBounds(410, 100, 245, 440);
		
		/* Upload File from Client Button */
		uploadFileToServerButton.addActionListener(this);
		uploadFileToServerButton.setText("Upload");
		uploadFileToServerButton.setActionCommand("sendFileToServer");
		uploadFileToServerButton.setToolTipText("Clique aqui para enviar o arquivo selecionado ao Servidor");
		uploadFileToServerButton.setBounds(660, 175, 125, 25);
		
		/* Delete File from Client Button */
		deleteLocalFileButton.addActionListener(this);
		deleteLocalFileButton.setText("Deletar arquivo");
		deleteLocalFileButton.setActionCommand("deleteLocalFile");
		deleteLocalFileButton.setToolTipText("Clique aqui para deletar o arquivo local");
		deleteLocalFileButton.setBounds(660, 215, 125, 25);
		
		switchButtons(false);
		/* Adiciona tudo a janela */
		window.add(labelHost);
		window.add(hostField);
		window.add(connectButton);
		window.add(disconnectButton);
		
		window.add(listServerButton);
		window.add(downloadFileFromServerButton);
		window.add(fileInfoServerButton);
		window.add(deleteServerFileButton);
		
		window.add(fileInfoClientButton);
		window.add(listClientButton);
		window.add(uploadFileToServerButton);
		window.add(deleteLocalFileButton);
		
		window.add(labelServer);
		window.add(ServerList);
		window.add(labelClient);
		window.add(ClientList);
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
				
				// Verifica se foi possível iniciar o cliente
				if(Cliente.startCliente()){
					// Desabilita o input de host
					switchButtons(true);
				}
			}
		}
		// Comando de desconectar
		if(arg0.getActionCommand().equals("disconnect")){
			Cliente.execute("disconnect");
			
			// Habilita o input de host
			switchButtons(false);
		}
		
		// Comando de listar Servidor
		if(arg0.getActionCommand().equals("serverList")){
			Cliente.execute("ls -p");
		}
		// Comando de informações de arquivos dos Servidor
		if(arg0.getActionCommand().equals("infoServer")){
			if(ServerList.getSelectedIndex() != -1){
				Object[] selected = ServerList.getSelectedValues();
				
				for(int i=0; i<selected.length; i++){
					Cliente.execute("ls -ltr " + selected[i]);
				}	
			}
		}
		// Commando para receber arquivos do Servidor
		if(arg0.getActionCommand().equals("receiveFileFromServer")){
			if(ServerList.getSelectedIndex() != -1){
				Object[] selected = ServerList.getSelectedValues();
				
				Cliente.execute("receiveFileFromServer@#"+selected[0]);
			}
		}
		// Comando deletar o arquivo local
		if(arg0.getActionCommand().equals("deleteServerFile")){
			if(ServerList.getSelectedIndex() != -1){
				Object[] selected = ServerList.getSelectedValues();
				
				Cliente.execute("rm " + selected[0]);
			}
		}
		
		
		// Comando de listar arquivos locais
		if(arg0.getActionCommand().equals("clientList")){
			Cliente.runLocalCommand("ls -p");
		}
		// Commando para mostrar detalhe do arquivo Local
		if(arg0.getActionCommand().equals("infoClient")){
			if(ClientList.getSelectedIndex() != -1){
				Object[] selected = ClientList.getSelectedValues();
				
				for(int i=0; i<selected.length; i++){
					Cliente.runLocalCommand("ls -ltr " + selected[i]);
				}	
			}
		}
		// Comando para enviar arquivos para o Servidor
		if(arg0.getActionCommand().equals("sendFileToServer")){
			if(ClientList.getSelectedIndex() != -1){
				Object[] selected = ClientList.getSelectedValues();
				
				Cliente.execute("sendFileToServer@#"+selected[0]);	
			}
		}
		// Comando deletar o arquivo local
		if(arg0.getActionCommand().equals("deleteLocalFile")){
			if(ClientList.getSelectedIndex() != -1){
				Object[] selected = ClientList.getSelectedValues();
				
				Cliente.runLocalCommand("rm " + selected[0]);
			}
		}
		
		
	}
	
	private void switchButtons(boolean status){

		labelHost.setEnabled(!status);
		hostField.setEnabled(!status);
		connectButton.setEnabled(!status);
		disconnectButton.setEnabled(status);
		if(status){
			connectButton.setText("Conectado");
		}else{
			connectButton.setText("Conectar");
		}
		
		listServerButton.setEnabled(status);
		fileInfoServerButton.setEnabled(status);
		downloadFileFromServerButton.setEnabled(status);
		deleteServerFileButton.setEnabled(status);
		
		listClientButton.setEnabled(status);
		fileInfoClientButton.setEnabled(status);
		uploadFileToServerButton.setEnabled(status);
		deleteLocalFileButton.setEnabled(status);
		
		labelServer.setEnabled(status);
		ServerList.setEnabled(status);
		labelClient.setEnabled(status);
		ClientList.setEnabled(status);
	}

	// Abre uma janela com a mensagem passada
	public static void showMessage(String information) {
		JOptionPane.showMessageDialog(View.window, information);
	}

}

class Canvas extends JComponent {
	private static final long serialVersionUID = 5703217428905757134L;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setPaint(new Color(230, 230, 230));
		g2.fillRect(10, 17, 780, 40);
		g2.fillRect(10, 72, 385, 475);
		g2.fillRect(400, 71, 391, 476);
		
		g2.setPaint(new Color(180, 180, 180));
		g2.drawRect(9, 16, 781, 41);
		g2.drawRect(9, 71, 386, 476);
		g2.drawRect(409, 99, 246, 441);
		g2.drawRect(400, 71, 391, 476);
		g2.drawRect(139, 99, 246, 441);
		
	}
}
