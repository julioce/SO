#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <mqueue.h>

#define MSG_SIZE 256
#define MAX_MSG_SIZE 10000
#define MSGQOBJ_NAME "/IPC_CHANNEL"

int main(void){
	int	status, id, j;

	//Cria as estruturas de IPC
	mqd_t msgq_id;
	unsigned int sender;
	char msgcontent[MSG_SIZE];
	char msgcontentRCV[MAX_MSG_SIZE];
	struct mq_attr msgq_attr;
	int msgsz;
	
	//Insira um comando para pegar o PID do processo corrente e mostre na tela da console.
	printf("Processo Corrente - %i\n\n", getpid());
	
	id = fork();
	
	if (id != 0){
		//Faça com que o processo pai execute este trecho de código
		//Mostre na console o PID do processo pai e do processo filho
		printf("Sou o processo Pai de PID %i e tenho o processo Filho de PID %i\n", getpid(), id);

		
		//Abre o queue
		msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR | O_CREAT | O_EXCL, S_IRWXU | S_IRWXG, NULL);
		if (msgq_id < 0) {
		    msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR);
		}


		//Monte uma mensagem e a envie para o processo filho
		strcpy(msgcontent, "Olá processo Filho!");
		mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, 10);

		
		//Mostre na tela o texto da mensagem enviada
		printf("Mensagem enviada ao Filho: %s\n", msgcontent);	


		//Aguarde o término do processo filho
		wait(&status);
		
		
		//Aguarde a resposta do processo filho
		msgsz = mq_receive(msgq_id, msgcontentRCV, MAX_MSG_SIZE, &sender);
		if (msgsz == -1) {
			perror("In mq_receive()");
			exit(1);
		}
		printf("1a Mensagem enviada pelo filho - %s\n", msgcontentRCV);
		
		
		//Aguarde mensagem do filho e mostre o texto recebido
		msgsz = mq_receive(msgq_id, msgcontentRCV, MAX_MSG_SIZE, &sender);
		if (msgsz == -1) {
			perror("In mq_receive()");
			exit(1);
		}
		printf("2a Mensagem enviada pelo filho - %s\n", msgcontentRCV);
		
		
		mq_close(msgq_id);
		
		//Informe na tela que o filho terminou e que o processo pai também vai encerrar
		printf("O Processo Filho terminou e o pai também se encerrará.\n");
		exit(0);
	}else{
		//Faça com que o processo filho execute este trecho de código
		//Mostre na tela o PID do processo corrente e do processo pai
		printf("Sou o processo de PID %i e tenho o Processo Pai de PID %i\n", getpid(), getppid());
		
		//Abre o message queue
		msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR);
		if (msgq_id == (mqd_t)-1) {
			perror("In mq_open()");
			exit(1);
		}

		
		//Aguarde a mensagem do processo pai e ao receber mostre o texto na tela
		mq_getattr(msgq_id, &msgq_attr);
		msgsz = mq_receive(msgq_id, msgcontentRCV, MAX_MSG_SIZE, &sender);
		if (msgsz == -1) {
			perror("In mq_receive()");
			exit(1);
		}
		printf("Mensagem enviada pelo pai - %s\n", msgcontentRCV);
		
		
		//Envie uma mensagem resposta ao pai
		strcpy(msgcontent, "Olá processo Pai!");
		mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, 10);
		
		
		//Execute o comando “for” abaixo
		for (j = 0; j <= 10000; j++);

		
		//Envie mensagem ao processo pai com o valor final de “j”
		sprintf(msgcontent, "j=%i", j);
		mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, 10); 


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
		mq_close(msgq_id);
		exit(0);
	}
	
}
