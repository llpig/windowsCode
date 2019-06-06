#include "twoTree.h"

void create(Node** root)
{
	int val = 0;
	scanf("%d", &val);
	if (val == 0)
	{
		*root = NULL;
		return;
	}
	(*root) = new (Node);
	(*root)->val = val;
	(*root)->left = NULL;
	(*root)->right = NULL;
	create(&((*root)->left));
	create(&((*root)->right));
}

void create(Node** root, int* treeArr, int len)
{
	static int i = 0;
	if (i >= len || treeArr[i] == 0)
	{
		(*root) = NULL;
		if (treeArr[i] == 0)
		{
			++i;
		}
		return;
	}
	(*root) = new (Node);
	(*root)->val = treeArr[i++];
	(*root)->left = NULL;
	(*root)->right = NULL;
	create((&(*root)->left), treeArr, len);
	create((&(*root)->right), treeArr, len);
}

void showTree(Node* root)
{
	printf("前序遍历:");
	perorder(root);
	printf("\n中序遍历:");
	inorder(root);
	printf("\n后序遍历:");
	postorder(root);
}

void perorder(Node* root)
{
	if (root != NULL)
	{
		printf("%d,", root->val);
		perorder(root->left);
		perorder(root->right);
	}
}

void inorder(Node* root)
{
	if (root != NULL)
	{
		inorder(root->left);
		printf("%d,", root->val);
		inorder(root->right);
	}
}

void postorder(Node* root)
{
	if (root != NULL)
	{
		postorder(root->left);
		postorder(root->right);
		printf("%d,", root->val);
	}
}
int main()
{
	int treeArr[] = { 1, 2, 0, 0, 3, 4, 0, 0, 5};
	Node* root = NULL;
	create(&root,treeArr,sizeof(treeArr)/sizeof(treeArr[0]));
	showTree(root);
	return 0;
}