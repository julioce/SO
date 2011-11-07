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
		printf("PID do processo corrente = %i    |    i=%i    |    d1 = %i    |    d2 = %i    |   m = %i\n\n", getpid(), i, d1, d2, m);
		
		/*
		=================================================================================================================
		Responda: Quais processos executam este trecho do código?
		Todos os processos executam este trecho já que neste trecho o fork() ainda não foi realizado.
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
			printf("PID do processo corrente = %i    |    i=%i    |    d1 = %i    |    d2 = %i    |   m = %i  Ramo if\n\n", getpid(), i, d1, d2, m);
			
			//Contabilizando o número de filhos
			nFilhos++;

			//Armazenando os processos filhos criados
			sprintf(pid, "%i ", id);
			strcat(pid_list, pid);
					

			/*
			=================================================================================================================
			Responda: Quais processos executam este trecho do código?
			Todos os processos que possuem algum subprocesso associado a ele, ou seja, todos os processos pai.
			------------------------------------------------------------------------------------------------------------------
			*/
		}else{
			//altere os valores de d1 e d2 de diferentes maneiras e também diferente do usado no trecho “then”
			d1 = d1 - i;
			d2 = (d2 - d1) * 3;
			
			//execute o comando de atualização de “j” abaixo
			j = i + 1;
			
			//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe 				estar no ramo “else” do “if”
			printf("PID do processo corrente = %i    |    i=%i    |    d1 = %i    |    d2 = %i    |   m = %i  Ramo else\n\n", getpid(), i, d1, d2, m);
			
			/*
			=================================================================================================================
			Responda: Quais processos executam este trecho do código?
			Este trecho de código é executado por todos os processos que possuem um pai, ou seja, por todos os processos exceto 
			o processo pai original. 
			------------------------------------------------------------------------------------------------------------------
			*/
		}
		
	}
	/*
	=================================================================================================================
	Responda: Quais processos executam este trecho do código?
	Todos os processos executam este trecho.
	------------------------------------------------------------------------------------------------------------------
	*/
	

	if (id != 0) {
		//mostre na console o PID do processo corrente e verifique quais processos executam este trecho do código
		printf("PID do processo corrente = %i\n", getpid());
		
		for (i = j; i <= m; i++){
			/*
			=================================================================================================================
			Responda: Explique o papel da variável “j”
			A variável "j" contabiliza a altura da árvore genealógica a partir dos processos que são filho e pai ao mesmo tempo.
			Desta forma, o processo pai original e os procesos folhas (processos filhos que não possuem filhos) não são considerados. 
				
			Responda: Verifique se o comando “for” está correto de forma que cada processo pai aguarde pelo término de todos seus
			processos filhos
			O comando for(i = j; i == m; i++) está incorreto porque ele se refere somente ao último pai.
			O correto seria for(i = j; i<=m; i++), garantindo que cada processo pai tenha conhecimento de todos os seus filhos e aguarde
			pelo término deles.
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
				Os processos filhos referentes ao processo corrente terminaram a sua execução com sucesso.
				------------------------------------------------------------------------------------------------------------------			
				*/
			}else{
				/*
				=================================================================================================================
				Responda: o que ocorre quando este trecho é executado?
				Os processos filhos referentes ao processo corrente ainda não terminaram sua execução ou terminaram com falhas.
				------------------------------------------------------------------------------------------------------------------			
				*/


			}
		}
	}
	
	exit(0);
}
