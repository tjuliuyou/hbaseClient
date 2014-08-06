package byone.hbase.filter;

/**
 * Created by dream on 8/5/14.
 */
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.protobuf.generated.ComparatorProtos;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * A comparator which compares against a specified byte array, but only compares
 * up to the length of this byte array. For the rest it is similar to
 * {@link BinaryComparator}.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class BinaryPrefixComparator extends ByteArrayComparable {

    /**
     * Constructor
     * @param value value
     */
    private int offset;
    public BinaryPrefixComparator(byte[] value) {
        super(value);
        this.offset = 0;
    }

    public BinaryPrefixComparator(byte[] value, int offset) {
        super(value);
        this.offset = offset;
    }
    @Override
    public int compareTo(byte [] value, int offset, int length) {
        return Bytes.compareTo(this.value, 0, this.value.length, value, offset + this.offset,
                this.value.length <= length ? this.value.length : length);
    }

    /**
     * @return The comparator serialized using pb
     */
    public byte [] toByteArray() {
        ComparatorProtos.BinaryPrefixComparator.Builder builder =
                ComparatorProtos.BinaryPrefixComparator.newBuilder();
        builder.setComparable(super.convert());
        return builder.build().toByteArray();
    }

    /**
     * @param pbBytes A pb serialized {@link BinaryPrefixComparator} instance
     * @return An instance of {@link BinaryPrefixComparator} made from <code>bytes</code>
     * @throws DeserializationException
     * @see #toByteArray
     */
    public static BinaryPrefixComparator parseFrom(final byte [] pbBytes)
            throws DeserializationException {
        ComparatorProtos.BinaryPrefixComparator proto;
        try {
            proto = ComparatorProtos.BinaryPrefixComparator.parseFrom(pbBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new DeserializationException(e);
        }
        return new BinaryPrefixComparator(proto.getComparable().getValue().toByteArray());
    }

    /**
     * @param other
     * @return true if and only if the fields of the comparator that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(ByteArrayComparable other) {
        if (other == this) return true;
        if (!(other instanceof BinaryPrefixComparator)) return false;

        return super.areSerializedFieldsEqual(other);
    }
}