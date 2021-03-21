package work3_21;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: starry
 * Date: 2021 -03 -21
 * Time: 14:18
 */
public class Update {

    /**
     * 修改图书《深入理解Java虚拟机》的价格为61.20
     */

    public static void main(String[] args) {
        Connection connection    = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnection();
            String sql = "update book set price=? where name =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, new BigDecimal("61.20"));
            preparedStatement.setString(2, "深入理解Java虚拟机");
            System.out.println(preparedStatement);
            int result = preparedStatement.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(null, preparedStatement, connection);
        }
    }

}
