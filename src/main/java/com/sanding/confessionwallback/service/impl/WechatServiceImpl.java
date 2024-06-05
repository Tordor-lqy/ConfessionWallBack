package com.sanding.confessionwallback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sanding.confessionwallback.common.exception.BaseException;
import com.sanding.confessionwallback.common.properties.WeChatProperties;
import com.sanding.confessionwallback.common.utils.EmptyUtil;
import com.sanding.confessionwallback.common.utils.HttpClientUtil;
import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class WechatServiceImpl implements WeChatService {

    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";


    @Autowired
    private WeChatProperties weChatProperties;



    /**
     * 微信登录接口实现
     * @param userLoginDTO
     * @return
     */
    @Override
    public String wxLogin(UserLoginDTO userLoginDTO){
        String code = userLoginDTO.getCode();
        return getOpenid(code);
    }


    /**
     * 获取用户手机号
     * @param userLoginDTO
     * @return
     */
    public String getPhone(UserLoginDTO userLoginDTO) {
        String code = userLoginDTO.getCode();
        // 获取服务端调用凭证 token
        String token = getToken();
        // 增加请求token
        String url = PHONE_REQUEST_URL + token;
        Map<String, String> map = new HashMap<>();
        // 小程序端授权后的手机号临时凭证
        map.put("code", code);
        // 发送post请求读取调用微信接口获取手机号
        String result = null;
        try {
            result = HttpClientUtil.doPost4Json(url, map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject.getInteger("errcode") != 0) {
            //若code不正确，则获取不到phone，响应失败
            throw new BaseException(jsonObject.getString("errmsg"));

        }
        return jsonObject.getJSONObject("phone_info").getString("purePhoneNumber");
    }

    /**
     * 获取应用配置
     * @return 参数集合
     */
    private Map<String, String> getAppConfig() {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId，开发者后台获取
        requestUrlParam.put("appid", weChatProperties.getAppid());
        // 小程序secret，开发者后台获取
        requestUrlParam.put("secret", weChatProperties.getSecret());
        return requestUrlParam;
    }

    /**
     * 获取服务端调用凭证 token
     * @return token
     */
    public String getToken() {
        Map<String, String> requestUrlParam = getAppConfig();
        requestUrlParam.put("grant_type", "client_credential");
        String result = HttpClientUtil.doGet(TOKEN_URL, requestUrlParam);
        JSONObject jsonObject = JSON.parseObject(result);
        //若code不正确，则获取不到openid，响应失败
        if (!EmptyUtil.isNullOrEmpty(jsonObject.getInteger("errcode"))) {
            throw new BaseException(jsonObject.getString("errmsg"));
        }
        return jsonObject.getString("access_token");
    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        Map<String, String> requestUrlParam = getAppConfig();
        requestUrlParam.put("js_code", code);
        requestUrlParam.put("grant_type", "authorization_code");
        String result = HttpClientUtil.doGet(REQUEST_URL, requestUrlParam);
        JSONObject jsonObject = JSON.parseObject(result);
        //若code不正确，则获取不到openid，响应失败
        if (!EmptyUtil.isNullOrEmpty(jsonObject.getInteger("errcode"))) {
            throw new BaseException(jsonObject.getString("errmsg"));
        }
        return jsonObject.getString("openid");
    }

}
