import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main implements ActionListener {
	/* Inicia a contagem de produtos */
	private static Integer recursosProduzidos = 0;
	
	public static int getRecursosProduzidos(){
		return recursosProduzidos;
	}
	
	public static void setRecursosProduzidos(Integer recursosProduzidos) {
		Main.recursosProduzidos = recursosProduzidos;
	}
	
	public static void inicializaEntidades(){
		/* Repositório de recursos */  
		Estoque estoque = new Estoque();
		
		/* Inicializa os Produtores */
		Produtor produtor1 = new Produtor(estoque);
		produtor1.setNome("Produtor 1");
		Produtor produtor2 = new Produtor(estoque);
		produtor2.setNome("Produtor 2");
		Produtor produtor3 = new Produtor(estoque);
		produtor3.setNome("Produtor 3");
		
		/* Inicializa os Consumidores */
		Consumidor consumidor1 = new Consumidor(estoque);
		consumidor1.setNome("Consumidor 1");
		Consumidor consumidor2 = new Consumidor(estoque);
		consumidor2.setNome("Consumidor 2");
		Consumidor consumidor3 = new Consumidor(estoque);
		consumidor3.setNome("Consumidor 3");
		Consumidor consumidor4 = new Consumidor(estoque);
		consumidor4.setNome("Consumidor 4");
		Consumidor consumidor5 = new Consumidor(estoque);
		consumidor5.setNome("Consumidor 5");
		
		consumidor1.setPriority(Thread.MIN_PRIORITY);
		consumidor2.setPriority(Thread.MIN_PRIORITY);
		consumidor3.setPriority(Thread.MIN_PRIORITY);
		consumidor4.setPriority(Thread.MIN_PRIORITY);
		consumidor5.setPriority(Thread.MIN_PRIORITY);
		
		/* Inicia as threads */
		produtor1.start();
		produtor2.start();
		produtor3.start();
		consumidor1.start();
		consumidor2.start();
		consumidor3.start();
		consumidor4.start();
		consumidor5.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("iniciar".equals(e.getActionCommand())) {
			/* Cria as entidades e as inicia */
			inicializaEntidades();
		}
	}

	public static void main(String[] args) {
		Main main = new Main();
		
		/* Pega o Look and Feel do OS nativo e instala */
		String nativeLF = UIManager.getSystemLookAndFeelClassName();

		try {
			UIManager.setLookAndFeel(nativeLF);
		}
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		
		/* Cria tudo: Janela, canvas, label, botão... */
		JFrame window = new JFrame("Programa 2 - Trabalho 2 - SO");
		Desenho canvas = new Desenho();
		JLabel label = new JLabel(Configuracoes.NOME_PROJETO);
		JButton iniciar = new JButton("Iniciar");
		
		/* Label do trabalho */
		{
			label.setText(Configuracoes.NOME_PROJETO);
			label.setLayout(null);
			label.setBounds(Configuracoes.WIDTH_SIZE/2-150, 0, 300, 30);
		}
		
		/* Botão de Iniciar */
		{
			iniciar.addActionListener(main);
			iniciar.setLayout(null);
			iniciar.setActionCommand("iniciar");
			iniciar.setToolTipText("Clique aqui para iniciar a simulação");
			iniciar.setBounds(Configuracoes.WIDTH_SIZE/2-50, Configuracoes.HEIGHT-45, 100, 30);
		}
		
		/* Adiciona na janela principal */
		{
			window.add(label);
			window.add(iniciar);
			window.add(canvas);
		}

		/* Amarra tudo e exibe a janela */
		window.setPreferredSize(new Dimension(Configuracoes.WIDTH_SIZE, Configuracoes.HEIGHT));
		window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

}
