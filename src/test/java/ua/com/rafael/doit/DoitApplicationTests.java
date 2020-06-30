package ua.com.rafael.doit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ua.com.rafael.doit.feature.model.CommonValidatorCommand;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DoitApplicationTests {
    private final CommonValidatorCommand commonValidatorCommand = new CommonValidatorCommand(new CommandMock(null));

    @ParameterizedTest
    @MethodSource
    void shouldValidateSuccessfully(final String taskMessage) {
        final String actual = commonValidatorCommand.execute(taskMessage);
        assertEquals(taskMessage, actual);
    }

    static Stream<String> shouldValidateSuccessfully() {
        return Stream.of(
                "doit plan",
                "doit plan -abc",
                "doit plan --time",
                "doit plan --time-abc",
                "doit plan -abc --abc",
                "doit plan -abc --time-abc --day-abc",
                "doit seto 1-f",
                "doit seto f-1",
                "doit seto 1-f,2-s",
                "doit seto 1-f,s-2",
                "doit seto 1-f,2-s,3-b",
                "doit seto 1-f,s-2,3-b",
                "doit seto 1-f,s-2,b-b",
                "doit seto false",
                "doit seto \"New task\"",
                "doit seto \"New task\" 2020.12.12",
                "doit seto \"New task\" 2020.12.12-12:59:00",
                "doit seto \"New task\" 2020.12.12-2020.12.26",
                "doit seto \"New task\" 2020.12.12-00:17:45-2020.12.26-11:32:17");
    }

    @ParameterizedTest
    @MethodSource
    void shouldValidateUnsuccessfully(final String taskMessage) {
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage));
    }

    static Stream<String> shouldValidateUnsuccessfully() {
        return Stream.of(
                "doet plan",
                "doit plan1",
                "doit ",
                "plan",
                "plan doit",
                "doit plan-abc",
                "doit plan ---abc",
                "doit plan -abc--abc",
                "doit plan --time--abd",
                "doit plan --time-ab -abc",
                "doit seto 1-",
                "doit seto -1",
                "doit seto 1-f,2",
                "doit seto 1-f,2-",
                "doit seto 1-f,2a",
                "doit seto 1-f,s-2,b-b false",
                "doit seto 1-f,s-2,b-b false \"New task\"",
                "doit seto 1-f,s-2,b-b \"New task\" false",
                "doit seto \"New task\" 1998.12.12",
                "doit seto \"New task\" 2020.13.12",
                "doit seto \"New task\" 2020.12.12-",
                "doit seto \"New task\" 2020.12.12-13:00:00",
                "doit seto \"New task\" 2020.12.12-11:60:00",
                "doit seto \"New task\" 2020.12.12-00:00:60",
                "doit seto \"New task\" 2020.12.12-11:00:00-2020.12.11");
    }

    @Test
    void test() {
    }
}
