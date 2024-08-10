obd-java-api-v2
============

Extending Pires OBD-II Java API to simulate a vehicle ECU.

## NOTICE

## Important resources

Before opening an issue or using this, understand that this is a fork of the original [obd-java-api] and:

* Familiarize yourself with the *OBD-II standard*
* Familiarize yourself with the *[ELM327]* chip.

## Build ##

### Requisites ###

* JDK 21

### Compile, package and install locally ###

```
mvn clean install
```

## Usage ##

### Maven ###
```
pending
```

### Gradle ###
```
pending
```

### Example ###

The following example was tested using [OBD-II] <-> [ELM327] <-> [RS232] <-> [USB] serial comm.
(It was tested on Ford Ranger 1999, Toyota Raize 2024, Jeep Liberty 2006)
(Hardware was what I had available, so it may not be the best choice for your application)
```
        OBDSender sender = new SerialPortSender("COM5");

        if (!sender.startConnection()) {
            System.err.println("Failed to start connection.");
            return;
        }

        sender.setupEML327();

        List<ObdCommand> commands = Arrays.asList(
                new DistanceSinceCCCommand(),
                new TroubleCodesCommand(),
                new PermanentTroubleCodesCommand(),
                new RPMCommand(),
                new VinCommand(),
                new FuelLevelCommand(),
                new EngineCoolantTemperatureCommand(),
                new AvailablePidsCommand_01_20(),
                new DtcNumberCommand(),
                new ObdRawCommand("01 03")
        );

        sender.sendCommands(commands);

        List<CommandResponse> responses = sender.getCommandResponses();
        for (CommandResponse response : responses) {
            System.out.println("Command: " + response.getCommand() + ", Response: " + response.getResponse());
        }
```

## Troubleshooting ##

As *@dembol* noted:

Have you checked your ELM327 adapter with Torque or Scanmaster to see if it works with your car? Maybe the problem is with your device?

Popular OBD diagnostic tools reset state and disable echo, spaces etc before protocol selection. Download some ELM327 terminal for android and try following commands in order:
```
ATD
ATZ
AT E0
AT L0
AT S0
AT H0
AT SP 0
```
