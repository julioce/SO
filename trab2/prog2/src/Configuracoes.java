import java.awt.Color;

public interface Configuracoes {
	/* Tempo máximo que leva para produzir/consumir um recurso */
	int MAX_TIME_TO_SLEEP = 10000;
	
	/* Número de recursos a ser produzido */
	int TOTAL_RECURSOS_A_SER_PRODUZIDO = 10;

	/* Valor limite do recurso */
	int MAX_RESOURCE_VALUE = 10000;
	
	/* Propriedades da Janela */
	int WIDTH_SIZE = 800;
	int HEIGHT = 600;
	String NOME_PROJETO = "Simulação do Sistema Produtor/Consumidor";
	
	/* Cores da simulação */
	Color FUNDO_PRODUTOR_ATIVO = Color.GREEN;
	Color FUNDO_PRODUTOR_INATIVO = Color.YELLOW;
	Color FUNDO_CONSUMIDOR_ATIVO = Color.BLUE;
	Color FUNDO_CONSUMIDOR_INATIVO = Color.DARK_GRAY;
}
