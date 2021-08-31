package com.spring.tobysrpingframework.user.dao;

import com.spring.tobysrpingframework.user.domain.User;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcContext jdbcContext;
    private DataSource dataSource;

    public UserDao(){
    }

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcContext = new JdbcContext();
        this.jdbcContext.setDataSource(dataSource);
        this.dataSource = dataSource;
    }


    public void add(User user) throws ClassNotFoundException, SQLException {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
                                        user.getId(),user.getName(),user.getPassword());
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c
                .prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement("delete from users");
                    }
                }
        );
    }

    public int getCount() throws SQLException  {
        return this.jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement("select count(*) from users");
                    }
                }, new ResultSetExtractor<Integer>() {
                    @Override
                    public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        resultSet.next();
                        return resultSet.getInt(1);
                    }
                }
        );


    }


}
