package com.github.messengers.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageStatus implements Serializable {

    private static final long serialVersionUID = 8662600212950993629L;

    @JsonProperty(value = "id")
    @NotEmpty(message = "Field required not empty.")
    private Integer id;

    @JsonProperty(value = "groupName")
    @NotEmpty(message = "Field required not empty.")
    private String groupName;

}
