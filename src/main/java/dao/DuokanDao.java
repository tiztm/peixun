package dao;

import entity.Duokan;
import util.db.DbBuilder;

/**
 * Created by IDEA
 * User:    tiztm
 * Date:    2016/9/21.
 */
public class DuokanDao extends DbBuilder<Duokan> {

    private static DuokanDao duokanDao;

    public static  DuokanDao dao()
    {
        if(duokanDao ==null)
            duokanDao = new DuokanDao();
        return duokanDao;
    }



}
