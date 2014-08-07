package byone.hbase.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SkipFilter;
import org.apache.hadoop.hbase.filter.WhileMatchFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * This class allows a user to specify a filter via a string
 * The string is parsed using the methods of this class and
 * a filter object is constructed. This filter object is then wrapped
 * in a scanner object which is then returned
 * <p>
 * This class addresses the HBASE-4168 JIRA. More documentation on this
 * Filter Language can be found at: https://issues.apache.org/jira/browse/HBASE-4176
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class ByParseFilter {
    private static final Log LOG = LogFactory.getLog(ByParseFilter.class);

    private static HashMap<ByteBuffer, Integer> operatorPrecedenceHashMap;
    private static HashMap<String, String> filterHashMap;

    static {
        // Registers all the filter supported by the Filter Language
        filterHashMap = new HashMap<String, String>();
        filterHashMap.put("KeyOnlyFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "KeyOnlyFilter");
        filterHashMap.put("FirstKeyOnlyFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "FirstKeyOnlyFilter");
        filterHashMap.put("PrefixFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "PrefixFilter");
        filterHashMap.put("ColumnPrefixFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "ColumnPrefixFilter");
        filterHashMap.put("MultipleColumnPrefixFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "MultipleColumnPrefixFilter");
        filterHashMap.put("ColumnCountGetFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "ColumnCountGetFilter");
        filterHashMap.put("PageFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "PageFilter");
        filterHashMap.put("ColumnPaginationFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "ColumnPaginationFilter");
        filterHashMap.put("InclusiveStopFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "InclusiveStopFilter");
        filterHashMap.put("TimestampsFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "TimestampsFilter");
        filterHashMap.put("RowFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "RowFilter");
        filterHashMap.put("EventFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "EventFilter");
        filterHashMap.put("FamilyFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "FamilyFilter");
        filterHashMap.put("QualifierFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "QualifierFilter");
        filterHashMap.put("ValueFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "ValueFilter");
        filterHashMap.put("ColumnRangeFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "ColumnRangeFilter");
        filterHashMap.put("SingleColumnValueFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "SingleColumnValueFilter");
        filterHashMap.put("SingleColumnNumberFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "SingleColumnNumberFilter");
        filterHashMap.put("SingleColumnValueExcludeFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "SingleColumnValueExcludeFilter");
        filterHashMap.put("DependentColumnFilter", ByParseConstants.FILTER_PACKAGE + "." +
                "DependentColumnFilter");


        // Creates the operatorPrecedenceHashMap
        operatorPrecedenceHashMap = new HashMap<ByteBuffer, Integer>();
        operatorPrecedenceHashMap.put(ByParseConstants.SKIP_BUFFER, 1);
        operatorPrecedenceHashMap.put(ByParseConstants.WHILE_BUFFER, 1);
        operatorPrecedenceHashMap.put(ByParseConstants.AND_BUFFER, 2);
        operatorPrecedenceHashMap.put(ByParseConstants.OR_BUFFER, 3);
    }

    /**
     * Parses the filterString and constructs a filter using it
     * <p>
     * @param filterString filter string given by the user
     * @return filter object we constructed
     */
    public Filter parseFilterString (String filterString)
            throws CharacterCodingException {
        return parseFilterString(Bytes.toBytes(filterString));
    }

    /**
     * Parses the filterString and constructs a filter using it
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @return filter object we constructed
     */
    public Filter parseFilterString (byte [] filterStringAsByteArray)
            throws CharacterCodingException {
        // stack for the operators and parenthesis
        Stack<ByteBuffer> operatorStack = new Stack<ByteBuffer>();
        // stack for the filter objects
        Stack <Filter> filterStack = new Stack<Filter>();

        Filter filter = null;
        for (int i=0; i<filterStringAsByteArray.length; i++) {
            if (filterStringAsByteArray[i] == ByParseConstants.LPAREN) {
                // LPAREN found
                operatorStack.push(ByParseConstants.LPAREN_BUFFER);
            } else if (filterStringAsByteArray[i] == ByParseConstants.WHITESPACE ||
                    filterStringAsByteArray[i] == ByParseConstants.TAB) {
                // WHITESPACE or TAB found
                continue;
            } else if (checkForOr(filterStringAsByteArray, i)) {
                // OR found
                i += ByParseConstants.OR_ARRAY.length - 1;
                reduce(operatorStack, filterStack, ByParseConstants.OR_BUFFER);
                operatorStack.push(ByParseConstants.OR_BUFFER);
            } else if (checkForAnd(filterStringAsByteArray, i)) {
                // AND found
                i += ByParseConstants.AND_ARRAY.length - 1;
                reduce(operatorStack, filterStack, ByParseConstants.AND_BUFFER);
                operatorStack.push(ByParseConstants.AND_BUFFER);
            } else if (checkForSkip(filterStringAsByteArray, i)) {
                // SKIP found
                i += ByParseConstants.SKIP_ARRAY.length - 1;
                reduce(operatorStack, filterStack, ByParseConstants.SKIP_BUFFER);
                operatorStack.push(ByParseConstants.SKIP_BUFFER);
            } else if (checkForWhile(filterStringAsByteArray, i)) {
                // WHILE found
                i += ByParseConstants.WHILE_ARRAY.length - 1;
                reduce(operatorStack, filterStack, ByParseConstants.WHILE_BUFFER);
                operatorStack.push(ByParseConstants.WHILE_BUFFER);
            } else if (filterStringAsByteArray[i] == ByParseConstants.RPAREN) {
                // RPAREN found
                if (operatorStack.empty()) {
                    throw new IllegalArgumentException("Mismatched parenthesis");
                }
                ByteBuffer argumentOnTopOfStack = operatorStack.peek();
                while (!(argumentOnTopOfStack.equals(ByParseConstants.LPAREN_BUFFER))) {
                    filterStack.push(popArguments(operatorStack, filterStack));
                    if (operatorStack.empty()) {
                        throw new IllegalArgumentException("Mismatched parenthesis");
                    }
                    argumentOnTopOfStack = operatorStack.pop();
                }
            } else {
                // SimpleFilterExpression found
                byte [] filterSimpleExpression = extractFilterSimpleExpression(filterStringAsByteArray, i);
                i+= (filterSimpleExpression.length - 1);
                filter = parseSimpleFilterExpression(filterSimpleExpression);
                filterStack.push(filter);
            }
        }

        // Finished parsing filterString
        while (!operatorStack.empty()) {
            filterStack.push(popArguments(operatorStack, filterStack));
        }
        filter = filterStack.pop();
        if (!filterStack.empty()) {
            throw new IllegalArgumentException("Incorrect Filter String");
        }
        return filter;
    }

    /**
     * Extracts a simple filter expression from the filter string given by the user
     * <p>
     * A simpleFilterExpression is of the form: FilterName('arg', 'arg', 'arg')
     * The user given filter string can have many simpleFilterExpressions combined
     * using operators.
     * <p>
     * This function extracts a simpleFilterExpression from the
     * larger filterString given the start offset of the simpler expression
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @param filterExpressionStartOffset start index of the simple filter expression
     * @return byte array containing the simple filter expression
     */
    public byte [] extractFilterSimpleExpression (byte [] filterStringAsByteArray,
                                                  int filterExpressionStartOffset)
            throws CharacterCodingException {
        int quoteCount = 0;
        for (int i=filterExpressionStartOffset; i<filterStringAsByteArray.length; i++) {
            if (filterStringAsByteArray[i] == ByParseConstants.SINGLE_QUOTE) {
                if (isQuoteUnescaped(filterStringAsByteArray, i)) {
                    quoteCount ++;
                } else {
                    // To skip the next quote that has been escaped
                    i++;
                }
            }
            if (filterStringAsByteArray[i] == ByParseConstants.RPAREN && (quoteCount %2 ) == 0) {
                byte [] filterSimpleExpression = new byte [i - filterExpressionStartOffset + 1];
                Bytes.putBytes(filterSimpleExpression, 0, filterStringAsByteArray,
                        filterExpressionStartOffset, i-filterExpressionStartOffset + 1);
                return filterSimpleExpression;
            }
        }
        throw new IllegalArgumentException("Incorrect Filter String");
    }

    /**
     * Constructs a filter object given a simple filter expression
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @return filter object we constructed
     */
    public Filter parseSimpleFilterExpression (byte [] filterStringAsByteArray)
            throws CharacterCodingException {

        String filterName = Bytes.toString(getFilterName(filterStringAsByteArray));
        ArrayList<byte []> filterArguments = getFilterArguments(filterStringAsByteArray);
        if (!filterHashMap.containsKey(filterName)) {
            throw new IllegalArgumentException("Filter Name " + filterName + " not supported");
        }
        try {
            filterName = filterHashMap.get(filterName);
            Class<?> c = Class.forName(filterName);
            Class<?>[] argTypes = new Class [] {ArrayList.class};
            Method m = c.getDeclaredMethod("createFilterFromArguments", argTypes);
            return (Filter) m.invoke(null,filterArguments);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Incorrect filter string " +
                new String(filterStringAsByteArray));
    }

    /**
     * Returns the filter name given a simple filter expression
     * <p>
     * @param filterStringAsByteArray a simple filter expression
     * @return name of filter in the simple filter expression
     */
    public static byte [] getFilterName (byte [] filterStringAsByteArray) {
        int filterNameStartIndex = 0;
        int filterNameEndIndex = 0;

        for (int i=filterNameStartIndex; i<filterStringAsByteArray.length; i++) {
            if (filterStringAsByteArray[i] == ByParseConstants.LPAREN ||
                    filterStringAsByteArray[i] == ByParseConstants.WHITESPACE) {
                filterNameEndIndex = i;
                break;
            }
        }

        if (filterNameEndIndex == 0) {
            throw new IllegalArgumentException("Incorrect Filter Name");
        }

        byte [] filterName = new byte[filterNameEndIndex - filterNameStartIndex];
        Bytes.putBytes(filterName, 0, filterStringAsByteArray, 0,
                filterNameEndIndex - filterNameStartIndex);
        return filterName;
    }

    /**
     * Returns the arguments of the filter from the filter string
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @return an ArrayList containing the arguments of the filter in the filter string
     */
    public static ArrayList<byte []> getFilterArguments (byte [] filterStringAsByteArray) {
        int argumentListStartIndex = KeyValue.getDelimiter(filterStringAsByteArray, 0,
                filterStringAsByteArray.length,
                ByParseConstants.LPAREN);
        if (argumentListStartIndex == -1) {
            throw new IllegalArgumentException("Incorrect argument list");
        }

        int argumentStartIndex = 0;
        int argumentEndIndex = 0;
        ArrayList<byte []> filterArguments = new ArrayList<byte []>();

        for (int i = argumentListStartIndex + 1; i<filterStringAsByteArray.length; i++) {

            if (filterStringAsByteArray[i] == ByParseConstants.WHITESPACE ||
                    filterStringAsByteArray[i] == ByParseConstants.COMMA ||
                    filterStringAsByteArray[i] == ByParseConstants.RPAREN) {
                continue;
            }

            // The argument is in single quotes - for example 'prefix'
            if (filterStringAsByteArray[i] == ByParseConstants.SINGLE_QUOTE) {
                argumentStartIndex = i;
                for (int j = argumentStartIndex+1; j < filterStringAsByteArray.length; j++) {
                    if (filterStringAsByteArray[j] == ByParseConstants.SINGLE_QUOTE) {
                        if (isQuoteUnescaped(filterStringAsByteArray,j)) {
                            argumentEndIndex = j;
                            i = j+1;
                            byte [] filterArgument = createUnescapdArgument(filterStringAsByteArray,
                                    argumentStartIndex, argumentEndIndex);
                            filterArguments.add(filterArgument);
                            break;
                        } else {
                            // To jump over the second escaped quote
                            j++;
                        }
                    } else if (j == filterStringAsByteArray.length - 1) {
                        throw new IllegalArgumentException("Incorrect argument list");
                    }
                }
            } else {
                // The argument is an integer, boolean, comparison operator like <, >, != etc
                argumentStartIndex = i;
                for (int j = argumentStartIndex; j < filterStringAsByteArray.length; j++) {
                    if (filterStringAsByteArray[j] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[j] == ByParseConstants.COMMA ||
                            filterStringAsByteArray[j] == ByParseConstants.RPAREN) {
                        argumentEndIndex = j - 1;
                        i = j;
                        byte [] filterArgument = new byte [argumentEndIndex - argumentStartIndex + 1];
                        Bytes.putBytes(filterArgument, 0, filterStringAsByteArray,
                                argumentStartIndex, argumentEndIndex - argumentStartIndex + 1);
                        filterArguments.add(filterArgument);
                        break;
                    } else if (j == filterStringAsByteArray.length - 1) {
                        throw new IllegalArgumentException("Incorrect argument list");
                    }
                }
            }
        }
        return filterArguments;
    }

    /**
     * This function is called while parsing the filterString and an operator is parsed
     * <p>
     * @param operatorStack the stack containing the operators and parenthesis
     * @param filterStack the stack containing the filters
     * @param operator the operator found while parsing the filterString
     */
    public void reduce(Stack<ByteBuffer> operatorStack,
                       Stack<Filter> filterStack,
                       ByteBuffer operator) {
        while (!operatorStack.empty() &&
                !(ByParseConstants.LPAREN_BUFFER.equals(operatorStack.peek())) &&
                hasHigherPriority(operatorStack.peek(), operator)) {
            filterStack.push(popArguments(operatorStack, filterStack));
        }
    }

    /**
     * Pops an argument from the operator stack and the number of arguments required by the operator
     * from the filterStack and evaluates them
     * <p>
     * @param operatorStack the stack containing the operators
     * @param filterStack the stack containing the filters
     * @return the evaluated filter
     */
    public static Filter popArguments (Stack<ByteBuffer> operatorStack, Stack <Filter> filterStack) {
        ByteBuffer argumentOnTopOfStack = operatorStack.peek();

        if (argumentOnTopOfStack.equals(ByParseConstants.OR_BUFFER)) {
            // The top of the stack is an OR
            try {
                ArrayList<Filter> listOfFilters = new ArrayList<Filter>();
                while (!operatorStack.empty() && operatorStack.peek().equals(ByParseConstants.OR_BUFFER)) {
                    Filter filter = filterStack.pop();
                    listOfFilters.add(0, filter);
                    operatorStack.pop();
                }
                Filter filter = filterStack.pop();
                listOfFilters.add(0, filter);
                Filter orFilter = new FilterList(FilterList.Operator.MUST_PASS_ONE, listOfFilters);
                return orFilter;
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException("Incorrect input string - an OR needs two filters");
            }

        } else if (argumentOnTopOfStack.equals(ByParseConstants.AND_BUFFER)) {
            // The top of the stack is an AND
            try {
                ArrayList<Filter> listOfFilters = new ArrayList<Filter>();
                while (!operatorStack.empty() && operatorStack.peek().equals(ByParseConstants.AND_BUFFER)) {
                    Filter filter = filterStack.pop();
                    listOfFilters.add(0, filter);
                    operatorStack.pop();
                }
                Filter filter = filterStack.pop();
                listOfFilters.add(0, filter);
                Filter andFilter = new FilterList(FilterList.Operator.MUST_PASS_ALL, listOfFilters);
                return andFilter;
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException("Incorrect input string - an AND needs two filters");
            }

        } else if (argumentOnTopOfStack.equals(ByParseConstants.SKIP_BUFFER)) {
            // The top of the stack is a SKIP
            try {
                Filter wrappedFilter = filterStack.pop();
                Filter skipFilter = new SkipFilter(wrappedFilter);
                operatorStack.pop();
                return skipFilter;
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException("Incorrect input string - a SKIP wraps a filter");
            }

        } else if (argumentOnTopOfStack.equals(ByParseConstants.WHILE_BUFFER)) {
            // The top of the stack is a WHILE
            try {
                Filter wrappedFilter = filterStack.pop();
                Filter whileMatchFilter = new WhileMatchFilter(wrappedFilter);
                operatorStack.pop();
                return whileMatchFilter;
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException("Incorrect input string - a WHILE wraps a filter");
            }

        } else if (argumentOnTopOfStack.equals(ByParseConstants.LPAREN_BUFFER)) {
            // The top of the stack is a LPAREN
            try {
                Filter filter  = filterStack.pop();
                operatorStack.pop();
                return filter;
            } catch (EmptyStackException e) {
                throw new IllegalArgumentException("Incorrect Filter String");
            }

        } else {
            throw new IllegalArgumentException("Incorrect arguments on operatorStack");
        }
    }

    /**
     * Returns which operator has higher precedence
     * <p>
     * If a has higher precedence than b, it returns true
     * If they have the same precedence, it returns false
     */
    public boolean hasHigherPriority(ByteBuffer a, ByteBuffer b) {
        if ((operatorPrecedenceHashMap.get(a) - operatorPrecedenceHashMap.get(b)) < 0) {
            return true;
        }
        return false;
    }

    /**
     * Removes the single quote escaping a single quote - thus it returns an unescaped argument
     * <p>
     * @param filterStringAsByteArray filter string given by user
     * @param argumentStartIndex start index of the argument
     * @param argumentEndIndex end index of the argument
     * @return returns an unescaped argument
     */
    public static byte [] createUnescapdArgument (byte [] filterStringAsByteArray,
                                                  int argumentStartIndex, int argumentEndIndex) {
        int unescapedArgumentLength = 2;
        for (int i = argumentStartIndex + 1; i <= argumentEndIndex - 1; i++) {
            unescapedArgumentLength ++;
            if (filterStringAsByteArray[i] == ByParseConstants.SINGLE_QUOTE &&
                    i != (argumentEndIndex - 1) &&
                    filterStringAsByteArray[i+1] == ByParseConstants.SINGLE_QUOTE) {
                i++;
                continue;
            }
        }

        byte [] unescapedArgument = new byte [unescapedArgumentLength];
        int count = 1;
        unescapedArgument[0] = '\'';
        for (int i = argumentStartIndex + 1; i <= argumentEndIndex - 1; i++) {
            if (filterStringAsByteArray [i] == ByParseConstants.SINGLE_QUOTE &&
                    i != (argumentEndIndex - 1) &&
                    filterStringAsByteArray [i+1] == ByParseConstants.SINGLE_QUOTE) {
                unescapedArgument[count++] = filterStringAsByteArray [i+1];
                i++;
            }
            else {
                unescapedArgument[count++] = filterStringAsByteArray [i];
            }
        }
        unescapedArgument[unescapedArgumentLength - 1] = '\'';
        return unescapedArgument;
    }

    /**
     * Checks if the current index of filter string we are on is the beginning of the keyword 'OR'
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @param indexOfOr index at which an 'O' was read
     * @return true if the keyword 'OR' is at the current index
     */
    public static boolean checkForOr (byte [] filterStringAsByteArray, int indexOfOr)
            throws CharacterCodingException, ArrayIndexOutOfBoundsException {

        try {
            if (filterStringAsByteArray[indexOfOr] == ByParseConstants.O &&
                    filterStringAsByteArray[indexOfOr+1] == ByParseConstants.R &&
                    (filterStringAsByteArray[indexOfOr-1] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfOr-1] == ByParseConstants.RPAREN) &&
                    (filterStringAsByteArray[indexOfOr+2] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfOr+2] == ByParseConstants.LPAREN)) {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Checks if the current index of filter string we are on is the beginning of the keyword 'AND'
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @param indexOfAnd index at which an 'A' was read
     * @return true if the keyword 'AND' is at the current index
     */
    public static boolean checkForAnd (byte [] filterStringAsByteArray, int indexOfAnd)
            throws CharacterCodingException {

        try {
            if (filterStringAsByteArray[indexOfAnd] == ByParseConstants.A &&
                    filterStringAsByteArray[indexOfAnd+1] == ByParseConstants.N &&
                    filterStringAsByteArray[indexOfAnd+2] == ByParseConstants.D &&
                    (filterStringAsByteArray[indexOfAnd-1] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfAnd-1] == ByParseConstants.RPAREN) &&
                    (filterStringAsByteArray[indexOfAnd+3] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfAnd+3] == ByParseConstants.LPAREN)) {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Checks if the current index of filter string we are on is the beginning of the keyword 'SKIP'
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @param indexOfSkip index at which an 'S' was read
     * @return true if the keyword 'SKIP' is at the current index
     */
    public static boolean checkForSkip (byte [] filterStringAsByteArray, int indexOfSkip)
            throws CharacterCodingException {

        try {
            if (filterStringAsByteArray[indexOfSkip] == ByParseConstants.S &&
                    filterStringAsByteArray[indexOfSkip+1] == ByParseConstants.K &&
                    filterStringAsByteArray[indexOfSkip+2] == ByParseConstants.I &&
                    filterStringAsByteArray[indexOfSkip+3] == ByParseConstants.P &&
                    (indexOfSkip == 0 ||
                            filterStringAsByteArray[indexOfSkip-1] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfSkip-1] == ByParseConstants.RPAREN ||
                            filterStringAsByteArray[indexOfSkip-1] == ByParseConstants.LPAREN) &&
                    (filterStringAsByteArray[indexOfSkip+4] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfSkip+4] == ByParseConstants.LPAREN)) {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Checks if the current index of filter string we are on is the beginning of the keyword 'WHILE'
     * <p>
     * @param filterStringAsByteArray filter string given by the user
     * @param indexOfWhile index at which an 'W' was read
     * @return true if the keyword 'WHILE' is at the current index
     */
    public static boolean checkForWhile (byte [] filterStringAsByteArray, int indexOfWhile)
            throws CharacterCodingException {

        try {
            if (filterStringAsByteArray[indexOfWhile] == ByParseConstants.W &&
                    filterStringAsByteArray[indexOfWhile+1] == ByParseConstants.H &&
                    filterStringAsByteArray[indexOfWhile+2] == ByParseConstants.I &&
                    filterStringAsByteArray[indexOfWhile+3] == ByParseConstants.L &&
                    filterStringAsByteArray[indexOfWhile+4] == ByParseConstants.E &&
                    (indexOfWhile == 0 || filterStringAsByteArray[indexOfWhile-1] == ByParseConstants.WHITESPACE
                            || filterStringAsByteArray[indexOfWhile-1] == ByParseConstants.RPAREN ||
                            filterStringAsByteArray[indexOfWhile-1] == ByParseConstants.LPAREN) &&
                    (filterStringAsByteArray[indexOfWhile+5] == ByParseConstants.WHITESPACE ||
                            filterStringAsByteArray[indexOfWhile+5] == ByParseConstants.LPAREN)) {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Returns a boolean indicating whether the quote was escaped or not
     * <p>
     * @param array byte array in which the quote was found
     * @param quoteIndex index of the single quote
     * @return returns true if the quote was unescaped
     */
    public static boolean isQuoteUnescaped (byte [] array, int quoteIndex) {
        if (array == null) {
            throw new IllegalArgumentException("isQuoteUnescaped called with a null array");
        }

        if (quoteIndex == array.length - 1 || array[quoteIndex+1] != ByParseConstants.SINGLE_QUOTE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Takes a quoted byte array and converts it into an unquoted byte array
     * For example: given a byte array representing 'abc', it returns a
     * byte array representing abc
     * <p>
     * @param quotedByteArray the quoted byte array
     * @return Unquoted byte array
     */
    public static byte [] removeQuotesFromByteArray (byte [] quotedByteArray) {
        if (quotedByteArray == null ||
                quotedByteArray.length < 2 ||
                quotedByteArray[0] != ByParseConstants.SINGLE_QUOTE ||
                quotedByteArray[quotedByteArray.length - 1] != ByParseConstants.SINGLE_QUOTE) {
            throw new IllegalArgumentException("removeQuotesFromByteArray needs a quoted byte array");
        } else {
            byte [] targetString = new byte [quotedByteArray.length - 2];
            Bytes.putBytes(targetString, 0, quotedByteArray, 1, quotedByteArray.length - 2);
            return targetString;
        }
    }

    /**
     * Converts an int expressed in a byte array to an actual int
     * <p>
     * This doesn't use Bytes.toInt because that assumes
     * that there will be {@link Bytes#SIZEOF_INT} bytes available.
     * <p>
     * @param numberAsByteArray the int value expressed as a byte array
     * @return the int value
     */
    public static int convertByteArrayToInt (byte [] numberAsByteArray) {

        long tempResult = ByParseFilter.convertByteArrayToLong(numberAsByteArray);

        if (tempResult > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Integer Argument too large");
        } else if (tempResult < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Integer Argument too small");
        }

        int result = (int) tempResult;
        return result;
    }

    /**
     * Converts a long expressed in a byte array to an actual long
     * <p>
     * This doesn't use Bytes.toLong because that assumes
     * that there will be {@link Bytes#SIZEOF_INT} bytes available.
     * <p>
     * @param numberAsByteArray the long value expressed as a byte array
     * @return the long value
     */
    public static long convertByteArrayToLong (byte [] numberAsByteArray) {
        if (numberAsByteArray == null) {
            throw new IllegalArgumentException("convertByteArrayToLong called with a null array");
        }

        int i = 0;
        long result = 0;
        boolean isNegative = false;

        if (numberAsByteArray[i] == ByParseConstants.MINUS_SIGN) {
            i++;
            isNegative = true;
        }

        while (i != numberAsByteArray.length) {
            if (numberAsByteArray[i] < ByParseConstants.ZERO ||
                    numberAsByteArray[i] > ByParseConstants.NINE) {
                throw new IllegalArgumentException("Byte Array should only contain digits");
            }
            result = result*10 + (numberAsByteArray[i] - ByParseConstants.ZERO);
            if (result < 0) {
                throw new IllegalArgumentException("Long Argument too large");
            }
            i++;
        }

        if (isNegative) {
            return -result;
        } else {
            return result;
        }
    }

    /**
     * Converts a boolean expressed in a byte array to an actual boolean
     *<p>
     * This doesn't used Bytes.toBoolean because Bytes.toBoolean(byte [])
     * assumes that 1 stands for true and 0 for false.
     * Here, the byte array representing "true" and "false" is parsed
     * <p>
     * @param booleanAsByteArray the boolean value expressed as a byte array
     * @return the boolean value
     */
    public static boolean convertByteArrayToBoolean (byte [] booleanAsByteArray) {
        if (booleanAsByteArray == null) {
            throw new IllegalArgumentException("convertByteArrayToBoolean called with a null array");
        }

        if (booleanAsByteArray.length == 4 &&
                (booleanAsByteArray[0] == 't' || booleanAsByteArray[0] == 'T') &&
                (booleanAsByteArray[1] == 'r' || booleanAsByteArray[1] == 'R') &&
                (booleanAsByteArray[2] == 'u' || booleanAsByteArray[2] == 'U') &&
                (booleanAsByteArray[3] == 'e' || booleanAsByteArray[3] == 'E')) {
            return true;
        }
        else if (booleanAsByteArray.length == 5 &&
                (booleanAsByteArray[0] == 'f' || booleanAsByteArray[0] == 'F') &&
                (booleanAsByteArray[1] == 'a' || booleanAsByteArray[1] == 'A') &&
                (booleanAsByteArray[2] == 'l' || booleanAsByteArray[2] == 'L') &&
                (booleanAsByteArray[3] == 's' || booleanAsByteArray[3] == 'S') &&
                (booleanAsByteArray[4] == 'e' || booleanAsByteArray[4] == 'E')) {
            return false;
        }
        else {
            throw new IllegalArgumentException("Incorrect Boolean Expression");
        }
    }

    /**
     * Takes a compareOperator symbol as a byte array and returns the corresponding CompareOperator
     * <p>
     * @param compareOpAsByteArray the comparatorOperator symbol as a byte array
     * @return the Compare Operator
     */
    public static CompareFilter.CompareOp createCompareOp (byte [] compareOpAsByteArray) {
        ByteBuffer compareOp = ByteBuffer.wrap(compareOpAsByteArray);
        if (compareOp.equals(ByParseConstants.LESS_THAN_BUFFER))
            return CompareFilter.CompareOp.LESS;
        else if (compareOp.equals(ByParseConstants.LESS_THAN_OR_EQUAL_TO_BUFFER))
            return CompareFilter.CompareOp.LESS_OR_EQUAL;
        else if (compareOp.equals(ByParseConstants.GREATER_THAN_BUFFER))
            return CompareFilter.CompareOp.GREATER;
        else if (compareOp.equals(ByParseConstants.GREATER_THAN_OR_EQUAL_TO_BUFFER))
            return CompareFilter.CompareOp.GREATER_OR_EQUAL;
        else if (compareOp.equals(ByParseConstants.NOT_EQUAL_TO_BUFFER))
            return CompareFilter.CompareOp.NOT_EQUAL;
        else if (compareOp.equals(ByParseConstants.EQUAL_TO_BUFFER))
            return CompareFilter.CompareOp.EQUAL;
        else
            throw new IllegalArgumentException("Invalid compare operator");
    }

    /**
     * Parses a comparator of the form comparatorType:comparatorValue form and returns a comparator
     * <p>
     * @param comparator the comparator in the form comparatorType:comparatorValue
     * @return the parsed comparator
     */
    public static ByteArrayComparable createComparator (byte [] comparator) {
        if (comparator == null)
            throw new IllegalArgumentException("Incorrect Comparator");
        byte [][] parsedComparator = ByParseFilter.parseComparator(comparator);
        byte [] comparatorType = parsedComparator[0];
        byte [] comparatorValue = parsedComparator[1];


        if (Bytes.equals(comparatorType, ByParseConstants.binaryType))
            return new BinaryComparator(comparatorValue);
        else if (Bytes.equals(comparatorType, ByParseConstants.binaryPrefixType))
            return new BinaryPrefixComparator(comparatorValue);
        else if (Bytes.equals(comparatorType, ByParseConstants.regexStringType))
            return new RegexStringComparator(new String(comparatorValue));
        else if (Bytes.equals(comparatorType, ByParseConstants.substringType))
            return new SubstringComparator(new String(comparatorValue));
        else if (Bytes.equals(comparatorType, ByParseConstants.numberType))
            return new NumberComparator(comparatorValue);
        else
            throw new IllegalArgumentException("Incorrect comparatorType");
    }

    /**
     * Splits a column in comparatorType:comparatorValue form into separate byte arrays
     * <p>
     * @param comparator the comparator
     * @return the parsed arguments of the comparator as a 2D byte array
     */
    public static byte [][] parseComparator (byte [] comparator) {
        final int index = KeyValue.getDelimiter(comparator, 0, comparator.length, ByParseConstants.COLON);
        if (index == -1) {
            throw new IllegalArgumentException("Incorrect comparator");
        }

        byte [][] result = new byte [2][0];
        result[0] = new byte [index];
        System.arraycopy(comparator, 0, result[0], 0, index);

        final int len = comparator.length - (index + 1);
        result[1] = new byte[len];
        System.arraycopy(comparator, index + 1, result[1], 0, len);

        return result;
    }

    /**
     * Return a Set of filters supported by the Filter Language
     */
    public Set<String> getSupportedFilters () {
        return filterHashMap.keySet();
    }

    /**
     * Returns all known filters
     * @return an unmodifiable map of filters
     */
    public static Map<String, String> getAllFilters() {
        return Collections.unmodifiableMap(filterHashMap);
    }

    /**
     * Register a new filter with the parser.  If the filter is already registered,
     * an IllegalArgumentException will be thrown.
     *
     * @param name a name for the filter
     * @param filterClass fully qualified class name
     */
    public static void registerFilter(String name, String filterClass) {
        if(LOG.isInfoEnabled())
            LOG.info("Registering new filter " + name);

        filterHashMap.put(name, filterClass);
    }
}

