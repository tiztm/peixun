package dao;

import entity.Duokan;
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
        object.setName("123");
        object.setUrl("1233");
        object.setIsdown(1);


        DuokanDao.dao().save(object);

        object.setId(1212);


        System.out.println(DuokanDao.dao().update(object));


    }
}
