#ifndef HANDLEDATA_H
#define HANDLEDATA_H

/*
 * 该类主要负责的数据的处理
*/

#include <QUdpSocket>

class HandleData
{
public:
    HandleData();
    ~HandleData();
    //发送数据
    int SendData();
protected:
    //初始化教师端网络情况
    void InitTeacherNet();
    static int m_NetPort;//网络端口号
    QUdpSocket *m_SocketPoint;
};

#endif // HANDLEDATA_H
