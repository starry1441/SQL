package work3_21;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: starry
 * Date: 2021 -03 -21
 * Time: 14:15
 */
public class Select {
    /**
     * 查询计算机分类下的图书借阅信息
     * @param args
     */
    public static void main(String[] args) {
        Connection connection    = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Util.getConnection();
            String sql = "SELECT bk.NAME book_name,bk.author book_author," +
                    "s.NAME student_name,bi.start_time,bi.end_time" +
                    " FROM borrow_info bi JOIN book bk ON bi.book_id = bk.id" +
                    " JOIN category c ON bk.category_id = c.id" +
                    " JOIN student s ON bi.student_id = s.id" +
                    " WHERE c.NAME = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "计算机");
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String  bookName  = resultSet.getString("book_name");
                String  bookAuthor = resultSet.getString("book_author");
                String  studentName = resultSet.getString("student_name");
                Timestamp startTime  = resultSet.getTimestamp("start_time");
                Timestamp  endTime   = resultSet.getTimestamp("end_time");
                System.out.println(String.format("书名：%s，作者：%s，借阅者：%s，" +
                                "借阅起始日期：%s，结束日期：%s",
                        bookName, bookAuthor, studentName, startTime, endTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(resultSet, preparedStatement, connection);
        }
    }
}
