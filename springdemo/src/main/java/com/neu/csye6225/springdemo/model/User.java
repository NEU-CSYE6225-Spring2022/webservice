package com.neu.csye6225.springdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "account_created", "account_updated" }, allowGetters = true)
@ApiModel
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @ApiModelProperty(hidden = true, readOnly = true)
    private String id;

    @Column(name = "first_name")
    @ApiModelProperty(example = "manojreddy", required = true)
    private String first_name;

    @Column(name = "last_name")
    @ApiModelProperty(example = "amireddy", required = true)
    private String last_name;

    @Column(name = "username")
    @ApiModelProperty(example = "amireddy.m@northeastern.edu", required = true)
    private String username;

    @Column(name = "password")
    @ApiModelProperty(example = "anything you prefer", required = true)
    private String password;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @ApiModelProperty(example = "Default generated", readOnly = true)
    private Date account_created;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @ApiModelProperty(example = "Default generated", readOnly = true)
    private Date account_updated;

    @Column(name = "account_verified")
    @ApiModelProperty(example = "Account email is Verified ?")
    private boolean accountVerified;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String first_name, String last_name, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
    }
}
