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
			printf("스택이 꽉찼습니다! \n");
			return;
		}
	}
	stack[top] = insert;
	printf(" %d 번째 인덱스에 푸시되었습니다.\n", top + 1);
}

char pop(char *stack, int size){
	char popnum;
	int top;
	if (stack[0] == NULL) {
		printf("스택이 비어있습니다.\n");
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
	printf("원하는 스택의 크기를 입력하세요:");
	scanf("%d", &size);

	printf("스택이 생성되었습니다.\n");
	char *stack;
	stack = (char*)malloc(sizeof(char)*size);
	initialize(stack, size);

	while (true){
		printf("\n현재 스택: ");
		for (int i = 0; i<size; i++){
			if (stack[0] == NULL){
				printf("비어있습니다 \n");
				break;
			}
			printf("%c ", stack[i]);
		}
		printf("\n");


		printf("===============================\n");
		printf(" 1. 스택 push \n 2. 스택 pop \n 3. 종료 \n");
		printf("===============================\n");
		int answer;
		scanf("%d", &answer);

		switch (answer){
		case 1:
			printf("push할 값: ");
			char pn;
			scanf(" %c", &pn);
			push(stack, size, pn);
			break;
		case 2:
			printf("pop 하였습니다.\n");
			printf("pop값 : %c", pop(stack, size));
			break;
		case 3:
			printf("종료합니다.\n");
			freestack(stack, size);
			return 0;
		default:
			printf("1~3 사이의 값만 입력하세요.\n");
			continue;
		}
	}
	return 0;
}
