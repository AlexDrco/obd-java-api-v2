package com.obd.examples;

import com.obd.comm.CommandResponseRaw;
import com.obd.comm.SimulatedSender;
import com.obd.comm.sender.AbstractOBDSender;
import com.obd.pires.commands.ObdCommand;
import com.obd.pires.commands.control.SpeedCommand;
import com.obd.pires.commands.control.*;
import com.obd.pires.commands.engine.*;
import com.obd.pires.commands.fuel.*;
import com.obd.pires.commands.pressure.*;
import com.obd.pires.commands.temperature.AirIntakeTemperatureCommand;
import com.obd.pires.commands.temperature.AmbientAirTemperatureCommand;
import com.obd.pires.commands.temperature.EngineCoolantTemperatureCommand;

import java.util.Arrays;
import java.util.List;

public class SimulatedExample {

    /*
     * This example demonstrates how to use the SimulatedSender class to simulate a
     * "scanner" communicating with an "OBD-II capable ECU".
     * The Sender makes the command requests, simulates the stream and the corresponding
     * response class provides the default response.
     * The Command <-> Response switch statement can be found in AbstractOBDSender class.
     */
    public static void main(String[] args) {

        AbstractOBDSender sender = new SimulatedSender();

        // Only simulated, code does nothing
        if (!sender.startConnection()) {
            System.err.println("Failed to simulate start connection.");
            return;
        }

        // Only simulated, code does nothing
        sender.setupEML327();

        List<ObdCommand> commands = Arrays.asList(
                // Control Commands
                new DistanceMILOnCommand(),
                new DistanceSinceCCCommand(),
                new DtcNumberCommand(),
                new EquivalentRatioCommand(),
                new IgnitionMonitorCommand(),
                new ModuleVoltageCommand(),
                new PendingTroubleCodesCommand(),
                new PermanentTroubleCodesCommand(),
                new RuntimeMilCommand(),
                new TimeSinceDtcCCommand(),
                new TimingAdvanceCommand(),
                new TroubleCodesCommand(),
                new VinCommand(),
                new WarmUpCyclesSinceDtcClrCommand(),
                // Engine Commands
                new AbsoluteLoadCommand(),
                new LoadCommand(),
                new MassAirFlowCommand(),
                new OilTempCommand(),
                new RPMCommand(),
                new RuntimeCommand(),
                new ThrottlePositionCommand(),
                // Fuel Commands
                new AirFuelRatioCommand(),
                new ConsumptionRateCommand(),
                new FindFuelTypeCommand(),
                new FuelLevelCommand(),
                new WidebandAirFuelRatioCommand(),
                // Pressure Commands
                new BarometricPressureCommand(),
                new EvapVpCommand(),
                new FuelPressureCommand(),
                new FuelRailPressureCommand(),
                new IntakeManifoldPressureCommand(),
                // Temperature Commands
                new AirIntakeTemperatureCommand(),
                new AmbientAirTemperatureCommand(),
                new EngineCoolantTemperatureCommand(),
                // Speed Commands
                new SpeedCommand()

        );

        sender.sendCommands(commands);

        List<CommandResponseRaw> responses = sender.getCommandResponses();
        for (CommandResponseRaw response : responses) {
            System.out.println("Command: " + response.getCommand() + ", Response: " + response.getResponse() + ", Raw: " + response.getRaw());
        }
    }
}
