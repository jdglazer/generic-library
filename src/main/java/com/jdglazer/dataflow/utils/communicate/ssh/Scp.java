package com.jdglazer.dataflow.utils.communicate.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jdglazer.utils.ProcessExecutor;

public class Scp extends SSHOperation {
	
	public static void from( String user, String password, String ip, String localFile, String remoteFile ) throws JSchException {
		Session session = shellRoot.getSession(user, ip, 22);
		session.setPassword(password);
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
//WRITE AN SSH HOST AND USER PROVIDER CLASS FOR THE LOCAL IMPLEMENTATION OF SSH
//MAY ALSO NEED TO ADD A MECHANISM TO PERFORM A KEY TRANSFER
		channel.setCommand("scp "+remoteFile+" "+user+"@"+ip+":"+localFile );
	}
	
	public static boolean to( String user, String password, String ip, String remoteFolder, String localFile ) {
		ProcessExecutor process = new ProcessExecutor();
		return process.execute("scp",localFile, user+"@"+ip+":"+remoteFolder ) != 0  ? false : true;
	}

}
