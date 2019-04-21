package cn.cjf.shiro.dao.impl;

import cn.cjf.shiro.dao.IUserDAO;
import cn.cjf.shiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements IUserDAO {

    private JdbcTemplate template;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return template.queryForObject("select * from user where username = ? ", new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setId(rs.getInt("id"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}
