package ua.com.rafael.doit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ua.com.rafael.doit.feature.model.CommonValidatorCommand;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoitApplicationTests {

    private CommonValidatorCommand commonValidatorCommand = new CommonValidatorCommand(null);

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
                "doit seto 1-f,s-2,b-b");
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
                "doit seto 1-f,2a");
    }
}
