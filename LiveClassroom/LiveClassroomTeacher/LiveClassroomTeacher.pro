#-------------------------------------------------
#
# Project created by QtCreator 2019-11-23T21:14:05
#
#-------------------------------------------------

QT       += core gui
QT       += network  #添加网络支持

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = LiveClassroomTeacher
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    handledata.cpp

HEADERS  += mainwindow.h \
    handledata.h

FORMS    += mainwindow.ui
