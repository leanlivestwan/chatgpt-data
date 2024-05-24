package cn.scalecraft.chatgpt.data.test;

import com.alibaba.fastjson2.JSON;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;


@Slf4j
public class WeChatPayTest {

    // 公众号开发信息，开发者ID(AppID) appId；https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&token=329083210&lang=zh_CN
    private static final String appId = "";

    // 商户号；https://pay.weixin.qq.com/index.php/core/account/info - 打开可以看见微信支付商户号
    public static final String merchantId = "";

    // 商户API私钥路径；https://pay.weixin.qq.com/docs/merchant/development/interface-rules/privatekey-and-certificate.html
    public static final String privateKeyPath = System.getProperty("user.dir") + "/src/main/resources/cert/apiclient_key.pem";

    // 商户证书序列号【脚本：openssl x509 -in apiclient_cert.pem -noout -serial】
    public static final String merchantSerialNumber = "";

    // 商户APIV3密钥 32位自己生成
    public static final String apiV3Key = "";

    private NativePayService nativePayService;

    @Before
    public void init() {
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();

        this.nativePayService = new NativePayService.Builder().config(config).build();
    }

    @Test
    public void test_prepay() {
        PrepayRequest request = new PrepayRequest();
        // 支付金额，单位分
        Amount amount = new Amount();
        amount.setTotal(1);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription("测试商品标题");
        request.setNotifyUrl("https://iteuds.cn");
        request.setOutTradeNo("100000010002");

        // 调用下单方法，得到应答
        PrepayResponse response = nativePayService.prepay(request);

        // 获得支付URL，复制到 https://cli.im/ 生成二维码，手机扫码支付
        System.out.println(response.getCodeUrl());
    }

    @Test
    public void test_queryOrderById() {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(merchantId);
        request.setOutTradeNo("100000010001");
        Transaction transaction = nativePayService.queryOrderByOutTradeNo(request);
        log.info("测试结果：{}", JSON.toJSONString(transaction));
    }

    @Test
    public void test_closeOrder() {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(merchantId);
        request.setOutTradeNo("100000010001");
        nativePayService.closeOrder(request);
    }

}
