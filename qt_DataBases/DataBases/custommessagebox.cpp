#include "custommessagebox.h"

int CustomMessageBox::m_LeftInterval=15;
int CustomMessageBox::m_RightInterval=15;
int CustomMessageBox::m_IntervalLR=10;
int CustomMessageBox::m_IntervalUL=5;

CustomMessageBox::CustomMessageBox(QWidget *parent) : QMessageBox(parent)
{

}

CustomMessageBox::~CustomMessageBox()
{

}

void CustomMessageBox::adjustWindowSize(QSize windowSize)
{
    m_MessageBoxWidth=windowSize.width();
    m_MessageBoxHeight=windowSize.height();
}

QSize CustomMessageBox::addWidgetToMessageBox(QStringList stringList, QWidget *parent)
{
    if(stringList.isEmpty())
    {
       return;
    }
    int maxLabelWidth=0,labelHeight=0;
    for(int i=0;i<stringList.size();++i)
    {
        QLabel *label=new QLabel(stringList[i],parent);
        QPushButton *pushButton=new QPushButton();
        pushButton->setObjectName(stringList[i]);

    }
}

void CustomMessageBox::resizeEvent(QResizeEvent *event)
{
    setFixedSize(m_MessageBoxWidth,m_MessageBoxHeight);
}

