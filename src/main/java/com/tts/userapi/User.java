package com.tts.userapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="UserInfo")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="id number of the user", name="id", dataType="long", example="4")
	private Long id;
	
	@ApiModelProperty(value="first name of the user", name="firstName", dataType="string", example="Lucy")
	@Size(max=20)
	private String firstName;
	
	@Size(min=2)
	@ApiModelProperty(value="last name of the user", name="lastName", dataType="string", example="Poppy")
	private String lastName;
	
	@Size(min=4, max=20)
	@ApiModelProperty(value="state of resisdence of the user", name="state", dataType="string", example="New Hampshire")
	private String state;
}
