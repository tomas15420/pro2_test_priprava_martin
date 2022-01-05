package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Run();
            }
        }).start();
    }

    public static void Run()
    {
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(180);
            while (true)
            {
                Socket socketConnection = serverSocket.accept();

                Thread thread = new Thread(){
                    @Override
                    public void run()
                    {
                        try
                        {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
                            PrintWriter printWriter = new PrintWriter(socketConnection.getOutputStream());

                            String firstLine = bufferedReader.readLine();
                            String[] split = firstLine.split(" ");
                            String[] splitAddress = split[1].split("/");
                            printWriter.println("HTTP/1.1 200 OK");
                            printWriter.println();

                            if(splitAddress[1].equals("help"))
                            {
                               printWriter.println("HELPING!");
                            }

                            printWriter.flush();
                            printWriter.close();
                            socketConnection.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
