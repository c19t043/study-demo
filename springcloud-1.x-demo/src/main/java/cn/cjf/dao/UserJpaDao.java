package cn.cjf.dao;

import cn.cjf.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaDao extends JpaRepository<User,Long>{

    User findUserByUsername(String username);
}
