import java.awt.Color;

public interface Configuracoes {
	/* Tempo máximo em milisegundos que leva para produzir/consumir um recurso */
	int MAX_TIME_TO_PRODUCE = 30;
	int MAX_TIME_TO_CONSUME = 15;
	
	/* Número de recursos a ser produzido */
	int TOTAL_RECURSOS_A_SER_PRODUZIDO = 1000;

	/* Número de recursos a ser produzido */
	int MAX_RESOURCE_VALUE = 50;
	
	/* Propriedades da Janela */
	int WIDTH_SIZE = 800;
	int HEIGHT = 600;
	String NOME_PROJETO = "Simulação do Sistema Produtor/Consumidor";
	
	/* Strings de Exibição*/
	Color WAITING_COLOR = new Color(255, 144, 0);
	Color ACTIVE_COLOR = new Color(0, 151, 0);
	Color TERMINATED_COLOR = Color.RED;
	Color SLEEPING_COLOR = Color.GRAY;
	
	/* Fundo das entidades */
	Color PRODUTOR_COLOR = new Color(229, 239, 248);
	Color CONSUMIDOR_COLOR = new Color(241, 226, 228);
	Color LEGENDA_COLOR = new Color(225, 225, 225);
}
