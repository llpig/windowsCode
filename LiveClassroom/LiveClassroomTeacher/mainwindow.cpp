#include "mainwindow.h"
#include "ui_mainwindow.h"

QString MainWindow::m_MainWindowTitle="勤径";

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    m_DataPonit=new HandleData();
    InitWindowStyle();

}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::InitWindowStyle()
{
    setWindowTitle(m_MainWindowTitle);
}



void MainWindow::on_Button_StartLiving_clicked()
{
    //处理点击后的工作
    m_DataPonit->SendData();
    qDebug()<<"教师端消息已发送"<<endl;
}
