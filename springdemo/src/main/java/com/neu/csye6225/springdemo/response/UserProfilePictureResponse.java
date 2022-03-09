package com.neu.csye6225.springdemo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel
public class UserProfilePictureResponse implements Serializable {

    @ApiModelProperty(example = "FileName uploaded by user")
    private String file_name;

    @ApiModelProperty(example = "Users unique Id")
    private String id;

    @ApiModelProperty(example = "S3 bucket location path")
    private String url;

    @ApiModelProperty(example = "Users unique Id")
    private String user_id;

    @ApiModelProperty(example = "2016-08-29T09:12:33.001Z")
    private Date upload_date;

}
