package com.obd.agnx.response;

/**
 * Abstract class representing a generic OBD response.
 */
public abstract class OBDResponse {
    /**
     * The OBD command associated with this response.
     */
    protected String command;

    /**
     * Constructs an OBDResponse with the specified command.
     *
     * @param command the OBD command associated with this response
     */
    public OBDResponse(String command) {
        this.command = command;
    }

    /**
     * Returns the OBD command associated with this response.
     *
     * @return the OBD command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the default response for this OBD command.
     * This method should be implemented by subclasses to provide
     * the specific default response.
     *
     * @return the default response
     */
    public abstract String getDefaultResponse();

    /**
     * Returns the simulated default response for this OBD command.
     * This method should be implemented by subclasses to provide
     * the specific simulated default response.
     *
     * @return the simulated default response
     */
    public abstract String getSimulatedDefaultResponse();

    /**
     * Returns the simulated response for this OBD command based on the initial value.
     * This method should be implemented by subclasses to provide
     * the specific simulated response.
     *
     * @param initialValue the initial value to base the simulated response on
     * @return the simulated response
     */
    public abstract String getSimulatedResponse(String initialValue);
}