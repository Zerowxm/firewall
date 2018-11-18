package testSuite;

import firewall.Firewall;
import org.junit.Test;
import static org.junit.Assert.*;

public class FirewallTest {

    @Test
    public void accept_packet() {
        Firewall fw = new Firewall("./src/fw.csv");
        assertTrue(fw.accept_packet("inbound", "tcp", 80, "192.168.1.2") );
        assertTrue(fw.accept_packet("inbound", "udp", 53, "192.168.2.1") );
        assertTrue(fw.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
        assertFalse(fw.accept_packet("inbound", "tcp", 81, "192.168.1.2"));
        assertFalse(fw.accept_packet("inbound", "udp", 24, "52.12.48.92"));
        assertFalse(fw.accept_packet("1", "1", 24, "52.12.48.92"));
        assertFalse(fw.accept_packet("inbound", "tcp", 0, "52.12.48.92"));
        assertFalse(fw.accept_packet("inbound", "tcp", 1, "1"));
        assertFalse(fw.accept_packet("inbound", "udp", 0, "0"));
    }
}

