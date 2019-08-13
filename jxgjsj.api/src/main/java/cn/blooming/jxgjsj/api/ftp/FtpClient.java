package cn.blooming.jxgjsj.api.ftp;

import lombok.*;

import java.io.InputStream;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FtpClient {

    private String url;//FTP服务器hostname
    private int port;//FTP服务器端口
    private String username;// FTP登录账号
    private String password;//FTP登录密码
    private String uploadPath; //FTP服务器保存目录

    private String filename;//上传到FTP服务器上的文件名

    private InputStream input ;// 输入流

}
