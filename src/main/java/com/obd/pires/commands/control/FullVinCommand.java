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
package com.obd.pires.commands.control;

import com.obd.pires.commands.PersistentCommand;
import com.obd.pires.enums.AvailableCommandNames;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullVinCommand extends PersistentCommand {

    String vin = "";
    String year = "";
    String make = "";
    String model = "";

    /**
     * Default ctor.
     */
    public FullVinCommand() {
        super("09 02");
    }

    /**
     * Copy ctor.
     *
     * @param other a {@link VinCommand} object.
     */
    public FullVinCommand(VinCommand other) {
        super(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performCalculations() {
        final String result = getResult();
        String vin = "";
        String year = "";
        String make = "";
        String model = "";

        if (result.contains(":")) { // CAN(ISO-15765) protocol
            String workingData = result.replaceAll(".:", "").substring(9); // 9 is xxx490201, xxx is bytes of information to follow
            Matcher m = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE).matcher(convertHexToString(workingData));
            if (m.find()) {
                workingData = result.replaceAll("0:49", "").replaceAll(".:", "");
            }
            vin = convertHexToString(workingData).replaceAll("[\\u0000-\\u001f]", "");

            // Extract year, make, and model from VIN
            year = vin.substring(9, 11);
            make = vin.substring(1, 3);
            model = vin.substring(3, 9);
        } else { // ISO9141-2, KWP2000 Fast and KWP2000 5Kbps (ISO15031) protocols
            String workingData = result.replaceAll("49020.", "");
            vin = convertHexToString(workingData).replaceAll("[\\u0000-\\u001f]", "");

            // Extract year, make, and model from VIN
            year = vin.substring(9, 11);
            make = vin.substring(1, 3);
            model = vin.substring(3, 9);
        }

        // Set the calculated results
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFormattedResult() {
        return String.valueOf(vin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return AvailableCommandNames.VIN.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCalculatedResult() {
        return String.valueOf(vin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillBuffer() {
    }

    public String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
        }
        return sb.toString();
    }
}

