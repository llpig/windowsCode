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
    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!SqlOperationDialogBox("查询",tableName))
        return;

    //将对话框中内容拼接为sql语句
    QString sqlStr="select ",selectStr="",condStr="";

    for(int i=0;i<sqlVector.size();++i)
    {
        if(sqlVector[i][1].isEmpty())
        {
            selectStr+=sqlVector[i][0];
            selectStr+=",";
        }
        else
        {
            condStr+=sqlVector[i][0];
            condStr+=" like ";
            condStr=condStr+"'%"+sqlVector[i][1]+"%'";
            condStr+=" and ";
        }
    }
    //保存需要查询内容
    selectStr[selectStr.length()-1]=' ';
    sqlStr+=selectStr;
    sqlStr+="from ";
    sqlStr+=ui->DB_comboBox_tableName->currentText();
    //保存查询条件
    condStr.remove(condStr.length()-4,4);
    condStr=" where "+condStr;
    sqlStr+=condStr;
    QStringList strList=selectStr.split(',');
    //计算本次共查询了几个内容
    int selectNum=strList.length();
    for(int i=0;i<selectNum;++i)
    {
        ui->DB_show->textCursor().insertText(strList[i]+" ");
    }
    ui->DB_show->textCursor().insertText("\n");
    query->exec(sqlStr);
    while(query->next())
    {
        for(int i=0;i<selectNum;++i)
        {
            ui->DB_show->textCursor().insertText(query->value(i).toString()+" ");
        }
        ui->DB_show->textCursor().insertText("\n");
    }
    sqlVector.clear();

}

void DataBases::on_DB_update_clicked()
{
    //更新（更新存在旧值和新值的替换关系，所以需要一个新的自定义控件）
    //增加一个添加表的功能
}

void DataBases::on_DB_add_clicked()
{
    //添加
    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!SqlOperationDialogBox("添加",tableName))
        return;
    QString sqlStr="insert into "+tableName;
    QString addStr="(",condStr=" (";
    for(int i=0;i<sqlVector.size();++i)
    {
        if(!sqlVector[i][1].isEmpty())
        {
            addStr=addStr+"'"+sqlVector[i][1]+"'"+",";
            condStr=condStr+sqlVector[i][0]+",";
        }
    }
    addStr[addStr.length()-1]=')';
    condStr[condStr.length()-1]=')';
    sqlStr=sqlStr+condStr+" values ";
    sqlStr=sqlStr+addStr+";";
    if(query->exec(sqlStr))
    {
        ui->DB_show->textCursor().insertText("成功插入一条记录");
    }
    else
    {
        ui->DB_show->textCursor().insertText(query->lastError().text());
    }
    sqlVector.clear();
}

void DataBases::on_DB_detele_clicked()
{
    //删除
    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!SqlOperationDialogBox("删除",tableName))
        return;
    QString sqlStr="delete from "+tableName;
    QString condStr=" where ";
    for(int i=0;i<sqlVector.size();++i)
    {
        if(!sqlVector[i][1].isEmpty())
        {
            condStr=condStr+sqlVector[i][0]+" like '"+sqlVector[i][1]+"' and ";
        }
    }
    condStr.remove(condStr.length()-5,5);
    sqlStr=sqlStr+condStr+";";
    if(query->exec(sqlStr))
    {
        ui->DB_show->textCursor().insertText("成功删除一条记录");
    }
    else
    {
        ui->DB_show->textCursor().insertText(query->lastError().text());
    }
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

bool DataBases::SqlOperationDialogBox(QString opName,QString tableName)
{
    QMessageBox *dialogBox=new QMessageBox(this);
    //设置自定义对话框的主题名
    dialogBox->setWindowTitle(opName);
    dialogBox->adjustSize();
    //创建一个条形框的数组，用例保存创建出的条形框
    QVector<QLineEdit*> lineEditVector;
    QString sqlStr="DESCRIBE "+tableName;
    query->exec(sqlStr);
    int maxLabelWidth=0,labelHeight=0;
    while(query->next())
    {
        //将组件绑定在父控件上，当父空间被销毁时，子控件会自动被销毁
        QLabel *label=new QLabel(dialogBox);
        QLineEdit *lineEdit=new QLineEdit(dialogBox);
        label->setText(query->value(0).toString());
        //标签会根据文本自动调节大小
        label->adjustSize();
        maxLabelWidth=max(maxLabelWidth,label->width());
        labelHeight=label->height();
        label->setGeometry(0,lineEditVector.size()*(label->height()+5),label->width(),label->height());
        //将条形框添加入数组
        lineEdit->setObjectName(label->text());
        lineEditVector.push_back(lineEdit);
    }
    for(int i=0;i<lineEditVector.size();++i)
    {
        lineEditVector[i]->setGeometry(maxLabelWidth+5,i*(labelHeight+5),100,labelHeight);
    }
    QPushButton *commitButton=dialogBox->addButton(tr("commit"),QMessageBox::ActionRole);
    commitButton->adjustSize();
    QPushButton *quitButton=dialogBox->addButton(QMessageBox::Cancel);
    quitButton->adjustSize();
    dialogBox->exec();
    sqlVector.resize(0);
    if(dialogBox->clickedButton()==commitButton)
    {
        //将框中的数据转为sql语句请执行
        sqlVector.resize(lineEditVector.size());
        for(int i=0;i<lineEditVector.size();++i)
        {
            sqlVector[i].resize(2);
            sqlVector[i][0]=lineEditVector[i]->objectName();
            sqlVector[i][1]=lineEditVector[i]->text();
        }
    }

    delete commitButton;
    delete quitButton;
    delete dialogBox;
    //将文本框中的内容清空
    ui->DB_show->clear();
    return ((sqlVector.size()==0)?false:true);
}
