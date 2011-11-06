#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>
#include <sys/timeb.h>

#define INF 0x33333333

int **aloca_matriz(int m, int k) {
	//ponteiro para a matriz e variável de iteração
	int **v, i;
	
	//Veririfica os parâmetros
	if(m < 1 || k < 1 ){
		printf ("\n\nErro: Valores de m e k inválidos!\n\n");
		exit(1);
	}
	
	//Aloca a linha da matriz
	v = (int **) calloc (m, sizeof(int *));
	if(v == NULL){
		printf ("\n\nErro: Memória insuficiente!\n\n");
		exit(1);
	}
	
	//Aloca as colunas
	for( i = 0; i < m; i++ ){
		v[i] = (int*) calloc (k, sizeof(int));
		if(v[i] == NULL){
			printf ("\n\nErro: Memória insuficiente!\n\n");
			exit(1);
		}
	}
	
	//Retorna a matriz
	return (v);
}



int **free_matriz(int m, int k, int **v){
	int  i;
	if(v == NULL){
		exit(1);
	}
	
	//Verifica parâmetros
	if(m < 1 || k < 1){
		printf("\n\nErro: Parâmetro invalido!\n\n");
		return(v);
	}
	
	//Libera as linhas da matriz
	for(i=0; i<m; i++){
		free(v[i]);
	}
	
	//Libera a matriz
	free(v);
	return (NULL);
}


int *aloca_vetor(int m){
	//ponteiro do vetor
	int *v;
	
	//Veririfica o parâmetro
	if(m < 1){
		printf ("\n\nErro: Parâmetro invalido!\n\n");
		exit(1);
	}
	
	//Aloca a linha da matriz
	v = (int *) calloc (m+1, sizeof(int *));
	if(v == NULL){
		printf ("\n\nErro: Memória insuficiente!\n\n");
		exit(1);
	}
	
	//Retorna o vetor
	return (v);
}


int main(void){
	int **matriz, *produtoInterno, i, j, menor_i, maior_i, k, m, menor, maior, somatorio;
	double soma, soma_desvio, desvio_padrao, tempo_execucao;
	time_t time(time_t *t);
	srand((unsigned)time(NULL));
	struct timeb inicio_execucao, fim_execucao;
	
	
	//Recebe os valores iniciais de m e k
	printf("Defina o número de linhas  -> m = ");
	scanf("%i", &m);
	printf("Defina o número de colunas -> k = ");
	scanf("%i", &k);
	
	
	while( m!= 0 && k!=0 ){
		//Inicia a contagem do tempo de execução
		ftime(&inicio_execucao);
		
		//Aloca a matriz e vetor de Produto Interno
		printf("\nMontando a matriz...\n");
		matriz = aloca_matriz(m, k);
		produtoInterno = aloca_vetor(m);
		printf("Concluído!\n\n");
		
		
		//Inicializa outros valores da iteração
		menor = INF;
		maior = -INF;
		menor_i = 0;
		maior_i = 0;
		soma = 0;
		soma_desvio = 0;
		desvio_padrao = 0;
		
		
		//Gera sobre a matriz
		printf("Inserindo valores na Matriz...\n");
		for(i=0; i<m; i++){
			for(j=0; j<k; j++){
				//gera o número aleatório e armazena na matriz
				matriz[i][j] = (rand()%201)-100;
			}
		}
		printf("Concluído!\n\n");
		
		
		printf("Calculando a Produto Interno...\n");
		fflush(stdout);
		//Calcula o somatório
		for(i=0; i<m; i++){
			somatorio = 0;
			
			for(j=0; j<k; j++){
				//Realiza o produto interno
				somatorio += matriz[i][j] * matriz[i][j];
			}
			
			//Armazena o PI(i) e soma para cálculo de média
			produtoInterno[i] = somatorio;
			soma += produtoInterno[i];
		}
		printf("Concluído!\n");
		
		
		//Calcula o desvio padrão
		for(i=0; i<m; i++){
			soma_desvio += pow(produtoInterno[i]-(soma/m), 2);
			
			//Detecta o maior e menor
			if(produtoInterno[i] <= menor){
				menor = produtoInterno[i];
				menor_i = i+1;
			}
			if(produtoInterno[i] >= maior){
				maior = produtoInterno[i];
				maior_i = i+1;
			}
		}
		desvio_padrao = sqrt(soma_desvio/m);
		
		
		//Calcula o tempo de execução
		ftime(&fim_execucao);
		tempo_execucao = (((fim_execucao.time-inicio_execucao.time)*1000.0+fim_execucao.millitm)-inicio_execucao.millitm)/1000.0;
		
		
		//Exibe os valores resultantes
		printf("--------------------------Valores Aferidos---------------------------\n");
		printf("Menor valor = %i (m=%i) e Maior valor = %i (m=%i)\n", menor, menor_i, maior, maior_i);
		printf("Desvio Padrão = %f\n", desvio_padrao);
		printf("Tempo de execução = %.3f segundos\n", tempo_execucao);
		printf("---------------------------------------------------------------------\n");
		
		
		//Libera a matriz
		free_matriz(m, k, matriz);
		free(produtoInterno);
		
		
		//Recebe os valores de m e k para nova iteração
		printf("Montando a matriz da nova iteração\n\n");
		printf("Defina o número de linhas  -> m = ");
		scanf("%i", &m);
		printf("Defina o número de colunas -> k = ");
		scanf("%i", &k);
	}
	
	exit(0);
}

