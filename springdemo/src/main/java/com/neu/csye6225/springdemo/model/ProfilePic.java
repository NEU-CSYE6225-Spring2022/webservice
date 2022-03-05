package com.neu.csye6225.springdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile_pic")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "upload_created" }, allowGetters = true)
@ApiModel
public class ProfilePic {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    @ApiModelProperty(hidden = true, readOnly = true)
    private Long uuid;

    @Column(name="file_name")
    @ApiModelProperty(example = "File Name which is uploaded", required = true)
    private String file_name;

    @Column(name = "uuid")
    @ApiModelProperty(example = "user-uuid same as users unique id", required = true)
    private String id;

    @Column(name = "url")
    @ApiModelProperty(example = "S3 location path", required = true)
    private String url;

    @Column(name = "user_id")
    @ApiModelProperty(example = "users unique id", required = true)
    private String user_id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @ApiModelProperty(example = "Default generated", readOnly = true)
    private Date upload_created;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @ApiModelProperty(example = "Default generated", readOnly = true)
    private Date upload_date;

    public ProfilePic(String file_name, String id, String url, String user_id) {
        this.file_name = file_name;
        this.id = id;
        this.url = url;
        this.user_id = user_id;
    }
}
