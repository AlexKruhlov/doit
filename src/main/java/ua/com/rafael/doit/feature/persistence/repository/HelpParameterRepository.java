package ua.com.rafael.doit.feature.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.rafael.doit.feature.persistence.entity.HelpParameter;

public interface HelpParameterRepository extends MongoRepository<HelpParameter, String> {
}
