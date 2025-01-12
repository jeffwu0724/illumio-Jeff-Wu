import java.util.Objects;

public class PortProtocolPair {
    private final String _port;
    private final String _protocol;

    public PortProtocolPair(String port, String protocolNumber) {
        this._port = port;
        this._protocol = protocolNumber;
    }

    @Override
    public String toString() {
        return this._port + "," + this._protocol;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        PortProtocolPair _portProtocolPair = (PortProtocolPair) object;
        return Objects.equals(_port, _portProtocolPair._port) && Objects.equals(_protocol, _portProtocolPair._protocol);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(_port, _protocol);
    }

    public String getPort() {
        return _port;
    }



}
