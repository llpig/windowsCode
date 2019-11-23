#include "mainwindow.h"
#include "ui_mainwindow.h"

int MainWindow::m_NetPort=5500;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    m_SocketPoint=NULL;
    InitStudentNet();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::InitStudentNet()
{
    setWindowTitle("勤径学生端");
    if(NULL==m_SocketPoint)
    {
        m_SocketPoint=new QUdpSocket();
        m_SocketPoint->bind(m_NetPort);
        connect(m_SocketPoint,SIGNAL(readyRead()),this,SLOT(DataHandle()));
    }
}

void MainWindow::DataHandle()
{
    QByteArray data;
    data.resize(m_SocketPoint->pendingDatagramSize());
    m_SocketPoint->readDatagram(data.data(),data.size());
    qDebug()<<"教书端消息:"<<data.data();
}
