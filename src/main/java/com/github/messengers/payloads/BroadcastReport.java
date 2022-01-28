package com.github.messengers.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastReport implements Serializable {

    private static final long serialVersionUID = -6212423249386935651L;

    @JsonProperty(value = "phone")
    @NotEmpty(message = "Field required not empty.")
    private String phone;

    @JsonProperty(value = "messageStatus")
    @NotEmpty(message = "Field required not empty.")
    private MessageStatus messageStatus;

}
