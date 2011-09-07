#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int main(void){
	int	status, id, j;
	
	//Insira um comando para pegar o PID do processo corrente e mostre na tela da console.
	printf("Processo Corrente - %i\n\n", getpid());
	
	//Cria o canal de comunicação inter-processos
	int comunicacao[2];
	pipe(comunicacao);
	
	id = fork();
	
	if (id != 0){
		//Faça com que o processo pai execute este trecho de código
		//Mostre na console o PID do processo pai e do processo filho
		printf("Sou o processo Pai - %i e tenho o processo Filho - %i\n", getpid(), id);
		
		//Monte uma mensagem e a envie para o processo filho
		char mensagemEnviada[255] = "Olá processo filho!";
		close(comunicacao[0]); //Fecha o canal de leitura
		write(comunicacao[1], mensagemEnviada, 255); //Escreve a mensagem no canal de escrita
		
		/*
		***** Mostre na tela o texto da mensagem enviada
		***** Aguarde a resposta do processo filho
		***** Mostre na tela o texto recebido do processo filho
		***** Aguarde mensagem do filho e mostre o texto recebido
		***** Aguarde o término do processo filho
		***** Informe na tela que o filho terminou e que o processo pai também vai encerrar
		 */
	}else{
		//Faça com que o processo filho execute este trecho de código
		//Mostre na tela o PID do processo corrente e do processo pai
		printf("Sou o processo - %i e tenho o Processo Pai - %i\n", getpid(), id);
		
		//Aguarde a mensagem do processo pai e ao receber mostre o texto na tela
		char mensagemRecebida[255];
		read(comunicacao[0], mensagemRecebida, sizeof(mensagemRecebida));//Lê a mensagem no canal de leitura
		close(comunicacao[1]); //Fecha o canal de escrita
		printf("Mensagem enviada pelo processo Pai - %s" , mensagemRecebida);
		
		/*
		***** 
		***** Envie uma mensagem resposta ao pai
		***** Execute o comando “for” abaixo
		for (j = 0; j <= 10000; i++);
			***** Envie mensagem ao processo pai com o valor final de “j”
			***** Execute o comando abaixo e responda às perguntas
			execl(“/Bin/ls”, “ls”, NULL);
			***** O que acontece após este comando?
			***** O que pode acontecer se o comando “execl” falhar?
		 */
	}
	
	exit(0);
}
