package com.obd.agnx.response;

import com.obd.agnx.response.common.*;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.RPMCommand;
import com.obd.pires.commands.engine.RuntimeCommand;
import com.obd.pires.commands.fuel.FuelLevelCommand;
import com.obd.pires.commands.pressure.EvapVpCommand;
import com.obd.pires.commands.protocol.*;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;

/**
 * Representing an OBD (On-Board Diagnostics) response.
 * This class provides methods to generate default and simulated responses for various OBD commands.
 */
public abstract class OBDResponse {
    protected String command;

    /**
     * Constructor for OBDResponse.
     *
     * @param command the OBD command string
     */
    public OBDResponse(String command) {
        this.command = command;
    }

    /**
     * Gets the OBD command string.
     *
     * @return the OBD command string
     */
    public String getCommand() {
        return command;
    }

    /**
     * Abstract method to get the default response for the OBD command.
     *
     * @return the default response string
     */
    public abstract String getDefaultResponse();

    /**
     * Abstract method to get the simulated default response for the OBD command.
     *
     * @return the simulated default response string
     */
    public abstract String getSimulatedDefaultResponse();

    /**
     * Abstract method to get the simulated response for the OBD command.
     *
     * @return the simulated response string
     */
    public abstract String getSimulatedResponse();

    /**
     * Abstract method to get the simulated response for the OBD command with an initial value.
     *
     * @param initialValue the initial value for the simulated response
     * @return the simulated response string
     */
    public abstract String getSimulatedResponse(String initialValue);

    /**
     * Generates the default response for a given OBD command.
     *
     * @param command the OBD command
     * @return the default response string
     */
    public static String generateDefaultResponse(ObdCommand command) {
        return switch (command) {
            case DistanceSinceCCCommand distanceSinceCCCommand -> new DistanceSinceCCResponse().getDefaultResponse();
            case TroubleCodesCommand troubleCodesCommand -> new TroubleCodesResponse().getDefaultResponse();
            case PermanentTroubleCodesCommand permanentTroubleCodesCommand ->
                    new PermanentTroubleCodesResponse().getDefaultResponse();
            case RPMCommand rpmCommand -> new RPMResponse().getDefaultResponse();
            case VinCommand vinCommand -> new VinResponse().getDefaultResponse();
            case FuelLevelCommand fuelLevelCommand -> new FuelLevelResponse().getDefaultResponse();
            case EngineCoolantTemperatureCommand engineCoolantTemperatureCommand ->
                    new EngineCoolantTemperatureResponse().getDefaultResponse();
            case DtcNumberCommand dtcNumberCommand -> new DtcNumberResponse().getDefaultResponse();
            case ObdRawCommand obdRawCommand -> "NO DATA";
            case RuntimeCommand runtimeCommand -> new RuntimeResponse().getDefaultResponse();
            case DistanceMILOnCommand distanceMILOnCommand -> new DistanceMILOnResponse().getDefaultResponse();
            case WarmUpCyclesSinceDtcClrCommand warmUpCyclesSinceDtcClrCommand ->
                    new WarmUpCyclesSinceDtcClrResponse().getDefaultResponse();
            case EvapVpCommand evapVpCommand -> new EvapVpResponse().getDefaultResponse();
            case null, default -> "NO DATA";
        };
    }

    /**
     * Generates the simulated response for a given OBD command.
     *
     * @param command the OBD command
     * @return the simulated response string
     */
    public static String generateSimulatedResponse(ObdCommand command) {
        return switch (command) {
            case DistanceSinceCCCommand distanceSinceCCCommand -> new DistanceSinceCCResponse().getSimulatedResponse();
            case TroubleCodesCommand troubleCodesCommand -> new TroubleCodesResponse().getSimulatedResponse();
            case PermanentTroubleCodesCommand permanentTroubleCodesCommand ->
                    new PermanentTroubleCodesResponse().getSimulatedResponse();
            case RPMCommand rpmCommand -> new RPMResponse().getSimulatedResponse();
            case VinCommand vinCommand -> new VinResponse().getSimulatedResponse();
            case FuelLevelCommand fuelLevelCommand -> "41 2F 64";
            case EngineCoolantTemperatureCommand engineCoolantTemperatureCommand ->
                    new EngineCoolantTemperatureResponse().getSimulatedResponse();
            case AvailablePidsCommand_01_20 availablePidsCommand0120 -> "41 00 BE 3E B8 13";
            case DtcNumberCommand dtcNumberCommand -> new DtcNumberResponse().getSimulatedResponse();
            case ObdRawCommand obdRawCommand -> "NO DATA";
            case RuntimeCommand runtimeCommand -> new RuntimeResponse().getSimulatedResponse();
            case DistanceMILOnCommand distanceMILOnCommand -> new DistanceMILOnResponse().getSimulatedResponse();
            case WarmUpCyclesSinceDtcClrCommand warmUpCyclesSinceDtcClrCommand ->
                    new WarmUpCyclesSinceDtcClrResponse().getSimulatedResponse();
            case EvapVpCommand evapVpCommand -> new EvapVpResponse().getSimulatedResponse();
            case null, default -> "NO DATA";
        };
    }
}