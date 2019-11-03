#-------------------------------------------------
#
# Project created by QtCreator 2019-11-02T13:05:56
#
#-------------------------------------------------

QT       += core gui
QT       += sql

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = DataBases
TEMPLATE = app


SOURCES += main.cpp\
        databases.cpp \
    dblogin.cpp

HEADERS  += databases.h \
    dblogin.h

FORMS    += databases.ui \
    dblogin.ui
