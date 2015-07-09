#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

void freestack(char * stack, int size){
	for(int i=0;i<size;i++)
		free(stack[i]);
}
void initialize(char *stack, int size){
	for (int i = 0; i < size; i++)
		stack[i] = NULL;
}

void push(char *stack, int size, char insert){
	int top;
	for (int i = 0; i<size; i++){
		if (stack[i] == NULL){
			top = i;
			break;
		}
		if (i == size - 1){
			printf("������ ��á���ϴ�! \n");
			return;
		}
	}
	stack[top] = insert;
	printf(" %d ��° �ε����� Ǫ�õǾ����ϴ�.\n", top + 1);
}

char pop(char *stack, int size){
	char popnum;
	int top;
	if (stack[0] == NULL) {
		printf("������ ����ֽ��ϴ�.\n");
		return 0;
	}
	else {
		for (int i = 1; i<size; i++){
			if (stack[i] == NULL){
				top = i - 1;
				break;
			}
			if (i == size - 1){
				top = size - 1;
			}
		}
		popnum = stack[top];
		stack[top]=NULL;
	}
	return popnum;
}

int main(void){
	int size = 0;
	printf("���ϴ� ������ ũ�⸦ �Է��ϼ���:");
	scanf("%d", &size);

	printf("������ �����Ǿ����ϴ�.\n");
	char *stack;
	stack = (char*)malloc(sizeof(char)*size);
	initialize(stack, size);

	while (true){
		printf("\n���� ����: ");
		for (int i = 0; i<size; i++){
			if (stack[0] == NULL){
				printf("����ֽ��ϴ� \n");
				break;
			}
			printf("%c ", stack[i]);
		}
		printf("\n");


		printf("===============================\n");
		printf(" 1. ���� push \n 2. ���� pop \n 3. ���� \n");
		printf("===============================\n");
		int answer;
		scanf("%d", &answer);

		switch (answer){
		case 1:
			printf("push�� ��: ");
			char pn;
			scanf(" %c", &pn);
			push(stack, size, pn);
			break;
		case 2:
			printf("pop �Ͽ����ϴ�.\n");
			printf("pop�� : %c", pop(stack, size));
			break;
		case 3:
			printf("�����մϴ�.\n");
			freestack(stack, size);
			return 0;
		default:
			printf("1~3 ������ ���� �Է��ϼ���.\n");
			continue;
		}
	}
	return 0;
}
