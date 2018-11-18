package firewall;

import java.util.*;

/**
 * A collection of ips including ranges and individual ips
 */
public class IPPool {
    private List<Range> ranges; // store ip ranges without overlap
    private HashSet<String> separateIPs;
    IPPool(){
        ranges = new ArrayList<>();
        separateIPs = new HashSet<>();
    }


    public void addIP(String ip){
        if (!contains(ip)){
            separateIPs.add(ip);
        }
    }

    public void addRange(String range){
        String[] ips = range.split(Firewall.DASH);
        ranges.add(new Range(ips[0],ips[1]));
        ranges = Utils.merge(ranges); // merge overlap ranges
    }

    public boolean contains(String ip){
        for(Range r : ranges){
            if(r.start.compareTo(ip) <= 0 && r.end.compareTo(ip) >= 0){
                return true;
            }
        }
        return separateIPs.contains(ip);
    }

    @Override
    public String toString() {
        return "IPPool{" +
                "ranges=" + ranges +
                ", separateIPs=" + separateIPs +
                '}';
    }
}
