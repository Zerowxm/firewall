package firewall;

import java.io.*;
import java.util.HashMap;
import java.util.function.Consumer;

public class Firewall {
    String csvFile;
    public static final String COMMA = ",";
    public static final String DASH = "-";

    // we use a array with length 4, each represent a hash map, key is the port, value is the ipPool.
    HashMap<String, IPPool>[] rules;
    HashMap<String, Integer> indices; // rules[0] is all rules of inbound and tcp, rules[1] is inbound udp
                                        // rules[2] is outbound tcp, rules[3] is outbound udp

    public Firewall(String csvPath){
        csvFile = csvPath;
        rules = new HashMap[4];
        for (int i = 0; i < 4; i++){
            rules[i] = new HashMap<>();
        }
        indices = new HashMap<>();
        indices.put("inbound", 0);
        indices.put("outbound", 2);
        indices.put("tcp", 0);
        indices.put("udp", 1);
        init();
    }

    private int getIndex(String direction, String protocol){
        return indices.getOrDefault(direction, -5) + indices.getOrDefault(protocol, -5);
    }

    private IPPool findRule(String direction, String protocol, String port, String ip){
        IPPool pool = null;
        int index = indices.get(direction) + indices.get(protocol);
        HashMap<String, IPPool> rule = rules[index];
        for (String key : rule.keySet()){
            if (key.contains(DASH)){
                String[] ports = key.split(DASH);
                if (ports[0].compareTo(port) <= 0 && ports[1].compareTo(port) >= 0){
                    pool = rule.get(key);
                    break;
                }
            }
            else if (key.equals(port)){
                pool = rule.get(key);
                break;
            }
        }
        return pool;
    }

    //takes exactly four arguments and returns true if the given packet is valid
    public boolean accept_packet(String direction, String protocol, int port, String ip){
        if (getIndex(direction,protocol) < 0){
            return false;
        }
        IPPool pool = findRule(direction,protocol,String.valueOf(port),ip);
        return pool != null && pool.contains(ip);
    }

    private void init(){
        try {
            File csv = new File(csvFile);
            InputStream stream = new FileInputStream(csv);

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            br.lines().forEach(rulesConsumer); // read csv into memory
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Firewall fw = new Firewall("./src/fw.csv");
        fw.printRules();
    }

    private void printRules(){
        for (HashMap rule : rules){
            System.out.println(rule);
        }
    }

    // parse csv line into hashMap
    Consumer<String> rulesConsumer= line -> {
        String[] input = line.split(COMMA);
        String direction = input[0];
        String protocol = input[1];
        String port = input[2];
        String ip = input[3];
        int index = indices.get(direction) + indices.get(protocol);
        HashMap<String, IPPool> rule = rules[index];
        IPPool pool = findRule(direction,protocol,port,ip);
        if (pool == null){
            pool = new IPPool();
        }
        if (ip.contains(DASH)){
            pool.addRange(ip);
        }
        else pool.addIP(ip);
        rule.put(port, pool);
    };


}
