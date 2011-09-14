#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/wait.h>

#define MSG_SIZE 256

struct msg {
	int type; // message type
	char text[MSG_SIZE]; //message text
};

int main(void){
	int	status, id, j;
	int msqid;
    int msgflg = IPC_CREAT | 0666;
    key_t key = 1234;
    struct msg message;

	//Insira um comando para pegar o PID do processo corrente e mostre na tela da console.
	printf("Processo Corrente - %i\n\n", getpid());

	id = fork();
	
	if (id != 0){
		//Faça com que o processo pai execute este trecho de código
		//Mostre na console o PID do processo pai e do processo filho
		printf("Sou o processo Pai de PID %i e tenho o processo Filho de PID %i\n", getpid(), id);
		
		//Monte uma mensagem e a envie para o processo filho
		strcpy(message.text, "Olá processo Filho!");
		message.type = 1;
		msqid = msgget(key, msgflg);
		msgsnd(msqid, &message, strlen(message.text)+1, IPC_NOWAIT);
		
		
		//Mostre na tela o texto da mensagem enviada
		printf("Mensagem enviada ao Filho: %s\n", message.text);
		
		
		//Aguarde a resposta do processo filho
		msqid = msgget(key, 0666);
		msgrcv(msqid, &message, MSG_SIZE, 1, 0);
		printf("Mensagem enviada pelo processo Filho - %s\n" , message.text);
		
		/*
		//Mostre na tela o texto recebido do processo filho
		read(canalFilho[0], mensagem, sizeof(mensagem));//Lê a mensagem no canal de leitura
		close(canalFilho[1]); //Fecha o canal de escrita
		printf("1a Mensagem enviada pelo processo Filho - %s\n" , mensagem);
		
		//Aguarde mensagem do filho e mostre o texto recebido
		wait(&canalFilho[1]);
		read(canalFilho[0], mensagem, sizeof(mensagem));//Lê a mensagem no canal de leitura
		close(canalFilho[1]); //Fecha o canal de escrita
		printf("2a Mensagem enviada pelo processo Filho - %s\n" , mensagem);
		*/
		//Aguarde o término do processo filho
		wait(&status);

		//Informe na tela que o filho terminou e que o processo pai também vai encerrar
		printf("O Processo Filho terminou e o pai também se encerrará.");
		exit(0);
	}else{
		//Faça com que o processo filho execute este trecho de código
		//Mostre na tela o PID do processo corrente e do processo pai
		printf("Sou o processo de PID %i e tenho o Processo Pai de PID %i\n", getpid(), getppid());
		
		//Aguarde a mensagem do processo pai e ao receber mostre o texto na tela
		msqid = msgget(key, 0666);
		msgrcv(msqid, &message, MSG_SIZE, 1, 0);
		printf("Mensagem enviada pelo processo Pai - %s\n" , message.text);
		
		
		//Envie uma mensagem resposta ao pai
		strcpy(message.text, "Olá processo Pai!");
		message.type = 1;
		msqid = msgget(key, msgflg);
		msgsnd(msqid, &message, strlen(message.text)+1, IPC_NOWAIT);
		
		//Execute o comando “for” abaixo
		for (j = 0; j <= 10000; j++);
		/*
		//Envie mensagem ao processo pai com o valor final de “j”
		sprintf(mensagem, "j=%i", j, 10);
		close(canalFilho[0]); //Fecha o canal de leitura
		write(canalFilho[1], mensagem, 255); //Escreve a mensagem no canal de escrita

		//Execute o comando abaixo e responda às perguntas
		execl("/bin/ls", "ls", NULL);
		/*
			***** O que acontece após este comando?
			O  processo filho executa o comando execl que é criado pelo SO de forma independente.
			Isso ocorre pois o ls não é uma instância de Prog1, diferentemente dos processos filho e pai.
			Sendo assim, esse comando será executado pelo SO de forma dissociada dos demais, 
			não respeitando nenhuma ordem de execução estabelecida no código do Prog1.
			
			Após a execução de execl os processos voltam a ser executados normalmente.

			***** O que pode acontecer se o comando “execl” falhar?
			Como o ls é um processo independente do Prog1, quando o comando execl falha 
			o Prog1 continua sua execução normalmente. 
		 */
		exit(0);
	}
	
}
