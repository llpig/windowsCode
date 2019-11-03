#include "databases.h"
#include "dblogin.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    DataBases dataBases;
    DBLogin login;
    if(login.exec()==LOGINSUCCESS)
    {
        dataBases.initWindow();
        dataBases.show();
    }
    else
    {
        qDebug()<< dataBases.getDataBasePointer()->lastError().text()<<endl;
    }
    return a.exec();
}
