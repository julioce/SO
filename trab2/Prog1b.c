#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>
#include <sys/timeb.h>

#include <sys/types.h>
#include <sys/ipc.h> 
#include <sys/shm.h>

#include <pthread.h>

#define INF 0x33333333
#define THREADS 2

int i, j, m, k, somatorio, **matriz, *produtoInterno, id, status;
int menor_matriz, maior_matriz, menor_matriz_i, maior_matriz_i, menor_matriz_j, maior_matriz_j;
int parte[THREADS], returnThread[THREADS];
double soma_desvio, desvio_padrao, tempo_execucao;
struct timeb inicio_execucao, fim_execucao;
pthread_t thread[THREADS];
pthread_attr_t atributosThread;

//Cria estrutura de compartilhamento de memória
struct shared compartilhado;
int shmid[10];

struct shared{
	double *variancia;
	int shmid, *produtoInterno, *produtoInterno_compartilhado, *menor_i, *maior_i, *menor, *maior;
	key_t key;
};

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
		if (v[i] == NULL){
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


int max(int a, int b){
	if(a >= b){return a;}
	else{return b;}
}

void *calculaProdutoInterno(void *arg){
	int i, j, inicio, fim, somatorio, *pos;
	
	pos = (int *) arg;
	inicio = (*pos - 1)*floor(m/THREADS);
	fim = inicio + floor(m/THREADS) ;
	
	//Detecta se a thread é a última. Nesse caso precisa calcular também o resto da divisão
	if( m-fim < THREADS ){
		fim = m;
	}
	
	//Calcula o somatório
	for(i=inicio; i<fim; i++){
		somatorio = 0;
		
		for(j=0; j<k; j++){
			//Realiza o produto interno
			somatorio += matriz[i][j] * matriz[i][j];
		}
		
		//Armazena o PI(i) e soma para cálculo de média para o desvio padrão
		compartilhado.produtoInterno_compartilhado[i] = somatorio;
		*compartilhado.variancia += compartilhado.produtoInterno_compartilhado[i];
	}
	
	printf("\nThread %i terminou de calcular as linhas entre %i e %i", *pos, inicio+1, fim);
	
	//Termina a thread
	pthread_exit(NULL);
}

int main(void){
	
	//Recebe os valores iniciais de m e k
	printf("Defina o número de linhas  -> m = ");
	scanf("%i", &m);
	printf("Defina o número de colunas -> k = ");
	scanf("%i", &k);
	
	//Inicia o parâmetro da contagem de tempo
	srand((unsigned)time(NULL));
	
	//Configura os atributos da Thread
	pthread_attr_init(&atributosThread);
	pthread_attr_setdetachstate(&atributosThread, PTHREAD_CREATE_JOINABLE);
	//No caso, tipo Kernel
	pthread_attr_setscope(&atributosThread, PTHREAD_SCOPE_SYSTEM);
	
	while( m!= 0 && k!=0 ){
		//Cria o compartilhamento de memória
		for(i=0; i<6; i++){
			if(i == 0){
				if( (shmid[i] = shmget(getpid(), max(m, k)*sizeof(int), IPC_CREAT | SHM_W | SHM_R)) < 0 ){
					if((shmid[i] = shmget((unsigned)time(NULL), max(m, k)*sizeof(int), IPC_CREAT | SHM_W | SHM_R)) < 0){
						perror("shmget 1");
						exit(1);
					}
				}
			}
			else if(i > 0 && i < 5){
				if((shmid[i] = shmget(getpid()+i, sizeof(int), IPC_CREAT | SHM_W | SHM_R)) < 0){
					perror("shmget 2");
					exit(1);
				}
			}
			else{
				if((shmid[i] = shmget(getpid()+5, sizeof(double), IPC_CREAT | SHM_W | SHM_R)) < 0){
					perror("shmget 3");
					exit(1);
				}
			}
		}
		if((compartilhado.produtoInterno_compartilhado = shmat(shmid[0], NULL, 0)) == (int *) -1){
			perror("shmat produtointerno");
			exit(1);
		}
		if((compartilhado.menor = shmat(shmid[1] , NULL, 0)) == (int *) -1){
			perror("shmat menor");
			exit(1);
		}
		if((compartilhado.maior = shmat(shmid[2], NULL, 0)) == (int *) -1){
			perror("shmat maior");
			exit(1);
		}
		if((compartilhado.menor_i = shmat(shmid[3], NULL, 0)) == (int *) -1){
			perror("shmat menor_i");
			exit(1);
		}
		if((compartilhado.maior_i = shmat(shmid[4], NULL, 0)) == (int *) -1){
			perror("shmat maior i");
			exit(1);
		}
		if((compartilhado.variancia = shmat(shmid[5], NULL, 0)) == (double *) -1){
			perror("shmat variancia");
			exit(1);
		}
		
		
		//Inicializa outros valores da iteração
		*compartilhado.menor = INF;
		*compartilhado.maior = -INF;
		*compartilhado.variancia = 0;
		menor_matriz = INF;
		maior_matriz = -INF;
		menor_matriz_i = 0;
		maior_matriz_i = 0;
		menor_matriz_j = 0;
		maior_matriz_j = 0;
		soma_desvio = 0;
		desvio_padrao = 0;
		
		//Inicia a contagem do tempo de execução
		ftime(&inicio_execucao);
		
		//Aloca a matriz Principal
		printf("\nMontando a matriz... ");
		matriz = aloca_matriz(m, k);
		compartilhado.produtoInterno = aloca_vetor(m);
		printf("Concluído!\n\n");
		
		//Insere na matriz
		printf("Inserindo valores na Matriz... ");
		for(i=0; i<m; i++){
			for(j=0; j<k; j++){
				//gera o número aleatório e armazena na matriz
				matriz[i][j] = (rand()%201)-100;
				
				//Detecta o maior e menor na matriz
				if(matriz[i][j] <= menor_matriz){
					menor_matriz = matriz[i][j];
					menor_matriz_i = i+1;
					menor_matriz_j = j+1;
				}
				if(matriz[i][j] >= maior_matriz){
					maior_matriz = matriz[i][j];
					maior_matriz_i = i+1;
					maior_matriz_j = j+1;
				}
			}
		}
		printf("Concluído!\n\n");
		
		
		printf("Calculando a Produto Interno...");
		//Loop que cria as threads
		for(i=0; i<THREADS; i++){
			
			//Determina o posicionamento da Thread na matriz
			parte[i] = i+1;
			
			//Inicializa as threads passando o parâmetro da sua posição
			returnThread[i] = pthread_create(&(thread[i]), &atributosThread, calculaProdutoInterno, (void*) &(parte[i]));
			
			//Verifica se houve um erro ao criar a Thread
			if(returnThread[i] > 0){
				printf("Não foi possível criar a Thread para o segmento i=%i", i);
				exit(1);
			}
			
		}
		
		//Aguarda o término das threads
		for(i=0; i<THREADS; i++){
			pthread_join(thread[i], NULL);
		}
		printf("Concluído!\n\n");
		
		
		//Calcula o desvio padrão
		for(i=0; i<m; i++){
			soma_desvio += pow(compartilhado.produtoInterno_compartilhado[i]-(*compartilhado.variancia/m), 2);
			
			//Detecta o maior e menor
			if(compartilhado.produtoInterno_compartilhado[i] <= *compartilhado.menor){
				*compartilhado.menor = compartilhado.produtoInterno_compartilhado[i];
				*compartilhado.menor_i = i+1;
			}
			if(compartilhado.produtoInterno_compartilhado[i] >= *compartilhado.maior){
				*compartilhado.maior = compartilhado.produtoInterno_compartilhado[i];
				*compartilhado.maior_i = i+1;
			}
		}
		desvio_padrao = sqrt(soma_desvio/m);
		
		
		//Calcula o tempo de execução
		ftime(&fim_execucao);
		tempo_execucao = (((fim_execucao.time-inicio_execucao.time)*1000.0+fim_execucao.millitm)-inicio_execucao.millitm)/1000.0;
		
		
		//Libera a matriz e a memória compartilhada
		free_matriz(m, k, matriz);
		for(i=0; i<11; i++){
			shmctl(shmid[i], IPC_RMID, NULL);
		}
		
		
		//Exibe os valores resultantes
		printf("---------------------------------------Valores Aferidos----------------------------------------\n");
		printf("Menor valor na matriz = %i (i=%i, j=%i) e Maior valor na matriz = %i (i=%i, j=%i)\n", menor_matriz, menor_matriz_i, menor_matriz_j, maior_matriz, maior_matriz_i, maior_matriz_j);
		printf("Menor valor Produto Interno = %i (i=%i) e Maior valor Produto Interno = %i (i=%i)\n", *compartilhado.menor, *compartilhado.menor_i, *compartilhado.maior, *compartilhado.maior_i);
		printf("Desvio Padrão = %f\n", desvio_padrao);
		printf("Tempo de execução = %.3f segundos\n", tempo_execucao);
		printf("-----------------------------------------------------------------------------------------------\n\n");
		
		
		//Recebe os valores de m e k para nova iteração
		printf("Nova iteração\n");
		printf("Defina o número de linhas  -> m = ");
		scanf("%i", &m);
		printf("Defina o número de colunas -> k = ");
		scanf("%i", &k);
	}
	
	exit(0);
}
