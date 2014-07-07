package byone.hbase;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;

import java.io.IOException;

/**
 * Created by dream on 7/7/14.
 */
public class ScanCovert {
    public ScanCovert(Scan s) {
        this.scan = s;
    }
    public String coverToScan() throws IOException {
        ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
        return Base64.encodeBytes(proto.toByteArray());
    }
    public Scan convertStringToScan(String base64) throws IOException {
        byte [] decoded = Base64.decode(base64);
        ClientProtos.Scan scan;
        try {
            scan = ClientProtos.Scan.parseFrom(decoded);
        } catch (InvalidProtocolBufferException ipbe) {
            throw new IOException(ipbe);
        }

        return ProtobufUtil.toScan(scan);
    }
    private Scan scan;
}
