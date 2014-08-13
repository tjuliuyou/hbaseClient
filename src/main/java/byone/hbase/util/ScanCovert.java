package byone.hbase.util;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dream on 7/7/14.
 */
public class ScanCovert {

    public String coverToScan(Scan scan) throws IOException {
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

    public String[] coverToScan(List<Scan> sl) throws IOException {
        List<String> scanStrings = new ArrayList<String>();
        for (Scan sn : sl) {
            scanStrings.add(coverToScan(sn));
        }
        //return scanStrings;
        return scanStrings.toArray(new String[scanStrings.size()]);
    }

    public List<Scan> convertStringToScan(List<String> base64List) throws IOException {
        List<Scan> scanlist = new ArrayList<Scan>();
        for(String base : base64List ) {
            scanlist.add(convertStringToScan(base));
        }
        return scanlist;
    }
   // private Scan scan;
}
