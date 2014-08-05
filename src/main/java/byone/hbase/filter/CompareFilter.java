package byone.hbase.filter;

import com.google.common.base.Preconditions;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterBase;
import org.apache.hadoop.hbase.protobuf.generated.FilterProtos;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos.CompareType;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;

/**
 * Created by dream on 8/5/14.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class CompareFilter extends FilterBase {

    /** Comparison operators. */
    @InterfaceAudience.Public
    @InterfaceStability.Stable
    public enum CompareOp {
        /** less than */
        LESS,
        /** less than or equal to */
        LESS_OR_EQUAL,
        /** equals */
        EQUAL,
        /** not equal */
        NOT_EQUAL,
        /** greater than or equal to */
        GREATER_OR_EQUAL,
        /** greater than */
        GREATER,
        /** no operation */
        NO_OP,
    }

    protected CompareOp compareOp;
    protected ByteArrayComparable comparator;

    /**
     * Constructor.
     * @param compareOp the compare op for row matching
     * @param comparator the comparator for row matching
     */
    public CompareFilter(final CompareOp compareOp,
                         final ByteArrayComparable comparator) {
        this.compareOp = compareOp;
        this.comparator = comparator;
    }

    /**
     * @return operator
     */
    public CompareOp getOperator() {
        return compareOp;
    }

    /**
     * @return the comparator
     */
    public ByteArrayComparable getComparator() {
        return comparator;
    }

    protected boolean doCompare(final CompareOp compareOp,
                                final ByteArrayComparable comparator, final byte [] data,
                                final int offset, final int length) {
        if (compareOp == CompareOp.NO_OP) {
            return true;
        }
        int compareResult = comparator.compareTo(data, offset, length);
        switch (compareOp) {
            case LESS:
                return compareResult <= 0;
            case LESS_OR_EQUAL:
                return compareResult < 0;
            case EQUAL:
                return compareResult != 0;
            case NOT_EQUAL:
                return compareResult == 0;
            case GREATER_OR_EQUAL:
                return compareResult > 0;
            case GREATER:
                return compareResult >= 0;
            default:
                throw new RuntimeException("Unknown Compare op " +
                        compareOp.name());
        }
    }

    // returns an array of heterogeneous objects
    public static ArrayList<Object> extractArguments(ArrayList<byte []> filterArguments) {
        Preconditions.checkArgument(filterArguments.size() == 2,
                "Expected 2 but got: %s", filterArguments.size());
        CompareOp compareOp = ParseFilter.createCompareOp(filterArguments.get(0));
        ByteArrayComparable comparator = ParseFilter.createComparator(
                ParseFilter.removeQuotesFromByteArray(filterArguments.get(1)));

        if (comparator instanceof RegexStringComparator ||
                comparator instanceof SubstringComparator) {
            if (compareOp != CompareOp.EQUAL &&
                    compareOp != CompareOp.NOT_EQUAL) {
                throw new IllegalArgumentException ("A regexstring comparator and substring comparator" +
                        " can only be used with EQUAL and NOT_EQUAL");
            }
        }
        ArrayList<Object> arguments = new ArrayList<Object>();
        arguments.add(compareOp);
        arguments.add(comparator);
        return arguments;
    }

    /**
     * @return A pb instance to represent this instance.
     */
    FilterProtos.CompareFilter convert() {
        FilterProtos.CompareFilter.Builder builder =
                FilterProtos.CompareFilter.newBuilder();
        HBaseProtos.CompareType compareOp = CompareType.valueOf(this.compareOp.name());
        builder.setCompareOp(compareOp);
        if (this.comparator != null) builder.setComparator(ProtobufUtil.toComparator(this.comparator));
        return builder.build();
    }

    /**
     *
     * @param o
     * @return true if and only if the fields of the filter that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(Filter o) {
        if (o == this) return true;
        if (!(o instanceof CompareFilter)) return false;

        CompareFilter other = (CompareFilter)o;
        return this.getOperator().equals(other.getOperator()) &&
                (this.getComparator() == other.getComparator()
                        || this.getComparator().areSerializedFieldsEqual(other.getComparator()));
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s)",
                this.getClass().getSimpleName(),
                this.compareOp.name(),
                Bytes.toStringBinary(this.comparator.getValue()));
    }
}
