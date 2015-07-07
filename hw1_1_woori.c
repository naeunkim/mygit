#include <stdio.h>

#define MAX_SIZE 100
char arraystack[MAX_SIZE];
int top = -1;

void push(int *top, char x){
	if(*top == MAX_SIZE-1)
		printf("stack is full!\n");
	else
		arraystack[++(*top)] = x;
}
char pop(int *top){
	if(*top == -1)
		return '$';
	else
		return arraystack[(*top)--];

}

int main(void){
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
