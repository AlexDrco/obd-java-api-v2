package com.obd.examples;

import com.obd.comm.CommandResponseRaw;
import com.obd.comm.SimulatedSender;
import com.obd.comm.sender.AbstractOBDSender;
import com.obd.pires.commands.ObdCommand;
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
                new RuntimeCommand(),
                new DistanceMILOnCommand(), //Distance MIL On
                new FuelLevelCommand(),

                new WarmUpCyclesSinceDtcClrCommand(),
                new DistanceSinceCCCommand(), //Clr Distance
                new EvapVpCommand(),

                new RPMCommand(),
                new EngineCoolantTemperatureCommand(),

                new TroubleCodesCommand(),
                new PermanentTroubleCodesCommand(),


                new VinCommand(),

                new DtcNumberCommand(),

                new EquivalentRatioCommand(),
                new IgnitionMonitorCommand(),
                new ModuleVoltageCommand(),
                new PermanentTroubleCodesCommand(),
                new TimingAdvanceCommand(),
                new AbsoluteLoadCommand(),
                new LoadCommand(),
                new MassAirFlowCommand(),
                new OilTempCommand(),
                new ThrottlePositionCommand(),
                new AirFuelRatioCommand(),
                new ConsumptionRateCommand(),
                new FindFuelTypeCommand(),
                new FuelTrimCommand(),
                new WidebandAirFuelRatioCommand(),

                new BarometricPressureCommand(),
                new FuelPressureCommand(),
                new FuelRailPressureCommand(),
                new IntakeManifoldPressureCommand(),

                new AirIntakeTemperatureCommand(),
                new AmbientAirTemperatureCommand()
        );

        sender.sendCommands(commands);

        List<CommandResponseRaw> responses = sender.getCommandResponses();
        for (CommandResponseRaw response : responses) {
            System.out.println("Command: " + response.getCommand() + ", Response: " + response.getResponse() + ", Raw: " + response.getRaw());
        }
    }
}
