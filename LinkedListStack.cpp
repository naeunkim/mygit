#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

typedef struct node{
	char data;
	node *link;
};

void initialize(node *stack, int size){
	for (int i = 0; i < size - 1 ; i++){
		stack[i].data = NULL;
		stack[i].link = &stack[i + 1];
	}
	stack[size - 1].data = NULL;
	stack[size - 1].link = NULL;

}

void push(node *stack, int size, char insert){
	int top;
	for (int i = 0; i<size; i++){
		if (stack[i].data == NULL){
			top = i;
			break;
		}
		if (i == size - 1){
			printf("������ ��á���ϴ�! \n");
			return;
		}
	}
	stack[top].data = insert;
	printf(" %d ��° �ε����� Ǫ�õǾ����ϴ�.\n", top + 1);
}

char pop(node *stack, int size){
	char popnum;
	int top;
	if (stack[0].data == NULL) {
		printf("������ ����ֽ��ϴ�.\n");
		return 0;
	}
	else {
		for (int i = 1; i<size; i++){
			if (stack[i].data == NULL){
				top = i - 1;
				break;
			}
			if (i == size - 1){
				top = size - 1;
			}
		}
		popnum = stack[top].data;
		stack[top].data= NULL;
	}
	return popnum;
}
int main(void){
	int size = 0;
	printf("���ϴ� ������ ũ�⸦ �Է��ϼ���:");
	scanf("%d", &size);

	printf("������ �����Ǿ����ϴ�.\n");
	node* stack;
	stack = (node *)malloc(sizeof(node)*size);
	initialize(stack, size);
	while (true){
		printf(" \n���� ����: ");
		for (int i = 0; i < size; i++){
			if (stack[0].data == NULL){
				printf("����ֽ��ϴ� \n");
				break;
			}
			printf("%c ", stack[i].data);
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
			return 0;
		default:
			printf("1~3 ������ ���� �Է��ϼ���.\n");
			continue;
		}
	}
	return 0;
}