#include "BinarySearchTree.h"
int main()
{
	BST bsTree;
	DataType dataArr[] = { 5, 8, 3, 2, 4, 1, 6, 7, 9 };
	TreeNode* treeNode = bsTree.createBSTree(dataArr, sizeof(dataArr) / sizeof(dataArr[0]));
	//bsTree.addTreeNode(10);
	//bsTree.addTreeNode(9);
	//bsTree.addTreeNode(11);
	bsTree.delTreeNode(5);
	bsTree.inorderTraversal(bsTree.getTreeRoot());
	cout << endl;
	bsTree.delTreeNode(3);
	bsTree.inorderTraversal(bsTree.getTreeRoot());
	cout << endl;
	bsTree.delTreeNode(8);
	bsTree.inorderTraversal(bsTree.getTreeRoot());
	cout << endl;
	return 0;
}