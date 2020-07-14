package ua.com.rafael.doit.feature.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

import static ua.com.rafael.doit.feature.persistence.entity.PersistanceConstants.CollectionNames.HELP_PARAMETERS;

@Document(collection = HELP_PARAMETERS)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HelpParameter {

    @Id
    private String id;

    @NotBlank
    private String parameterName;

    @NotBlank
    private char parameterAlias;

    @NotBlank
    private String description;
}
