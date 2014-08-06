package byone.hbase.filter;

import com.google.protobuf.HBaseZeroCopyByteString;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.protobuf.generated.ComparatorProtos;
import org.apache.hadoop.hbase.util.DynamicClassLoader;
import java.io.IOException;
import java.lang.reflect.Method;
/**
 * Created by dream on 8/5/14.
 */
/**
 * Protobufs utility.
 */
public final class ProtobufUtil {

    private ProtobufUtil() {
    }

    /**
     * Dynamic class loader to load filter/comparators
     */
    private final static ClassLoader CLASS_LOADER;

    static {
        ClassLoader parent = ProtobufUtil.class.getClassLoader();
        Configuration conf = HBaseConfiguration.create();
        CLASS_LOADER = new DynamicClassLoader(conf, parent);

    }

    /**
     * Convert a ByteArrayComparable to a protocol buffer Comparator
     *
     * @param comparator the ByteArrayComparable to convert
     * @return the converted protocol buffer Comparator
     */
    public static ComparatorProtos.Comparator toComparator(ByteArrayComparable comparator) {
        ComparatorProtos.Comparator.Builder builder = ComparatorProtos.Comparator.newBuilder();
        builder.setName(comparator.getClass().getName());
        builder.setSerializedComparator(HBaseZeroCopyByteString.wrap(comparator.toByteArray()));
        return builder.build();
    }

    /**
     * Convert a protocol buffer Comparator to a ByteArrayComparable
     *
     * @param proto the protocol buffer Comparator to convert
     * @return the converted ByteArrayComparable
     */
    @SuppressWarnings("unchecked")
    public static ByteArrayComparable toComparator(ComparatorProtos.Comparator proto)
            throws IOException {
        String type = proto.getName();
        String funcName = "parseFrom";
        byte [] value = proto.getSerializedComparator().toByteArray();
        try {
            Class<? extends ByteArrayComparable> c =
                    (Class<? extends ByteArrayComparable>)Class.forName(type, true, CLASS_LOADER);
            Method parseFrom = c.getMethod(funcName, byte[].class);
            if (parseFrom == null) {
                throw new IOException("Unable to locate function: " + funcName + " in type: " + type);
            }
            return (ByteArrayComparable)parseFrom.invoke(null, value);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
