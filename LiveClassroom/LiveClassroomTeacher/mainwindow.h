#ifndef MAINWINDOW_H
#define MAINWINDOW_H

/*
 * 该类主要负责处理主页面的相关事宜
*/

#include <QMainWindow>
#include <QString>
#include <QDebug>
#include "handledata.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    void InitWindowStyle();

private slots:
    void on_Button_StartLiving_clicked();

private:
    Ui::MainWindow *ui;
    static QString m_MainWindowTitle;
    HandleData *m_DataPonit;
};

#endif // MAINWINDOW_H
