package com.pan.solver.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yemingfeng
 */
@Data
@Entity
@Table(name = VerifyCode.TABLE_NAME, indexes = {
        @Index(name = "emailAddress", columnList = "emailAddress"),
        @Index(name = "code", columnList = "code"),
        @Index(name = "used", columnList = "used"),
        @Index(name = "type", columnList = "type")
})
@EntityListeners(AuditingEntityListener.class)
public class VerifyCode {

    public static final String TABLE_NAME = "VerifyCode";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emailAddress", columnDefinition = "VARCHAR(255) DEFAULT 0", nullable = false)
    private String email;

    @Column(name = "code", columnDefinition = "VARCHAR(255) DEFAULT ''", nullable = false)
    private String code;

    @Column(name = "used", columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private boolean used;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(20) DEFAULT NULL")
    private Type type;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    public enum Type {
        REGISTER,
        CHANGE_PASSWORD
    }
}