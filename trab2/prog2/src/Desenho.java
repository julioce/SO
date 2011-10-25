import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Desenho extends Canvas {
	private static final long serialVersionUID = 1L;
	public Desenho()
    {
    }

	public void paint(Graphics g) {
		/* Produtor 1 */
		{
			g.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			g.fillRect(10, 40, 100, 100);
			g.setColor(Color.black);
			g.drawRect(10, 40, 100, 100);
		}
		
		/* Produtor 2 */
		{
			g.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			g.fillRect(340, 40, 100, 100);
			g.setColor(Color.black);
			g.drawRect(340, 40, 100, 100);
		}
		
		/* Produtor 3 */
		{
			g.setColor(Configuracoes.FUNDO_PRODUTOR_INATIVO);
			g.fillRect(690, 40, 100, 100);
			g.setColor(Color.black);
			g.drawRect(690, 40, 100, 100);
		}
		
		/* Consumidor 1 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(10, 440, 100, 100);
			g.setColor(Color.black);
			g.drawRect(10, 440, 100, 100);
		}

		/* Consumidor 2 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(185, 440, 100, 100);
			g.setColor(Color.black);
			g.drawRect(185, 440, 100, 100);
		}

		/* Consumidor 3 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(350, 440, 100, 100);
			g.setColor(Color.black);
			g.drawRect(350, 440, 100, 100);
		}

		/* Consumidor 4 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(515, 440, 100, 100);
			g.setColor(Color.black);
			g.drawRect(515, 440, 100, 100);
		}

		/* Consumidor 5 */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_INATIVO);
			g.fillRect(680, 440, 100, 100);
			g.setColor(Color.black);
			g.drawRect(680, 440, 100, 100);
		}
    }
	
	public void atualizaProdutor(Graphics g, Color cor, int produtor){
		/* Produtor produtor */
		{
			g.setColor(Configuracoes.FUNDO_CONSUMIDOR_ATIVO);
			g.fillRect(produtor*200+90, 40, 100, 100);
			g.setColor(Color.black);
			g.drawRect(produtor*200+90, 40, 100, 100);
		}
	}
}
