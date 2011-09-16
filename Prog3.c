#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <mqueue.h>

int main(void){
	int i, j;
	int k = 3;
	int m = 3;
	int somatorio = 0;
	int menor = 100;
	int maior = -100;
	double inicio;
	srand((unsigned)time(NULL));
	
	while( m!= 0 && k!=0 ){
		//Cria a matriz de acordo com os parâmetros
		printf("Digite o valor para m:");
		scanf("%i", &m);
		printf("Digite o valor para k:");
		scanf("%i", &k);
		
		inicio = time(NULL);
		int matriz[m][k];
		int produtoInterno[m];
	
		//Itera sobre a matriz
		for(i=0; i<m; i++){
			for(j=0; j<k; j++){
				//gera o número aleatório
				matriz[i][j] = (rand()%200)-100;
			
				//detecta o maior e menor
				if(matriz[i][j]<menor){menor=matriz[i][j];}
				if(matriz[i][j]>maior){maior=matriz[i][j];}
				printf("%i \t", matriz[i][j]);
			}
			printf("\n");
		}
	
		//Calcula o somatório
		for(i=0; i<m; i++){ 
			for(j=0; j<k; j++){
				somatorio += matriz[i][j] * matriz[j][i];
			}
			produtoInterno[i] = somatorio;
			somatorio = 0;
			printf("\nProduto interno para m=%i, %i", i, produtoInterno[i]);
		}
		
		//Exibe o tempo de execução
		printf("\nTempo de processamento = %f segundos\n\n",  time(NULL)-inicio);
	}
	
	exit(0);
}
