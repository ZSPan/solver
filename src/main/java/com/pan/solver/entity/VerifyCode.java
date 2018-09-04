package com.pan.solver.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
        REGISTER
    }
}