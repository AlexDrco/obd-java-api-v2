obd-java-api-v2
============

Extends Pires OBD-II Java API and simulate vehicle ECU responses.

## NOTICE

## Important resources

Before opening an issue or using this, understand that this is a fork of the original [obd-java-api] and:

* Familiarize yourself with the *OBD-II standard*
* Familiarize yourself with the *[ELM327]* chip implementations (Serial, Bluetooth, Wi-Fi, etc.).

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

* The following example was tested using [OBD-II] <-> [ELM327 v1.1] <-> [RS232] <-> [USB] serial comm.
* It was successfully tested on Ford Ranger 1999, Toyota Raize 2024, Jeep Liberty 2006.
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

As noted in original [obd-java-api]:

Test your ELM327 device with ScanMaster for serial or some app for bluetooth to see if it works with your car.

Popular OBD diagnostic tools reset state and disable echo, spaces etc before protocol selection. Download some ELM327 terminal for android and try following commands in order:

Note: AT S0 caused my ELM327 v1.1 device to stop responding, not using that command solved the issue.
```
ATD
ATZ
AT E0
AT L0
AT S0
AT H0
AT SP 0
```
