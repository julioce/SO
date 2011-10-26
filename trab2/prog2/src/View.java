import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/* Cria tudo: Janela, panel, label, botão... */
	public static JFrame window = new JFrame("Programa 2 - Trabalho 2 - SO");
	public static JPanel panel = new JPanel();
	public static JLabel label = new JLabel();
	public static JLabel labelProdutor1 = new JLabel();
	public static JLabel labelProdutor2 = new JLabel();
	public static JLabel labelProdutor3 = new JLabel();
	public static JLabel labelConsumidor1 = new JLabel();
	public static JLabel labelConsumidor2 = new JLabel();
	public static JLabel labelConsumidor3 = new JLabel();
	public static JLabel labelConsumidor4 = new JLabel();
	public static JLabel labelConsumidor5 = new JLabel();
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
		label.setText(Configuracoes.NOME_PROJETO);
		label.setBounds(Configuracoes.WIDTH_SIZE/2-150, 0, 300, 30);
		
		/* Label dos Produtores */
		labelProdutor1.setText("Produtor 1");
		labelProdutor1.setBounds(30, 70, 300, 30);
		labelProdutor2.setText("Produtor 2");
		labelProdutor2.setBounds(370, 70, 300, 30);
		labelProdutor3.setText("Produtor 3");
		labelProdutor3.setBounds(710, 70, 300, 30);
		
		/* Label dos Consumidores */
		labelConsumidor1.setText("Consumidor 1");
		labelConsumidor1.setBounds(20, 330, 300, 30);
		labelConsumidor2.setText("Consumidor 2");
		labelConsumidor2.setBounds(192, 330, 300, 30);
		labelConsumidor3.setText("Consumidor 3");
		labelConsumidor3.setBounds(358, 330, 300, 30);
		labelConsumidor4.setText("Consumidor 4");
		labelConsumidor4.setBounds(522, 330, 300, 30);
		labelConsumidor5.setText("Consumidor 5");
		labelConsumidor5.setBounds(685, 330, 300, 30);
		
		/* Botão de Iniciar */
		iniciar.addActionListener(this);
		iniciar.setText("Iniciar");
		iniciar.setActionCommand("iniciar");
		iniciar.setToolTipText("Clique aqui para iniciar a simulação");
		iniciar.setBounds(Configuracoes.WIDTH_SIZE/2-60, Configuracoes.HEIGHT-45, 120, 30);

		/* Adiciona na janela principal */
		window.add(label);
		window.add(labelProdutor1);
		window.add(labelProdutor2);
		window.add(labelProdutor3);
		window.add(labelConsumidor1);
		window.add(labelConsumidor2);
		window.add(labelConsumidor3);
		window.add(labelConsumidor4);
		window.add(labelConsumidor5);
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
			Main.inicializaEntidades();
			iniciar.setText("Executando...");
		}
		
	}
	
	public static void changeTextProdutor(long l, String text){
		int id = (int) l;
		
		switch(id){
			case 1:
				labelProdutor1.setText(text);
			case 2:
				labelProdutor2.setText(text);
			case 3:
				labelProdutor3.setText(text);
			default:
				break;
		}
		
		window.repaint();
	}
	
	public static void changeTextConsumidor(long l, String text){
		int id = (int) l;
		
		switch(id){
			case 1:
				labelConsumidor1.setText(text);
			case 2:
				labelConsumidor2.setText(text);
			case 3:
				labelConsumidor3.setText(text);
			case 4:
				labelConsumidor4.setText(text);
			case 5:
				labelConsumidor5.setText(text);
			default:
				break;
		}
		
		window.repaint();
	}
	
	public static void changeButtonIniciar(String text){
		iniciar.setText(text);
		window.repaint();
	}
	
}
