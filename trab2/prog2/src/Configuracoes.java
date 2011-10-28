import java.awt.Color;

public interface Configuracoes {
	/* Tempo máximo que leva para produzir/consumir um recurso */
	int MAX_TIME_TO_PRODUCE = 300;
	int MAX_TIME_TO_CONSUME = 200;
	
	/* Número de recursos a ser produzido */
	int TOTAL_RECURSOS_A_SER_PRODUZIDO = 100;

	/* Número de recursos a ser produzido */
	int MAX_RESOURCE_VALUE = 50;
	
	/* Propriedades da Janela */
	int WIDTH_SIZE = 800;
	int HEIGHT = 600;
	String NOME_PROJETO = "Simulação do Sistema Produtor/Consumidor";
	
	/* Strings de Exibição*/
	Color WAITING_COLOR = new Color(237, 169, 39);
	Color ACTIVE_COLOR = new Color(51, 156, 0);
	Color TERMINATED_COLOR = Color.RED;
	Color SLEEPING_COLOR = Color.GRAY;
	
	/* Fundo das entidades */
	Color PRODUTOR_COLOR = new Color(229, 239, 248);
	Color CONSUMIDOR_COLOR = new Color(241, 226, 228);
}
