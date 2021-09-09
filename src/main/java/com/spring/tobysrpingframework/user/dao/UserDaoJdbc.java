package com.spring.tobysrpingframework.user.dao;

import com.mysql.cj.result.Row;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao{

    private JdbcTemplate jdbcTemplate;
    private  RowMapper<User> userMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));

                    user.setLevl(Level.valueOf(rs.getInt("level")));
                    user.setLogin(rs.getInt("login"));
                    user.setRecommend(rs.getInt("recommend"));

                    return user;
                }
            };

    public UserDaoJdbc(){
    }

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void add(User user)  {
        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) " +
                                    "values(?,?,?,?,?,?)",
                                        user.getId(),user.getName(),user.getPassword(),
                                        user.getLevl().initValue(), user.getLogin(), user.getRecommend()
        );
    }


    public User get(String id) {
       return this.jdbcTemplate.queryForObject(
               "select * from users where id = ?",
               new Object[]{id},
               this.userMapper
              );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement("delete from users");
                    }
                }
        );
    }

    public int getCount() {
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
