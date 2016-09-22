package util.db;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
public class DbBuilder<T> {


    protected Class<T> entityClass = null;


    protected DbBuilder() {


        if (entityClass == null) {
            Type t = getClass().getGenericSuperclass();
            if(t instanceof ParameterizedType){
                Type[] p = ((ParameterizedType)t).getActualTypeArguments();
                entityClass = (Class<T>)p[0];
            }
        }
    }





    public static QueryRunner getQueryRunner() {

        DBUtilsHelper dbh = new DBUtilsHelper();
        QueryRunner runner = dbh.getRunner();
        return runner;


    }

    /**
     * 得到查询记录的条数
     *
     * @param sql
     *            必须为select count(*) from t_user的格式
     * @return
     */
    public static int getCount(String sql) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            Object value = runner.query(sql, new ScalarHandler());
            return CommonUtil.objectToInteger(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    /**
     * 得到查询记录的条数
     *
     * @param sql
     *            必须为select count(*) from t_user的格式
     * @param params
     * @return
     */
    public static int getCount(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            Object value = runner.query(sql, new ScalarHandler(), params);
            return CommonUtil.objectToInteger(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据传入的sql，查询记录，以数组形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
     *
     * @param sql
     * @return
     */
    public static Object[] getFirstRowArray(String sql) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new ArrayHandler());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据传入的sql，查询记录，以数组形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
     *
     * @param sql
     * @param params
     * @return
     */
    public static Object[] getFirstRowArray(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new ArrayHandler(), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 根据sql查询返回所有记录，以List数组形式返回
     *
     * @param sql
     * @return
     */
    public static List getListArray(String sql) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new ArrayListHandler());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据sql查询返回所有记录，以List数组形式返回
     *
     * @param sql
     * @param params
     * @return
     */
    public static List getListArray(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new ArrayListHandler(), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据传入的sql，查询记录，以Map形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
     *
     * @param sql
     * @return
     */
    public static Map getFirstRowMap(String sql) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new MapHandler());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据传入的sql，查询记录，以Map形式返回第一行记录。 注意：如果有多行记录，只会返回第一行，所以适用场景需要注意，可以使用根据主键来查询的场景
     *
     * @param sql
     * @param params
     * @return
     */
    public static Map getFirstRowMap(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new MapHandler(), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据传入的sql查询所有记录，以List Map形式返回
     *
     * @param sql
     * @return
     */
    public static List getListMap(String sql) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new MapListHandler());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据传入的sql查询所有记录，以List Map形式返回
     *
     * @param sql
     * @param params
     * @return
     */
    public static List getListMap(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.query(sql, new MapListHandler(), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据sql和对象，查询结果并以对象形式返回
     *
     * @param sql
     * @param type
     * @return
     */
    public  T getBean(String sql) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return (T)runner.query(sql, new BeanHandler(entityClass));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 根据sql和对象，查询结果并以对象形式返回
     *
     * @param sql
     * @param type
     * @param params
     * @return
     */
    public   T getBean(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return (T)runner.query(sql, new BeanHandler(entityClass), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据sql查询list对象
     *
     * @param sql
     * @param type
     * @return
     */
    public   List<T> getListBean(String sql ) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return ( List<T>)runner.query(sql, new BeanListHandler(entityClass));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 根据sql查询list对象
     *
     * @param sql
     * @param type
     * @param params
     * @return
     */
    public   List<T> getListBean(String sql , Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return (  List<T>)runner.query(sql, new BeanListHandler(entityClass), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    public int save(T object) {

        String tableName="";
        String cols="";
        String values="";




        if(object==null) return 0;
        Class objectClass = object.getClass();
        tableName = objectClass.getName();
        Field[] fields = objectClass.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(object);
                if(o==null) continue;
                //TODO:当前仅仅支持数字和Char
                cols =cols+field.getName()+ ",";
                values=values+o+ ",";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(cols.length()<1) return 0;

        tableName = tableName.substring(tableName.lastIndexOf(".")+1,tableName.length());
          String sql =       "INSERT INTO " +tableName+//
                " (" +cols.substring(0,cols.length()-1)+//id,name,url
                ")  VALUES (" +values.substring(0,values.length()-1)+//?, '22','33'
                        ")";

        System.out.println("新增："+sql);

        return save(sql);


    }




    public int update(T object) {

        String tableName="";
        String cols="";
        String values="";

        Integer id = -1;


        if(object==null) return 0;
        Class objectClass = object.getClass();
        tableName = objectClass.getName();
        Field[] fields = objectClass.getDeclaredFields();

        try {
            for (Field field : fields) {


                field.setAccessible(true);
                String name = field.getName();

                if(name.equals("id"))
                {
                    id = (Integer) field.get(object);
                    continue;
                }

                Object o = field.get(object);
                if(o==null) continue;
                //TODO:当前仅仅支持数字和Char
                cols =cols+ name +"="+o+ ",";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(id==-1)  return 0;

        if(cols.length()<1) return 0;

        tableName = tableName.substring(tableName.lastIndexOf(".")+1,tableName.length());

        //update duokan t set t.name = "1",t.url="2" where t.id = 12
        String sql =  "update " +tableName+
                " set  " +cols.substring(0,cols.length()-1)+//name = "1",url="2"
                "  where id = "+id;



        System.out.println("更新:"+sql);

        return update(sql);


    }


    /**
     * 保存操作
     *
     * @param sql
     * @param params
     * @return
     */
    public static int save(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.update(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }



    /**
     * 更新操作
     *
     * @param sql
     * @param params
     * @return
     */
    public static int update(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.update(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }



    /**
     * 删除操作
     *
     * @param sql
     * @param params
     * @return
     */
    public static int delete(String sql, Object... params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.update(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }



    /**
     * 批量操作，包括批量保存、修改、删除
     *
     * @param sql
     * @param params
     * @return
     */
    public static int[] batch(String sql, Object[][] params) {
        try {
            QueryRunner runner = DbBuilder.getQueryRunner();
            return runner.batch(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    /**
     * 开启事务
     */
    public static void beginTransaction(Connection conn) {
        try {
            // 开启事务
            conn.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 提交事务
     */
    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}