import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that acts as a scheduler for a List of Events
 * Provides various greedy scheduling strategies using comparators
 *   @author James Brainard
 *   @version 10/29/24
 */
public class Scheduler {
    private List<Event> events;

    /**
     * Constructs a Scheduler with a given List of Events.
     * 
     * @param events the List of Events to be scheduled
     */
    public Scheduler(List<Event> events) {
        this.events = new ArrayList<>(events);
    }

    /**
     * Generates a schedule based on the specified comparator.
     * 
     * @param comparator the comparator to be used to sort events
     * @return the generated schedule as a List of Events
     */
    public List<Event> generateSchedule(Comparator<Event> comparator) {
        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort(comparator);

        List<Event> schedule = new ArrayList<>();

        for (Event event : sortedEvents) {
            if (event.isRequired()) {
                schedule.add(event);
            }
        }

        for (Event event : sortedEvents) {
            if (!event.isRequired() && isNonConflicting(schedule, event)) {
                schedule.add(event);
            }
        }

        schedule.sort(new EarliestStartTimeComparator());
        return schedule;
    }  

    /**
     * Checks if a new event conflicts with any event already in the schedule.
     * 
     * @param schedule the current List of scheduled Events
     * @param newEvent the new Event to check for conflicts
     * @return true if the new event does not conflict, false if it does
     */
    private boolean isNonConflicting(List<Event> schedule, Event newEvent) {
        for (Event event : schedule) {
            // System.out.println("New event start time:" + newEvent.getStartTime());
            // System.out.println("New event end time:" + newEvent.getEndTime());
            // System.out.println("Event start time:" + event.getStartTime());
            // System.out.println("Event end time:" + event.getEndTime());
            if ((newEvent.getStartTime() >= event.getStartTime() && newEvent.getStartTime() < event.getEndTime()) 
            || (newEvent.getEndTime() > event.getStartTime() && newEvent.getEndTime() <= event.getEndTime())
            || (newEvent.getStartTime() <= event.getStartTime() && newEvent.getEndTime() >= event.getEndTime())) {
                return false; 
            }
        }
        return true;
    }

    /**
     * Displays the given schedule with a specified title or strategy name.
     * 
     * @param schedule the List of Events to display
     * @param strategyName the name of the scheduling strategy used
     */
    public void displaySchedule(List<Event> schedule, String strategyName) {
        System.out.println(strategyName);
        System.out.println("----------------------");
        for (Event event : schedule) {
            System.out.println(event);
        }
        System.out.println();
    }

    /**
     * Comparator that sorts events by their duration, shortest first.
     */
    private static class ShortestEventFirstComparator implements Comparator<Event> {
        @Override
        public int compare(Event e1, Event e2) {
            return Integer.compare(e1.getDuration(), e2.getDuration());
        }
    }

    /**
     * Comparator that sorts events by their duration, longest first.
     */
    private static class LongestEventFirstComparator implements Comparator<Event> {
        @Override
        public int compare(Event e1, Event e2) {
            return Integer.compare(e2.getDuration(), e1.getDuration());
        }
    }

    /**
     * Comparator that sorts events by their start time, earliest first.
     */
    private static class EarliestStartTimeComparator implements Comparator<Event> {
        @Override
        public int compare(Event e1, Event e2) {
            return Integer.compare(e1.getStartTime(), e2.getStartTime());
        }
    }

    /**
     * Comparator that sorts events by their end time, earliest first.
     */
    private static class EarliestEndTimeComparator implements Comparator<Event> {
        @Override
        public int compare(Event e1, Event e2) {
            return Integer.compare(e1.getEndTime(), e2.getEndTime());
        }
    }

    /**
     * Returns a comparator that sorts events by duration, shortest first.
     * 
     * @return a comparator for sorting events by shortest duration first
     */
    public static Comparator<Event> getShortestEventFirstComparator() {
        return new ShortestEventFirstComparator();
    }

    /**
     * Returns a comparator that sorts events by duration, longest first.
     * 
     * @return a comparator for sorting events by longest duration first
     */
    public static Comparator<Event> getLongestEventFirstComparator() {
        return new LongestEventFirstComparator();
    }

    /**
     * Returns a comparator that sorts events by start time, earliest first.
     * 
     * @return a comparator for sorting events by earliest start time first
     */
    public static Comparator<Event> getEarliestStartTimeComparator() {
        return new EarliestStartTimeComparator();
    }

    /**
     * Returns a comparator that sorts events by end time, earliest first.
     * 
     * @return a comparator for sorting events by earliest end time first
     */
    public static Comparator<Event> getEarliestEndTimeComparator() {
        return new EarliestEndTimeComparator();
    }
}