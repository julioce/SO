#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int main(void){
	int	status, id, j;
	char mensagem[255];
	
	//Cria os canais de comunicação inter-processual
	int canalPai[2];
	int canalFilho[2];
	pipe(canalPai);
	pipe(canalFilho);
	
	//Insira um comando para pegar o PID do processo corrente e mostre na tela da console.
	printf("Processo Corrente - %i\n\n", getpid());

	id = fork();
	
	if (id != 0){
		//Faça com que o processo pai execute este trecho de código
		//Mostre na console o PID do processo pai e do processo filho
		printf("Sou o processo Pai de PID %i e tenho o processo Filho de PID %i\n", getpid(), id);
		
		//Monte uma mensagem e a envie para o processo filho
		strcpy(mensagem, "Olá processo Filho!");
		close(canalPai[0]); //Fecha o canal de leitura
		write(canalPai[1], mensagem, 255); //Escreve a mensagem no canal de escrita
		
		//Mostre na tela o texto da mensagem enviada
		printf("Mensagem enviada ao Filho: %s\n", mensagem);
		
		//Aguarde a resposta do processo filho
		wait(&canalFilho[1]);
		
		//Mostre na tela o texto recebido do processo filho
		read(canalFilho[0], mensagem, sizeof(mensagem));//Lê a mensagem no canal de leitura
		close(canalFilho[1]); //Fecha o canal de escrita
		printf("1a Mensagem enviada pelo processo Filho - %s\n" , mensagem);
		
		//Aguarde mensagem do filho e mostre o texto recebido
		wait(&canalFilho[1]);
		read(canalFilho[0], mensagem, sizeof(mensagem));//Lê a mensagem no canal de leitura
		close(canalFilho[1]); //Fecha o canal de escrita
		printf("2a Mensagem enviada pelo processo Filho - %s\n" , mensagem);
		
		//Aguarde o término do processo filho
		wait(&status);

		//Informe na tela que o filho terminou e que o processo pai também vai encerrar
		printf("O Processo Filho terminou e o pai também se encerrará.");
		
	}else{
		//Faça com que o processo filho execute este trecho de código
		//Mostre na tela o PID do processo corrente e do processo pai
		printf("Sou o processo de PID %i e tenho o Processo Pai de PID %i\n", getpid(), getppid());
		
		//Aguarde a mensagem do processo pai e ao receber mostre o texto na tela
		read(canalPai[0], mensagem, sizeof(mensagem));//Lê a mensagem no canal de leitura
		close(canalPai[1]); //Fecha o canal de escrita
		printf("Mensagem enviada pelo processo Pai - %s\n" , mensagem);
		
		//Envie uma mensagem resposta ao pai
		strcpy(mensagem, "Olá processo Pai!");
		close(canalFilho[0]); //Fecha o canal de leitura
		write(canalFilho[1], mensagem, 255); //Escreve a mensagem no canal de escrita
		
		
		//Execute o comando “for” abaixo
		for (j = 0; j <= 10000; j++);
			
		//Envie mensagem ao processo pai com o valor final de “j”
		sprintf(mensagem, "%i", j, 10);
		close(canalFilho[0]); //Fecha o canal de leitura
		write(canalFilho[1], mensagem, 255); //Escreve a mensagem no canal de escrita

		//Execute o comando abaixo e responda às perguntas
		execl("/Bin/ls", "ls", NULL);
		/*
			***** O que acontece após este comando?
			O processo filho continua o seu fluxo de código e termina sua execução,
			retornando ao processo o seu status de terminado.

			***** O que pode acontecer se o comando “execl” falhar?
			Não é exibido nada nada tela e portanto o processo é encerrado com erro.
		 */
		exit(0);
	}
	
}
