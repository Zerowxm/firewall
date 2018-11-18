package firewall;

/**
 * range class with start and end
 */
public class Range {
    String start;
    String end;
    Range(String s, String e){
        start = s;
        end = e;
    }

    @Override
    public String toString() {
        return "Range{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
