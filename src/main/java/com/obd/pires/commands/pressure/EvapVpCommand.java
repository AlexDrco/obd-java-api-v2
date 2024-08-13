/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.obd.pires.commands.pressure;

import com.obd.pires.commands.ObdCommand;
import com.obd.pires.enums.AvailableCommandNames;

/**
 * Get EVAP system vapor pressure
 *
 */
public class EvapVpCommand extends ObdCommand {

    private float pressure = 0.0f;

    /**
     * <p>Constructor for EvapVpCommand.</p>
     */
    public EvapVpCommand() {
        super("01 32");
    }

    /** {@inheritDoc} */
    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        int a = buffer.get(2);
        int b = buffer.get(3);
        pressure = (a * 256 + b) / 4.0f - 8.192f;
    }

    /** {@inheritDoc} */
    @Override
    public String getFormattedResult() {
        return String.format("%.2f%s", pressure, getResultUnit());
    }

    /** {@inheritDoc} */
    @Override
    public String getCalculatedResult() {
        return String.valueOf(pressure);
    }

    /** {@inheritDoc} */
    @Override
    public String getResultUnit() {
        return "inH2O";
    }

    /**
     * <p>getPressure.</p>
     *
     * @return a float.
     */
    public float getPressure() {
        return pressure;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return AvailableCommandNames.EVAP_VP.getValue();
    }
}