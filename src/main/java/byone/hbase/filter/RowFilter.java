package byone.hbase.filter;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.filter.Filter;

import com.google.protobuf.InvalidProtocolBufferException;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class RowFilter extends CompareFilter {

    private boolean filterOutRow = false;

    /**
     * Constructor.
     * @param rowCompareOp the compare op for row matching
     * @param rowComparator the comparator for row matching
     */
    public RowFilter(final CompareFilter.CompareOp rowCompareOp,
                     final ByteArrayComparable rowComparator) {
        super(rowCompareOp, rowComparator);
    }

    @Override
    public void reset() {
        this.filterOutRow = false;
    }

    @Override
    public ReturnCode filterKeyValue(Cell v) {
        if(this.filterOutRow) {
            return ReturnCode.NEXT_ROW;
        }
        return ReturnCode.INCLUDE;
    }

    @Override
    public boolean filterRowKey(byte[] data, int offset, int length) {
        if(doCompare(this.compareOp, this.comparator, data, offset, length)) {
            this.filterOutRow = true;
        }
        return this.filterOutRow;
    }

    @Override
    public boolean filterRow() {
        return this.filterOutRow;
    }

    public static Filter createFilterFromArguments(ArrayList<byte []> filterArguments) {
        @SuppressWarnings("rawtypes") // for arguments
                ArrayList arguments = CompareFilter.extractArguments(filterArguments);
        CompareOp compareOp = (CompareOp)arguments.get(0);
        ByteArrayComparable comparator = (ByteArrayComparable)arguments.get(1);
        return new RowFilter(compareOp, comparator);
    }

    /**
     * @return The filter serialized using pb
     */
    public byte [] toByteArray() {
        ByFilterProtos.RowFilter.Builder builder =
                ByFilterProtos.RowFilter.newBuilder();
        builder.setCompareFilter(super.convert());
        return builder.build().toByteArray();
    }

    /**
     * @param pbBytes A pb serialized {@link RowFilter} instance
     * @return An instance of {@link RowFilter} made from <code>bytes</code>
     * @throws DeserializationException
     * @see #toByteArray
     */
    public static RowFilter parseFrom(final byte [] pbBytes)
            throws DeserializationException {
        ByFilterProtos.RowFilter proto;
        try {
            proto = ByFilterProtos.RowFilter.parseFrom(pbBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new DeserializationException(e);
        }
        final CompareOp valueCompareOp =
                CompareOp.valueOf(proto.getCompareFilter().getCompareOp().name());
        ByteArrayComparable valueComparator = null;
        try {
            if (proto.getCompareFilter().hasComparator()) {
                valueComparator = ByProtobufUtil.toComparator(proto.getCompareFilter().getComparator());
            }
        } catch (IOException ioe) {
            throw new DeserializationException(ioe);
        }
        return new RowFilter(valueCompareOp,valueComparator);
    }

    /**
     * @param //other
     * @return true if and only if the fields of the filter that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(Filter o) {
        if (o == this) return true;
        if (!(o instanceof RowFilter)) return false;

        return super.areSerializedFieldsEqual(o);
    }
}
