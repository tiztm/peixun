package core.util;

/**
 * Created by IDEA
 * User:    tiztm
 * Date:    2016/9/21
 *
 *
 */
public class CommonUtil {


    /**
     * 将对象转化为INT型
     * @param obj
     * @return
     */
    public static int objectToInteger(Object obj) {
        try {
            if (obj != null && !obj.toString().trim().equals(""))
                return Integer.parseInt(obj.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
        return 0;
    }
}
