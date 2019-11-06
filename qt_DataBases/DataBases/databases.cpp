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

//查询按钮
void DataBases::on_DB_select_clicked()
{
    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!createDialogBox("信息查询",tableName,Select))
    {
        return;
    }
    QString queries="",queryCond="",queryStr="";
    for(int i=0;i<m_MessageBoxInfo.size();i+=2)
    {
        if(m_MessageBoxInfo[i+1].isEmpty())
        {
            queries=queries+m_MessageBoxInfo[i]+",";
        }
        else
        {
            queryCond=queryCond+m_MessageBoxInfo[i]+" like '%"+m_MessageBoxInfo[i+1]+"%' and ";
        }
    }
    if(queries.isEmpty())
    {

        qDebug()<<"未检测到需要查询的内容"<<endl;
        return;
    }
    else if(queryCond.isEmpty())
    {
        queries.remove(queries.length()-1,1);
        queryStr=QString("Select %1 from %2;").arg(queries).arg(tableName);
    }
    else
    {
        queries.remove(queries.length()-1,1);
        queryCond.remove(queryCond.length()-5,5);
        queryStr=QString("Select %1 from %2 where %3;").arg(queries).arg(tableName).arg(queryCond);
    }
    addTextToShowBox("查询内容:("+queries+")\n查询结果：");
    int nums=queries.split(',').length();
    if(!queryStr.isEmpty())
    {
        if(query->exec(queryStr))
        {
            while(query->next())
            {
                for(int i=0;i<nums;++i)
                {
                     addTextToShowBox(query->value(i).toString()+",");
                }
                addTextToShowBox("\n");
            }
        }
        else
        {
            addTextToShowBox(query->lastError().text()+"\n");
        }
    }
}

//更新按钮
void DataBases::on_DB_update_clicked()
{
    //更新（更新存在旧值和新值的替换关系，所以需要一个新的自定义控件）
    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!createDialogBox("信息更新",tableName,Update))
    {
        return;
    }
    QString queries="",queryCond="",queryStr="";
    for(int i=0;i<m_MessageBoxInfo.size();i+=3)
    {
        //需要修改的内容
        if(!m_MessageBoxInfo[i+1].isEmpty())
        {
            queryCond=queryCond+m_MessageBoxInfo[i]+" like '%"+m_MessageBoxInfo[i+1]+"%' and ";
        }
        //条件
        if(!m_MessageBoxInfo[i+2].isEmpty())
        {
            queries=queries+m_MessageBoxInfo[i]+" = '"+m_MessageBoxInfo[i+2]+"',";
        }
    }
    if(queries.isEmpty()||queryCond.isEmpty())
    {
        qDebug()<<"请输入修改后的内容和修改的条件！"<<endl;
    }
    queries.remove(queries.length()-1,1);
    queryCond.remove(queryCond .length()-5,5);
    queryStr=QString("UpDate %1 Set %2 Where %3;").arg(tableName).arg(queries).arg(queryCond);
    if(query->exec(queryStr))
    {
        addTextToShowBox("符合条件的记录已更改完成！\n");
    }
    else
    {
        addTextToShowBox(query->lastError().text()+"\n");
    }
}

//添加按钮
void DataBases::on_DB_add_clicked()
{

    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!createDialogBox("信息添加",tableName,Add))
    {
        return;
    }
    QString queries="",queryCond="",queryStr="";
    for(int i=0;i<m_MessageBoxInfo.size();i+=2)
    {
        if(!m_MessageBoxInfo[i+1].isEmpty())
        {
            queries=queries+m_MessageBoxInfo[i]+",";
            queryCond=queryCond+"'"+m_MessageBoxInfo[i+1]+"',";
        }
    }
    if(queries.isEmpty())
    {
        qDebug()<<"请输入需要插入的内容"<<endl;
        return;
    }
    queries.remove(queries.length()-1,1);
    queryCond.remove(queryCond.length()-1,1);
    queryStr=QString("Insert into %1 (%2) values (%3);").arg(tableName).arg(queries).arg(queryCond);
    if(query->exec(queryStr))
    {
        addTextToShowBox("记录已成功添加！");
    }
    else
    {
        addTextToShowBox(query->lastError().text()+"\n");
    }
}

//删除按钮
void DataBases::on_DB_detele_clicked()
{
    QString tableName=ui->DB_comboBox_tableName->currentText();
    if(!createDialogBox("信息删除",tableName,Delete))
    {
        return;
    }
    QString queryCond="",queryStr="";
    for(int i=0;i<m_MessageBoxInfo.size();i+=2)
    {
        if(!m_MessageBoxInfo[i+1].isEmpty())
        {
            queryCond=queryCond+m_MessageBoxInfo[i]+" like '%"+m_MessageBoxInfo[i+1]+"%' and ";
        }
    }
    if(queryCond.isEmpty())
    {
        qDebug()<<"请填写需要删除内容的条件"<<endl;
        return;
    }
    queryCond.remove(queryCond.length()-5,5);
    queryStr=QString("Delete from %1 where %2;").arg(tableName).arg(queryCond);
    qDebug()<<queryStr<<endl;
    if(query->exec(queryStr))
    {
        addTextToShowBox("符合条件的内容已经被删除！\n");
    }
    else
    {
        addTextToShowBox(query->lastError().text());
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


bool DataBases::createDialogBox(QString opName, QString tableName, OpType opType)
{
    CustomMessageBox *dialogBox=new CustomMessageBox(this,opName);
    QSize boxSize;
    if(opType==Update)
    {
        boxSize=dialogBox->addWidgetToMessageBoxUpdate(getTabelDescribe(tableName));
    }
    else
    {
        boxSize=dialogBox->addWidgetToMessageBox(getTabelDescribe(tableName));
    }
    int boxHeight=dialogBox->addPushButtonToMessageBox();
    boxSize.setHeight(boxSize.height()+1.5*boxHeight);
    dialogBox->adjustWindowSize(boxSize);
    dialogBox->exec();
    if(opType==Update)
    {
        return isCommitClickedUpdate(dialogBox);
    }
    else
    {
        return isCommitClicked(dialogBox);
    }
}


QStringList DataBases::getTabelDescribe(QString tableName)
{
    QString sqlStr="Describe "+tableName;
    QStringList sqlResult;
    bool res=query->exec(sqlStr);
    while(res&&query->next())
    {
        sqlResult.push_back(query->value(0).toString());
    }
    return sqlResult;
}

bool DataBases::isCommitClicked(CustomMessageBox *messageBox)
{
    bool bRet=false;
    m_MessageBoxInfo.clear();
    if(messageBox->clickedButton()==messageBox->m_CommitPushButton)
    {
        bRet=true;
        for(int i=0;i<messageBox->m_LineEditVector.size();++i)
        {
            m_MessageBoxInfo.push_back(messageBox->m_LineEditVector[i]->objectName());
            m_MessageBoxInfo.push_back(messageBox->m_LineEditVector[i]->text());
        }
    }
    return bRet;
}

bool DataBases::isCommitClickedUpdate(CustomMessageBox *messageBox)
{
    bool bRet=false;
    m_MessageBoxInfo.clear();
    if(messageBox->clickedButton()==messageBox->m_CommitPushButton)
    {
        bRet=true;
        for(int i=0;i<messageBox->m_LineEditVector.size();i+=2)
        {
            m_MessageBoxInfo.push_back(messageBox->m_LineEditVector[i]->objectName());
            m_MessageBoxInfo.push_back(messageBox->m_LineEditVector[i]->text());
            m_MessageBoxInfo.push_back(messageBox->m_LineEditVector[i+1]->text());
        }
    }
    return bRet;
}


void DataBases::addTextToShowBox(QString text)
{
    //将文本框中的内容清空
    ui->DB_show->clear();
    ui->DB_show->textCursor().insertText(text);
}

