package com.github.messengers.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleMessage implements Serializable {

    private static final long serialVersionUID = 2199462035650476003L;

    @JsonProperty(value = "from")
    @NotBlank(message = "Field required not blank")
    private String from;

    @JsonProperty(value = "phoneNumber")
    @NotBlank(message = "Field required not blank")
    private String phoneNumber;

    @JsonProperty(value = "text")
    @NotBlank(message = "Field required not blank")
    private String text;

    @JsonProperty(value = "languageCode")
    @NotBlank(message = "Field required not blank")
    private String languageCode;

    @JsonProperty(value = "transliteration")
    @NotBlank(message = "Field required not blank")
    private String transliteration;

}
