import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/* Cria tudo: Janela, entidades, label, botão... */
	public static JFrame window = new JFrame("Programa 2 - Trabalho 2 - SO");
	public static JLabel labelTitulo = new JLabel();
	public static JLabel labelProdutor1 = new JLabel();
	public static JLabel labelProdutor2 = new JLabel();
	public static JLabel labelProdutor3 = new JLabel();
	public static JLabel statusProdutor1 = new JLabel();
	public static JLabel statusProdutor2 = new JLabel();
	public static JLabel statusProdutor3 = new JLabel();
	public static JTextArea ocorrencias = new JTextArea();
	public static JScrollPane panelOcorrencias = new JScrollPane(ocorrencias);
	public static JLabel labelConsumidor1 = new JLabel();
	public static JLabel labelConsumidor2 = new JLabel();
	public static JLabel labelConsumidor3 = new JLabel();
	public static JLabel labelConsumidor4 = new JLabel();
	public static JLabel labelConsumidor5 = new JLabel();
	public static JLabel statusConsumidor1 = new JLabel();
	public static JLabel statusConsumidor2 = new JLabel();
	public static JLabel statusConsumidor3 = new JLabel();
	public static JLabel statusConsumidor4 = new JLabel();
	public static JLabel statusConsumidor5 = new JLabel();
	public static JCheckBox checkBoxVersaoB = new JCheckBox();
	public static JButton iniciar = new JButton();

	public View(){
		/* Pega o Look and Feel do OS nativo e instala */
		String nativeLF = UIManager.getSystemLookAndFeelClassName();

		try {
			UIManager.setLookAndFeel(nativeLF);
		}
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		
		/* Label do trabalho */
		labelTitulo.setText(Configuracoes.NOME_PROJETO);
		labelTitulo.setBounds(Configuracoes.WIDTH_SIZE/2-150, 0, 300, 30);
		
		/* Produtores */
		labelProdutor1.setText("Produtor 1");
		labelProdutor1.setBounds(50, 70, 300, 30);
		labelProdutor2.setText("Produtor 2");
		labelProdutor2.setBounds(360, 70, 300, 30);
		labelProdutor3.setText("Produtor 3");
		labelProdutor3.setBounds(640, 70, 300, 30);
		statusProdutor1.setText("Status: ");
		statusProdutor1.setBounds(50, 100, 300, 30);
		statusProdutor2.setText("Status: ");
		statusProdutor2.setBounds(360, 100, 300, 30);
		statusProdutor3.setText("Status: ");
		statusProdutor3.setBounds(640, 100, 300, 30);
		
		/* Text Area de Ocorrencias */
		ocorrencias.setEditable(false);
		ocorrencias.setLineWrap(true);
		ocorrencias.setWrapStyleWord(true);
		panelOcorrencias.setBounds(10, 160, 780, 180);
		
		/* Consumidores */
		labelConsumidor1.setText("Consumidor 1");
		labelConsumidor1.setBounds(10, 350, 300, 30);
		labelConsumidor2.setText("Consumidor 2");
		labelConsumidor2.setBounds(182, 350, 300, 30);
		labelConsumidor3.setText("Consumidor 3");
		labelConsumidor3.setBounds(350, 350, 300, 30);
		labelConsumidor4.setText("Consumidor 4");
		labelConsumidor4.setBounds(507, 350, 300, 30);
		labelConsumidor5.setText("Consumidor 5");
		labelConsumidor5.setBounds(670, 350, 300, 30);
		statusConsumidor1.setText("Status: ");
		statusConsumidor1.setBounds(10, 380, 300, 30);
		statusConsumidor2.setText("Status: ");
		statusConsumidor2.setBounds(182, 380, 300, 30);
		statusConsumidor3.setText("Status: ");
		statusConsumidor3.setBounds(350, 380, 300, 30);
		statusConsumidor4.setText("Status: ");
		statusConsumidor4.setBounds(507, 380, 300, 30);
		statusConsumidor5.setText("Status: ");
		statusConsumidor5.setBounds(670, 380, 300, 30);
		
		/* Checkbox de versão b do trabalho */
		checkBoxVersaoB.setText("Versão B");
		checkBoxVersaoB.setToolTipText("Versão com aplicação da modificações sugeridas no trabalho");
		checkBoxVersaoB.setBounds(Configuracoes.WIDTH_SIZE/2-120, Configuracoes.HEIGHT-45, 120, 30);

		/* Botão de Iniciar */
		iniciar.addActionListener(this);
		iniciar.setText("Iniciar");
		iniciar.setActionCommand("iniciar");
		iniciar.setToolTipText("Clique aqui para iniciar a simulação");
		iniciar.setBounds(Configuracoes.WIDTH_SIZE/2, Configuracoes.HEIGHT-45, 120, 30);

		/* Adiciona na janela principal */
		window.add(labelTitulo);
		window.add(labelProdutor1);
		window.add(labelProdutor2);
		window.add(labelProdutor3);
		window.add(statusProdutor1);
		window.add(statusProdutor2);
		window.add(statusProdutor3);
		window.add(panelOcorrencias);
		window.add(labelConsumidor1);
		window.add(labelConsumidor2);
		window.add(labelConsumidor3);
		window.add(labelConsumidor4);
		window.add(labelConsumidor5);
		window.add(statusConsumidor1);
		window.add(statusConsumidor2);
		window.add(statusConsumidor3);
		window.add(statusConsumidor4);
		window.add(statusConsumidor5);
		window.add(checkBoxVersaoB);
		window.add(iniciar);
		window.add(this);

		/* Amarra tudo e exibe a janela */
		window.setPreferredSize(new Dimension(Configuracoes.WIDTH_SIZE, Configuracoes.HEIGHT));
		window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ("iniciar".equals(arg0.getActionCommand())) {
			/* Cria as entidades e as inicia */
			ocorrencias.setText("Inicio da Simulação!");
			checkBoxVersaoB.setDisabledIcon(null);
			iniciar.setText("Executando...");
			iniciar.setEnabled(false);
			Main.inicializaEntidades();
		}
		
	}
	
	public static void changeTextProdutor(long l, String text){
		int id = (int) l;
		
		switch(id){
			case 1:
				statusProdutor1.setText("Status: " + text);
			case 2:
				statusProdutor2.setText("Status: " + text);
			case 3:
				statusProdutor3.setText("Status: " + text);
			default:
				break;
		}
		
		window.repaint();
	}
	
	public static void changeTextConsumidor(long l, String text){
		int id = (int) l;
		
		switch(id){
			case 1:
				statusConsumidor1.setText("Status: " + text);
			case 2:
				statusConsumidor2.setText("Status: " + text);
			case 3:
				statusConsumidor3.setText("Status: " + text);
			case 4:
				statusConsumidor4.setText("Status: " + text);
			case 5:
				statusConsumidor5.setText("Status: " + text);
			default:
				break;
		}
		
		window.repaint();
	}
	
	public static void changeButtonIniciar(String text){
		iniciar.setText(text);
		window.repaint();
	}
	
	public static void addTextOcorrencias(String text){
		ocorrencias.setText(ocorrencias.getText() + "\n" + text);
		ocorrencias.setCaretPosition(ocorrencias.getDocument().getLength());
	}
	
}
