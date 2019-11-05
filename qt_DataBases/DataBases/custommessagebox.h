#ifndef CUSTOMMESSAGEBOX_H
#define CUSTOMMESSAGEBOX_H

#include <QMessageBox>
#include <QResizeEvent>
#include <QLabel>
#include <QPushButton>
#include <algorithm>
#include <QVector>
#include <QLineEdit>
using namespace std;

class CustomMessageBox : public QMessageBox
{
    Q_OBJECT
public:
    explicit CustomMessageBox(QWidget *parent = 0);
    ~CustomMessageBox();
    //对话框可以根据其内容自动计算大小
    void adjustWindowSize(QSize windowSize);
    QSize addWidgetToMessageBox(QStringList stringList,QWidget *parent);
protected:
    int m_MessageBoxWidth;
    int m_MessageBoxHeight;
    static int m_LeftInterval;//窗口左间隔
    static int m_RightInterval;//窗口右间隔
    static int m_IntervalLR;//组件左右间隔
    static int m_IntervalUL;//组件上下间隔
    QVector<QLineEdit&> m_pushButtonVector;//用于保存创建的条形框
    void resizeEvent(QResizeEvent* event);
};

#endif // CUSTOMMESSAGEBOX_H
