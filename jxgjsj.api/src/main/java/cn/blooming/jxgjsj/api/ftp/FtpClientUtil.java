package cn.blooming.jxgjsj.api.ftp;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.*;

/**
 * JAVA FTPClient 工具类
 *
 * commons-net-1.4.1.jar PFTClinet jar包
 *
 * @author : hpp
 */
@Log4j
@Component
public class FtpClientUtil {

    @Value("${ftpClient.url}")
    private String url;//FTP服务器hostname
    @Value("${ftpClient.port}")
    private int port;//FTP服务器端口
    @Value("${ftpClient.username}")
    private String username;// FTP登录账号
    @Value("${ftpClient.password}")
    private String password;//FTP登录密码
    @Value("${ftpClient.uploadPath}")
    private String uploadPath; //FTP服务器保存目录

    /**
     * Description: 向FTP服务器上传文件
     * @Version1.0
     * @param FTP服务器hostname
     * @param FTP服务器端口
     * @param FTP登录账号
     * @param FTP登录密码
     * @param FTP服务器保存目录
     * @param 上传到FTP服务器上的文件名
     * @param 输入流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(FtpClient ftpClient) {
        boolean success = false;
        //FTPSClient ftp = new FTPSClient("SSL",true);
        FTPClient ftp = new FTPClient();
        ftp.enterLocalPassiveMode();        //这个设置允许被动连接--访问远程ftp时需要
        FTPClientConfig config = new FTPClientConfig();
        config.setLenientFutureDates(true);
        ftp.configure(config);
        try {
            ftp.setDataTimeout(60000);       //设置传输超时时间为60秒
            ftp.setConnectTimeout(60000);       //连接超时为60秒
            int reply;
            //ftp.connect(url, port);//连接FTP服务器
            ftp.connect(ftpClient.getUrl());
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(ftpClient.getUsername(), ftpClient.getPassword());//登录
            reply = ftp.getReplyCode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                log.info("FTP服务器 拒绝连接");
                return success;
            }
            ftp.changeWorkingDirectory(ftpClient.getUploadPath());
            ftp.storeFile(ftpClient.getFilename(), ftpClient.getInput());

            ftpClient.getInput().close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 删除文件
     * @param fileName 要删除的文件地址
     * @return true/false
     * @throws IOException
     */
    public static boolean delete(String fileName, FTPClient ftpClient) throws IOException {
        return ftpClient.deleteFile(fileName);
    }


    /**
     * 下载文件到指定目录
     * @param ftpFile 文件服务器上的文件地址
     * @param dstFile 输出文件的路径和名称
     * @throws Exception
     */
    public static void downLoad(String ftpFile, String dstFile, FTPClient ftpClient) throws Exception {
        if (StringUtils.isBlank(ftpFile)) {
            throw new RuntimeException("ftpFile为空");
        }
        if (StringUtils.isBlank(dstFile)) {
            throw new RuntimeException("dstFile为空");
        }
        File file = new File(dstFile);
        FileOutputStream fos = new FileOutputStream(file);
        ftpClient.retrieveFile(ftpFile, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 从文件服务器获取文件流
     * @param ftpFile 文件服务器上的文件地址
     * @return {@link InputStream}
     * @throws IOException
     */
    public static InputStream retrieveFileStream(String ftpFile, FTPClient ftpClient) throws IOException {
        if (StringUtils.isBlank(ftpFile)) {
            throw new RuntimeException("ftpFile为空");
        }
        return ftpClient.retrieveFileStream(ftpFile);
    }

    public boolean startUpload(InputStream inputStream,String fileName){
        try {
            FtpClient ftpClient=new FtpClient();
            ftpClient.setUrl(url);
            ftpClient.setPort(port);
            ftpClient.setUsername(username);
            ftpClient.setPassword(password);
            ftpClient.setUploadPath(uploadPath);
            ftpClient.setFilename(fileName);
            ftpClient.setInput(inputStream);
            return uploadFile(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
    public static void main(String[] args) throws FileNotFoundException {
        FtpClientUtil ftpClientUtil=new FtpClientUtil();
        InputStream fileInputStream=new FileInputStream("D:\\img_zhong.jpg");
        boolean flag = ftpClientUtil.startUpload(fileInputStream, "abc.jpg");
        log.info("flag:");
    }
    */
}
