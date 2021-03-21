package work3_21;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: starry
 * Date: 2021 -03 -21
 * Time: 14:20
 */
public class Delete {

    /**
     * 删除id最大的一条借阅记录
     */

    public static void main(String[] args) {
        Connection connection    = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnection();
            String sql = "delete from borrow_info where id =" +
                    "(select r.id from (select max(id) id from borrow_info) r)";
            preparedStatement = connection.prepareStatement(sql);
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
