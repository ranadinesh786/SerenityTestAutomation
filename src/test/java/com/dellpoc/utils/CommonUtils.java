package com.dellpoc.utils;

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static void runSparkJobOnLinux(String command, String host, String user, String password) {
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected to " + host);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            List<String> consoleMsgList = new ArrayList<>();
            int index = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(++index + " : " + line);
                consoleMsgList.add(line);
            }

            boolean flag = false;
            for (String msgLine : consoleMsgList) {
                if (msgLine.contains("Spark Job Successfully completed")) {
                    System.out.println("Spark Job Ran Successfully");
                    flag = true;
                }
            }

            if (!flag) {
                System.err.println("Spark Job failed");
                throw new RuntimeException("Spark Job failed");
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Run Spark Job to Get Data in Tables has failed", e);
        }
    }
}
