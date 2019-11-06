/********************************************************************************
** Form generated from reading UI file 'databases.ui'
**
** Created by: Qt User Interface Compiler version 5.4.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_DATABASES_H
#define UI_DATABASES_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QComboBox>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_DataBases
{
public:
    QWidget *centralWidget;
    QPushButton *DB_select;
    QPushButton *DB_add;
    QPushButton *DB_update;
    QPushButton *DB_detele;
    QTextEdit *DB_show;
    QComboBox *DB_comboBox_tableName;
    QLabel *label;
    QLabel *label_2;
    QLineEdit *DB_lineEdit_DBname;
    QLabel *label_3;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *DataBases)
    {
        if (DataBases->objectName().isEmpty())
            DataBases->setObjectName(QStringLiteral("DataBases"));
        DataBases->resize(400, 300);
        centralWidget = new QWidget(DataBases);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        DB_select = new QPushButton(centralWidget);
        DB_select->setObjectName(QStringLiteral("DB_select"));
        DB_select->setGeometry(QRect(300, 60, 75, 23));
        DB_add = new QPushButton(centralWidget);
        DB_add->setObjectName(QStringLiteral("DB_add"));
        DB_add->setGeometry(QRect(300, 160, 75, 23));
        DB_update = new QPushButton(centralWidget);
        DB_update->setObjectName(QStringLiteral("DB_update"));
        DB_update->setGeometry(QRect(300, 110, 75, 23));
        DB_detele = new QPushButton(centralWidget);
        DB_detele->setObjectName(QStringLiteral("DB_detele"));
        DB_detele->setGeometry(QRect(300, 210, 75, 23));
        DB_show = new QTextEdit(centralWidget);
        DB_show->setObjectName(QStringLiteral("DB_show"));
        DB_show->setGeometry(QRect(20, 60, 261, 161));
        DB_comboBox_tableName = new QComboBox(centralWidget);
        DB_comboBox_tableName->setObjectName(QStringLiteral("DB_comboBox_tableName"));
        DB_comboBox_tableName->setGeometry(QRect(300, 10, 69, 22));
        label = new QLabel(centralWidget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(250, 10, 21, 16));
        label_2 = new QLabel(centralWidget);
        label_2->setObjectName(QStringLiteral("label_2"));
        label_2->setGeometry(QRect(30, 40, 54, 12));
        DB_lineEdit_DBname = new QLineEdit(centralWidget);
        DB_lineEdit_DBname->setObjectName(QStringLiteral("DB_lineEdit_DBname"));
        DB_lineEdit_DBname->setGeometry(QRect(100, 10, 113, 20));
        label_3 = new QLabel(centralWidget);
        label_3->setObjectName(QStringLiteral("label_3"));
        label_3->setGeometry(QRect(10, 10, 71, 20));
        DataBases->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(DataBases);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 400, 23));
        DataBases->setMenuBar(menuBar);
        mainToolBar = new QToolBar(DataBases);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        DataBases->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(DataBases);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        DataBases->setStatusBar(statusBar);

        retranslateUi(DataBases);

        QMetaObject::connectSlotsByName(DataBases);
    } // setupUi

    void retranslateUi(QMainWindow *DataBases)
    {
        DataBases->setWindowTitle(QApplication::translate("DataBases", "DataBases", 0));
        DB_select->setText(QApplication::translate("DataBases", "\346\237\245\350\257\242", 0));
        DB_add->setText(QApplication::translate("DataBases", "\346\267\273\345\212\240", 0));
        DB_update->setText(QApplication::translate("DataBases", "\346\233\264\346\226\260", 0));
        DB_detele->setText(QApplication::translate("DataBases", "\345\210\240\351\231\244", 0));
        label->setText(QApplication::translate("DataBases", "\350\241\250\357\274\232", 0));
        label_2->setText(QApplication::translate("DataBases", "\345\261\225\347\244\272\346\241\206\357\274\232", 0));
        label_3->setText(QApplication::translate("DataBases", "\346\225\260\346\215\256\345\272\223\345\220\215\347\247\260\357\274\232", 0));
    } // retranslateUi

};

namespace Ui {
    class DataBases: public Ui_DataBases {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DATABASES_H
