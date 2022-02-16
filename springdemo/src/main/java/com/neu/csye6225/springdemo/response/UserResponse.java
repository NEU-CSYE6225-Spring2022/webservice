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
public class UserResponse implements Serializable {

    @ApiModelProperty(example = "random hash")
    private String id;

    @ApiModelProperty(example = "manojreddy")
    private String first_name;

    @ApiModelProperty(example = "amireddy")
    private String last_name;

    @ApiModelProperty(example = "manoj@gmail.com")
    private String username;

    @ApiModelProperty(example = "2016-08-29T09:12:33.001Z")
    private Date account_created;

    @ApiModelProperty(example = "2016-08-29T09:12:33.001Z")
    private Date account_updated;

}
