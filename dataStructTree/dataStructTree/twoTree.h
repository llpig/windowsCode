#ifndef TWOTREE
#define TWOTREE

#include <iostream>
#include <stdio.h>
using namespace std;

struct treeNode
{
	int val;
	struct treeNode* left;
	struct treeNode* right;
};
typedef struct treeNode Node;
//������
void create(Node** root);//�û�����һ����������
void create(Node** root, int* treeArr, int len);//ʹ����ȫ������������
void create(Node** root, int*perorder, int* inorder);//ʹ��ǰ�������Ľṹ
void create(Node** root, int*postorder, int* inorder); //ʹ�ú��������Ľṹ
//������
	//�ݹ�ʵ��
void perorder(Node* root);//ǰ�����
void inorder(Node* root);//�������
void postorder(Node* root);//�������
	//ѭ��ʵ��

#endif