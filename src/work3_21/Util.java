package work3_21;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: starry
 * Date: 2021 -03 -21
 * Time: 14:00
 */
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Util {
    //使用连接池
    private static final DataSource DATA_SOURCE    = new MysqlDataSource();

    static {
        ((MysqlDataSource) DATA_SOURCE).setUrl("jdbc:mysql://localhost:3306/ebook?useSSL=false");
        ((MysqlDataSource) DATA_SOURCE).setUser("root");
        ((MysqlDataSource) DATA_SOURCE).setPassword("111111");
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }

    // 获取数据库连接
    public static Connection getConnection(){
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    // 释放资源
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库操作异常", e);
        }
    }

    //日期字符串转Java日期类Date和sql时间戳Timestamp
    public static Timestamp getTimestamp(String dateString){
        try {
            // 年-月-日 小时:分钟:秒
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
            return new java.sql.Timestamp(date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("日期格式化错误："+dateString, e);
        }
    }

}


