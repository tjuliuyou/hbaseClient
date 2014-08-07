package byone.hbase.filter;

import byone.hbase.protobuf.ComparatorProtos;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by dream on 14-8-7.
 * A binary comparator which lexicographically compares against the specified
 * byte array using
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class NumberComparator extends ByteArrayComparable {

    /**
     * Constructor
     * @param value value
     */
    public NumberComparator(byte[] value) {
        super(value);
    }


    @Override
    public int compareTo(byte [] value, int offset, int length) {
        if(length > this.value.length)
            return 1;
        else if (length < this.value.length)
            return -1;
        else
        return Bytes.compareTo(this.value, 0, this.value.length, value, offset, this.value.length);
    }

    /**
     * @return The comparator serialized using pb
     */
    public byte [] toByteArray() {
        ComparatorProtos.NumberComparator.Builder builder =
                ComparatorProtos.NumberComparator.newBuilder();
        builder.setComparable(super.convert());
        return builder.build().toByteArray();
    }

    /**
     * @param pbBytes A pb serialized {@link NumberComparator} instance
     * @return An instance of {@link NumberComparator} made from <code>bytes</code>
     * @throws org.apache.hadoop.hbase.exceptions.DeserializationException
     * @see #toByteArray
     */
    public static NumberComparator parseFrom(final byte [] pbBytes)
            throws DeserializationException {
        ComparatorProtos.NumberComparator proto;
        try {
            proto = ComparatorProtos.NumberComparator.parseFrom(pbBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new DeserializationException(e);
        }
        return new NumberComparator(proto.getComparable().getValue().toByteArray());
    }

    /**
     * @param other
     * @return true if and only if the fields of the comparator that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(ByteArrayComparable other) {
        if (other == this) return true;
        if (!(other instanceof NumberComparator)) return false;

        return super.areSerializedFieldsEqual(other);
    }
}