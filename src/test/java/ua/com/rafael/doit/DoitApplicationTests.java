package ua.com.rafael.doit;

import org.junit.jupiter.api.Test;
import ua.com.rafael.doit.feature.model.CommonValidatorCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoitApplicationTests {

    private CommonValidatorCommand commonValidatorCommand = new CommonValidatorCommand(null);

    @Test
    void shouldValidateKeyWordAndCommand() {
        final String taskMessage = "doit plan";
        final String expected = "doit plan";
        final String actual = commonValidatorCommand.execute(taskMessage);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateKeyWordAndCommand() {
        final String taskMessage1 = "doet plan";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage1));

        final String taskMessage2 = "doit plan1";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage2));

        final String taskMessage3 = "doit ";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage3));

        final String taskMessage4 = "plan";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage4));

        final String taskMessage5 = "plan doit";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage5));
    }

    @Test
    void shouldValidateParametersWithTransitionNames(){
        final String taskMessage1 = "doit plan -abc";
        final String expected1 = "doit plan -abc";
        final String actual1 = commonValidatorCommand.execute(taskMessage1);
        assertEquals(expected1, actual1);

        final String taskMessage2 = "doit plan --time";
        final String expected2 = "doit plan --time";
        final String actual2 = commonValidatorCommand.execute(taskMessage2);
        assertEquals(expected2, actual2);

        final String taskMessage3 = "doit plan --time-abc";
        final String expected3 = "doit plan --time-abc";
        final String actual3 = commonValidatorCommand.execute(taskMessage3);
        assertEquals(expected3, actual3);

        final String taskMessage4 = "doit plan -abc --abc";
        final String expected4 = "doit plan -abc --abc";
        final String actual4 = commonValidatorCommand.execute(taskMessage4);
        assertEquals(expected4, actual4);

        final String taskMessage5 = "doit plan -abc --time-abc --day-abc";
        final String expected5 = "doit plan -abc --time-abc --day-abc";
        final String actual5 = commonValidatorCommand.execute(taskMessage5);
        assertEquals(expected5, actual5);
    }

    @Test
    void shouldNotValidateParametersWithTransitionNames(){
        final String taskMessage1 = "doit plan-abc";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage1));

        final String taskMessage2 = "doit plan ---abc";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage2));

        final String taskMessage3 = "doit plan -abc--abc";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage3));

        final String taskMessage4 = "doit plan --time--abd";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage4));

        final String taskMessage5 = "doit plan --time-ab -abc";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage5));
    }

    @Test
    void shouldValidateFlagsAndAttributes(){
        final String taskMessage1 = "doit seto 1-f";
        final String expected1 = "doit seto 1-f";
        final String actual1 = commonValidatorCommand.execute(taskMessage1);
        assertEquals(expected1, actual1);

        final String taskMessage2 = "doit seto f-1";
        final String expected2 = "doit seto f-1";
        final String actual2 = commonValidatorCommand.execute(taskMessage2);
        assertEquals(expected2, actual2);

        final String taskMessage3 = "doit seto 1-f,2-s";
        final String expected3 = "doit seto 1-f,2-s";
        final String actual3 = commonValidatorCommand.execute(taskMessage3);
        assertEquals(expected3, actual3);

        final String taskMessage4 = "doit seto 1-f,s-2";
        final String expected4 = "doit seto 1-f,s-2";
        final String actual4 = commonValidatorCommand.execute(taskMessage4);
        assertEquals(expected4, actual4);

        final String taskMessage5 = "doit seto 1-f,2-s,3-b";
        final String expected5 = "doit seto 1-f,2-s,3-b";
        final String actual5 = commonValidatorCommand.execute(taskMessage5);
        assertEquals(expected5, actual5);

        final String taskMessage6 = "doit seto 1-f,s-2,3-b";
        final String expected6 = "doit seto 1-f,s-2,3-b";
        final String actual6 = commonValidatorCommand.execute(taskMessage6);
        assertEquals(expected6, actual6);

        final String taskMessage7 = "doit seto 1-f,s-2,b-b";
        final String expected7 = "doit seto 1-f,s-2,b-b";
        final String actual7 = commonValidatorCommand.execute(taskMessage7);
        assertEquals(expected7, actual7);
    }

    @Test
    void shouldNotValidateFlagsAndAttributes(){
        final String taskMessage1 = "doit seto 1-";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage1));

        final String taskMessage2 = "doit seto -1";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage2));

        final String taskMessage3 = "doit seto 1-f,2";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage3));

        final String taskMessage4 = "doit seto 1-f,2-";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage4));

        final String taskMessage5 = "doit seto 1-f,2a";
        assertThrows(RuntimeException.class, () -> commonValidatorCommand.execute(taskMessage5));
    }

}
