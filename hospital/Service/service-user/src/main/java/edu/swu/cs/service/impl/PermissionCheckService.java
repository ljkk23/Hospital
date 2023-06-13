//package edu.swu.cs.service.impl;
//
//import edu.swu.cs.securityService.IMenuService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Service("sc")
//@Slf4j
//public class PermissionCheckService {
//
//    @Autowired
//    private IMenuService platformManageService;
//
//    public boolean checkSgin(HttpServletRequest request){
//        String appid = request.getHeader("appid");
//        String signature = request.getHeader("signature");
//        String timestamp = request.getHeader("timestamp");
//        //非crm管理平台
//        PlatformManage platformManage = platformManageService.getOne(Wrappers.<PlatformManage>lambdaQuery().eq(PlatformManage::getSourcetype, appid));
//        if (platformManage == null) {
//            log.error("平台不存在:" + appid);
//             //鉴权失败抛出自定义异常
//            throw new SginCheckException();
//        }
//        //校验签名
//        String secretKey = platformManage.getPrivateKey();
//        MD5 md5 = new MD5();
//        String lowerCase = md5.getMD5ofStr(appid + secretKey + timestamp).toLowerCase();
//        if (!lowerCase.equals(signature)) {
//            log.error("签名校验失败:" + "crm:" + lowerCase + "，接口:" + signature);
//            //鉴权失败抛出自定义异常
//            throw new SginCheckException();
//        }
//        //如果鉴权成功不return true 会报错.
//        return true;
//    }
//}
//————————————————
//版权声明：本文为CSDN博主「Slavic_」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/weixin_47345400/article/details/108666451