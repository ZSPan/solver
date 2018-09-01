package com.pan.solver.mapper;

import com.pan.solver.entity.User.Sex;
import java.util.Date;

/**
 * @author yemingfeng
 */
class CommonMapper {

    Long toTs(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime();
    }

    Date toDate(Long ts) {
        return new Date(ts);
    }

    Sex toSex(String sex) {
        if (sex == null) {
            return null;
        }
        return Sex.valueOf(sex);
    }

    String toSex(Sex sex) {
        if (sex == null) {
            return null;
        }
        return sex.name();
    }
}