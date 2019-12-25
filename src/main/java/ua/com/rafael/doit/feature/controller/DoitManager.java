package ua.com.rafael.doit.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.rafael.doit.api.controller.DoitController;
import ua.com.rafael.doit.api.view.DoitViewer;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoitManager implements DoitController {
    private final DoitViewer<String> doitViewer;

    @Override
    public void runManager() {
        doitViewer.write("Welcome to Task Manager Application \"DOIT\"!!!");
        while (true) {
            doitViewer.write("Please, enter your task:");
            String taskMessage = doitViewer.read();
            if (taskMessage.equals("exit")) {
                System.exit(0);
            }
        }
    }
}
