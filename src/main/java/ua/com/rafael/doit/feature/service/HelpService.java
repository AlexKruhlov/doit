package ua.com.rafael.doit.feature.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.rafael.doit.feature.persistence.repository.HelpRepository;

@Service
@RequiredArgsConstructor
public class HelpService {

    private final HelpRepository helpRepository;

    public void find() {
        helpRepository.findAll();
    }
}
