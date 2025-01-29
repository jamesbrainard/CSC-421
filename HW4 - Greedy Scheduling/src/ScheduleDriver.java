import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class that reads in an events file and outputs the greedy schedules.
 *   @author James Brainard
 *   @version 10/29/24
 */
public class ScheduleDriver {
    /**
     * Prompts the user for a filename containing events
     * and generates the schedules based on greedy criteria.
     * 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException{
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file containing events: ");
        String filename = scanner.nextLine();

        List<Event> events;
        try {
            events = readEventsFromFile(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return;
        }

        Scheduler scheduler = new Scheduler(events);

        List<Event> shortestSchedule = scheduler.generateSchedule(Scheduler.getShortestEventFirstComparator());
        scheduler.displaySchedule(shortestSchedule, "Shortest event first:"); //MOVE TO DRIVER

        List<Event> longestSchedule = scheduler.generateSchedule(Scheduler.getLongestEventFirstComparator());
        scheduler.displaySchedule(longestSchedule, "Longest event first:"); //MOVE TO DRIVER

        List<Event> earliestStartSchedule = scheduler.generateSchedule(Scheduler.getEarliestStartTimeComparator());
        scheduler.displaySchedule(earliestStartSchedule, "Earliest start-time first:"); //MOVE TO DRIVER

        List<Event> earliestEndSchedule = scheduler.generateSchedule(Scheduler.getEarliestEndTimeComparator());
        scheduler.displaySchedule(earliestEndSchedule, "Earliest end-time first:"); //MOVE TO DRIVER
    }

    /**
     * Reads events from a specified file and returns them as a list.
     * 
     * @param filename the name of the file to be read from
     * @return a list of Event objects read from the file
     * @throws FileNotFoundException
     */
    private static List<Event> readEventsFromFile(String filename) throws FileNotFoundException {
        List<Event> events = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
    
                // Split the line into parts by whitespace
                String[] parts = line.split("\\s+", 3);
                if (parts.length < 3) {
                    System.out.println("Invalid format in line: " + line);
                    continue;
                }
    
                String start = parts[0].trim();
                String end = parts[1].trim();
                String description = parts[2].trim();
    
                events.add(new Event(description, start, end));
            }
        }
        return events;
    }
}