/********************************************************************************
** Form generated from reading UI file 'databaseslogin.ui'
**
** Created by: Qt User Interface Compiler version 5.4.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_DATABASESLOGIN_H
#define UI_DATABASESLOGIN_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_DataBaseslogin
{
public:
    QWidget *centralwidget;
    QPushButton *DB_pushButton_commit;
    QPushButton *DB_pushButton_quit;
    QLabel *label;
    QLineEdit *DB_LineEdit_username;
    QLineEdit *DB_lineEdit_password;
    QLabel *label_2;
    QLabel *label_3;
    QLineEdit *DB_lineEdit_hostname;
    QLineEdit *DB_lineEdit_port;
    QLabel *label_4;
    QLabel *label_5;
    QLineEdit *DB_lineEdit_DBname;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *DataBaseslogin)
    {
        if (DataBaseslogin->objectName().isEmpty())
            DataBaseslogin->setObjectName(QStringLiteral("DataBaseslogin"));
        DataBaseslogin->resize(397, 298);
        centralwidget = new QWidget(DataBaseslogin);
        centralwidget->setObjectName(QStringLiteral("centralwidget"));
        DB_pushButton_commit = new QPushButton(centralwidget);
        DB_pushButton_commit->setObjectName(QStringLiteral("DB_pushButton_commit"));
        DB_pushButton_commit->setGeometry(QRect(40, 220, 75, 23));
        DB_pushButton_quit = new QPushButton(centralwidget);
        DB_pushButton_quit->setObjectName(QStringLiteral("DB_pushButton_quit"));
        DB_pushButton_quit->setGeometry(QRect(270, 220, 75, 23));
        label = new QLabel(centralwidget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(80, 20, 54, 12));
        DB_LineEdit_username = new QLineEdit(centralwidget);
        DB_LineEdit_username->setObjectName(QStringLiteral("DB_LineEdit_username"));
        DB_LineEdit_username->setGeometry(QRect(150, 19, 151, 25));
        DB_lineEdit_password = new QLineEdit(centralwidget);
        DB_lineEdit_password->setObjectName(QStringLiteral("DB_lineEdit_password"));
        DB_lineEdit_password->setGeometry(QRect(150, 60, 151, 25));
        label_2 = new QLabel(centralwidget);
        label_2->setObjectName(QStringLiteral("label_2"));
        label_2->setGeometry(QRect(80, 61, 54, 12));
        label_3 = new QLabel(centralwidget);
        label_3->setObjectName(QStringLiteral("label_3"));
        label_3->setGeometry(QRect(80, 101, 54, 12));
        DB_lineEdit_hostname = new QLineEdit(centralwidget);
        DB_lineEdit_hostname->setObjectName(QStringLiteral("DB_lineEdit_hostname"));
        DB_lineEdit_hostname->setGeometry(QRect(150, 100, 151, 25));
        DB_lineEdit_port = new QLineEdit(centralwidget);
        DB_lineEdit_port->setObjectName(QStringLiteral("DB_lineEdit_port"));
        DB_lineEdit_port->setGeometry(QRect(150, 149, 151, 25));
        label_4 = new QLabel(centralwidget);
        label_4->setObjectName(QStringLiteral("label_4"));
        label_4->setGeometry(QRect(80, 150, 54, 12));
        label_5 = new QLabel(centralwidget);
        label_5->setObjectName(QStringLiteral("label_5"));
        label_5->setGeometry(QRect(80, 190, 54, 12));
        DB_lineEdit_DBname = new QLineEdit(centralwidget);
        DB_lineEdit_DBname->setObjectName(QStringLiteral("DB_lineEdit_DBname"));
        DB_lineEdit_DBname->setGeometry(QRect(150, 189, 151, 25));
        DataBaseslogin->setCentralWidget(centralwidget);
        menubar = new QMenuBar(DataBaseslogin);
        menubar->setObjectName(QStringLiteral("menubar"));
        menubar->setGeometry(QRect(0, 0, 397, 23));
        DataBaseslogin->setMenuBar(menubar);
        statusbar = new QStatusBar(DataBaseslogin);
        statusbar->setObjectName(QStringLiteral("statusbar"));
        DataBaseslogin->setStatusBar(statusbar);

        retranslateUi(DataBaseslogin);

        QMetaObject::connectSlotsByName(DataBaseslogin);
    } // setupUi

    void retranslateUi(QMainWindow *DataBaseslogin)
    {
        DataBaseslogin->setWindowTitle(QApplication::translate("DataBaseslogin", "MainWindow", 0));
        DB_pushButton_commit->setText(QApplication::translate("DataBaseslogin", "\347\231\273\345\275\225", 0));
        DB_pushButton_quit->setText(QApplication::translate("DataBaseslogin", "\351\200\200\345\207\272", 0));
        label->setText(QApplication::translate("DataBaseslogin", "\347\224\250\346\210\267\345\220\215:", 0));
        label_2->setText(QApplication::translate("DataBaseslogin", "\345\257\206\347\240\201:", 0));
        label_3->setText(QApplication::translate("DataBaseslogin", "\344\270\273\346\234\272\345\220\215:", 0));
        label_4->setText(QApplication::translate("DataBaseslogin", "\347\253\257\345\217\243\345\217\267:", 0));
        label_5->setText(QApplication::translate("DataBaseslogin", "\346\225\260\346\215\256\345\272\223:", 0));
    } // retranslateUi

};

namespace Ui {
    class DataBaseslogin: public Ui_DataBaseslogin {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DATABASESLOGIN_H
