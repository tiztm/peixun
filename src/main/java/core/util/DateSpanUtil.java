package core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该类用来打印两次时间差，需要初始化
 * 初始化打印初始化的时间
 * 每次调用打印方法，打印出于上一次的时间差
 * @author nilszhang
 *
 */
public class DateSpanUtil {
	
	/**
	 * 上次的时间
	 */
	private Date nowDate;
	
	private SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm:ss-SSS - yyyy年MM月dd日 ");
	
	public DateSpanUtil() {
		nowDate = new Date();
		System.out.println(sdFormat.format(nowDate));
	}
	
	public void getSpanDate() {
		Date nDate = new Date();
		 long diff = nDate.getTime() - nowDate.getTime();
		System.out.print("diff:"+diff+" ms");
		System.out.println(" - "+sdFormat.format(nDate));
		nowDate = nDate;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DateSpanUtil();
	}

}
