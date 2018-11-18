package firewall;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Utils {
    public static List<Range> merge(List<Range> ranges) {
        if (ranges.size() <= 1)
            return ranges;

        // Sort by ascending starting point using an anonymous Comparator
        ranges.sort(Comparator.comparing(i -> i.start));

        List<Range> result = new LinkedList<>();
        String start = ranges.get(0).start;
        String end = ranges.get(0).end;

        for (Range range : ranges) {
            if (range.start.compareTo(end) <= 0) // Overlapping ranges, move the end if needed
                end = end.compareTo(range.end) > 0? end : range.end;
            else {                     // Disjoint ranges, add the previous one and reset bounds
                result.add(new Range(start, end));
                start = range.start;
                end = range.end;
            }
        }

        // Add the last range
        result.add(new Range(start, end));
        return result;
    }
}
