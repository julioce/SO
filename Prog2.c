#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>

#define m 1

int i, j, id, d1, d2, status;
char pid[8];
char pid_list[256];

int main(void) {
	//inicialize as variáveis d1 e d2 com valores distintos;
	d1 = 0;
	d2 = 1;
	
	//mostre o PID do processo corrente e os valores de d1 e d2 na tela da console
	printf("PID do processo corrente = %i e d1 = %i e d2 = %i\n\n", getpid(), d1, d2);
	
	/*
	**** responda: quais processos executarão este trecho do código?
	**** 
	**** Somente o processo pai executará essa parte do código.
	*/
	j = 0;
	
	for (i = 0; i <= m; i++){
		//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2” e “m”
		printf("PID do processo corrente = %i e d1 = %i , d2 = %i e m = %i\n", getpid(), d1, d2, m);
		
		/*
		***** responda: quais processos executam este trecho do código?
		***** 
		***** Novamente todos os processos executam este trecho já que nenhum fork() foi realizado.
		*/
		id = fork();
		
		//Armazena o PID do filho criado
		sprintf(pid, "%i ", id);
		
		if (id){
			//altere os valores de d1 e d2 de diferentes maneiras como exemplificado abaixo
			d1 = d1 + i + 1;
			d2 = d2 + d1 * 3;
			
			//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe estar no ramo “then” do “if”
			printf("PID do processo corrente = %i e d1 = %i , d2 = %i e m = %i - Ramo if\n", getpid(), d1, d2, m);
			
			//Armazenando os processos filhos
			strcat(pid_list, pid);
			
			/*
			***** responda: quais processos executam este trecho do código?
			*****
			***** Todos os processos executam este código exceto o processo pai original.
			***** Nesse caso ele é um processo pai de algum outro subprocesso.
			*/
		}else{
			//altere os valores de d1 e d2 de diferentes maneiras e também diferente do usado no trecho “then”
			d1 = d1 - i;
			d2 = (d2 - d1) * 3;
			
			//execute o comando de atualização de “j” abaixo
			j = i + 1;
			
			//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe estar no ramo “else” do “if”
			printf("PID do processo corrente = %i e d1 = %i , d2 = %i e m = %i - Ramo else\n", getpid(), d1, d2, m);
			
			/*
			***** responda: quais processos executam este trecho do código?
			***** 
			***** Este trecho de código é executado por todos os processos exceto o processo pai original.
			***** Nesse caso ele é um subprocesso. Uma folha na árvore de subprocessos.
			*/
		}
		
	}
	
	/*
	***** responda: quais processos executam este trecho do código?
	***** 
	***** Todos os processos executam esse código exceto o os subprocessos sem filhos.
	*/
	
	if (id != 0) {
		//mostre na console o PID do processo corrente e verifique quais processos executam este trecho do código
		printf("PID do processo corrente = %i\n", getpid());
		
		for (i = j; i <= m; i++){
			/*
			**** explique o papel da variável “j” e verifique se o comando “for” está correto de forma a que cada processo pai
			**** aguarde pelo término de todos seus processos filhos
			**** 
			**** "j" contabiliza a altura da árvore de processos onde os processos são filhos e pais ao mesmo tempo.
			**** Ou seja, ele mostra a quantidade de níveis de processos excetuando-se o processo pai original e os processos
			**** filhos contido nas folhas.
			**** Dessa forma o comando for(i = j; i == m; i++) está incorreto porque ele se refere somente ao último pai.
			**** O correto seria for(i = j; i<=m; i++) conforme implementado.
			*/
			
			//mostre na console o PID do processo corrente e o número de filhos que ele aguardou ou está aguardando
			printf("PID do processo corrente = %i e número de filhos = %i\n", getpid(), i);
			
			//Mostra os processos que estão sendo esperados no momento
			printf("\nO processo de PID = %i está esperando os seguintes filhos: %s\n\n", getpid(), pid_list);
			wait(&status);
			
			if (status == 0){
				/*
				**** responda: o que ocorre quando este trecho é executado?
				****
				**** O filhos referentes ao processo corrente terminaram a sua execução com sucesso.
				*/
			}else{
				/*
				**** responda: o que ocorre quando este trecho é executado?
				****
				**** O filhos referentes ao processo corrente terminaram a sua execução com falhas.
				*/
			}
		}
	}
	
	exit(0);
}
