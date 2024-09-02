package com.obd.pires.commands.control;

import com.obd.pires.commands.ObdCommand;
import com.obd.pires.enums.AvailableCommandNames;

public class TimeSinceDtcCCommand extends ObdCommand {

    private int value = 0;

    /**
     * Default ctor.
     */
    public TimeSinceDtcCCommand() {
        super("01 4E");
    }

    /**
     * Copy ctor.
     *
     * @param other a {@link TimeSinceDtcCCommand} object.
     */
    public TimeSinceDtcCCommand(TimeSinceDtcCCommand other) {
        super(other);
    }

    /** {@inheritDoc} */
    @Override
    protected void performCalculations() {
        // ignore first two bytes [01 4E] of the response
        value = buffer.get(2) * 256 + buffer.get(3);
    }

    /** {@inheritDoc} */
    @Override
    public String getFormattedResult() {
        // determine time
        final String hh = String.format("%02d", value / 3600);
        final String mm = String.format("%02d", (value % 3600) / 60);
        final String ss = String.format("%02d", value % 60);
        return String.format("%s:%s:%s", hh, mm, ss);
    }

    /** {@inheritDoc} */
    @Override
    public String getCalculatedResult() {
        return String.valueOf(value);
    }

    /** {@inheritDoc} */
    @Override
    public String getResultUnit() {
        return "s";
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return AvailableCommandNames.TIME_SINCE_TC_CLEARED.getValue();
    }
}