#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>
#include <sys/timeb.h>

int **aloca_matriz(int m, int k) {
	//ponteiro para a matriz e variável de iteração
	int **v, i;
	
	//Veririfica os parâmetros
	if(m < 1 || k < 1 || m != k){
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
	printf(" Concluído!\n");
	
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
	int **matriz, *produtoInterno, i, j, menor_i, menor_j, maior_i, maior_j, k, m, menor, maior, somatorio;
	double soma, soma_desvio, desvio_padrao, tempo_execucao;
	srand((unsigned)time(NULL));
	struct timeb inicio_execucao, fim_execucao;
	
	
	printf("\nMontando a matriz... \n\n");
	//Recebe os valores iniciais de m e k
	printf("Defina o número de linhas  -> m = ");
	scanf("%i", &m);
	printf("Defina o número de colunas -> k = ");
	scanf("%i", &k);
	
	
	while( m!= 0 && k!=0 ){
		//Aloca a matriz e vetor de Produto Interno
		matriz = aloca_matriz(m, k);
		produtoInterno = aloca_vetor(m);
		
		
		//Inicializa outros valores da iteração
		menor = 0;
		maior = 0;
		soma = 0;
		soma_desvio = 0;
		desvio_padrao = 0;
		
		
		//Inicia a contagem do tempo de execução
		ftime(&inicio_execucao);
		
		
		//Gera sobre a matriz
		printf("\nInserindo valores na Matriz...");
		for(i=0; i<m; i++){
			for(j=0; j<k; j++){
				//gera o número aleatório e armazena na matriz
				matriz[i][j] = (rand()%201)-100;
			}
		}
		printf(" Concluído!\n\n");
		
		
		printf("\nCalculando a Produto Interno...");
		fflush(stdout);
		//Calcula o somatório
		for(i=0; i<m; i++){
			somatorio = 0;
			
			for(j=0; j<k; j++){
				//Realiza o produto interno
				somatorio += matriz[i][j] * matriz[j][i];
				
				//detecta o maior e menor
				if(matriz[i][j] <= menor){
					menor = matriz[i][j];
					menor_i = i+1;
					menor_j = j+1;
				}
				if(matriz[i][j] >= maior){
					maior = matriz[i][j];
					maior_i = i+1;
					maior_j = j+1;
				}
			}
			
			//Armazena o PI(i) e soma para cálculo de média
			produtoInterno[i] = somatorio;
			soma += produtoInterno[i];
		}
		printf(" Concluído!\n");
		
		
		//Calcula o desvio padrão
		for(i=0; i<m; i++){
			soma_desvio += pow(produtoInterno[i]-(soma/m), 2);
		}
		desvio_padrao = sqrt(soma_desvio/m);
		
		
		//Calcula o tempo de execução
		ftime(&fim_execucao);
		tempo_execucao = (((fim_execucao.time-inicio_execucao.time)*1000.0+fim_execucao.millitm)-inicio_execucao.millitm)/1000.0;
		
		
		//Libera a matriz e o Produto Interno
		free_matriz(m, k, matriz);
		free(produtoInterno);
		
		
		//Exibe os valores resultantes
		printf("--------------------------Valores Aferidos--------------------------\n");
		printf("Menor valor = %i (i=%i, j=%i) e Maior valor = %i (i=%i, j=%i)\n", menor, menor_i, menor_j, maior, maior_i, maior_j);
		printf("Desvio Padrão = %f\n", desvio_padrao);
		printf("Tempo de execução = %.3f segundos\n", tempo_execucao);
		printf("---------------------------------------------------------------------\n");
		
		//Recebe os valores de m e k para nova iteração
		printf("\nMontando a matriz da nova iteração\n\n");
		printf("Defina o número de linhas  -> m = ");
		scanf("%i", &m);
		printf("Defina o número de colunas -> k = ");
		scanf("%i", &k);


	}
	
	exit(0);
}

