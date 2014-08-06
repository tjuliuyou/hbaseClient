package byone.hbase.filter;

/**
 * Created by dream on 8/5/14.
 */
import com.google.protobuf.HBaseZeroCopyByteString;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.protobuf.generated.ComparatorProtos;
import org.apache.hadoop.hbase.util.Bytes;


/** Base class for byte array comparators */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class ByteArrayComparable implements Comparable<byte[]> {

    byte[] value;

    /**
     * Constructor.
     * @param value the value to compare against
     */
    public ByteArrayComparable(byte [] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    /**
     * @return The comparator serialized using pb
     */
    public abstract byte [] toByteArray();

    ComparatorProtos.ByteArrayComparable convert() {
        ComparatorProtos.ByteArrayComparable.Builder builder =
                ComparatorProtos.ByteArrayComparable.newBuilder();
        if (value != null) builder.setValue(HBaseZeroCopyByteString.wrap(value));
        return builder.build();
    }

    /**
     * @param pbBytes A pb serialized {@link ByteArrayComparable} instance
     * @return An instance of {@link ByteArrayComparable} made from <code>bytes</code>
     * @throws DeserializationException
     * @see #toByteArray
     */
    public static ByteArrayComparable parseFrom(final byte [] pbBytes)
            throws DeserializationException {
        throw new DeserializationException(
                "parseFrom called on base ByteArrayComparable, but should be called on derived type");
    }

    /**
     * @param other
     * @return true if and only if the fields of the comparator that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(ByteArrayComparable other) {
        if (other == this) return true;

        return Bytes.equals(this.getValue(), other.getValue());
    }

    @Override
    public int compareTo(byte [] value) {
        return compareTo(value, 0, value.length);
    }

    /**
     * Special compareTo method for subclasses, to avoid
     * copying byte[] unnecessarily.
     * @param value byte[] to compare
     * @param offset offset into value
     * @param length number of bytes to compare
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    public abstract int compareTo(byte [] value, int offset, int length);
}

