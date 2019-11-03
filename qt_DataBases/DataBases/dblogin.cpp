#include "dblogin.h"
#include "ui_dblogin.h"

DBLogin::DBLogin(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::DBLogin)
{
    ui->setupUi(this);
    DBpointer=(new DataBases())->getDataBasePointer();
    initLoginWindow();
}

DBLogin::~DBLogin()
{
    delete ui;
}

QString DBLogin::getUserName()
{
    QString userName=ui->DB_LineEdit_username->text();
    iniPointer->setValue("USERNAME/username",userName);
    return userName;
}

QString DBLogin::getPassWord()
{
    QString passWord= ui->DB_lineEdit_password->text();
    iniPointer->setValue("PASSWORD/password",passWord);
    return passWord;
}

QString DBLogin::getHostName()
{
    QString hostName=ui->DB_lineEdit_hostname->text();
    iniPointer->setValue("HOSTNAME/hostname",hostName);
    return hostName;
}

QString DBLogin::getPort()
{
    QString port=ui->DB_lineEdit_port->text();
    iniPointer->setValue("PORT/port",port);
    return port;
}

QString DBLogin::getDataBaseName()
{
    DataBases::DataBasesName=ui->DB_lineEdit_DBname->text();
    iniPointer->setValue("DATABASESNAME/databasesname",DataBases::DataBasesName);
    return DataBases::DataBasesName;
}

void DBLogin::on_DB_pushButton_commit_clicked()
{
    if(login()==false)
    {
        QDialog::done(LOGINFAILURE);
    }
    else
    {
        QDialog::done(LOGINSUCCESS);
    }

}

void DBLogin::initLoginWindow()
{
    setWindowTitle("数据库管理工具登录窗口");
    ui->DB_lineEdit_password->setEchoMode(QLineEdit::PasswordEchoOnEdit);
    readINIFile();
}

bool DBLogin::login()
{
    DBpointer->setHostName(getHostName());
    DBpointer->setPort(getPort().toInt());
    DBpointer->setUserName(getUserName());
    DBpointer->setPassword(getPassWord());
    DBpointer->setDatabaseName(getDataBaseName());
    return DBpointer->open();
}

void DBLogin::readINIFile()
{
    QString fileName="DataBases.ini";
    //读取配置文件
    iniPointer=new QSettings(fileName,QSettings::IniFormat);
    ui->DB_lineEdit_hostname->setText(iniPointer->value("HOSTNAME/hostname").toString());
    ui->DB_LineEdit_username->setText(iniPointer->value("USERNAME/username").toString());
    ui->DB_lineEdit_port->setText(iniPointer->value("PORT/port").toString());
    ui->DB_lineEdit_password->setText(iniPointer->value("PASSWORD/password").toString());
    ui->DB_lineEdit_DBname->setText(iniPointer->value("DATABASESNAME/databasesname").toString());
}
