package com.npichuzhkin.JsonViewTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.npichuzhkin.JsonViewTask.views.Views;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonView(Views.GeneralUserInformation.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "Name field can only contain letters.")
    @Size(min = 2, max = 200, message = "Name field must contain from 2 to 200 letters.")
    @JsonView(Views.GeneralUserInformation.class)
    private String name;

    @Email(message = "Incorrect Email")
    @JsonView(Views.GeneralUserInformation.class)
    private String email;

    @JsonView(Views.DetailedUserInformation.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<OrderDTO> orders;
}
