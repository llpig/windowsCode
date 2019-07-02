#ifndef Binary_Search_Tree
#define Binary_Search_Tree

#include <iostream>
using namespace std;

//树的节点的类型
typedef int DataType;
//数据的节点
struct TreeNode
{
	DataType data;
	struct TreeNode* left;
	struct TreeNode* right;
};

//二叉排序树的数据结构
class BinarySearchTree
{
public:
	BinarySearchTree();
	~BinarySearchTree();
	TreeNode* createBSTree(DataType* dataList,int length);//创建二叉排序树
	void inorderTraversal(TreeNode* node);//二叉排序树的中序遍历
	void addTreeNode(DataType data);//给树添加新的节点
	void delTreeNode(DataType data);//删除树中的节点
	TreeNode* findTreeNode(DataType data);//查询
	void desTree(TreeNode* node);//销毁树
	TreeNode* getTreeRoot();
private:
	TreeNode* treeRoot;//树根
};

typedef BinarySearchTree BST;
#endif

