#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>
#include <sys/timeb.h>

#include <sys/wait.h>

#include <sys/types.h>
#include <sys/ipc.h> 
#include <sys/shm.h>

struct shared{
	double *variancia;
	int shmid, *produtoInterno, *produtoInterno_compartilhado, *menor_i, *menor_j, *maior_i, *maior_j, *menor, *maior;
	key_t key;
};

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
		if (v[i] == NULL){
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
		printf ("\n\nErro: Parâmetro invalido!\n\n");
		return (v);
	}
	
	//Libera as linhas da matriz
	for(i=0; i<m; i++){
		free (v[i]);
	}
	
	//Libera a matriz
	free (v);
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
	int i, j, m, k, somatorio, **matriz, *produtoInterno, *pids, id, status;
	double soma_desvio, desvio_padrao, tempo_execucao;
	struct timeb inicio_execucao, fim_execucao;
	
	//Aloca recursos de compartilhamento de memória
	struct shared compartilhado;
	srand((unsigned)time(NULL));
	
	
	printf("\nMontando a matriz... \n\n");
	//Recebe os valores iniciais de m e k
	printf("Defina o número de linhas  -> m = ");
	scanf("%i", &m);
	printf("Defina o número de colunas -> k = ");
	scanf("%i", &k);
	
	
	while( m!= 0 && k!=0 ){
		//Aloca a matriz Principal, vetor de Produto Interno e de PIDs
		matriz = aloca_matriz(m, k);
		compartilhado.produtoInterno = aloca_vetor(m);
		pids = aloca_vetor(m);
		
		
		//Inicializa outros valores da iteração
		compartilhado.key = 1234;
		compartilhado.menor = 0;
		compartilhado.maior = 0;
		soma_desvio = 0;
		desvio_padrao = 0;
		
		
		//Cria o compartilhamento de memória
		if((compartilhado.shmid = shmget(IPC_PRIVATE, 2*m*sizeof(int), IPC_CREAT | SHM_W | SHM_R)) < 0){
			perror("shmget");
			exit(1);
		}
		if((compartilhado.produtoInterno_compartilhado = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.menor = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.maior = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.menor_i = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.maior_i = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.menor_j = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.maior_j = shmat(compartilhado.shmid, NULL, 0)) == (int *) -1){
			perror("shmat");
			exit(1);
		}
		if((compartilhado.variancia = shmat(compartilhado.shmid, NULL, 0)) == (double *) -1){
			perror("shmat");
			exit(1);
		}
		compartilhado.produtoInterno_compartilhado = compartilhado.produtoInterno;
		*compartilhado.variancia = 0;
		
		
		//Inicia a contagem do tempo de execução
		ftime(&inicio_execucao);
		
		
		//Insere na matriz
		printf("\nInserindo valores na Matriz...");
		for(i=0; i<m; i++){
			for(j=0; j<k; j++){
				//gera o número aleatório e armazena na matriz
				matriz[i][j] = (rand()%201)-100;
			}
		}
		printf(" Concluído!\n");
		
		printf("\nCalculando a Produto Interno...");
		fflush(stdout);
		//Calcula o somatório
		for(i=0; i<m; i++){
			somatorio = 0;
			pids[i] = fork();
			
			if(pids[i] == 0){
				for(j=0; j<k; j++){
					//Realiza o produto interno
					somatorio += matriz[i][j] * matriz[j][i];
					
					//detecta o maior e menor
					if(matriz[i][j] <= *compartilhado.menor){
						*compartilhado.menor = matriz[i][j];
						*compartilhado.menor_i = i+1;
						*compartilhado.menor_j = j+1;
					}
					if(matriz[i][j] >= *compartilhado.maior){
						*compartilhado.maior = matriz[i][j];
						*compartilhado.maior_i = i+1;
						*compartilhado.maior_j = j+1;
					}
				}
				
				//Armazena o PI(i) e soma para cálculo de média para o desvio padrão
				compartilhado.produtoInterno_compartilhado[i] = somatorio;
				*compartilhado.variancia += compartilhado.produtoInterno_compartilhado[i];
				exit(0);
				
			}
		}
		printf(" Concluído!\n");
		
		
		//Calcula o desvio padrão
		for(i=0; i<m; i++){
			soma_desvio += pow(compartilhado.produtoInterno_compartilhado[i]-(*compartilhado.variancia/m), 2);
		}
		desvio_padrao = sqrt(soma_desvio/m);
		
		
		//Calcula o tempo de execução
		ftime(&fim_execucao);
		tempo_execucao = (((fim_execucao.time-inicio_execucao.time)*1000.0+fim_execucao.millitm)-inicio_execucao.millitm)/1000.0;
		
		
		//Libera a matriz e o Produto Interno
		free_matriz(m, k, matriz);
		free(compartilhado.produtoInterno_compartilhado);
		
		
		//Exibe os valores resultantes
		printf("--------------------------Valores Aferidos--------------------------\n");
		printf("Menor valor = %i (i=%i, j=%i) e Maior valor = %i (i=%i, j=%i)\n", *compartilhado.menor, *compartilhado.menor_i, *compartilhado.menor_j, *compartilhado.maior, *compartilhado.maior_i, *compartilhado.maior_j);
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
