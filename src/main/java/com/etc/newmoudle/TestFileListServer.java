package com.etc.newmoudle;

/*
 * 本程序主要功能：
 * 服务器端：
 * 获取客户端传输的指定路径下的文件列表
 * 并将其保存至一个 String[] 数组中
 *
 */

import java.io.*;
import java.net.*;

/*
 * 本类主要功能：
 * 使用 Socket 连接至客户端
 * 并获取文件列表
 *
 */
class SocketServer
{
    ServerSocket serverSocket;
    Socket socket;
    String[] fileList;
    DataInputStream dis;

    //连接客户端
    public SocketServer()
    {
        try
        {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //获取文件列表名
    public String[] getFileList()
    {
        try
        {
            //先从客户端获取指定路径下的文件数目，存入 fileCount 中
            int fileCount = dis.readInt();
            System.out.println(fileCount);
            //通过 fileCount 构造 String[] fileList 的大小
            fileList = new String[fileCount];
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //当 i < 文件数目时，获取文件名
        for(int i = 0; i < fileList.length; i++)
        {
            try
            {
                fileList[i] = dis.readUTF();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            dis.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return fileList;

    }

}

public class TestFileListServer
{
    public static void main(String[] args)
    {
        // TODO 自动生成的方法存根
        SocketServer ss = new SocketServer();

        //通过 Socket.getFileList 方法，获取客户端传输过来的文件名，并保存之 readFileList 中
        String[] readFileList = ss.getFileList();

        //输出客户端传输过来的文件名
        for (int i = 0; i < readFileList.length; i++)
        {
            System.out.println(readFileList[i]);
        }
    }

}
