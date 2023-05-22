package org.bayasik.connection;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Responser {
    private final ConnectionContext context;

    public Responser(ConnectionContext context) {
        this.context = context;
    }

    public void jsonResponse(short command, Object data) {
        try {
            var output = context.get(Socket.class).getOutputStream();
            var gson = new Gson();
            var bytes = gson.toJson(data).getBytes();
            var response = new byte[bytes.length + 4 + 2];
            response[0] = (byte) (command >> 8);
            response[1] = (byte) command;
            response[2] = (byte) (bytes.length >> 24);
            response[3] = (byte) (bytes.length >> 16);
            response[4] = (byte) (bytes.length >> 8);
            response[5] = (byte) bytes.length;
            System.arraycopy(bytes, 0, response, 6, bytes.length);
            output.write(response);
            output.flush();
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}