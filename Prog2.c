#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>


#define m 2

int i, j, k, id, d1, d2, status;
int nFilhos = 0;
char pid[8];
char pid_list[1000][256];

int main(void) {

	//inicialize as variáveis d1 e d2 com valores distintos;
	d1 = 0;
	d2 = 1;
	

	printf("\n");
	//mostre o PID do processo corrente e os valores de d1 e d2 na tela da console
	printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i\n\n", getpid(), d1, d2);
	
	/*
	======================================================================================================================
	Responda: Quais processos executarão este trecho do código?
		  Somente o processo pai original ("raiz da árvore de processos") executará este trecho. 
	----------------------------------------------------------------------------------------------------------------------
	*/

	j = 0;
	
	for (i = 0; i <= m; i++){
		//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2” e “m”
		printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i    |   m = %i  |  iteração i = %i\n\n", getpid(), d1, d2, m,i);
		
		/*
		=================================================================================================================
		Responda: Quais processos executam este trecho do código?
		Novamente todos os processos executam este trecho já que nenhum fork() foi realizado.
		------------------------------------------------------------------------------------------------------------------
		*/
		id = fork();
		
		//Armazena o PID do filho criado
		sprintf(pid, "%i ", id);
		
		if (id){
			//altere os valores de d1 e d2 de diferentes maneiras como exemplificado abaixo
			d1 = d1 + i + 1;
			d2 = d2 + d1 * 3;
			
			// mostre na tela da console, a cada passagem, os seguintes valores: 
			// PID do processo corrente, "i", "d1", "d2", "m" e informe estar no ramo “then” do “if”
			printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i    |   m = %i  |  iteração i = %i  |    valor de j = %i  |  Ramo if [PAI] \n\n", getpid(), d1, d2, m, i,j);
			
			//Armazenando os processos filhos
			strcat(pid_list[getpid()-16000], pid);
			//contabilizando o número de filhos
			nFilhos++;			

			/*
			=================================================================================================================
			Responda: Quais processos executam este trecho do código?
			Todos os processos executam este código exceto o processo pai original.
			Nesse caso ele é um processo pai de algum outro subprocesso.
			------------------------------------------------------------------------------------------------------------------
			*/
		}else{
			//altere os valores de d1 e d2 de diferentes maneiras e também diferente do usado no trecho “then”
			d1 = d1 - i;
			d2 = (d2 - d1) * 3;
			
			//execute o comando de atualização de “j” abaixo
			j = i + 1;
			
			//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe 				estar no ramo “else” do “if”
			printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i    |   m = %i  |  iteração i = %i  |  valor de j = %i  |  Ramo else [FILHO]\n\n", getpid(), d1, d2, m, i,j);
			
			/*
			=================================================================================================================
			Responda: Quais processos executam este trecho do código?
			Este trecho de código é executado por todos os processos exceto o processo pai original.
			Nesse caso ele é um subprocesso. Uma folha na árvore de subprocessos.
			------------------------------------------------------------------------------------------------------------------
			*/
		}
		
	}
	/*
	=================================================================================================================
	Responda: Quais processos executam este trecho do código?
	Todos os processos executam esse código exceto o os subprocessos sem filhos.
	------------------------------------------------------------------------------------------------------------------
	*/
	

	if (id != 0) {
		//mostre na console o PID do processo corrente e verifique quais processos executam este trecho do código
		printf("PID do processo corrente = %i\n", getpid());
		
		for (i = j; i <= m; i++){
			/*
			=================================================================================================================
			Explique o papel da variável “j” e verifique se o comando “for” está correto de forma a que cada processo pai
			aguarde pelo término de todos seus processos filhos
			
			"j" contabiliza a altura da árvore de processos onde os processos são filhos e pais ao mesmo tempo.
			Ou seja, ele mostra a quantidade de níveis de processos excetuando-se o processo pai original e os processos
			filhos contido nas folhas.
			Dessa forma o comando for(i = j; i == m; i++) está incorreto porque ele se refere somente ao último pai.
			O correto seria for(i = j; i<=m; i++) conforme implementado.
			------------------------------------------------------------------------------------------------------------------			
			*/
			
			//mostre na console o PID do processo corrente e o número de filhos que ele aguardou ou está aguardando

			printf("PID do processo corrente = %i e número de filhos = %i", getpid(), nFilhos);

			//Mostra os processos que estão sendo esperados no momento
			printf("\nO processo de PID = %i está esperando os seguintes filhos: %s\n\n", getpid(), pid_list[getpid()-16000]);


			wait(&status);
			
			if (status == 0){

				/*
				=================================================================================================================
				Responda: o que ocorre quando este trecho é executado?
				Os filhos referentes ao processo corrente terminaram a sua execução com sucesso.
				------------------------------------------------------------------------------------------------------------------			
				*/
			}else{
				/*
				=================================================================================================================
				Responda: o que ocorre quando este trecho é executado?
				Os filhos referentes ao processo corrente ainda não terminaram sua execução ou terminaram com falhas.
				------------------------------------------------------------------------------------------------------------------			
				*/


			}
		}
	}
	
	exit(0);
}
