package org.foxbat.opswise.util;

import com.jcraft.jsch.*;
import org.foxbat.opswise.AppConfig;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoteShellManager implements Runnable {
    private static JSch jsch;
    private Channel channel;
    private Session session;
    private Thread th;
    private StringBuilder result = new StringBuilder();
    private String[] args;
    private Mode current_mode;



    static {
        try {
            jsch = new JSch();
            jsch.addIdentity(AppConfig.getPath()
                    + "/ssh/id_rsa");
            jsch.setKnownHosts(AppConfig.getPath()
                    + "/ssh/known_hosts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RemoteShellManager(JsonX ops_config) {
        try {
            JsonX ssh = ops_config.getJSONObject("server").getJSONObject("ssh");
            session = jsch.getSession(ssh.getString("user"), ssh.getString("host"), Integer.parseInt(ssh.getString("port")));
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void executeCommand(String... args) {
        this.args = args;
        this.current_mode = Mode.COMMAND;
        th = new Thread(this);
        th.start();
        while (th.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeSFTP(String... args) {
        this.args = args;
        this.current_mode = Mode.SFTP;
        th = new Thread(this);
        th.start();
        while (th.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isComplete() {
        return !th.isAlive();
    }

    public String getResult() {
        return this.result.toString();
    }

    @Override
    public void run() {
        if (this.current_mode == Mode.COMMAND)
            this.command();
        else if (this.current_mode == Mode.SFTP)
            this.sftp();
    }

    private void command() {
        BufferedReader br = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (String arg : args)
                builder.append(arg + " ");
            String commandtext = builder.toString();
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(commandtext.toString());
            channel.setInputStream(null);
            channel.setOutputStream(System.out);
            channel.connect();
            br = new BufferedReader(new InputStreamReader(
                    channel.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                this.result.append(line + "\n");
            }
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.channel.disconnect();
        }

    }

    private void sftp() {
        try {
            if (!session.isConnected()) {
                session.connect();
            }
            channel = session.openChannel("sftp");
            channel.connect();
            ((ChannelSftp) channel).put(
                    new ByteArrayInputStream(args[0].getBytes()), args[1]);
        } catch (SftpException | JSchException e) {
            e.printStackTrace();
        } finally {
            this.channel.disconnect();
        }

    }

    public void close() {
        this.session.disconnect();
    }

    @Override
    public void finalize() {
        this.session.disconnect();
    }

    private enum Mode {
        COMMAND, SFTP
    }

}
