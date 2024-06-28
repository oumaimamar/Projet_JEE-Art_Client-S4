package com.test.Arty.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    @NotEmpty(message = "the name is required")
    private String nameService;

    @NotEmpty(message = "the secteurActivite is required")
    private String secteurActivite;
    @Min(10)
    private double montantService;

    @Size(min = 5,message = "the description should be at least 5 characters")
    @Size(max = 200,message = "the description cannot exceed 200 characters")
    private String descriptionMertier;

    @Size(min = 5,message = "the description should be at least 5 characters")
    @Size(max = 200,message = "the description cannot exceed 200 characters")
    private String descriptionService;

    @NotEmpty(message = "the Status is required")
    private String Status;
    private MultipartFile imageFile;
}
