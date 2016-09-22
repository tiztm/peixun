package util.db;

/**
 * Created by IDEA
 * User:    tiztm
 * Date:    2016/9/21.
 */
public class CommonUtil {
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
