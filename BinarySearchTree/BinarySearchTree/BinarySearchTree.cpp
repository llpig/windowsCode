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
		//如果新结点的值大于根结点的值，则在右子树。
		if (newNode->data >= node->data)
		{
			//若右子树为空，这直接插入
			if (node->right == NULL)
			{
				node->right = newNode;
				break;
			}
			//不为空，则继续判断。
			else
			{
				node = node->right;
			}
		}
		//否则在左子树上。
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
	TreeNode* node = treeRoot;//待删除节点的值
	TreeNode* parNode = NULL;//待删除节点的父节点
	while (node)
	{
		if (node->data == data)
		{
			//（1）叶子结点
			if (node->left == NULL && node->right == NULL)
			{
				//根结点
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
			//（2）只有左/右子树
			else if (node->left == NULL || node->right == NULL)
			{
				//根节点
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
			//（3）既有左子树，又有右子树
			else
			{
				//寻找左子树的最右节点作为替换节点
				TreeNode* replaceNode = node->left;
				while (replaceNode->right)
				{
					replaceNode = replaceNode->right;
				}
				//保存替换节点的值
				DataType tempData = replaceNode->data;
				//删除replaceNode节点
				delTreeNode(tempData);
				replaceNode = NULL;
				//替换值域
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