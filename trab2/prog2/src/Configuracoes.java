import java.awt.Color;

public interface Configuracoes {
	/* Tempo máximo que leva para produzir/consumir um recurso */
	int MAX_TIME_TO_PRODUCE = 1000;
	int MAX_TIME_TO_CONSUME = 2000;
	
	/* Número de recursos a ser produzido */
	int TOTAL_RECURSOS_A_SER_PRODUZIDO = 1000;

	/* Número de recursos a ser produzido */
	int MAX_RESOURCE_VALUE = 50;
	
	/* Propriedades da Janela */
	int WIDTH_SIZE = 800;
	int HEIGHT = 600;
	String NOME_PROJETO = "Simulação do Sistema Produtor/Consumidor";
	
	/* Cores da simulação */
	Color COR_FONTE = Color.BLACK;
	Color BORDA_ENTIDADES = Color.LIGHT_GRAY;
	Color FUNDO_PRODUTOR_ATIVO = Color.GREEN;
	Color FUNDO_PRODUTOR_INATIVO = Color.YELLOW;
	Color FUNDO_CONSUMIDOR_ATIVO = Color.BLUE;
	Color FUNDO_CONSUMIDOR_INATIVO = Color.DARK_GRAY;
}
