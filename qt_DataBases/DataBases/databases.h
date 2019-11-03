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
#include "dblogin.h"

namespace Ui {
class DataBases;
}

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
    static QString configFileName;//配置文件名称

    //创建配置文件
    void createConfigFile();
    //查询当前数据中的表，并将表名写入下拉框
    void selectTable();
    //创建数据库操作的对话框
    void SqlOperationDialogBox(QString opName, QString tableName);
};

#endif // DATABASES_H
