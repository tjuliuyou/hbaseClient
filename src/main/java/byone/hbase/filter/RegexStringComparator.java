package byone.hbase.filter;

/**
 * Created by dream on 8/5/14.
 */
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.util.Bytes;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Arrays;
import java.util.regex.Pattern;


@InterfaceAudience.Public
@InterfaceStability.Stable
public class RegexStringComparator extends ByteArrayComparable {

    private static final Log LOG = LogFactory.getLog(RegexStringComparator.class);

    private Charset charset = HConstants.UTF8_CHARSET;

    private Pattern pattern;

    /**
     * Constructor
     * Adds Pattern.DOTALL to the underlying Pattern
     * @param expr a valid regular expression
     */
    public RegexStringComparator(String expr) {
        this(expr, Pattern.DOTALL);
    }

    /**
     * Constructor
     * @param expr a valid regular expression
     * @param flags java.util.regex.Pattern flags
     */
    public RegexStringComparator(String expr, int flags) {
        super(Bytes.toBytes(expr));
        this.pattern = Pattern.compile(expr, flags);
    }

    /**
     * Specifies the {@link Charset} to use to convert the row key to a String.
     * <p>
     * The row key needs to be converted to a String in order to be matched
     * against the regular expression.  This method controls which charset is
     * used to do this conversion.
     * <p>
     * If the row key is made of arbitrary bytes, the charset {@code ISO-8859-1}
     * is recommended.
     * @param charset The charset to use.
     */
    public void setCharset(final Charset charset) {
        this.charset = charset;
    }

    @Override
    public int compareTo(byte[] value, int offset, int length) {
        // Use find() for subsequence match instead of matches() (full sequence
        // match) to adhere to the principle of least surprise.
        String tmp;
        if (length < value.length / 2) {
            // See HBASE-9428. Make a copy of the relevant part of the byte[],
            // or the JDK will copy the entire byte[] during String decode
            tmp = new String(Arrays.copyOfRange(value, offset, offset + length), charset);
        } else {
            tmp = new String(value, offset, length, charset);
        }
        return pattern.matcher(tmp).find() ? 0 : 1;
    }

    /**
     * @return The comparator serialized using pb
     */
    public byte [] toByteArray() {
        ByComparatorProtos.RegexStringComparator.Builder builder =
                ByComparatorProtos.RegexStringComparator.newBuilder();
        builder.setPattern(pattern.toString());
        builder.setPatternFlags(pattern.flags());
        builder.setCharset(charset.name());
        return builder.build().toByteArray();
    }

    /**
     * @param pbBytes A pb serialized {@link RegexStringComparator} instance
     * @return An instance of {@link RegexStringComparator} made from <code>bytes</code>
     * @throws DeserializationException
     * @see #toByteArray
     */
    public static RegexStringComparator parseFrom(final byte [] pbBytes)
            throws DeserializationException {
        ByComparatorProtos.RegexStringComparator proto;
        try {
            proto = ByComparatorProtos.RegexStringComparator.parseFrom(pbBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new DeserializationException(e);
        }

        RegexStringComparator comparator =
                new RegexStringComparator(proto.getPattern(), proto.getPatternFlags());
        final String charset = proto.getCharset();
        if (charset.length() > 0) {
            try {
                comparator.setCharset(Charset.forName(charset));
            } catch (IllegalCharsetNameException e) {
                LOG.error("invalid charset", e);
            }
        }
        return comparator;
    }

    /**
     * @param other
     * @return true if and only if the fields of the comparator that are serialized
     * are equal to the corresponding fields in other.  Used for testing.
     */
    boolean areSerializedFieldsEqual(ByteArrayComparable other) {
        if (other == this) return true;
        if (!(other instanceof RegexStringComparator)) return false;

        RegexStringComparator comparator = (RegexStringComparator)other;
        return super.areSerializedFieldsEqual(comparator)
                && this.pattern.toString().equals(comparator.pattern.toString())
                && this.pattern.flags() == comparator.pattern.flags()
                && this.charset.equals(comparator.charset);
    }
}
