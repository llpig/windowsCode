#ifndef DBLOGIN_H
#define DBLOGIN_H

#include <QDialog>
#include <QString>
#include <QSettings>
#include <QFile>
#include "databases.h"

#define  LOGINSUCCESS 0
#define  LOGINFAILURE 1

namespace Ui {
class DBLogin;
}

class DBLogin : public QDialog
{
    Q_OBJECT

public:
    explicit DBLogin(QWidget *parent = 0);
    ~DBLogin();
    QString getUserName();
    QString getPassWord();
    QString getHostName();
    QString getPort();
    QString getDataBaseName();

private slots:
    void on_DB_pushButton_commit_clicked();

private:
    Ui::DBLogin *ui;

    QSqlDatabase *DBpointer;
    QSettings *iniPointer;
    //初始化登录窗口
    void initLoginWindow();
    //登录函数
    bool login();
    //读取配置文件
    void readINIFile();
    void updateINIFile();
};

#endif // DBLOGIN_H
