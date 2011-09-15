#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include <sys/wait.h>

#include <sys/stat.h>
#include <mqueue.h>

#define MSG_SIZE 256
#define MAX_MSG_SIZE 10000
#define NAME "IPC_CHANNEL"
#define MSGQOBJ_NAME "/myqdueue1234"

int main(void){
	int	status, id, j;

	//Cria as estruturas de IPC
	mqd_t msgq_id;
	unsigned int sender;
	char msgcontent[MSG_SIZE];
	char msgcontentRCV[MAX_MSG_SIZE];
	
	struct mq_attr msgq_attr;
	int create_queue = 0;
	unsigned int msgprio = 10;
	time_t currtime;
	int msgsz;
	//msgq_id = mq_open(NAME, O_RDWR | O_CREAT | O_EXCL, S_IRWXU | S_IRWXG, NULL);
	
	//Insira um comando para pegar o PID do processo corrente e mostre na tela da console.
	printf("Processo Corrente - %i\n\n", getpid());
	
	id = fork();
	
	if (id != 0){
		//Faça com que o processo pai execute este trecho de código
		//Mostre na console o PID do processo pai e do processo filho
		printf("Sou o processo Pai de PID %i e tenho o processo Filho de PID %i\n", getpid(), id);
		
		
        	/* mq_open() for creating a new queue (using default attributes) */
       		msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR | O_CREAT | O_EXCL, S_IRWXU | S_IRWXG, NULL);
	    	//create_queue = 1;	
		if (msgq_id < 0) {
		    msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR);
		}// else {
        	/* mq_open() for opening an existing queue */
    	    msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR);
    	//}
		if (msgq_id == (mqd_t)-1) {
        	perror("In mq_open()");
        	exit(1);
    	}

		//printf("Received message (%d bytes) from %d: %s\n", msgsz, sender, msgcontentRCV);
		currtime = time(NULL);
		snprintf(msgcontent, MSG_SIZE, "MSG1 %u (at %s).", getpid(), ctime(&currtime));
		
		mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, msgprio);
		//Monte uma mensagem e a envie para o processo filho
		//strcpy(msgcontent, "Olá processo Filho!");
		
		
		//Mostre na tela o texto da mensagem enviada
		//printf("Mensagem enviada ao Filho: %s\n", msgcontent);
		//mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, 1);

		
		/*
		//Aguarde a resposta do processo filho
		

		//Mostre na tela o texto recebido do processo filho
		msqid = msgget(key, 0666);
		msgrcv(msqid, &message, MSG_SIZE, 1, 0);
		printf("1a Mensagem enviada pelo processo Filho - %s\n" , message.text);
		

		//Aguarde mensagem do filho e mostre o texto recebido
		msqid = msgget(key, 0666);
		msgrcv(msqid, &message, MSG_SIZE, 1, 0);
		printf("2a Mensagem enviada pelo processo Filho - %s\n" , message.text);
		
		*/
		//Aguarde o término do processo filho
		/* getting a message */
		wait(&status);
		//sleep(1000);
		
		msgsz = mq_receive(msgq_id, msgcontentRCV, MAX_MSG_SIZE, &sender);
		if (msgsz == -1) {
			perror("In mq_receive()");
			exit(1);
		}
		printf("Pai RECEBEU message (%d bytes) from %d: %s\n", msgsz, sender, msgcontentRCV);
		
		msgsz = mq_receive(msgq_id, msgcontentRCV, MAX_MSG_SIZE, &sender);
		if (msgsz == -1) {
			perror("In mq_receive()");
			exit(1);
		}
		printf("Pai RECEBEU message (%d bytes) from %d: %s\n", msgsz, sender, msgcontentRCV);
		
		
		
		mq_close(msgq_id);
		
		//Informe na tela que o filho terminou e que o processo pai também vai encerrar
		printf("O Processo Filho terminou e o pai também se encerrará.");
		exit(0);
	}else{
		//Faça com que o processo filho execute este trecho de código
		//Mostre na tela o PID do processo corrente e do processo pai
		printf("Sou o processo de PID %i e tenho o Processo Pai de PID %i\n", getpid(), getppid());
		
		    /* opening the queue        --  mq_open() */
		msgq_id = mq_open(MSGQOBJ_NAME, O_RDWR);
		if (msgq_id == (mqd_t)-1) {
		    perror("In mq_open()");
		    exit(1);
		}

		/* getting the attributes from the queue        --  mq_getattr() */
		mq_getattr(msgq_id, &msgq_attr);
		//printf("Queue \"%s\":\n\t- stores at most %ld messages\n\t- large at most %ld bytes each\n\t- currently holds %ld messages\n", MSGQOBJ_NAME, msgq_attr.mq_maxmsg, msgq_attr.mq_msgsize, msgq_attr.mq_curmsgs);

		/* getting a message */
		msgsz = mq_receive(msgq_id, msgcontentRCV, MAX_MSG_SIZE, &sender);
		if (msgsz == -1) {
			perror("In mq_receive()");
			exit(1);
		}
		printf("Received message (%d bytes) from %d: %s\n", msgsz, sender, msgcontentRCV);
		
		
		currtime = time(NULL);
		snprintf(msgcontent, MSG_SIZE, "MSG2 %u (at %s).", getpid(), ctime(&currtime));
		
		mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, msgprio);
		
		currtime = time(NULL);
		snprintf(msgcontent, MSG_SIZE, "MSG3 %u (at %s).", getpid(), ctime(&currtime));
		
		mq_send(msgq_id, msgcontent, strlen(msgcontent)+1, msgprio);
		//Aguarde a mensagem do processo pai e ao receber mostre o texto na tela
		//q_receive(mq_open(NAME, O_RDWR), msgcontent, MSG_SIZE, &sender);
		//printf("Mensagem enviada pelo processo Pai - %s\n" , msgcontent);
		
		
		/*
		//Envie uma mensagem resposta ao pai
		strcpy(message.text, "Olá processo Pai!");
		message.type = 1;
		msqid = msgget(key, msgflg);
		msgsnd(msqid, &message, strlen(message.text)+1, IPC_NOWAIT);
		
		
		//Execute o comando “for” abaixo
		for (j = 0; j <= 10000; j++);


		sprintf(message.text, "j = %i", j);
		message.type = 1;
		msqid = msgget(key, msgflg);
		msgsnd(msqid, &message, strlen(message.text)+1, IPC_NOWAIT);


		//Execute o comando abaixo e responda às perguntas
		execl("/bin/ls", "ls", NULL);
		
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
