#include <stdio.h>
#include <wait.h>
#include <sys/types.h>

#define m, k;

int i, j, k, id, d1, d2, status;

int main(void) {
	**** inicialize as variáveis d1 e d2 com valores distintos;
	**** mostre o PID do processo corrente e os valores de d1 e d2 na tela da console
	**** responda: quais processos executarão este trecho do código?
	
	j = 0;
	
	for (i = 0; i <= m; i++){
		***** mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2” e “m”
		***** responda: quais processos executam este trecho do código?
		
		id = fork();
		
		if (id){
			***** altere os valores de d1 e d2 de diferentes maneiras como exemplificado abaixo
			d1 = d1 + (i + 1);
			d2 = d2 + d1 * 3;
			***** mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe estar no ramo “then” do “if”
			***** responda: quais processos executam este trecho do código?
		}else{
			***** altere os valores de d1 e d2 de diferentes maneiras e também diferente do usado no trecho “then”
			***** execute o comando de atualização de “j” abaixo
			j = i + 1;
			***** mostre na tela da console, a cada passagem, os seguintes valores: PID do processo corrente; “i”, “d1”, “d2”, “m” e informe estar no ramo “then” do “if”
			***** responda: quais processos executam este trecho do código?
		}
		
	}
	
	***** responda: quais processos executam este trecho do código?
	
	if (id != 0) {
		***** mostre na console o PID do processo corrente e verifique quais processos executam este trecho do código
		
		for (i = j; i == m; i++){
			**** explique o papel da variável “j” e verifique se o comando “for” está correto de forma a que cada processo pai aguarde pelo término de todos seus processos filhos
			**** mostre na console o PID do processo corrente e o número de filhos que ele aguardou ou está aguardando
			
			wait(&status);
			
			if (status == 0){
				**** responda: o que ocorre quando este trecho é executado?
			}else{
				**** responda: o que ocorre quando este trecho é executado?
			}
		}
	}
	
	exit(0);
}
			
			
			