package ua.com.rafael.doit.feature.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.rafael.doit.feature.persistence.entity.Help;

public interface HelpRepository extends MongoRepository<Help, String> {
}
