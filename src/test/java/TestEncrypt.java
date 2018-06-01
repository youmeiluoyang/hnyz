import com.alibaba.fastjson.JSONObject;
import com.dg11185.hnyz.bean.common.wx.Article;
import com.dg11185.hnyz.bean.common.wx.NewsMessage;
import com.dg11185.hnyz.bean.common.wx.RedPack;
import com.dg11185.hnyz.util.EncryptUtil;
import com.dg11185.hnyz.util.wx.WxMessageUtil;
import com.dg11185.util.network.CertLoader;
import org.junit.Test;

/**
 * @author xiesp
 * @description
 * @date 10:31 AM  3/29/2018
 */
public class TestEncrypt {



    @Test
    public void md5(){
        System.out.println(EncryptUtil.MD5Digest("ymly888"));
    }


    @Test
    public void testRnd(){
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setTouser("1111111111");
        Article article = new Article();
        article.setDescription("dsec1");
        article.setTitle("title1");

        Article article2 = new Article();
        article2.setDescription("dsec2");
        article2.setTitle("title2");

        newsMessage.addArtcle(article);
        newsMessage.addArtcle(article2);

        System.out.println(JSONObject.toJSONString(newsMessage));

    }


    @Test
    public void testChar(){
        System.out.println(WxMessageUtil.getNonceStr());
    }


    @Test
    public void testWxEncry(){
        RedPack redPack = new RedPack();
        String nonStr = WxMessageUtil.getNonceStr();
        //redPack.setNonce_str("K826YUQ6L36FDP0OVO48T1X8A3Y28W40");
        redPack.setNonce_str("9PNBB9Y7347XQ79506ZMWXLI919461S4");
        //测试,等下换替换
        //redPack.setMch_billno("19");
        redPack.setMch_billno("20");
        redPack.setClient_ip("39.107.68.157");
        redPack.setMch_id("1232991302");
        redPack.setWxappid("wx59cdc2234ac60d13");
        redPack.setTotal_amount("100");
        redPack.setTotal_num("1");
        redPack.setSend_name("邮美洛阳");
        //redPack.setRe_openid("oioFts1-YOJ2QXb5dG4GyM7t6Hzs");
        redPack.setRe_openid("oioFts37IniYBAxWCKjA_iqEcm4");
        redPack.setWishing("感谢您参与问卷调查,红包请收好!");
        redPack.setAct_name("有奖问卷调查");
        redPack.setRemark("运气不错哦,中奖啦");
        String xml = WxMessageUtil.messageToXml(redPack);
        System.out.println(WxMessageUtil.wxApiSing(redPack,"60ddf09e267b542f142862a7e2cbff77"));
        System.out.println(xml);
       // System.out.println(WxMessageUtil.wxApiSing(redPack,"60ddf09e267b542f142862a7e2cbff77"));

    }


    @Test
    public void testCert(){
        System.out.println(CertLoader.getSSLConnectionSocketFactory());

    }

}
