package com.pan.solver.repository;

import com.pan.solver.entity.VerifyCode;
import com.pan.solver.entity.VerifyCode.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yemingfeng
 */
@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Long> {

    VerifyCode findByCodeEqualsAndTypeEquals(String code, Type type);

}
