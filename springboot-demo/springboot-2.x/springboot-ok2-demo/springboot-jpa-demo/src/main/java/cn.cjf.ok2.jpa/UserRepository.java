package cn.cjf.ok2.jpa;

import cn.cjf.ok2.domain.jpa.JPA_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<JPA_User, Long> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 查询结果
     */
    List<JPA_User> findAllByUsername(String username);
}