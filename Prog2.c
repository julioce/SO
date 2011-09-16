#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>

#define m 1

int i, j, k, id, d1, d2, status;
int nFilhos = 0;
int meuPID = -1;
char pid[16];
char pid_list[512];

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
		printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i    |   m = %i\n\n", getpid(), d1, d2, m,i);
		
		/*
		=================================================================================================================
		Responda: Quais processos executam este trecho do código?
		Novamente todos os processos executam este trecho já que nenhum fork() foi realizado.
		------------------------------------------------------------------------------------------------------------------
		*/
		id = fork();
		
		//Verifica validade da atualização
		if(meuPID != getpid()){
			meuPID = getpid();
			nFilhos = 0;
			//Limpa o meu vetor
			memset(pid_list, 0, sizeof(pid_list));
		}
		
		
		if (id){
			//altere os valores de d1 e d2 de diferentes maneiras como exemplificado abaixo
			d1 = d1 + i + 1;
			d2 = d2 + d1 * 3;
			
			// mostre na tela da console, a cada passagem, os seguintes valores: 
			// PID do processo corrente, "i", "d1", "d2", "m" e informe estar no ramo “then” do “if”
			printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i    |   m = %i  Ramo if\n\n", getpid(), d1, d2, m, i,j);
			
			//Contabilizando o número de filhos
			nFilhos++;

			//Armazenando os processos filhos criados
			sprintf(pid, "%i ", id);
			strcat(pid_list, pid);
					

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
			printf("PID do processo corrente = %i    |    d1 = %i    |    d2 = %i    |   m = %i  Ramo else\n\n", getpid(), d1, d2, m, i,j);
			
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
			printf("\nO processo de PID = %i está esperando os seguintes filhos: %s\n\n", getpid(), pid_list);


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
