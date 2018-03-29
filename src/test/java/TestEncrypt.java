import com.dg11185.hnyz.util.EncryptUtil;
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

}
