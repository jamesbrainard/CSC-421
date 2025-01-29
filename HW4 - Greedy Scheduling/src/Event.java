/**
 * Generic class that implements an event, with a start time, end time, and description.
 *   @author James Brainard
 *   @version 10/29/24
 */
public class Event {
    private String description;
    private int startTime;
    private int endTime;
    private boolean isRequired;

    /**
     * Constructs an Event with a description, start time, and end time
     * 
     * @param description the description of the event
     * @param start the start time of the event in "HH:MM" format
     * @param end the end time of the event in "HH:MM" format
     */
    public Event(String description, String start, String end) {
        this.isRequired = description.startsWith("REQ");
        this.description = description;
        this.startTime = timeToMinutes(start);
        this.endTime = timeToMinutes(end);
    }

    /**
     * Formats a time given in minutes into "HH:MM" format
     * 
     * @param minutes the time, in minutes, to format
     * @return the formatted time string in "HH:MM" format
     */
    public static String formatTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;

        String hourStr = Integer.toString(hours);
        if (hours < 10) {
            hourStr = "0" + hourStr;
        }

        String minStr = Integer.toString(mins);
        if (mins < 10) {
            minStr = minStr + "0";
        }

        return hourStr + ":" + minStr;
    }

    /**
     * Converts a time string in "HH:MM" format to minutes.
     * 
     * @param time the time string to convert
     * @return the total minutes represented by the given time
     */
    private int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    /**
     * Returns whether the event is marked as required.
     * 
     * @return true if the event is required, false otherwise
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Returns the description of the event
     * 
     * @return the event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the start time of the event in minutes.
     * 
     * @return the start time in minutes
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the event in minutes.
     * 
     * @return the end time in minutes
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Returns the duration of the event in minutes.
     * 
     * @return the duration of the event in minutes
     */
    public int getDuration() {
        return endTime - startTime;
    }

    /**
     * Returns a string representation of the event.
     * If the description starts with "REQ", it is removed from the output.
     * 
     * @return the string representation fo the event
     */
    @Override
    public String toString() {
        String cleanDescription = description;
        if (description.startsWith("REQ")) {
            cleanDescription = description.substring(3).trim();
        }
        return formatTime(startTime) + " " + formatTime(endTime) + " " + cleanDescription;
    }
}