#include "handledata.h"

int HandleData::m_NetPort=5500;//之后需要通过配置文件读入

HandleData::HandleData()
{
    m_SocketPoint=NULL;
    InitTeacherNet();
}

HandleData::~HandleData()
{

}



void HandleData::InitTeacherNet()
{
    if(NULL==m_SocketPoint)
    {
        m_SocketPoint=new QUdpSocket();
    }
}

int HandleData::SendData()
{
    QString msg="Hello World!\n";
    //将消息通过广播的方式发送
    return m_SocketPoint->writeDatagram(msg.toLatin1(),msg.length(),QHostAddress::Broadcast,m_NetPort);
}
