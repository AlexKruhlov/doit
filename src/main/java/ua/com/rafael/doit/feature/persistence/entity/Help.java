package ua.com.rafael.doit.feature.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

import static ua.com.rafael.doit.feature.persistence.entity.PersistanceConstants.CollectionNames.HELPS;

@Document(collection = HELPS)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Help {

    @Id
    private String id;

    @NotBlank
    private String title;

    private String commandTemplate;

    @NotBlank
    private String shortDescription;

    private String fullDescription;

    private List<HelpParameter> params;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Help help = (Help) o;
        return Objects.equals(id, help.id) &&
                Objects.equals(title, help.title) &&
                Objects.equals(commandTemplate, help.commandTemplate) &&
                Objects.equals(shortDescription, help.shortDescription) &&
                Objects.equals(fullDescription, help.fullDescription) &&
                Objects.equals(params, help.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, commandTemplate, shortDescription, fullDescription, params);
    }
}
