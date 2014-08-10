package org.foxbat.opswise.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.foxbat.opswise.AppConfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class RemoteShellManager implements Runnable {
	private static JSch jsch;
	private Channel channel;
	private Session session;
	private Thread th;
	private StringBuilder result = new StringBuilder();
	private String[] args;

	private enum Mode {
		COMMAND, SFTP
	};

	private Mode current_mode;
	static {
		try {
			jsch = new JSch();
			jsch.addIdentity(AppConfig.getInstance().config.getJSONObject("general").getString("config_path")+"/config/ssh/id_rsa");
			jsch.setKnownHosts(AppConfig.getInstance().config.getJSONObject("general").getString("config_path")+"config/ssh/known_hosts");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RemoteShellManager(String user, String agent, int port) {
		try {
			session = jsch.getSession(user, agent, port);
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
		while(th.isAlive())
		{
			try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); } 
		}
	}
	
	
	public void executeSFTP(String... args) {
		this.args = args;
		this.current_mode = Mode.SFTP;
		th = new Thread(this);
		th.start();
		while(th.isAlive())
		{
			try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); } 
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
			try { br.close(); } catch (IOException e) { e.printStackTrace(); }
			this.channel.disconnect();
		}

	}

	private void sftp() {
		try {
			if(!session.isConnected())
			{
				System.out.println("Session reconnecting");
				session.connect();
			}
			channel = session.openChannel("sftp");
			channel.connect();
			((ChannelSftp) channel).put(new ByteArrayInputStream(args[0].getBytes()), args[1]);
		} catch (SftpException | JSchException e) {
			e.printStackTrace();
		} finally {
			this.channel.disconnect();
		}

	}
	
	public void close()
	{
		System.out.println("session disconnect from close");
		this.session.disconnect();
	}
	
	@Override
	public void finalize()
	{
		System.out.println("session disconnect from finalize");
		this.session.disconnect();
	}

}
