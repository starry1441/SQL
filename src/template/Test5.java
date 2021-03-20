package template;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 1.改变获取连接的方式，用datasoure连接池
 * 2. statement对象换成了preparedStatement对象
 * 3. sql语句使用占位符替换
 * 4. 设置每一个占位符对应的数据
 * 5. 关闭资源
 * User: starry
 * Date: 2021 -03 -20
 * Time: 15:18
 */
public class Test5 {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1. 加载JDBC驱动程序：反射，这样调用初始化com.mysql.jdbc.Driver类，即将该类加载到JVM方法区，并执行该类的静态方法块、静态属性。
        Class.forName("com.mysql.jdbc.Driver");

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
        int id = 3;
        String name = "儒林外史";
        String sql = "select * from book where id = ? and name = ?";    //?表示占位符
        //先要获取statement对象
        //Statement statement = connection.createStatement();   //不用statement，容易发生sql注入
        PreparedStatement preparedStatement = connection.prepareStatement(sql); //有预编译的功能，优于statement
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        //executeQuery专门查询的方法  返回值是ResultSet结果集
        //执行sql语句得到的结果就放在resultSet中
        ResultSet resultSet = preparedStatement.executeQuery();
        //打印结果集

        //也可以把获取到的数据集封装成对象
        while(resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getInt(1));
            book.setName(resultSet.getString(2));
            book.setAuthor(resultSet.getString(3));
            book.setPrice(resultSet.getFloat(4));
            book.setCategory_id(resultSet.getInt(5));
            System.out.println(book);
        }

        //资源需要关闭
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

}
