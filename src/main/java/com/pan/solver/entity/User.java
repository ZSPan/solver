package com.pan.solver.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author yemingfeng
 */

@Data
@Entity
@Table(name = User.TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class User {

  public static final String TABLE_NAME = "User";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nickname", columnDefinition = "VARCHAR(30) DEFAULT ''", nullable = false, unique = true)
  private String nickname;

  @Column(name = "email", columnDefinition = "VARCHAR(100) DEFAULT ''", nullable = false, unique = true)
  private String email;

  @Column(name = "password", columnDefinition = "VARCHAR(150) DEFAULT ''", nullable = false)
  private String password;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date creation;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date modification;
}
