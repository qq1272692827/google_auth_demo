package com.ccsu.springboot.controller;



import com.ccsu.springboot.utils.GoogleAuthenticator;
import com.ccsu.springboot.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wenzhenyu
 * @description
 * @date 2019/3/6
 */
@RestController
public class TestController {


//    @Value("${test}")
//    String userName;
    @RequestMapping("/testWar")
    public Object test(){
        return "";
    }

    @GetMapping("/generateGoogleSecret")
    @ResponseBody

    public Object generateGoogleSecret()throws Exception{
        String randomSecretKey = GoogleAuthenticator.getRandomSecretKey();
        String googleAuthenticatorBarCode = GoogleAuthenticator.getGoogleAuthenticatorBarCode(randomSecretKey, "admin", "https://www.lrshuai.top");
        Map<String,Object> data = new HashMap<>();
        //Google密钥
        data.put("secret",randomSecretKey);
        //用户二维码内容
        data.put("secretQrCode",googleAuthenticatorBarCode);
        return data;
    }

    /**
     *功能描述
     * @author wenzhenyu
     * @date 2019/6/5
     * @param  * @param secretQrCode  上面生成的secretQrCode
     * @param response
     * @return
     */
    @GetMapping("/genQrCode")
    public void genQrCode(String secretQrCode, HttpServletResponse response) throws Exception{
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        QRCodeUtil.encode(secretQrCode,stream);
    }


    @GetMapping("/googleLogin")
    @ResponseBody

    public String googleLogin( HttpServletRequest request  ) throws Exception{

        String codeStr = request.getParameter("code");

        boolean isTrue = GoogleAuthenticator.check_code("JKITBGR3YPIXIZL37JX2VXEY6IM5SRUL", Long.parseLong(codeStr), System.currentTimeMillis());
        return "验证的结果:"+isTrue;
    }




}
