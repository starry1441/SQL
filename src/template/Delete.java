package template;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: starry
 * Date: 2021 -03 -20
 * Time: 16:02
 */
public class Delete {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1. 加载JDBC驱动程序：反射，这样调用初始化com.mysql.jdbc.Driver类，即将该类加载到JVM方法区，并执行该类的静态方法块、静态属性。
        //Class.forName("com.mysql.jdbc.Driver");
        //不需要第一步了，因为Datasource已经把它封装起来了

        //2. 获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/ebook?useSSL=false"; //地址
        //url中  jdbc表示协议   127.0.0.1表示IP地址，默认是本地（也可以写成localhost）   3306表示端口号   ebook表示数据库的名称（需要更换）
        //IP用来确定主机的位置   端口号用来唯一确定一个“软件”
        String username = "root";   //用户名,默认是root
        String password = "111111"; //密码，mysql的密码
        //Connection connection = DriverManager.getConnection(url,username,password);

        //数据源
        //改变连接的方式，使用连接池，dataSource是接口
        DataSource dataSource = new MysqlDataSource();  //MysqlDataSource()不具备连接池的概念,之后可以用c3p0连接池，druid连接池等
        ((MysqlDataSource)dataSource).setURL(url);
        ((MysqlDataSource)dataSource).setUser(username);
        ((MysqlDataSource)dataSource).setPassword(password);
        Connection connection = dataSource.getConnection();

        //3. 执行SQL语句
        String sql = "delete from book where id = ?";    //?表示占位符

        PreparedStatement preparedStatement = connection.prepareStatement(sql); //有预编译的功能，优于statement
        preparedStatement.setInt(1,15);

        //除了 查询 其他都要用executeUpdate，返回值是受影响的行数
        int ret = preparedStatement.executeUpdate();
        if(ret != 0) {
            System.out.println("删除成功");
        }

        //资源需要关闭
        preparedStatement.close();
        connection.close();

    }

}
