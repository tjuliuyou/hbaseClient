package byone.hbase.filter;

/**
 * Created by dream on 8/5/14.
 */
import byone.hbase.protobuf.ComparatorProtos;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.exceptions.DeserializationException;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * A binary comparator which lexicographically compares against the specified
 * byte array using {@link org.apache.hadoop.hbase.util.Bytes#compareTo(byte[], byte[])}.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class BinaryComparator extends ByteArrayComparable {

    /**
     * Constructor
     * @param value value
     */
    public BinaryComparator(byte[] value) {
        super(value);
    }

    @Override
    public int compareTo(byte [] value, int offset, int length) {
        return Bytes.compareTo(this.value, 0, this.value.length, value, offset, this.value.length);
    }

    /**
     * @return The comparator serialized using pb
     */
    public byte [] toByteArray() {
        ComparatorProtos.BinaryComparator.Builder builder =
                ComparatorProtos.BinaryComparator.newBuilder();
        builder.setComparable(super.convert());
        return builder.build().toByteArray();
    }

    /**
     * @param pbBytes A pb serialized {@link BinaryComparator} instance
     * @return An instance of {@link BinaryComparator} made from <code>bytes</code>
     * @throws DeserializationException
     * @see #toByteArray
     */
    public static BinaryComparator parseFrom(final byte [] pbBytes)
            throws DeserializationException {
        ComparatorProtos.BinaryComparator proto;
        try {
            proto = ComparatorProtos.BinaryComparator.parseFrom(pbBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new DeserializationException(e);
        }
        return new BinaryComparator(proto.getComparable().getValue().toByteArray());
    }

    /**
     * @param other
     * @return true if and only if the fields of the comparator that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(ByteArrayComparable other) {
        if (other == this) return true;
        if (!(other instanceof BinaryComparator)) return false;

        return super.areSerializedFieldsEqual(other);
    }
}