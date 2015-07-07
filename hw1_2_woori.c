#include <stdio.h>
#include <stdlib.h>

typedef struct node *listptr;
typedef struct node{
	char data;
	listptr *link;
}node;



void push(listptr *top, char x){
	listptr temp;
	temp = (listptr)malloc(sizeof(node));
	if(!temp)
		printf("memory is full!\n");
	else{
		temp->data = x;
		temp->link = top;
		*top = temp;
	}
}
char pop(listptr *top){
	char x;
	listptr temp;
	temp = *top;
	if(temp == NULL)
		return '$';
	else{
		x = temp->data;
		top = temp->link;
		free(temp);
		return x;
	}
}

int main(void){
	listptr top = NULL;
	//top = (listptr)malloc(sizeof(node));

	int t, check = 0;
	char x;

	while(check != -1){
		printf("��� �۾��� �Ͻðڽ��ϱ�? (1. push 2. pop 3. ����) : ");
		scanf("%d", &t);
		fflush(stdin);
		switch(t){
			case 1:
				printf("��� ���ڸ� Ǫ���Ͻðڽ��ϱ�? :");
				scanf("%c", &x);
				fflush(stdin);
				push(&top, x);
				break;
			case 2:
				x = pop(&top);
				printf("���� ���ڰ� �˵Ǿ����ϴ� : %c", x);
				break;
			case 3:
				printf("���α׷��� �����մϴ�.");
				check = -1;
				break;
		}
		printf("\n");
	}
	return 0;	
}
