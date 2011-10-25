import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Desenho extends Canvas {
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		/* Produtor 1 */
		{
			g.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			g.fillRect(10, 50, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(10, 50, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Produtor 1", 30, 40);
		}
		
		/* Produtor 2 */
		{
			g.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			g.fillRect(350, 50, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(350, 50, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Produtor 2", 370, 40);
		}
		
		/* Produtor 3 */
		{
			g.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			g.fillRect(690, 50, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(690, 50, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Produtor 3", 710, 40);
		}
		
		/* Consumidor 1 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(10, 410, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(10, 410, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Consumidor 1", 20, 530);
		}

		/* Consumidor 2 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(185, 410, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(185, 410, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Consumidor 2", 192, 530);
		}

		/* Consumidor 3 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(350, 410, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(350, 410, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Consumidor 3", 358, 530);
		}

		/* Consumidor 4 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(515, 410, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(515, 410, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Consumidor 4", 522, 530);
		}

		/* Consumidor 5 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(680, 410, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(680, 410, 100, 100);
			g.setColor(Configuracoes.COR_FONTE);
			g.drawString("Consumidor 5", 685, 530);
		}
    }
	
	public void atualizaProdutor(Graphics g, Color cor, int produtor){
		/* Produtor produtor */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_ATIVO);
			g.fillRect(produtor*200+90, 40, 100, 100);
			g.setColor(Configuracoes.BORDA_ENTIDADES);
			g.drawRect(produtor*200+90, 40, 100, 100);
		}
	}
}
