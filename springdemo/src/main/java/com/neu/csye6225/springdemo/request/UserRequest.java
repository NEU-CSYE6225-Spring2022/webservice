package com.neu.csye6225.springdemo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserRequest implements Serializable {

    @ApiModelProperty(example = "manojreddy")
    private String first_name;

    @ApiModelProperty(example = "amireddy")
    private String last_name;

    @ApiModelProperty(example = "manoj@gmail.com")
    private String username;

    @ApiModelProperty(example = "user-defined")
    private String password;

}
