import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main {
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

	public static void criaGUI(){
		/* Cria tudo */
		JFrame window = new JFrame("Programa 2 - Trabalho 2 - SO");
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(Configuracoes.NOME_PROJETO);
		JButton iniciar = new JButton("Iniciar");
		
		/* Configura os itens */
		window.setPreferredSize(new Dimension(800, 600));
		panel.setPreferredSize(new Dimension(800, 600));
		iniciar.setSize(new Dimension(50, 20));
		
		/* Adiciona no painel */
		panel.add(label, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);
		panel.add(iniciar, BorderLayout.SOUTH);

		/* Amarra tudo e exibe */
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

	public static void main(String[] args) {
		/* Cria as entidades e as inicia */
		inicializaEntidades();
		
		/* Cria a interface gráfica */
		criaGUI();
	}

}
