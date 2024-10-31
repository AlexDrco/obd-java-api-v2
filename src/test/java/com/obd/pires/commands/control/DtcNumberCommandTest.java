package com.obd.pires.commands.control;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DtcNumberCommandTest {

    private void setBuffer(DtcNumberCommand command, String[] buffer) throws Exception {
        Field bufferField = command.getClass().getSuperclass().getDeclaredField("buffer");
        bufferField.setAccessible(true);
        List<Integer> bufferList = new ArrayList<>();
        for (String b : buffer) {
            bufferList.add(Integer.parseInt(b, 16));
        }
        bufferField.set(command, bufferList);
    }

    @Test
    public void testNoError() throws Exception {
        DtcNumberCommand command = new DtcNumberCommand();
        setBuffer(command, "41 01 00 07 E1 00".split(" "));
        command.performCalculations();

        assertFalse(command.getMilOn(), "MIL should be OFF");
        assertEquals(0, command.getTotalAvailableCodes(), "There should be no error codes");
        assertEquals("MIL is OFF 0 codes", command.getFormattedResult());
    }

    @Test
    public void testWithError() throws Exception {
        DtcNumberCommand command = new DtcNumberCommand();
        setBuffer(command, "41 01 81 27 E1 E1".split(" "));
        command.performCalculations();

        assertTrue(command.getMilOn(), "MIL should be ON");
        assertEquals(1, command.getTotalAvailableCodes(), "There should be one error code");
        assertEquals("MIL is ON 1 codes", command.getFormattedResult());
    }
}