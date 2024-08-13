package com.obd.pires.commands.control;

import com.obd.pires.commands.ObdCommand;
import com.obd.pires.enums.AvailableCommandNames;

/**
 * Get the number of warm-up cycles since DTCs were last cleared.
 */
public class WarmUpCyclesSinceDtcClrCommand extends ObdCommand {

    private int warmUpCycles = 0;

    /**
     * Default constructor.
     */
    public WarmUpCyclesSinceDtcClrCommand() {
        super("01 30");
    }

    /** {@inheritDoc} */
    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        warmUpCycles = buffer.get(2);
    }

    /**
     * <p>getFormattedResult.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFormattedResult() {
        return String.format("%d cycles", warmUpCycles);
    }

    /** {@inheritDoc} */
    @Override
    public String getCalculatedResult() {
        return String.valueOf(warmUpCycles);
    }

    /** {@inheritDoc} */
    @Override
    public String getResultUnit() {
        return "cycles";
    }

    /**
     * <p>Getter for the field <code>warmUpCycles</code>.</p>
     *
     * @return a int.
     */
    public int getWarmUpCycles() {
        return warmUpCycles;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return AvailableCommandNames.WARM_UPS_SINCE_DTC_CLEAR.getValue();
    }
}