/********************************************************************************
** Form generated from reading UI file 'dblogin.ui'
**
** Created by: Qt User Interface Compiler version 5.4.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_DBLOGIN_H
#define UI_DBLOGIN_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>

QT_BEGIN_NAMESPACE

class Ui_DBLogin
{
public:
    QLineEdit *DB_LineEdit_username;
    QLineEdit *DB_lineEdit_DBname;
    QPushButton *DB_pushButton_commit;
    QLabel *label_3;
    QLineEdit *DB_lineEdit_port;
    QLabel *label_2;
    QPushButton *DB_pushButton_quit;
    QLineEdit *DB_lineEdit_password;
    QLabel *label;
    QLineEdit *DB_lineEdit_hostname;
    QLabel *label_4;
    QLabel *label_5;

    void setupUi(QDialog *DBLogin)
    {
        if (DBLogin->objectName().isEmpty())
            DBLogin->setObjectName(QStringLiteral("DBLogin"));
        DBLogin->resize(400, 300);
        DB_LineEdit_username = new QLineEdit(DBLogin);
        DB_LineEdit_username->setObjectName(QStringLiteral("DB_LineEdit_username"));
        DB_LineEdit_username->setGeometry(QRect(160, 29, 151, 25));
        DB_lineEdit_DBname = new QLineEdit(DBLogin);
        DB_lineEdit_DBname->setObjectName(QStringLiteral("DB_lineEdit_DBname"));
        DB_lineEdit_DBname->setGeometry(QRect(160, 199, 151, 25));
        DB_pushButton_commit = new QPushButton(DBLogin);
        DB_pushButton_commit->setObjectName(QStringLiteral("DB_pushButton_commit"));
        DB_pushButton_commit->setGeometry(QRect(50, 230, 75, 23));
        label_3 = new QLabel(DBLogin);
        label_3->setObjectName(QStringLiteral("label_3"));
        label_3->setGeometry(QRect(90, 111, 54, 12));
        DB_lineEdit_port = new QLineEdit(DBLogin);
        DB_lineEdit_port->setObjectName(QStringLiteral("DB_lineEdit_port"));
        DB_lineEdit_port->setGeometry(QRect(160, 159, 151, 25));
        label_2 = new QLabel(DBLogin);
        label_2->setObjectName(QStringLiteral("label_2"));
        label_2->setGeometry(QRect(90, 71, 54, 12));
        DB_pushButton_quit = new QPushButton(DBLogin);
        DB_pushButton_quit->setObjectName(QStringLiteral("DB_pushButton_quit"));
        DB_pushButton_quit->setGeometry(QRect(280, 230, 75, 23));
        DB_lineEdit_password = new QLineEdit(DBLogin);
        DB_lineEdit_password->setObjectName(QStringLiteral("DB_lineEdit_password"));
        DB_lineEdit_password->setGeometry(QRect(160, 70, 151, 25));
        label = new QLabel(DBLogin);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(90, 30, 54, 12));
        DB_lineEdit_hostname = new QLineEdit(DBLogin);
        DB_lineEdit_hostname->setObjectName(QStringLiteral("DB_lineEdit_hostname"));
        DB_lineEdit_hostname->setGeometry(QRect(160, 110, 151, 25));
        label_4 = new QLabel(DBLogin);
        label_4->setObjectName(QStringLiteral("label_4"));
        label_4->setGeometry(QRect(90, 160, 54, 12));
        label_5 = new QLabel(DBLogin);
        label_5->setObjectName(QStringLiteral("label_5"));
        label_5->setGeometry(QRect(90, 200, 54, 12));

        retranslateUi(DBLogin);

        QMetaObject::connectSlotsByName(DBLogin);
    } // setupUi

    void retranslateUi(QDialog *DBLogin)
    {
        DBLogin->setWindowTitle(QApplication::translate("DBLogin", "Dialog", 0));
        DB_pushButton_commit->setText(QApplication::translate("DBLogin", "\347\231\273\345\275\225", 0));
        label_3->setText(QApplication::translate("DBLogin", "\344\270\273\346\234\272\345\220\215:", 0));
        label_2->setText(QApplication::translate("DBLogin", "\345\257\206\347\240\201:", 0));
        DB_pushButton_quit->setText(QApplication::translate("DBLogin", "\351\200\200\345\207\272", 0));
        label->setText(QApplication::translate("DBLogin", "\347\224\250\346\210\267\345\220\215:", 0));
        label_4->setText(QApplication::translate("DBLogin", "\347\253\257\345\217\243\345\217\267:", 0));
        label_5->setText(QApplication::translate("DBLogin", "\346\225\260\346\215\256\345\272\223:", 0));
    } // retranslateUi

};

namespace Ui {
    class DBLogin: public Ui_DBLogin {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DBLOGIN_H
