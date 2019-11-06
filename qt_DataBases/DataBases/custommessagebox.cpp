#include "custommessagebox.h"

int CustomMessageBox::m_LeftInterval=15;
int CustomMessageBox::m_RightInterval=15;
int CustomMessageBox::m_IntervalLR=10;
int CustomMessageBox::m_IntervalUL=5;
int CustomMessageBox::m_LineEditWidth=110;
QVector<QLineEdit*> CustomMessageBox::m_LineEditVector;

CustomMessageBox::CustomMessageBox(QWidget *parent, QString titleText) : QMessageBox(parent)
{
    setWindowTitle(titleText);
    m_CommitPushButton=NULL;
    m_CancelPushButtom=NULL;
}

CustomMessageBox::~CustomMessageBox()
{

}

void CustomMessageBox::adjustWindowSize(QSize windowSize)
{
    m_MessageBoxWidth=windowSize.width();
    m_MessageBoxHeight=windowSize.height();
}

QSize CustomMessageBox::addWidgetToMessageBox(QStringList stringList)
{
    m_LineEditVector.clear();
    if(stringList.isEmpty())
    {
       return QSize(0,0);
    }
    int maxLabelWidth=0,labelHeight=0;
    for(int i=0;i<stringList.size();++i)
    {
        QLabel *label=new QLabel(stringList[i],this);
        label->setGeometry(m_LeftInterval,i*(label->height()+m_IntervalUL),label->width(),label->height());
        QLineEdit *lineEdit=new QLineEdit(this);
        lineEdit->setObjectName(stringList[i]);
        m_LineEditVector.push_back(lineEdit);
        maxLabelWidth=max(maxLabelWidth,label->width());
        labelHeight=label->height();
    }
    for(int i=0;i<stringList.size();++i)
    {
        m_LineEditVector[i]->setGeometry(maxLabelWidth+m_IntervalLR,i*(labelHeight+m_IntervalUL),m_LineEditWidth,labelHeight);
    }
    int messageBoxWidth=m_LeftInterval+m_RightInterval+maxLabelWidth+m_IntervalLR+m_LineEditWidth;
    int messageBoxHeight=stringList.size()*(labelHeight+m_IntervalUL);
    return QSize(messageBoxWidth,messageBoxHeight);
}

QSize CustomMessageBox::addWidgetToMessageBoxUpdate(QStringList stringList)
{
    m_LineEditVector.clear();
    if(stringList.isEmpty())
    {
        return QSize(0,0);
    }
    int maxLabelWidth=0,labelHeight=0;
    for(int i=0;i<stringList.size();++i)
    {
        QLabel *label=new QLabel(stringList[i],this);
        label->setGeometry(m_LeftInterval,(i+1)*(label->height()+m_IntervalUL),label->width(),label->height());
        QLineEdit *oldLineEdit=new QLineEdit(this);
        oldLineEdit->setObjectName(stringList[i]);
        m_LineEditVector.push_back(oldLineEdit);
        QLineEdit *newLineEdit=new QLineEdit(this);
        m_LineEditVector.push_back(newLineEdit);
        maxLabelWidth=max(maxLabelWidth,label->width());
        labelHeight=label->height();
    }
    int k=1;
    for(int i=0;i<m_LineEditVector.size();i+=2)
    {
        m_LineEditVector[i]->setGeometry(maxLabelWidth+m_IntervalLR,k*(labelHeight+m_IntervalUL),m_LineEditWidth,labelHeight);
        m_LineEditVector[i+1]->setGeometry(maxLabelWidth+m_LineEditWidth+2*m_IntervalLR,k*(labelHeight+m_IntervalUL),m_LineEditWidth,labelHeight);
        ++k;
    }
    QLabel *newLabel=new QLabel("更新后数据",this);
    newLabel->setGeometry(maxLabelWidth+m_LineEditWidth+2*m_IntervalLR,0,newLabel->width(),newLabel->height());
    QLabel *oldLabel=new QLabel("更新前数据",this);
    oldLabel->setGeometry(maxLabelWidth+m_IntervalLR,0,oldLabel->width(),oldLabel->height());
    int messageBoxWidth=m_LeftInterval+m_RightInterval+maxLabelWidth+2*m_IntervalLR+2*m_LineEditWidth;
    int messageBoxHeight=(stringList.size()+1)*(labelHeight+m_IntervalUL);
    return QSize(messageBoxWidth,messageBoxHeight);
}

int CustomMessageBox::addPushButtonToMessageBox()
{
    m_CommitPushButton=this->addButton("commit",QMessageBox::ActionRole);
    m_CancelPushButtom=this->addButton(QMessageBox::Cancel);
    return m_CancelPushButtom->height();
}

void CustomMessageBox::resizeEvent(QResizeEvent *event)
{
    setFixedSize(m_MessageBoxWidth,m_MessageBoxHeight);
}

