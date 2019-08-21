package org.inbus.teamfiletransferclient.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP에 관한 클래스
 * 서버와 연결하여 파일을 업로드하고, 다운로드한다.
 *
 * @author lhw
 * @since 2019.08.06
 */

public class SFTPUtil{

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

    /**
     * 서버와 연결에 필요한 값들을 가져와 초기화 시킴
     *
     * @param host 서버 주소
     * @param userName 접속에 사용될 아이디
     * @param password 비밀번호
     * @param port  포트번호
     * @return 
     * @exception 
     */
    public void init(String host, String userName, String password, int port) {

        JSch jsch = new JSch();

        try {

            session = jsch.getSession(userName, host, port);

            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");	//인증서 체크 안하도록 설정

            session.setConfig(config);

            session.connect();

            channel = session.openChannel("sftp");

            channel.connect();

        } catch (JSchException e) {

            e.printStackTrace();

        }
        channelSftp = (ChannelSftp) channel;

    }

    
    /**
     * 인자로 받은 경로의 파일 리스트를 리턴한다.
     * @param path
     * @return 경로안에 디렉토리 목록 정보 반환
     * @exception 
     */
    public Vector<ChannelSftp.LsEntry> getFileList(String path) {
    	
    	Vector<ChannelSftp.LsEntry> list = null;
    	try {
    		channelSftp.cd(path);
    		//System.out.println(" pwd : " + channelSftp.pwd());
    		list = channelSftp.ls(".");
		} catch (SftpException e) {
			e.printStackTrace();
			return null;
		}
    	
    	return list;
    }


    /**
     * 하나의 파일을 업로드 한다.
     *
     * @param dir 저장시킬 주소(서버)
     * @param file 저장할 파일
     * @return
     * @exception 
     */

    public void upload(String dir, File file) {

        FileInputStream in = null;

        try {

            in = new FileInputStream(file);

            channelSftp.cd(dir);

            channelSftp.put(in, file.getName());

        } catch (SftpException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } finally {

            try {

                in.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }



    /**
     * 하나의 파일을 다운로드 한다.
     *
     * @param dir 저장할 경로(서버)
     * @param downloadFileName 다운로드할 파일
     * @param path 저장될 공간
     * @return
     * @exception 
     */

    public void download(String dir, String downloadFileName, String path) {

        InputStream in = null;

        FileOutputStream out = null;

        try {

            channelSftp.cd(dir);

            in = channelSftp.get(downloadFileName);

        } catch (SftpException e) {
            e.printStackTrace();
        }
        try {
            out = new FileOutputStream(new File(path + File.separator + downloadFileName));
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } finally {

            try {

                out.close();

                in.close();

            } catch (IOException e) {

                e.printStackTrace();

            }
        }
    }

    /**
     * 서버와의 연결을 끊는다.
     */

    public void disconnection() {
        channelSftp.quit();
    }

}



