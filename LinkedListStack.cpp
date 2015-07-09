#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

typedef struct node{
	char data;
	node *link;
};

void stack_status(node *stack){
	printf("\n현재 스택: ");
	if (stack == NULL){
		printf("비어있습니다. \n");
	}
	for (; stack;){
		printf("%c ", stack->data);
		stack = stack->link;
	}

}

node* push(node *top, char insert){
	node * newnode;
	newnode = (node *)malloc(sizeof(node));
	newnode->link = top;
	newnode->data = insert;
	return newnode;
}

node* pop(node *stack){
	char popnum = 0;
	if (stack == NULL)
		return NULL;
	else {
		popnum = stack->data;
		node *temp;
		temp = stack;
		printf("pop값 : %c \n", popnum);
		stack = stack->link;
		free(temp);
	}
	return stack;
}
int main(void){
	node* stack = NULL;

	while (true){

		stack_status(stack);
		printf("===============================\n");
		printf(" 1. 스택 push \n 2. 스택 pop \n 3. 종료 \n");
		printf("===============================\n");
		int answer;
		char popnum = 0;
		scanf("%d", &answer);

		switch (answer){
		case 1:
			printf("push할 값: ");
			char pn;
			scanf(" %c", &pn);
			stack = push(stack, pn);
			break;
		case 2:
			stack= pop(stack);
			break;
		case 3:
			printf("종료합니다.\n");
			free(stack);
			return 0;
		default:
			printf("1~3 사이의 값만 입력하세요.\n");
			continue;
		}
	}
	return 0;
}