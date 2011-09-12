#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

#define m 1;

int i, j, id, d1, d2, status;

int main(void) {
	//inicialize as variáveis d1 e d2 com valores distintos;
	d1 = 0;
	d2 = 10;
	
	//mostre o PID do processo corrente e os valores de d1 e d2 na tela da console
	printf("PID do processo corrente = %i\nd1 = %i e d2 = %i", getpid(), d1, d2);
	
	/*
	**** responda: quais processos executarão este trecho do código?
	**** Todos os processos executarão este código já que não foi
	**** realizado nenhum fork().
	*/
	j = 0;
	
	for (i = 0; i <= m; i++){
		//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2” e “m”
		printf("PID do processo corrente = %i\nd1 = %i , d2 = %i e m = %i", getpid(), d1, d2, m);
		
		/*
		***** responda: quais processos executam este trecho do código?
		***** Novamente todos os processos executam este trecho já que
		***** nenhum fork() foi realizado.
		*/
		id = fork();
		
		if (id){
			//altere os valores de d1 e d2 de diferentes maneiras como exemplificado abaixo
			d1 = d1 + i + 1;
			d2 = d2 + d1 * 3;
			
			//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe estar no ramo “then” do “if”
			printf("PID do processo corrente = %i\nd1 = %i , d2 = %i e m = %i - Ramo if", getpid(), d1, d2, m);
			
			/*
			***** responda: quais processos executam este trecho do código?
			**** Somente o processo pai executa esse código
			*/
		}else{
			//altere os valores de d1 e d2 de diferentes maneiras e também diferente do usado no trecho “then”
			d1 = d1 - i;
			d2 = (d2 - d1) * 3;
			
			//execute o comando de atualização de “j” abaixo
			j = i + 1;
			
			//mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe estar no ramo “else” do “if”
			printf("PID do processo corrente = %i\nd1 = %i , d2 = %i e m = %i - Ramo else", getpid(), d1, d2, m);
			
			/*
			***** responda: quais processos executam este trecho do código?
			***** Este trecho de código é executado por todos os processos
			***** exceto o processo pai
			*/
		}
		
	}
	
	/*
	***** responda: quais processos executam este trecho do código?
	***** Somente o processo pai executa esse código
	*/
	
	if (id != 0) {
		//mostre na console o PID do processo corrente e verifique quais processos executam este trecho do código
		printf("PID do processo corrente = %i", getpid());
		
		for (i = j; i == m; i++){
			/*
			**** explique o papel da variável “j” e verifique se o comando “for” está correto de forma a que cada processo pai aguarde pelo término de todos seus processos filhos
			**** "j" contabiliza o número de sub processos relativos ao processo pai original.
			*/
			
			//mostre na console o PID do processo corrente e o número de filhos que ele aguardou ou está aguardando
			printf("PID do processo corrente = %i e número de filhos = %i", getpid(), j);
			
			wait(&status);
			
			if (status == 0){
				/*
				**** responda: o que ocorre quando este trecho é executado?
				**** ?
				*/
			}else{
				/*
				**** responda: o que ocorre quando este trecho é executado?
				**** ?
				*/
			}
		}
	}
	
	exit(0);
}