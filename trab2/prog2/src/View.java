import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = -5509386488232238732L;

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
	public static JLabel itensConsumidor1 = new JLabel();
	public static JLabel itensConsumidor2 = new JLabel();
	public static JLabel itensConsumidor3 = new JLabel();
	public static JLabel itensConsumidor4 = new JLabel();
	public static JLabel itensConsumidor5 = new JLabel();
	
	public static JCheckBox checkBoxVersaoB = new JCheckBox();
	
	public static JButton iniciar = new JButton();

	public View(){
		/* Pega o Look and Feel do OS nativo e instala */
		try {
			String nativeLF = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(nativeLF);
		}
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		
		/* Label do trabalho */
		labelTitulo.setText(Configuracoes.NOME_PROJETO);
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		labelTitulo.setBounds(Configuracoes.WIDTH_SIZE/2-220, 0, 470, 30);
		
		/* Produtores */
		labelProdutor1.setText("Produtor 1");
		labelProdutor1.setFont(new Font("Arial", Font.BOLD, 16));
		labelProdutor1.setBounds(30, 50, 300, 30);
		labelProdutor2.setText("Produtor 2");
		labelProdutor2.setFont(new Font("Arial", Font.BOLD, 16));
		labelProdutor2.setBounds(330, 50, 300, 30);
		labelProdutor3.setText("Produtor 3");
		labelProdutor3.setFont(new Font("Arial", Font.BOLD, 16));
		labelProdutor3.setBounds(637, 50, 300, 30);
		statusProdutor1.setText("Status: ");
		statusProdutor1.setBounds(30, 80, 300, 30);
		statusProdutor2.setText("Status: ");
		statusProdutor2.setBounds(330, 80, 300, 30);
		statusProdutor3.setText("Status: ");
		statusProdutor3.setBounds(637, 80, 300, 30);
		
		/* Texrarea de Ocorrencias */
		ocorrencias.setEditable(false);
		ocorrencias.setLineWrap(true);
		ocorrencias.setWrapStyleWord(true);
		ocorrencias.setFont(new Font("Arial", Font.PLAIN, 12));
		panelOcorrencias.setBounds(20, 160, 760, 180);
		
		/* Consumidores */
		labelConsumidor1.setText("Consumidor 1");
		labelConsumidor1.setFont(new Font("Arial", Font.BOLD, 16));
		labelConsumidor1.setBounds(30, 350, 300, 30);
		labelConsumidor2.setText("Consumidor 2");
		labelConsumidor2.setFont(new Font("Arial", Font.BOLD, 16));
		labelConsumidor2.setBounds(188, 350, 300, 30);
		labelConsumidor3.setText("Consumidor 3");
		labelConsumidor3.setFont(new Font("Arial", Font.BOLD, 16));
		labelConsumidor3.setBounds(345, 350, 300, 30);
		labelConsumidor4.setText("Consumidor 4");
		labelConsumidor4.setFont(new Font("Arial", Font.BOLD, 16));
		labelConsumidor4.setBounds(505, 350, 300, 30);
		labelConsumidor5.setText("Consumidor 5");
		labelConsumidor5.setFont(new Font("Arial", Font.BOLD, 16));
		labelConsumidor5.setBounds(662, 350, 300, 30);
		statusConsumidor1.setText("Status: ");
		statusConsumidor1.setBounds(30, 380, 300, 30);
		statusConsumidor2.setText("Status: ");
		statusConsumidor2.setBounds(188, 380, 300, 30);
		statusConsumidor3.setText("Status: ");
		statusConsumidor3.setBounds(345, 380, 300, 30);
		statusConsumidor4.setText("Status: ");
		statusConsumidor4.setBounds(505, 380, 300, 30);
		statusConsumidor5.setText("Status: ");
		statusConsumidor5.setBounds(662, 380, 300, 30);
		itensConsumidor1.setText("Consumidos: ");
		itensConsumidor1.setBounds(30, 410, 300, 30);
		itensConsumidor2.setText("Consumidos: ");
		itensConsumidor2.setBounds(188, 410, 300, 30);
		itensConsumidor3.setText("Consumidos: ");
		itensConsumidor3.setBounds(345, 410, 300, 30);
		itensConsumidor4.setText("Consumidos: ");
		itensConsumidor4.setBounds(505, 410, 300, 30);
		itensConsumidor5.setText("Consumidos: ");
		itensConsumidor5.setBounds(662, 410, 300, 30);
		
		/* Checkbox de versão B do trabalho */
		checkBoxVersaoB.setText("Versão B");
		checkBoxVersaoB.setToolTipText("Versão com aplicação da modificações sugeridas no trabalho");
		checkBoxVersaoB.setBounds(Configuracoes.WIDTH_SIZE/2-120, Configuracoes.HEIGHT-65, 120, 30);

		/* Botão de Iniciar */
		iniciar.addActionListener(this);
		iniciar.setText("Iniciar");
		iniciar.setActionCommand("iniciar");
		iniciar.setToolTipText("Clique aqui para iniciar a simulação");
		iniciar.setBounds(Configuracoes.WIDTH_SIZE/2, Configuracoes.HEIGHT-65, 120, 30);

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
		window.add(itensConsumidor5);
		window.add(itensConsumidor1);
		window.add(itensConsumidor2);
		window.add(itensConsumidor3);
		window.add(itensConsumidor4);
		window.add(itensConsumidor5);
		window.add(checkBoxVersaoB);
		window.add(iniciar);
		window.add(this);
		window.add(new Canvas());

		/* Amarra tudo e exibe a janela */
		window.setPreferredSize(new Dimension(Configuracoes.WIDTH_SIZE, Configuracoes.HEIGHT));
		window.setFocusTraversalKeysEnabled(true);
		window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("iniciar")) {
			/* Muda a entrada */
			ocorrencias.setText("\t\t\t\tInicio da Simulação!");
			checkBoxVersaoB.setEnabled(false);
			iniciar.setText("Executando...");
			iniciar.setEnabled(false);
			
			/* Cria as entidades e as inicia */
			Main.inicializaEntidades();
		}
	}
	
	
	public static void changeStatusProdutor(long l, String text, Color cor){
		
		switch((int) l){
			case 1:
				statusProdutor1.setText("Status: " + text);
				statusProdutor1.setForeground(cor);
				break;
			case 2:
				statusProdutor2.setText("Status: " + text);
				statusProdutor2.setForeground(cor);
				break;
			case 3:
				statusProdutor3.setText("Status: " + text);
				statusProdutor3.setForeground(cor);
				break;
			default:
				break;
		}
	}
	
	public static void changeStatusConsumidor(long l, String text, Color cor){
		
		switch((int) l){
			case 1:
				statusConsumidor1.setText("Status: " + text);
				statusConsumidor1.setForeground(cor);
				break;
			case 2:
				statusConsumidor2.setText("Status: " + text);
				statusConsumidor2.setForeground(cor);
				break;
			case 3:
				statusConsumidor3.setText("Status: " + text);
				statusConsumidor3.setForeground(cor);
				break;
			case 4:
				statusConsumidor4.setText("Status: " + text);
				statusConsumidor4.setForeground(cor);
				break;
			case 5:
				statusConsumidor5.setText("Status: " + text);
				statusConsumidor5.setForeground(cor);
				break;
			default:
				break;
		}
	}
	
	public static void changeItensConsumidor(long l, int text){
		
		switch((int) l){
			case 1:
				itensConsumidor1.setText("Consumidos: " + text);
				break;
			case 2:
				itensConsumidor2.setText("Consumidos: " + text);
				break;
			case 3:
				itensConsumidor3.setText("Consumidos: " + text);
				break;
			case 4:
				itensConsumidor4.setText("Consumidos: " + text);
				break;
			case 5:
				itensConsumidor5.setText("Consumidos: " + text);
				break;
			default:
				break;
		}
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

class Canvas extends JComponent {
	private static final long serialVersionUID = 5703217428905757134L;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* Desenha os Produtores */
		g2.setPaint(Configuracoes.PRODUTOR_COLOR);
		g2.fillRect(20, 50, 150, 100);
		g2.fillRect(320, 50, 150, 100);
		g2.fillRect(630, 50, 150, 100);
		g2.setPaint(Color.GRAY);
		g2.drawRect(20, 50, 150, 100);
		g2.drawRect(320, 50, 150, 100);
		g2.drawRect(630, 50, 150, 100);
		
		/* Desenha os Conumidores */
		g2.setPaint(Configuracoes.CONSUMIDOR_COLOR);
		g2.fillRect(20, 350, 130, 100);
		g2.fillRect(178, 350, 130, 100);
		g2.fillRect(335, 350, 130, 100);
		g2.fillRect(492, 350, 130, 100);
		g2.fillRect(650, 350, 130, 100);
		g2.setPaint(Color.GRAY);
		g2.drawRect(20, 350, 130, 100);
		g2.drawRect(178, 350, 130, 100);
		g2.drawRect(335, 350, 130, 100);
		g2.drawRect(492, 350, 130, 100);
		g2.drawRect(650, 350, 130, 100);
	}
}
