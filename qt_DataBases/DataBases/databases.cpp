#include "databases.h"
#include "ui_databases.h"

QString DataBases::configFileName="DataBases.ini";
QString DataBases::DataBasesName="";

DataBases::DataBases(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::DataBases)
{
    ui->setupUi(this);
    query=NULL;
    DB_Mysql=new QSqlDatabase(QSqlDatabase::addDatabase("QMYSQL"));
    createConfigFile();
}

DataBases::~DataBases()
{
    delete ui;
}

QSqlDatabase *DataBases::getDataBasePointer()
{
    return DB_Mysql;
}

void DataBases::initWindow()
{
    setWindowTitle("数据库可视化管理工具");
    ui->DB_lineEdit_DBname->setText(DataBasesName+".db");
    query=new QSqlQuery(*DB_Mysql);
    selectTable();
}

void DataBases::on_DB_select_clicked()
{
    //查询
    SqlOperationDialogBox("查询",ui->DB_comboBox_tableName->currentText());
}

void DataBases::on_DB_update_clicked()
{
    //更新（更新存在旧值和新值的替换关系，所以需要一个新的自定义控件）
    //增加一个添加表的功能
}

void DataBases::on_DB_add_clicked()
{
    //添加
    SqlOperationDialogBox("添加",ui->DB_comboBox_tableName->currentText());

}

void DataBases::on_DB_detele_clicked()
{
    //删除
    SqlOperationDialogBox("删除",ui->DB_comboBox_tableName->currentText());
}

void DataBases::createConfigFile()
{
    //创建配置文件
    QFile *filePointer=new QFile();
    //如果配置文件不存在
    if(!filePointer->exists(configFileName))
    {
        filePointer->setFileName(configFileName);
        if(filePointer->open(QFile::WriteOnly))
        {
            filePointer->write("[USERNAME]\nusername=\n");
            filePointer->write("[PASSWORD]\npassword=\n");
            filePointer->write("[HOSTNAME]\nhostname=\n");
            filePointer->write("[PORT]\nport=\n");
            filePointer->write("[DATABASESNAME]\ndatabasesname=\n");
        }
        QSettings *iniPointer=new QSettings(configFileName,QSettings::IniFormat);
        iniPointer->setValue("USERNAME/username","root");
        iniPointer->setValue("HOSTNAME/hostname","localhost");
        iniPointer->setValue("PORT/port","3306");
        filePointer->close();
        delete iniPointer;
        delete filePointer;
    }
}

void DataBases::selectTable()
{
    QString sqlStr="show tables";
    query->exec(sqlStr);
    //遍历语句的执行结果将结果依此加入下拉框中
    while(query->next())
    {
        ui->DB_comboBox_tableName->addItem(query->value(0).toString());
    }
}

void DataBases::SqlOperationDialogBox(QString opName,QString tableName)
{
    QMessageBox *dialogBox=new QMessageBox(this);
    dialogBox->setMinimumWidth(200);
    //创建一个条形框的数组，用例保存创建出的条形框
    QVector<QLineEdit*> lineEditVector;
    //设置自定义框的主题名
    dialogBox->setWindowTitle(opName);
    QString sqlStr="DESCRIBE "+tableName;
    query->exec(sqlStr);
    while(query->next())
    {
        //将组件绑定在父控件上，当父空间被销毁时，子控件会自动被销毁
        QLabel *label=new QLabel(dialogBox);
        label->setText(query->value(0).toString());
        QLineEdit *lineEdit=new QLineEdit(dialogBox);
        lineEdit->setObjectName(label->text());
        label->setGeometry(0,lineEditVector.size()*20,100,15);
        lineEdit->setGeometry(110,lineEditVector.size()*20,50,15);
        lineEditVector.push_back(lineEdit);
    }

    QPushButton *commitButton=dialogBox->addButton(tr("commit"),QMessageBox::ActionRole);
    QPushButton *quitButton=dialogBox->addButton(QMessageBox::Cancel);
    dialogBox->exec();
    if(dialogBox->clickedButton()==commitButton)
    {
        //将框中的数据转为sql语句请执行
        for(int i=0;i<lineEditVector.size();++i)
        {
            qDebug()<<lineEditVector[i]->objectName()<<":"<<lineEditVector[i]->text()<<endl;
        }
    }
    if(dialogBox->clickedButton()==quitButton)
    {
        //取消操作
    }
    delete dialogBox;
}
