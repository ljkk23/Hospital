import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.junit.runner.RunWith;



public class Tests {
    @Test
    public void test4() {
        SimpleEmail email = new SimpleEmail();
        email.setStartTLSEnabled(true); // 设置认证
        email.setHostName("smtp.163.com"); // 发送方邮件服务器地址
        email.setAuthentication("17344276982@163.com", "MIOEGJNVNFEPTHFA");

        try {
            email.setFrom("17344276982@163.com");
            email.addTo("1684012385@qq.com");
            //email.setSmtpPort(465);
            //System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

            email.setSubject("忘记密码的通知");
            email.setCharset("utf-8");
            email.setMsg("这是一封邮件");

            email.send();
            System.out.println("邮件发送成功，请您查收");
        } catch (EmailException e) {
            System.out.println("邮件发送失败");
            e.printStackTrace(); // 输出详细异常信息
        }
    }
}
