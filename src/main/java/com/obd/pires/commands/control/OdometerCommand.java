package com.obd.pires.commands.control;

import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.SystemOfUnits;
import com.obd.pires.enums.AvailableCommandNames;

/**
 * Odometer reading.
 *
 */
public class OdometerCommand extends ObdCommand implements SystemOfUnits {

    private float km = 0;

    /**
     * Default ctor.
     */
    public OdometerCommand() {
        super("01 A6");
    }

    /**
     * Copy ctor.
     *
     * @param other a {@link OdometerCommand} object.
     */
    public OdometerCommand(OdometerCommand other) {
        super(other);
    }

    /** {@inheritDoc} */
    @Override
    protected void performCalculations() {
        // ignore first two bytes [01 A6] of the response
        int A = buffer.get(2);
        int B = buffer.get(3);
        int C = buffer.get(4);
        int D = buffer.get(5);
        km = (A * (1 << 24) + B * (1 << 16) + C * (1 << 8) + D) / 10.0f;
    }

    /**
     * <p>getFormattedResult.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFormattedResult() {
        return useImperialUnits ? String.format("%.2f%s", getImperialUnit(), getResultUnit())
                : String.format("%.1f%s", km, getResultUnit());
    }

    /** {@inheritDoc} */
    @Override
    public String getCalculatedResult() {
        return useImperialUnits ? String.valueOf(getImperialUnit()) : String.valueOf(km);
    }

    /** {@inheritDoc} */
    @Override
    public String getResultUnit() {
        return useImperialUnits ? "m" : "km";
    }

    /** {@inheritDoc} */
    @Override
    public float getImperialUnit() {
        return km * 0.621371192F;
    }

    /**
     * <p>Getter for the field <code>km</code>.</p>
     *
     * @return a float.
     */
    public float getKm() {
        return km;
    }

    /**
     * <p>Setter for the field <code>km</code>.</p>
     *
     * @param km a float.
     */
    public void setKm(float km) {
        this.km = km;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return AvailableCommandNames.ODOMETER.getValue();
    }
}