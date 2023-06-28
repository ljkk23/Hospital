import edu.swu.cs.utils.JwtUtil;
import edu.swu.cs.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.test.context.junit4.SpringRunner;


public class createJWT {

    /**
     * 生成访问的jwt
     */
    @Test
    public void test1(){
        String jwt= JwtUtil.createJWTForApp("visitor");
        System.out.println(jwt);
    }

    /**
     * 生成spring security的密码
     */
    @Test
    public void test2(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String zhangsan = bCryptPasswordEncoder.encode("zhangsan");
        System.out.println(zhangsan);
        System.out.println(bCryptPasswordEncoder.encode("lisi"));
    }

    @Test
    public void test3(){
        String encode = MD5Util.encode("zhangsan");
        System.out.println(encode);
    }



}
