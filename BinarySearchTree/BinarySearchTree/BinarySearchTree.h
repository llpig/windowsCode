#ifndef Binary_Search_Tree
#define Binary_Search_Tree

#include <iostream>
using namespace std;

//���Ľڵ������
typedef int DataType;
//���ݵĽڵ�
struct TreeNode
{
	DataType data;
	struct TreeNode* left;
	struct TreeNode* right;
};

//���������������ݽṹ
class BinarySearchTree
{
public:
	BinarySearchTree();
	~BinarySearchTree();
	TreeNode* createBSTree(DataType* dataList,int length);//��������������
	void inorderTraversal(TreeNode* node);//�������������������
	void addTreeNode(DataType data);//��������µĽڵ�
	void delTreeNode(DataType data);//ɾ�����еĽڵ�
	TreeNode* findTreeNode(DataType data);//��ѯ
	void desTree(TreeNode* node);//������
	TreeNode* getTreeRoot();
private:
	TreeNode* treeRoot;//����
};

typedef BinarySearchTree BST;
#endif

