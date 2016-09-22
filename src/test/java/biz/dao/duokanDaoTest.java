package biz.dao;

import biz.entity.Duokan;
import org.junit.Test;

/**
 * Created by IDEA
 * User:    tiztm
 * Date:    2016/9/21.
 */
public class duokanDaoTest {
    
    @Test
    public void test()
    {
        Duokan object = new Duokan();
        object.setUrl("4434");

        object.setId(1212);

        System.out.println(DuokanDao.dao.update(object));


    }
}
