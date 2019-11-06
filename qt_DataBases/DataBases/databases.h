#ifndef DATABASES_H
#define DATABASES_H

#include <QFile>
#include <QVector>
#include <QtDebug>
#include <QSqlQuery>
#include <QSqlError>
#include <QSettings>
#include <QMessageBox>
#include <QSqlDatabase>
#include <QMainWindow>
#include <QMap>
#include <QFont>
#include <algorithm>
#include <QGroupBox>
#include "dblogin.h"
#include "custommessagebox.h"

using namespace std;



namespace Ui {
class DataBases;
}
enum OpType{Select,Update,Add,Delete};
class DataBases : public QMainWindow
{
    Q_OBJECT

public:
    static QString DataBasesName;
    explicit DataBases(QWidget *parent = 0);
    ~DataBases();
    void initWindow();
    QSqlDatabase* getDataBasePointer();


private slots:
    void on_DB_select_clicked();

    void on_DB_update_clicked();

    void on_DB_add_clicked();

    void on_DB_detele_clicked();

private:
    Ui::DataBases *ui;
    QSqlDatabase *DB_Mysql;//数据库指针
    QSqlQuery *query;//数据库执行指针
    QStringList m_MessageBoxInfo;//用于保存的对话框信息
    static QString configFileName;//配置文件名称
    //创建配置文件
    void createConfigFile();
    //查询当前数据中的表，并将表名写入下拉框
    void selectTable();
    //创建数据库操作的对话框(查询，删除，插入)
    bool createDialogBox(QString opName, QString tableName, OpType opType);
    //获取表的描述
    QStringList getTabelDescribe(QString tableName);
    //判断是否点击的为"commit"按钮
    bool isCommitClicked(CustomMessageBox* messageBox);
    bool isCommitClickedUpdate(CustomMessageBox* messageBox);
    //将text的内容写入展示框（文本框）中
    void addTextToShowBox(QString text);
};

#endif // DATABASES_H
