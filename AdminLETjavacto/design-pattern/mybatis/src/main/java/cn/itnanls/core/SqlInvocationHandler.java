package cn.itnanls.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SqlInvocationHandler implements InvocationHandler {
    private Connection connection;

    private  Map<String,DaoWrapper> env;

    public SqlInvocationHandler(Connection connection, Map<String, DaoWrapper> env) {
        this.connection = connection;
        this.env = env;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据xml的内容，和接口的名字生成实际的方法体
        // 拿到包装
        DaoWrapper wrapper = env.get(method.getName());
        PreparedStatement statement = connection.prepareStatement(wrapper.getSql());
        // 对每一种sql语句进行独立的操作
        if ("insert".equals(wrapper.getType())) {
        // 暂定传入一个对象
            Class<?> clazz = args[0].getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i + 1, fields[i].get(args[0]));
            }
            return statement.executeUpdate();
        } else if ("delete".equals(wrapper.getType())) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            return statement.executeUpdate();
        } else if ("select".equals(wrapper.getType())) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            ResultSet result = statement.executeQuery();
            List list = new ArrayList();
            while (result.next()) {
                Class<?> clazz = Class.forName(wrapper.getResultType());
                Object object = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    fields[i].set(object, result.getObject(fields[i].getName()));
                }
                list.add(object);
            }
            return list;
        }
        return null;
    }
}

