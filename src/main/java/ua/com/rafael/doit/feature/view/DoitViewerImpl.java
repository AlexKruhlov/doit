package ua.com.rafael.doit.feature.view;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.rafael.doit.api.view.DoitViewer;

import java.util.Scanner;

@Component
@NoArgsConstructor
public class DoitViewerImpl implements DoitViewer<String> {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
