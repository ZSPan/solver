package com.pan.solver.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yemingfeng
 */

@Data
@Entity
@Table(name = User.TABLE_NAME, indexes = {
        @Index(name = "emailAddress", columnList = "emailAddress", unique = true),
        @Index(name = "nickname", columnList = "nickname", unique = true),
        @Index(name = "sex", columnList = "sex")
})
@EntityListeners(AuditingEntityListener.class)
public class User {

    public static final String TABLE_NAME = "User";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emailAddress", columnDefinition = "VARCHAR(255) DEFAULT ''", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "nickname", columnDefinition = "VARCHAR(30) DEFAULT ''", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password", columnDefinition = "VARCHAR(150) DEFAULT ''", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sex", columnDefinition = "VARCHAR(30) DEFAULT NULL")
    private Sex sex;

    @Column(name = "headPortraitName", columnDefinition = "VARCHAR(100) DEFAULT ''", nullable = false)
    private String headPortraitName;

    @Column(name = "district", columnDefinition = "VARCHAR(30) DEFAULT ''")
    private String district;

    @Column(name = "motto", columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String motto;

    @Column(name = "lastLoginTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modification;

    public enum Sex {
        MAN,
        WOMAN
    }
}