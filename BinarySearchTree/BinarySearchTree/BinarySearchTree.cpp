#include "BinarySearchTree.h"

BST::BinarySearchTree()
{
	treeRoot = NULL;
}

BST::~BinarySearchTree()
{
	desTree(treeRoot);
}

TreeNode* BST::createBSTree(DataType* dataList,int length)
{
	treeRoot = new TreeNode();
	treeRoot->data = dataList[0];
	treeRoot->left = NULL;
	treeRoot->right = NULL;
	for (int i = 1; i < length; ++i)
	{
		addTreeNode(dataList[i]);
	}
	return treeRoot;
}

void BST::addTreeNode(DataType data)
{
	TreeNode* newNode = new TreeNode();
	newNode->data = data;
	newNode->left = NULL;
	newNode->right = NULL;
	TreeNode* node = treeRoot;
	while (1)
	{
		//����½���ֵ���ڸ�����ֵ��������������
		if (newNode->data >= node->data)
		{
			//��������Ϊ�գ���ֱ�Ӳ���
			if (node->right == NULL)
			{
				node->right = newNode;
				break;
			}
			//��Ϊ�գ�������жϡ�
			else
			{
				node = node->right;
			}
		}
		//�������������ϡ�
		else
		{
			if (node->left == NULL)
			{
				node->left = newNode;
				break;
			}
			else
			{
				node = node->left;
			}
		}
	}
}

void BST::inorderTraversal(TreeNode* node)
{
	if (node != NULL)
	{
		inorderTraversal(node->left);
		cout << node->data << ",";
		inorderTraversal(node->right);
	}
}

void BST::desTree(TreeNode* node)
{
	if (node != NULL)
	{
		desTree(node->left);
		desTree(node->right);
		delete [] node;
		node = NULL;
	}
}

TreeNode* BST::findTreeNode(DataType data)
{
	TreeNode* node = treeRoot;
	while (node)
	{
		if (node->data == data)
		{
			return node;
		}
		else if (node->data < data)
		{
			node = node->right;
		}
		else
		{
			node = node->left;
		}
	}
	return node;
}

void BST::delTreeNode(DataType data)
{
	TreeNode* node = treeRoot;//��ɾ���ڵ��ֵ
	TreeNode* parNode = NULL;//��ɾ���ڵ�ĸ��ڵ�
	while (node)
	{
		if (node->data == data)
		{
			//��1��Ҷ�ӽ��
			if (node->left == NULL && node->right == NULL)
			{
				//�����
				if (parNode == NULL)
				{
					treeRoot = NULL;
				}
				else
				{
					(node == parNode->left) ? parNode->left = NULL : parNode->right = NULL;
				}
				delete[] node;
				node = NULL;
			}
			//��2��ֻ����/������
			else if (node->left == NULL || node->right == NULL)
			{
				//���ڵ�
				if (parNode == NULL)
				{
					(node->left == NULL) ? treeRoot = node->right : treeRoot = node->left;
				}
				else
				{
					if (parNode->left == node)
					{
						(node->left == NULL) ? parNode->left = node->right : parNode->left = node->left;
					}
					else
					{
						(node->right == NULL) ? parNode->right = node->right : parNode->right = node->left;
					}
				}
				delete[] node;
				node = NULL;
			}
			//��3������������������������
			else
			{
				//Ѱ�������������ҽڵ���Ϊ�滻�ڵ�
				TreeNode* replaceNode = node->left;
				while (replaceNode->right)
				{
					replaceNode = replaceNode->right;
				}
				//�����滻�ڵ��ֵ
				DataType tempData = replaceNode->data;
				//ɾ��replaceNode�ڵ�
				delTreeNode(tempData);
				replaceNode = NULL;
				//�滻ֵ��
				node->data = tempData;
			}
			break;
		}
		else
		{
			parNode = node;
			if (data < node->data)
			{
				node = node->left;
			}
			else
			{
				node = node->right;
			}
		}
	}
}

TreeNode* BST::getTreeRoot()
{
	return treeRoot;
}