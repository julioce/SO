import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class View extends Canvas implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/* Cria tudo: Janela, label, label, botão... */
	JFrame window = new JFrame("Programa 2 - Trabalho 2 - SO");
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	JButton iniciar = new JButton();

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
		
		/* Botão de Iniciar */
		iniciar.addActionListener(this);
		iniciar.setText("Iniciar");
		iniciar.setActionCommand("iniciar");
		iniciar.setToolTipText("Clique aqui para iniciar a simulação");
		iniciar.setBounds(Configuracoes.WIDTH_SIZE/2-60, Configuracoes.HEIGHT-45, 120, 30);
		

		/* Adiciona na janela principal */
		panel.setLayout(null);
		panel.add(label);
		panel.add(iniciar);
		panel.add(this);
		window.setContentPane(panel);

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
	
	public void paint(Graphics g) {
		Graphics canvas = (Graphics)g;
		
		/* Produtor 1 */
		{
			canvas.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			canvas.fillRect(10, 50, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(10, 50, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Produtor 1", 30, 40);
		}
		
		/* Produtor 2 */
		{
			canvas.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			canvas.fillRect(350, 50, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(350, 50, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Produtor 2", 370, 40);
		}
		
		/* Produtor 3 */
		{
			canvas.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			canvas.fillRect(690, 50, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(690, 50, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Produtor 3", 710, 40);
		}
		
		/* Consumidor 1 */
		{
			canvas.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			canvas.fillRect(10, 410, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(10, 410, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Consumidor 1", 20, 530);
		}

		/* Consumidor 2 */
		{
			canvas.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			canvas.fillRect(185, 410, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(185, 410, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Consumidor 2", 192, 530);
		}

		/* Consumidor 3 */
		{
			canvas.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			canvas.fillRect(350, 410, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(350, 410, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Consumidor 3", 358, 530);
		}

		/* Consumidor 4 */
		{
			canvas.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			canvas.fillRect(515, 410, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(515, 410, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Consumidor 4", 522, 530);
		}

		/* Consumidor 5 */
		{
			canvas.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			canvas.fillRect(680, 410, 100, 100);
			canvas.setColor(Configuracoes.BORDA_ENTIDADES);
			canvas.drawRect(680, 410, 100, 100);
			canvas.setColor(Configuracoes.COR_FONTE);
			canvas.drawString("Consumidor 5", 685, 530);
		}
    }
}
