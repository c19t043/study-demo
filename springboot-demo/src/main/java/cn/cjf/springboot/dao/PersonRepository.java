package cn.cjf.springboot.dao;

import cn.cjf.springboot.bean.JpaPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<JpaPerson, Integer> {
}
