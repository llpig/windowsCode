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
//创建树
void create(Node** root);//用户输入一个满二叉树
void create(Node** root, int* treeArr, int len);//使用完全二叉树的数组
void create(Node** root, int*perorder, int* inorder);//使用前序和中序的结构
void create(Node** root, int*postorder, int* inorder); //使用后序和中序的结构
//遍历树
	//递归实现
void perorder(Node* root);//前序遍历
void inorder(Node* root);//中序遍历
void postorder(Node* root);//后序遍历
	//循环实现

#endif