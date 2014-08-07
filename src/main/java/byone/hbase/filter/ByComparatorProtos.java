package byone.hbase.filter;

/**
 * Created by dream on 14-8-7.
 */

public final class ByComparatorProtos {
    private ByComparatorProtos() {}
    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
    }
    public interface ComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // required string name = 1;
        /**
         * <code>required string name = 1;</code>
         */
        boolean hasName();
        /**
         * <code>required string name = 1;</code>
         */
        java.lang.String getName();
        /**
         * <code>required string name = 1;</code>
         */
        com.google.protobuf.ByteString
        getNameBytes();

        // optional bytes serialized_comparator = 2;
        /**
         * <code>optional bytes serialized_comparator = 2;</code>
         */
        boolean hasSerializedComparator();
        /**
         * <code>optional bytes serialized_comparator = 2;</code>
         */
        com.google.protobuf.ByteString getSerializedComparator();
    }
    /**
     * Protobuf type {@code Comparator}
     */
    public static final class Comparator extends
            com.google.protobuf.GeneratedMessage
            implements ComparatorOrBuilder {
        // Use Comparator.newBuilder() to construct.
        private Comparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private Comparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final Comparator defaultInstance;
        public static Comparator getDefaultInstance() {
            return defaultInstance;
        }

        public Comparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private Comparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            bitField0_ |= 0x00000001;
                            name_ = input.readBytes();
                            break;
                        }
                        case 18: {
                            bitField0_ |= 0x00000002;
                            serializedComparator_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_Comparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_Comparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.Comparator.class, byone.hbase.filter.ByComparatorProtos.Comparator.Builder.class);
        }

        public static com.google.protobuf.Parser<Comparator> PARSER =
                new com.google.protobuf.AbstractParser<Comparator>() {
                    public Comparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new Comparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<Comparator> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // required string name = 1;
        public static final int NAME_FIELD_NUMBER = 1;
        private java.lang.Object name_;
        /**
         * <code>required string name = 1;</code>
         */
        public boolean hasName() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required string name = 1;</code>
         */
        public java.lang.String getName() {
            java.lang.Object ref = name_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    name_ = s;
                }
                return s;
            }
        }
        /**
         * <code>required string name = 1;</code>
         */
        public com.google.protobuf.ByteString
        getNameBytes() {
            java.lang.Object ref = name_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                name_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        // optional bytes serialized_comparator = 2;
        public static final int SERIALIZED_COMPARATOR_FIELD_NUMBER = 2;
        private com.google.protobuf.ByteString serializedComparator_;
        /**
         * <code>optional bytes serialized_comparator = 2;</code>
         */
        public boolean hasSerializedComparator() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }
        /**
         * <code>optional bytes serialized_comparator = 2;</code>
         */
        public com.google.protobuf.ByteString getSerializedComparator() {
            return serializedComparator_;
        }

        private void initFields() {
            name_ = "";
            serializedComparator_ = com.google.protobuf.ByteString.EMPTY;
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasName()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeBytes(1, getNameBytes());
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeBytes(2, serializedComparator_);
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(1, getNameBytes());
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(2, serializedComparator_);
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.Comparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.Comparator other = (byone.hbase.filter.ByComparatorProtos.Comparator) obj;

            boolean result = true;
            result = result && (hasName() == other.hasName());
            if (hasName()) {
                result = result && getName()
                        .equals(other.getName());
            }
            result = result && (hasSerializedComparator() == other.hasSerializedComparator());
            if (hasSerializedComparator()) {
                result = result && getSerializedComparator()
                        .equals(other.getSerializedComparator());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasName()) {
                hash = (37 * hash) + NAME_FIELD_NUMBER;
                hash = (53 * hash) + getName().hashCode();
            }
            if (hasSerializedComparator()) {
                hash = (37 * hash) + SERIALIZED_COMPARATOR_FIELD_NUMBER;
                hash = (53 * hash) + getSerializedComparator().hashCode();
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.Comparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.Comparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code Comparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.ComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_Comparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_Comparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.Comparator.class, byone.hbase.filter.ByComparatorProtos.Comparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.Comparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                name_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                serializedComparator_ = com.google.protobuf.ByteString.EMPTY;
                bitField0_ = (bitField0_ & ~0x00000002);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_Comparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.Comparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.Comparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.Comparator build() {
                byone.hbase.filter.ByComparatorProtos.Comparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.Comparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.Comparator result = new byone.hbase.filter.ByComparatorProtos.Comparator(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.name_ = name_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.serializedComparator_ = serializedComparator_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.Comparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.Comparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.Comparator other) {
                if (other == byone.hbase.filter.ByComparatorProtos.Comparator.getDefaultInstance()) return this;
                if (other.hasName()) {
                    bitField0_ |= 0x00000001;
                    name_ = other.name_;
                    onChanged();
                }
                if (other.hasSerializedComparator()) {
                    setSerializedComparator(other.getSerializedComparator());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasName()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.Comparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.Comparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // required string name = 1;
            private java.lang.Object name_ = "";
            /**
             * <code>required string name = 1;</code>
             */
            public boolean hasName() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required string name = 1;</code>
             */
            public java.lang.String getName() {
                java.lang.Object ref = name_;
                if (!(ref instanceof java.lang.String)) {
                    java.lang.String s = ((com.google.protobuf.ByteString) ref)
                            .toStringUtf8();
                    name_ = s;
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>required string name = 1;</code>
             */
            public com.google.protobuf.ByteString
            getNameBytes() {
                java.lang.Object ref = name_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    name_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>required string name = 1;</code>
             */
            public Builder setName(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                name_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required string name = 1;</code>
             */
            public Builder clearName() {
                bitField0_ = (bitField0_ & ~0x00000001);
                name_ = getDefaultInstance().getName();
                onChanged();
                return this;
            }
            /**
             * <code>required string name = 1;</code>
             */
            public Builder setNameBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                name_ = value;
                onChanged();
                return this;
            }

            // optional bytes serialized_comparator = 2;
            private com.google.protobuf.ByteString serializedComparator_ = com.google.protobuf.ByteString.EMPTY;
            /**
             * <code>optional bytes serialized_comparator = 2;</code>
             */
            public boolean hasSerializedComparator() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>optional bytes serialized_comparator = 2;</code>
             */
            public com.google.protobuf.ByteString getSerializedComparator() {
                return serializedComparator_;
            }
            /**
             * <code>optional bytes serialized_comparator = 2;</code>
             */
            public Builder setSerializedComparator(com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                serializedComparator_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>optional bytes serialized_comparator = 2;</code>
             */
            public Builder clearSerializedComparator() {
                bitField0_ = (bitField0_ & ~0x00000002);
                serializedComparator_ = getDefaultInstance().getSerializedComparator();
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:Comparator)
        }

        static {
            defaultInstance = new Comparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:Comparator)
    }

    public interface ByteArrayComparableOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // optional bytes value = 1;
        /**
         * <code>optional bytes value = 1;</code>
         */
        boolean hasValue();
        /**
         * <code>optional bytes value = 1;</code>
         */
        com.google.protobuf.ByteString getValue();
    }
    /**
     * Protobuf type {@code ByteArrayComparable}
     */
    public static final class ByteArrayComparable extends
            com.google.protobuf.GeneratedMessage
            implements ByteArrayComparableOrBuilder {
        // Use ByteArrayComparable.newBuilder() to construct.
        private ByteArrayComparable(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private ByteArrayComparable(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final ByteArrayComparable defaultInstance;
        public static ByteArrayComparable getDefaultInstance() {
            return defaultInstance;
        }

        public ByteArrayComparable getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private ByteArrayComparable(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            bitField0_ |= 0x00000001;
                            value_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_ByteArrayComparable_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_ByteArrayComparable_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.class, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder.class);
        }

        public static com.google.protobuf.Parser<ByteArrayComparable> PARSER =
                new com.google.protobuf.AbstractParser<ByteArrayComparable>() {
                    public ByteArrayComparable parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new ByteArrayComparable(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<ByteArrayComparable> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // optional bytes value = 1;
        public static final int VALUE_FIELD_NUMBER = 1;
        private com.google.protobuf.ByteString value_;
        /**
         * <code>optional bytes value = 1;</code>
         */
        public boolean hasValue() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>optional bytes value = 1;</code>
         */
        public com.google.protobuf.ByteString getValue() {
            return value_;
        }

        private void initFields() {
            value_ = com.google.protobuf.ByteString.EMPTY;
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeBytes(1, value_);
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(1, value_);
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.ByteArrayComparable)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable other = (byone.hbase.filter.ByComparatorProtos.ByteArrayComparable) obj;

            boolean result = true;
            result = result && (hasValue() == other.hasValue());
            if (hasValue()) {
                result = result && getValue()
                        .equals(other.getValue());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasValue()) {
                hash = (37 * hash) + VALUE_FIELD_NUMBER;
                hash = (53 * hash) + getValue().hashCode();
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code ByteArrayComparable}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_ByteArrayComparable_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_ByteArrayComparable_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.class, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                value_ = com.google.protobuf.ByteString.EMPTY;
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_ByteArrayComparable_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable build() {
                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable buildPartial() {
                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable result = new byone.hbase.filter.ByComparatorProtos.ByteArrayComparable(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.value_ = value_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.ByteArrayComparable) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.ByteArrayComparable)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable other) {
                if (other == byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance()) return this;
                if (other.hasValue()) {
                    setValue(other.getValue());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.ByteArrayComparable) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // optional bytes value = 1;
            private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
            /**
             * <code>optional bytes value = 1;</code>
             */
            public boolean hasValue() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>optional bytes value = 1;</code>
             */
            public com.google.protobuf.ByteString getValue() {
                return value_;
            }
            /**
             * <code>optional bytes value = 1;</code>
             */
            public Builder setValue(com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                value_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>optional bytes value = 1;</code>
             */
            public Builder clearValue() {
                bitField0_ = (bitField0_ & ~0x00000001);
                value_ = getDefaultInstance().getValue();
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:ByteArrayComparable)
        }

        static {
            defaultInstance = new ByteArrayComparable(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:ByteArrayComparable)
    }

    public interface BinaryComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // required .ByteArrayComparable comparable = 1;
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        boolean hasComparable();
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable();
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder();
    }
    /**
     * Protobuf type {@code BinaryComparator}
     */
    public static final class BinaryComparator extends
            com.google.protobuf.GeneratedMessage
            implements BinaryComparatorOrBuilder {
        // Use BinaryComparator.newBuilder() to construct.
        private BinaryComparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private BinaryComparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final BinaryComparator defaultInstance;
        public static BinaryComparator getDefaultInstance() {
            return defaultInstance;
        }

        public BinaryComparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private BinaryComparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder subBuilder = null;
                            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                                subBuilder = comparable_.toBuilder();
                            }
                            comparable_ = input.readMessage(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(comparable_);
                                comparable_ = subBuilder.buildPartial();
                            }
                            bitField0_ |= 0x00000001;
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryComparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryComparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.BinaryComparator.class, byone.hbase.filter.ByComparatorProtos.BinaryComparator.Builder.class);
        }

        public static com.google.protobuf.Parser<BinaryComparator> PARSER =
                new com.google.protobuf.AbstractParser<BinaryComparator>() {
                    public BinaryComparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new BinaryComparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<BinaryComparator> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // required .ByteArrayComparable comparable = 1;
        public static final int COMPARABLE_FIELD_NUMBER = 1;
        private byone.hbase.filter.ByComparatorProtos.ByteArrayComparable comparable_;
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public boolean hasComparable() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable() {
            return comparable_;
        }
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder() {
            return comparable_;
        }

        private void initFields() {
            comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasComparable()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeMessage(1, comparable_);
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(1, comparable_);
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.BinaryComparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.BinaryComparator other = (byone.hbase.filter.ByComparatorProtos.BinaryComparator) obj;

            boolean result = true;
            result = result && (hasComparable() == other.hasComparable());
            if (hasComparable()) {
                result = result && getComparable()
                        .equals(other.getComparable());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasComparable()) {
                hash = (37 * hash) + COMPARABLE_FIELD_NUMBER;
                hash = (53 * hash) + getComparable().hashCode();
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryComparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.BinaryComparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code BinaryComparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.BinaryComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryComparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryComparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.BinaryComparator.class, byone.hbase.filter.ByComparatorProtos.BinaryComparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.BinaryComparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                    getComparableFieldBuilder();
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (comparableBuilder_ == null) {
                    comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
                } else {
                    comparableBuilder_.clear();
                }
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryComparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.BinaryComparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.BinaryComparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.BinaryComparator build() {
                byone.hbase.filter.ByComparatorProtos.BinaryComparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.BinaryComparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.BinaryComparator result = new byone.hbase.filter.ByComparatorProtos.BinaryComparator(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                if (comparableBuilder_ == null) {
                    result.comparable_ = comparable_;
                } else {
                    result.comparable_ = comparableBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.BinaryComparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.BinaryComparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.BinaryComparator other) {
                if (other == byone.hbase.filter.ByComparatorProtos.BinaryComparator.getDefaultInstance()) return this;
                if (other.hasComparable()) {
                    mergeComparable(other.getComparable());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasComparable()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.BinaryComparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.BinaryComparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // required .ByteArrayComparable comparable = 1;
            private byone.hbase.filter.ByComparatorProtos.ByteArrayComparable comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
            private com.google.protobuf.SingleFieldBuilder<
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder> comparableBuilder_;
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public boolean hasComparable() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable() {
                if (comparableBuilder_ == null) {
                    return comparable_;
                } else {
                    return comparableBuilder_.getMessage();
                }
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder setComparable(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable value) {
                if (comparableBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    comparable_ = value;
                    onChanged();
                } else {
                    comparableBuilder_.setMessage(value);
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder setComparable(
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder builderForValue) {
                if (comparableBuilder_ == null) {
                    comparable_ = builderForValue.build();
                    onChanged();
                } else {
                    comparableBuilder_.setMessage(builderForValue.build());
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder mergeComparable(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable value) {
                if (comparableBuilder_ == null) {
                    if (((bitField0_ & 0x00000001) == 0x00000001) &&
                            comparable_ != byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance()) {
                        comparable_ =
                                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.newBuilder(comparable_).mergeFrom(value).buildPartial();
                    } else {
                        comparable_ = value;
                    }
                    onChanged();
                } else {
                    comparableBuilder_.mergeFrom(value);
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder clearComparable() {
                if (comparableBuilder_ == null) {
                    comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
                    onChanged();
                } else {
                    comparableBuilder_.clear();
                }
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder getComparableBuilder() {
                bitField0_ |= 0x00000001;
                onChanged();
                return getComparableFieldBuilder().getBuilder();
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder() {
                if (comparableBuilder_ != null) {
                    return comparableBuilder_.getMessageOrBuilder();
                } else {
                    return comparable_;
                }
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            private com.google.protobuf.SingleFieldBuilder<
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder>
            getComparableFieldBuilder() {
                if (comparableBuilder_ == null) {
                    comparableBuilder_ = new com.google.protobuf.SingleFieldBuilder<
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder>(
                            comparable_,
                            getParentForChildren(),
                            isClean());
                    comparable_ = null;
                }
                return comparableBuilder_;
            }

            // @@protoc_insertion_point(builder_scope:BinaryComparator)
        }

        static {
            defaultInstance = new BinaryComparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:BinaryComparator)
    }

    public interface BinaryPrefixComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // required .ByteArrayComparable comparable = 1;
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        boolean hasComparable();
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable();
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder();
    }
    /**
     * Protobuf type {@code BinaryPrefixComparator}
     */
    public static final class BinaryPrefixComparator extends
            com.google.protobuf.GeneratedMessage
            implements BinaryPrefixComparatorOrBuilder {
        // Use BinaryPrefixComparator.newBuilder() to construct.
        private BinaryPrefixComparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private BinaryPrefixComparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final BinaryPrefixComparator defaultInstance;
        public static BinaryPrefixComparator getDefaultInstance() {
            return defaultInstance;
        }

        public BinaryPrefixComparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private BinaryPrefixComparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder subBuilder = null;
                            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                                subBuilder = comparable_.toBuilder();
                            }
                            comparable_ = input.readMessage(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(comparable_);
                                comparable_ = subBuilder.buildPartial();
                            }
                            bitField0_ |= 0x00000001;
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryPrefixComparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryPrefixComparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.class, byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.Builder.class);
        }

        public static com.google.protobuf.Parser<BinaryPrefixComparator> PARSER =
                new com.google.protobuf.AbstractParser<BinaryPrefixComparator>() {
                    public BinaryPrefixComparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new BinaryPrefixComparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<BinaryPrefixComparator> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // required .ByteArrayComparable comparable = 1;
        public static final int COMPARABLE_FIELD_NUMBER = 1;
        private byone.hbase.filter.ByComparatorProtos.ByteArrayComparable comparable_;
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public boolean hasComparable() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable() {
            return comparable_;
        }
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder() {
            return comparable_;
        }

        private void initFields() {
            comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasComparable()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeMessage(1, comparable_);
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(1, comparable_);
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator other = (byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator) obj;

            boolean result = true;
            result = result && (hasComparable() == other.hasComparable());
            if (hasComparable()) {
                result = result && getComparable()
                        .equals(other.getComparable());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasComparable()) {
                hash = (37 * hash) + COMPARABLE_FIELD_NUMBER;
                hash = (53 * hash) + getComparable().hashCode();
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code BinaryPrefixComparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryPrefixComparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryPrefixComparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.class, byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                    getComparableFieldBuilder();
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (comparableBuilder_ == null) {
                    comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
                } else {
                    comparableBuilder_.clear();
                }
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BinaryPrefixComparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator build() {
                byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator result = new byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                if (comparableBuilder_ == null) {
                    result.comparable_ = comparable_;
                } else {
                    result.comparable_ = comparableBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator other) {
                if (other == byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator.getDefaultInstance()) return this;
                if (other.hasComparable()) {
                    mergeComparable(other.getComparable());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasComparable()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.BinaryPrefixComparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // required .ByteArrayComparable comparable = 1;
            private byone.hbase.filter.ByComparatorProtos.ByteArrayComparable comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
            private com.google.protobuf.SingleFieldBuilder<
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder> comparableBuilder_;
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public boolean hasComparable() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable() {
                if (comparableBuilder_ == null) {
                    return comparable_;
                } else {
                    return comparableBuilder_.getMessage();
                }
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder setComparable(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable value) {
                if (comparableBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    comparable_ = value;
                    onChanged();
                } else {
                    comparableBuilder_.setMessage(value);
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder setComparable(
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder builderForValue) {
                if (comparableBuilder_ == null) {
                    comparable_ = builderForValue.build();
                    onChanged();
                } else {
                    comparableBuilder_.setMessage(builderForValue.build());
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder mergeComparable(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable value) {
                if (comparableBuilder_ == null) {
                    if (((bitField0_ & 0x00000001) == 0x00000001) &&
                            comparable_ != byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance()) {
                        comparable_ =
                                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.newBuilder(comparable_).mergeFrom(value).buildPartial();
                    } else {
                        comparable_ = value;
                    }
                    onChanged();
                } else {
                    comparableBuilder_.mergeFrom(value);
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder clearComparable() {
                if (comparableBuilder_ == null) {
                    comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
                    onChanged();
                } else {
                    comparableBuilder_.clear();
                }
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder getComparableBuilder() {
                bitField0_ |= 0x00000001;
                onChanged();
                return getComparableFieldBuilder().getBuilder();
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder() {
                if (comparableBuilder_ != null) {
                    return comparableBuilder_.getMessageOrBuilder();
                } else {
                    return comparable_;
                }
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            private com.google.protobuf.SingleFieldBuilder<
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder>
            getComparableFieldBuilder() {
                if (comparableBuilder_ == null) {
                    comparableBuilder_ = new com.google.protobuf.SingleFieldBuilder<
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder>(
                            comparable_,
                            getParentForChildren(),
                            isClean());
                    comparable_ = null;
                }
                return comparableBuilder_;
            }

            // @@protoc_insertion_point(builder_scope:BinaryPrefixComparator)
        }

        static {
            defaultInstance = new BinaryPrefixComparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:BinaryPrefixComparator)
    }

    public interface BitComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // required .ByteArrayComparable comparable = 1;
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        boolean hasComparable();
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable();
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder();

        // required .BitComparator.BitwiseOp bitwise_op = 2;
        /**
         * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
         */
        boolean hasBitwiseOp();
        /**
         * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
         */
        byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp getBitwiseOp();
    }
    /**
     * Protobuf type {@code BitComparator}
     */
    public static final class BitComparator extends
            com.google.protobuf.GeneratedMessage
            implements BitComparatorOrBuilder {
        // Use BitComparator.newBuilder() to construct.
        private BitComparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private BitComparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final BitComparator defaultInstance;
        public static BitComparator getDefaultInstance() {
            return defaultInstance;
        }

        public BitComparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private BitComparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder subBuilder = null;
                            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                                subBuilder = comparable_.toBuilder();
                            }
                            comparable_ = input.readMessage(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(comparable_);
                                comparable_ = subBuilder.buildPartial();
                            }
                            bitField0_ |= 0x00000001;
                            break;
                        }
                        case 16: {
                            int rawValue = input.readEnum();
                            byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp value = byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                            } else {
                                bitField0_ |= 0x00000002;
                                bitwiseOp_ = value;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_BitComparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_BitComparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.BitComparator.class, byone.hbase.filter.ByComparatorProtos.BitComparator.Builder.class);
        }

        public static com.google.protobuf.Parser<BitComparator> PARSER =
                new com.google.protobuf.AbstractParser<BitComparator>() {
                    public BitComparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new BitComparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<BitComparator> getParserForType() {
            return PARSER;
        }

        /**
         * Protobuf enum {@code BitComparator.BitwiseOp}
         */
        public enum BitwiseOp
                implements com.google.protobuf.ProtocolMessageEnum {
            /**
             * <code>AND = 1;</code>
             */
            AND(0, 1),
            /**
             * <code>OR = 2;</code>
             */
            OR(1, 2),
            /**
             * <code>XOR = 3;</code>
             */
            XOR(2, 3),
            ;

            /**
             * <code>AND = 1;</code>
             */
            public static final int AND_VALUE = 1;
            /**
             * <code>OR = 2;</code>
             */
            public static final int OR_VALUE = 2;
            /**
             * <code>XOR = 3;</code>
             */
            public static final int XOR_VALUE = 3;


            public final int getNumber() { return value; }

            public static BitwiseOp valueOf(int value) {
                switch (value) {
                    case 1: return AND;
                    case 2: return OR;
                    case 3: return XOR;
                    default: return null;
                }
            }

            public static com.google.protobuf.Internal.EnumLiteMap<BitwiseOp>
            internalGetValueMap() {
                return internalValueMap;
            }
            private static com.google.protobuf.Internal.EnumLiteMap<BitwiseOp>
                    internalValueMap =
                    new com.google.protobuf.Internal.EnumLiteMap<BitwiseOp>() {
                        public BitwiseOp findValueByNumber(int number) {
                            return BitwiseOp.valueOf(number);
                        }
                    };

            public final com.google.protobuf.Descriptors.EnumValueDescriptor
            getValueDescriptor() {
                return getDescriptor().getValues().get(index);
            }
            public final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptorForType() {
                return getDescriptor();
            }
            public static final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.BitComparator.getDescriptor().getEnumTypes().get(0);
            }

            private static final BitwiseOp[] VALUES = values();

            public static BitwiseOp valueOf(
                    com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new java.lang.IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                return VALUES[desc.getIndex()];
            }

            private final int index;
            private final int value;

            private BitwiseOp(int index, int value) {
                this.index = index;
                this.value = value;
            }

            // @@protoc_insertion_point(enum_scope:BitComparator.BitwiseOp)
        }

        private int bitField0_;
        // required .ByteArrayComparable comparable = 1;
        public static final int COMPARABLE_FIELD_NUMBER = 1;
        private byone.hbase.filter.ByComparatorProtos.ByteArrayComparable comparable_;
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public boolean hasComparable() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable() {
            return comparable_;
        }
        /**
         * <code>required .ByteArrayComparable comparable = 1;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder() {
            return comparable_;
        }

        // required .BitComparator.BitwiseOp bitwise_op = 2;
        public static final int BITWISE_OP_FIELD_NUMBER = 2;
        private byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp bitwiseOp_;
        /**
         * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
         */
        public boolean hasBitwiseOp() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }
        /**
         * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
         */
        public byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp getBitwiseOp() {
            return bitwiseOp_;
        }

        private void initFields() {
            comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
            bitwiseOp_ = byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp.AND;
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasComparable()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasBitwiseOp()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeMessage(1, comparable_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeEnum(2, bitwiseOp_.getNumber());
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(1, comparable_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(2, bitwiseOp_.getNumber());
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.BitComparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.BitComparator other = (byone.hbase.filter.ByComparatorProtos.BitComparator) obj;

            boolean result = true;
            result = result && (hasComparable() == other.hasComparable());
            if (hasComparable()) {
                result = result && getComparable()
                        .equals(other.getComparable());
            }
            result = result && (hasBitwiseOp() == other.hasBitwiseOp());
            if (hasBitwiseOp()) {
                result = result &&
                        (getBitwiseOp() == other.getBitwiseOp());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasComparable()) {
                hash = (37 * hash) + COMPARABLE_FIELD_NUMBER;
                hash = (53 * hash) + getComparable().hashCode();
            }
            if (hasBitwiseOp()) {
                hash = (37 * hash) + BITWISE_OP_FIELD_NUMBER;
                hash = (53 * hash) + hashEnum(getBitwiseOp());
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.BitComparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.BitComparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code BitComparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.BitComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BitComparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BitComparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.BitComparator.class, byone.hbase.filter.ByComparatorProtos.BitComparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.BitComparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                    getComparableFieldBuilder();
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (comparableBuilder_ == null) {
                    comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
                } else {
                    comparableBuilder_.clear();
                }
                bitField0_ = (bitField0_ & ~0x00000001);
                bitwiseOp_ = byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp.AND;
                bitField0_ = (bitField0_ & ~0x00000002);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_BitComparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.BitComparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.BitComparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.BitComparator build() {
                byone.hbase.filter.ByComparatorProtos.BitComparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.BitComparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.BitComparator result = new byone.hbase.filter.ByComparatorProtos.BitComparator(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                if (comparableBuilder_ == null) {
                    result.comparable_ = comparable_;
                } else {
                    result.comparable_ = comparableBuilder_.build();
                }
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.bitwiseOp_ = bitwiseOp_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.BitComparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.BitComparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.BitComparator other) {
                if (other == byone.hbase.filter.ByComparatorProtos.BitComparator.getDefaultInstance()) return this;
                if (other.hasComparable()) {
                    mergeComparable(other.getComparable());
                }
                if (other.hasBitwiseOp()) {
                    setBitwiseOp(other.getBitwiseOp());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasComparable()) {

                    return false;
                }
                if (!hasBitwiseOp()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.BitComparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.BitComparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // required .ByteArrayComparable comparable = 1;
            private byone.hbase.filter.ByComparatorProtos.ByteArrayComparable comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
            private com.google.protobuf.SingleFieldBuilder<
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder> comparableBuilder_;
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public boolean hasComparable() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable getComparable() {
                if (comparableBuilder_ == null) {
                    return comparable_;
                } else {
                    return comparableBuilder_.getMessage();
                }
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder setComparable(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable value) {
                if (comparableBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    comparable_ = value;
                    onChanged();
                } else {
                    comparableBuilder_.setMessage(value);
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder setComparable(
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder builderForValue) {
                if (comparableBuilder_ == null) {
                    comparable_ = builderForValue.build();
                    onChanged();
                } else {
                    comparableBuilder_.setMessage(builderForValue.build());
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder mergeComparable(byone.hbase.filter.ByComparatorProtos.ByteArrayComparable value) {
                if (comparableBuilder_ == null) {
                    if (((bitField0_ & 0x00000001) == 0x00000001) &&
                            comparable_ != byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance()) {
                        comparable_ =
                                byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.newBuilder(comparable_).mergeFrom(value).buildPartial();
                    } else {
                        comparable_ = value;
                    }
                    onChanged();
                } else {
                    comparableBuilder_.mergeFrom(value);
                }
                bitField0_ |= 0x00000001;
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public Builder clearComparable() {
                if (comparableBuilder_ == null) {
                    comparable_ = byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.getDefaultInstance();
                    onChanged();
                } else {
                    comparableBuilder_.clear();
                }
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder getComparableBuilder() {
                bitField0_ |= 0x00000001;
                onChanged();
                return getComparableFieldBuilder().getBuilder();
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder getComparableOrBuilder() {
                if (comparableBuilder_ != null) {
                    return comparableBuilder_.getMessageOrBuilder();
                } else {
                    return comparable_;
                }
            }
            /**
             * <code>required .ByteArrayComparable comparable = 1;</code>
             */
            private com.google.protobuf.SingleFieldBuilder<
                    byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder>
            getComparableFieldBuilder() {
                if (comparableBuilder_ == null) {
                    comparableBuilder_ = new com.google.protobuf.SingleFieldBuilder<
                            byone.hbase.filter.ByComparatorProtos.ByteArrayComparable, byone.hbase.filter.ByComparatorProtos.ByteArrayComparable.Builder, byone.hbase.filter.ByComparatorProtos.ByteArrayComparableOrBuilder>(
                            comparable_,
                            getParentForChildren(),
                            isClean());
                    comparable_ = null;
                }
                return comparableBuilder_;
            }

            // required .BitComparator.BitwiseOp bitwise_op = 2;
            private byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp bitwiseOp_ = byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp.AND;
            /**
             * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
             */
            public boolean hasBitwiseOp() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
             */
            public byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp getBitwiseOp() {
                return bitwiseOp_;
            }
            /**
             * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
             */
            public Builder setBitwiseOp(byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                bitwiseOp_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required .BitComparator.BitwiseOp bitwise_op = 2;</code>
             */
            public Builder clearBitwiseOp() {
                bitField0_ = (bitField0_ & ~0x00000002);
                bitwiseOp_ = byone.hbase.filter.ByComparatorProtos.BitComparator.BitwiseOp.AND;
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:BitComparator)
        }

        static {
            defaultInstance = new BitComparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:BitComparator)
    }

    public interface NullComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {
    }
    /**
     * Protobuf type {@code NullComparator}
     */
    public static final class NullComparator extends
            com.google.protobuf.GeneratedMessage
            implements NullComparatorOrBuilder {
        // Use NullComparator.newBuilder() to construct.
        private NullComparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private NullComparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final NullComparator defaultInstance;
        public static NullComparator getDefaultInstance() {
            return defaultInstance;
        }

        public NullComparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private NullComparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_NullComparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_NullComparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.NullComparator.class, byone.hbase.filter.ByComparatorProtos.NullComparator.Builder.class);
        }

        public static com.google.protobuf.Parser<NullComparator> PARSER =
                new com.google.protobuf.AbstractParser<NullComparator>() {
                    public NullComparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new NullComparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<NullComparator> getParserForType() {
            return PARSER;
        }

        private void initFields() {
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.NullComparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.NullComparator other = (byone.hbase.filter.ByComparatorProtos.NullComparator) obj;

            boolean result = true;
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.NullComparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.NullComparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code NullComparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.NullComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_NullComparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_NullComparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.NullComparator.class, byone.hbase.filter.ByComparatorProtos.NullComparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.NullComparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_NullComparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.NullComparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.NullComparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.NullComparator build() {
                byone.hbase.filter.ByComparatorProtos.NullComparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.NullComparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.NullComparator result = new byone.hbase.filter.ByComparatorProtos.NullComparator(this);
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.NullComparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.NullComparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.NullComparator other) {
                if (other == byone.hbase.filter.ByComparatorProtos.NullComparator.getDefaultInstance()) return this;
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.NullComparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.NullComparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            // @@protoc_insertion_point(builder_scope:NullComparator)
        }

        static {
            defaultInstance = new NullComparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:NullComparator)
    }

    public interface RegexStringComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // required string pattern = 1;
        /**
         * <code>required string pattern = 1;</code>
         */
        boolean hasPattern();
        /**
         * <code>required string pattern = 1;</code>
         */
        java.lang.String getPattern();
        /**
         * <code>required string pattern = 1;</code>
         */
        com.google.protobuf.ByteString
        getPatternBytes();

        // required int32 pattern_flags = 2;
        /**
         * <code>required int32 pattern_flags = 2;</code>
         */
        boolean hasPatternFlags();
        /**
         * <code>required int32 pattern_flags = 2;</code>
         */
        int getPatternFlags();

        // required string charset = 3;
        /**
         * <code>required string charset = 3;</code>
         */
        boolean hasCharset();
        /**
         * <code>required string charset = 3;</code>
         */
        java.lang.String getCharset();
        /**
         * <code>required string charset = 3;</code>
         */
        com.google.protobuf.ByteString
        getCharsetBytes();
    }
    /**
     * Protobuf type {@code RegexStringComparator}
     */
    public static final class RegexStringComparator extends
            com.google.protobuf.GeneratedMessage
            implements RegexStringComparatorOrBuilder {
        // Use RegexStringComparator.newBuilder() to construct.
        private RegexStringComparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private RegexStringComparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final RegexStringComparator defaultInstance;
        public static RegexStringComparator getDefaultInstance() {
            return defaultInstance;
        }

        public RegexStringComparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private RegexStringComparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            bitField0_ |= 0x00000001;
                            pattern_ = input.readBytes();
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000002;
                            patternFlags_ = input.readInt32();
                            break;
                        }
                        case 26: {
                            bitField0_ |= 0x00000004;
                            charset_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_RegexStringComparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_RegexStringComparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.RegexStringComparator.class, byone.hbase.filter.ByComparatorProtos.RegexStringComparator.Builder.class);
        }

        public static com.google.protobuf.Parser<RegexStringComparator> PARSER =
                new com.google.protobuf.AbstractParser<RegexStringComparator>() {
                    public RegexStringComparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new RegexStringComparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<RegexStringComparator> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // required string pattern = 1;
        public static final int PATTERN_FIELD_NUMBER = 1;
        private java.lang.Object pattern_;
        /**
         * <code>required string pattern = 1;</code>
         */
        public boolean hasPattern() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required string pattern = 1;</code>
         */
        public java.lang.String getPattern() {
            java.lang.Object ref = pattern_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    pattern_ = s;
                }
                return s;
            }
        }
        /**
         * <code>required string pattern = 1;</code>
         */
        public com.google.protobuf.ByteString
        getPatternBytes() {
            java.lang.Object ref = pattern_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                pattern_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        // required int32 pattern_flags = 2;
        public static final int PATTERN_FLAGS_FIELD_NUMBER = 2;
        private int patternFlags_;
        /**
         * <code>required int32 pattern_flags = 2;</code>
         */
        public boolean hasPatternFlags() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }
        /**
         * <code>required int32 pattern_flags = 2;</code>
         */
        public int getPatternFlags() {
            return patternFlags_;
        }

        // required string charset = 3;
        public static final int CHARSET_FIELD_NUMBER = 3;
        private java.lang.Object charset_;
        /**
         * <code>required string charset = 3;</code>
         */
        public boolean hasCharset() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }
        /**
         * <code>required string charset = 3;</code>
         */
        public java.lang.String getCharset() {
            java.lang.Object ref = charset_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    charset_ = s;
                }
                return s;
            }
        }
        /**
         * <code>required string charset = 3;</code>
         */
        public com.google.protobuf.ByteString
        getCharsetBytes() {
            java.lang.Object ref = charset_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                charset_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        private void initFields() {
            pattern_ = "";
            patternFlags_ = 0;
            charset_ = "";
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasPattern()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasPatternFlags()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasCharset()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeBytes(1, getPatternBytes());
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeInt32(2, patternFlags_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeBytes(3, getCharsetBytes());
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(1, getPatternBytes());
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(2, patternFlags_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(3, getCharsetBytes());
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.RegexStringComparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.RegexStringComparator other = (byone.hbase.filter.ByComparatorProtos.RegexStringComparator) obj;

            boolean result = true;
            result = result && (hasPattern() == other.hasPattern());
            if (hasPattern()) {
                result = result && getPattern()
                        .equals(other.getPattern());
            }
            result = result && (hasPatternFlags() == other.hasPatternFlags());
            if (hasPatternFlags()) {
                result = result && (getPatternFlags()
                        == other.getPatternFlags());
            }
            result = result && (hasCharset() == other.hasCharset());
            if (hasCharset()) {
                result = result && getCharset()
                        .equals(other.getCharset());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasPattern()) {
                hash = (37 * hash) + PATTERN_FIELD_NUMBER;
                hash = (53 * hash) + getPattern().hashCode();
            }
            if (hasPatternFlags()) {
                hash = (37 * hash) + PATTERN_FLAGS_FIELD_NUMBER;
                hash = (53 * hash) + getPatternFlags();
            }
            if (hasCharset()) {
                hash = (37 * hash) + CHARSET_FIELD_NUMBER;
                hash = (53 * hash) + getCharset().hashCode();
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.RegexStringComparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.RegexStringComparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code RegexStringComparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.RegexStringComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_RegexStringComparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_RegexStringComparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.RegexStringComparator.class, byone.hbase.filter.ByComparatorProtos.RegexStringComparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.RegexStringComparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                pattern_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                patternFlags_ = 0;
                bitField0_ = (bitField0_ & ~0x00000002);
                charset_ = "";
                bitField0_ = (bitField0_ & ~0x00000004);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_RegexStringComparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.RegexStringComparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.RegexStringComparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.RegexStringComparator build() {
                byone.hbase.filter.ByComparatorProtos.RegexStringComparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.RegexStringComparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.RegexStringComparator result = new byone.hbase.filter.ByComparatorProtos.RegexStringComparator(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.pattern_ = pattern_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.patternFlags_ = patternFlags_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.charset_ = charset_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.RegexStringComparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.RegexStringComparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.RegexStringComparator other) {
                if (other == byone.hbase.filter.ByComparatorProtos.RegexStringComparator.getDefaultInstance()) return this;
                if (other.hasPattern()) {
                    bitField0_ |= 0x00000001;
                    pattern_ = other.pattern_;
                    onChanged();
                }
                if (other.hasPatternFlags()) {
                    setPatternFlags(other.getPatternFlags());
                }
                if (other.hasCharset()) {
                    bitField0_ |= 0x00000004;
                    charset_ = other.charset_;
                    onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasPattern()) {

                    return false;
                }
                if (!hasPatternFlags()) {

                    return false;
                }
                if (!hasCharset()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.RegexStringComparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.RegexStringComparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // required string pattern = 1;
            private java.lang.Object pattern_ = "";
            /**
             * <code>required string pattern = 1;</code>
             */
            public boolean hasPattern() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required string pattern = 1;</code>
             */
            public java.lang.String getPattern() {
                java.lang.Object ref = pattern_;
                if (!(ref instanceof java.lang.String)) {
                    java.lang.String s = ((com.google.protobuf.ByteString) ref)
                            .toStringUtf8();
                    pattern_ = s;
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>required string pattern = 1;</code>
             */
            public com.google.protobuf.ByteString
            getPatternBytes() {
                java.lang.Object ref = pattern_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    pattern_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>required string pattern = 1;</code>
             */
            public Builder setPattern(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                pattern_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required string pattern = 1;</code>
             */
            public Builder clearPattern() {
                bitField0_ = (bitField0_ & ~0x00000001);
                pattern_ = getDefaultInstance().getPattern();
                onChanged();
                return this;
            }
            /**
             * <code>required string pattern = 1;</code>
             */
            public Builder setPatternBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                pattern_ = value;
                onChanged();
                return this;
            }

            // required int32 pattern_flags = 2;
            private int patternFlags_ ;
            /**
             * <code>required int32 pattern_flags = 2;</code>
             */
            public boolean hasPatternFlags() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>required int32 pattern_flags = 2;</code>
             */
            public int getPatternFlags() {
                return patternFlags_;
            }
            /**
             * <code>required int32 pattern_flags = 2;</code>
             */
            public Builder setPatternFlags(int value) {
                bitField0_ |= 0x00000002;
                patternFlags_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required int32 pattern_flags = 2;</code>
             */
            public Builder clearPatternFlags() {
                bitField0_ = (bitField0_ & ~0x00000002);
                patternFlags_ = 0;
                onChanged();
                return this;
            }

            // required string charset = 3;
            private java.lang.Object charset_ = "";
            /**
             * <code>required string charset = 3;</code>
             */
            public boolean hasCharset() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }
            /**
             * <code>required string charset = 3;</code>
             */
            public java.lang.String getCharset() {
                java.lang.Object ref = charset_;
                if (!(ref instanceof java.lang.String)) {
                    java.lang.String s = ((com.google.protobuf.ByteString) ref)
                            .toStringUtf8();
                    charset_ = s;
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>required string charset = 3;</code>
             */
            public com.google.protobuf.ByteString
            getCharsetBytes() {
                java.lang.Object ref = charset_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    charset_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>required string charset = 3;</code>
             */
            public Builder setCharset(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                charset_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required string charset = 3;</code>
             */
            public Builder clearCharset() {
                bitField0_ = (bitField0_ & ~0x00000004);
                charset_ = getDefaultInstance().getCharset();
                onChanged();
                return this;
            }
            /**
             * <code>required string charset = 3;</code>
             */
            public Builder setCharsetBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                charset_ = value;
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:RegexStringComparator)
        }

        static {
            defaultInstance = new RegexStringComparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:RegexStringComparator)
    }

    public interface SubstringComparatorOrBuilder
            extends com.google.protobuf.MessageOrBuilder {

        // required string substr = 1;
        /**
         * <code>required string substr = 1;</code>
         */
        boolean hasSubstr();
        /**
         * <code>required string substr = 1;</code>
         */
        java.lang.String getSubstr();
        /**
         * <code>required string substr = 1;</code>
         */
        com.google.protobuf.ByteString
        getSubstrBytes();
    }
    /**
     * Protobuf type {@code SubstringComparator}
     */
    public static final class SubstringComparator extends
            com.google.protobuf.GeneratedMessage
            implements SubstringComparatorOrBuilder {
        // Use SubstringComparator.newBuilder() to construct.
        private SubstringComparator(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }
        private SubstringComparator(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

        private static final SubstringComparator defaultInstance;
        public static SubstringComparator getDefaultInstance() {
            return defaultInstance;
        }

        public SubstringComparator getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.UnknownFieldSet unknownFields;
        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private SubstringComparator(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFields,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 10: {
                            bitField0_ |= 0x00000001;
                            substr_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_SubstringComparator_descriptor;
        }

        protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return byone.hbase.filter.ByComparatorProtos.internal_static_SubstringComparator_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            byone.hbase.filter.ByComparatorProtos.SubstringComparator.class, byone.hbase.filter.ByComparatorProtos.SubstringComparator.Builder.class);
        }

        public static com.google.protobuf.Parser<SubstringComparator> PARSER =
                new com.google.protobuf.AbstractParser<SubstringComparator>() {
                    public SubstringComparator parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new SubstringComparator(input, extensionRegistry);
                    }
                };

        @java.lang.Override
        public com.google.protobuf.Parser<SubstringComparator> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // required string substr = 1;
        public static final int SUBSTR_FIELD_NUMBER = 1;
        private java.lang.Object substr_;
        /**
         * <code>required string substr = 1;</code>
         */
        public boolean hasSubstr() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required string substr = 1;</code>
         */
        public java.lang.String getSubstr() {
            java.lang.Object ref = substr_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    substr_ = s;
                }
                return s;
            }
        }
        /**
         * <code>required string substr = 1;</code>
         */
        public com.google.protobuf.ByteString
        getSubstrBytes() {
            java.lang.Object ref = substr_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                substr_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        private void initFields() {
            substr_ = "";
        }
        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasSubstr()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeBytes(1, getSubstrBytes());
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;
        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(1, getSubstrBytes());
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;
        @java.lang.Override
        protected java.lang.Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof byone.hbase.filter.ByComparatorProtos.SubstringComparator)) {
                return super.equals(obj);
            }
            byone.hbase.filter.ByComparatorProtos.SubstringComparator other = (byone.hbase.filter.ByComparatorProtos.SubstringComparator) obj;

            boolean result = true;
            result = result && (hasSubstr() == other.hasSubstr());
            if (hasSubstr()) {
                result = result && getSubstr()
                        .equals(other.getSubstr());
            }
            result = result &&
                    getUnknownFields().equals(other.getUnknownFields());
            return result;
        }

        private int memoizedHashCode = 0;
        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptorForType().hashCode();
            if (hasSubstr()) {
                hash = (37 * hash) + SUBSTR_FIELD_NUMBER;
                hash = (53 * hash) + getSubstr().hashCode();
            }
            hash = (29 * hash) + getUnknownFields().hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }
        public static byone.hbase.filter.ByComparatorProtos.SubstringComparator parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() { return Builder.create(); }
        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder(byone.hbase.filter.ByComparatorProtos.SubstringComparator prototype) {
            return newBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() { return newBuilder(this); }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code SubstringComparator}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessage.Builder<Builder>
                implements byone.hbase.filter.ByComparatorProtos.SubstringComparatorOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_SubstringComparator_descriptor;
            }

            protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_SubstringComparator_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                byone.hbase.filter.ByComparatorProtos.SubstringComparator.class, byone.hbase.filter.ByComparatorProtos.SubstringComparator.Builder.class);
            }

            // Construct using byone.hbase.filter.ByComparatorProtos.SubstringComparator.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }
            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                substr_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return byone.hbase.filter.ByComparatorProtos.internal_static_SubstringComparator_descriptor;
            }

            public byone.hbase.filter.ByComparatorProtos.SubstringComparator getDefaultInstanceForType() {
                return byone.hbase.filter.ByComparatorProtos.SubstringComparator.getDefaultInstance();
            }

            public byone.hbase.filter.ByComparatorProtos.SubstringComparator build() {
                byone.hbase.filter.ByComparatorProtos.SubstringComparator result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public byone.hbase.filter.ByComparatorProtos.SubstringComparator buildPartial() {
                byone.hbase.filter.ByComparatorProtos.SubstringComparator result = new byone.hbase.filter.ByComparatorProtos.SubstringComparator(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.substr_ = substr_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof byone.hbase.filter.ByComparatorProtos.SubstringComparator) {
                    return mergeFrom((byone.hbase.filter.ByComparatorProtos.SubstringComparator)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(byone.hbase.filter.ByComparatorProtos.SubstringComparator other) {
                if (other ==byone.hbase.filter.ByComparatorProtos.SubstringComparator.getDefaultInstance()) return this;
                if (other.hasSubstr()) {
                    bitField0_ |= 0x00000001;
                    substr_ = other.substr_;
                    onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasSubstr()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                byone.hbase.filter.ByComparatorProtos.SubstringComparator parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (byone.hbase.filter.ByComparatorProtos.SubstringComparator) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            // required string substr = 1;
            private java.lang.Object substr_ = "";
            /**
             * <code>required string substr = 1;</code>
             */
            public boolean hasSubstr() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required string substr = 1;</code>
             */
            public java.lang.String getSubstr() {
                java.lang.Object ref = substr_;
                if (!(ref instanceof java.lang.String)) {
                    java.lang.String s = ((com.google.protobuf.ByteString) ref)
                            .toStringUtf8();
                    substr_ = s;
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>required string substr = 1;</code>
             */
            public com.google.protobuf.ByteString
            getSubstrBytes() {
                java.lang.Object ref = substr_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    substr_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>required string substr = 1;</code>
             */
            public Builder setSubstr(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                substr_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required string substr = 1;</code>
             */
            public Builder clearSubstr() {
                bitField0_ = (bitField0_ & ~0x00000001);
                substr_ = getDefaultInstance().getSubstr();
                onChanged();
                return this;
            }
            /**
             * <code>required string substr = 1;</code>
             */
            public Builder setSubstrBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                substr_ = value;
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:SubstringComparator)
        }

        static {
            defaultInstance = new SubstringComparator(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:SubstringComparator)
    }

    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_Comparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_Comparator_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_ByteArrayComparable_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_ByteArrayComparable_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_BinaryComparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_BinaryComparator_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_BinaryPrefixComparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_BinaryPrefixComparator_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_BitComparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_BitComparator_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_NullComparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_NullComparator_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_RegexStringComparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_RegexStringComparator_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.Descriptor
            internal_static_SubstringComparator_descriptor;
    private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
            internal_static_SubstringComparator_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }
    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;
    static {
        java.lang.String[] descriptorData = {
                "\n\020Comparator.proto\"9\n\nComparator\022\014\n\004name" +
                        "\030\001 \002(\t\022\035\n\025serialized_comparator\030\002 \001(\014\"$\n" +
                        "\023ByteArrayComparable\022\r\n\005value\030\001 \001(\014\"<\n\020B" +
                        "inaryComparator\022(\n\ncomparable\030\001 \002(\0132\024.By" +
                        "teArrayComparable\"B\n\026BinaryPrefixCompara" +
                        "tor\022(\n\ncomparable\030\001 \002(\0132\024.ByteArrayCompa" +
                        "rable\"\216\001\n\rBitComparator\022(\n\ncomparable\030\001 " +
                        "\002(\0132\024.ByteArrayComparable\022,\n\nbitwise_op\030" +
                        "\002 \002(\0162\030.BitComparator.BitwiseOp\"%\n\tBitwi" +
                        "seOp\022\007\n\003AND\020\001\022\006\n\002OR\020\002\022\007\n\003XOR\020\003\"\020\n\016NullCo",
                "mparator\"P\n\025RegexStringComparator\022\017\n\007pat" +
                        "tern\030\001 \002(\t\022\025\n\rpattern_flags\030\002 \002(\005\022\017\n\007cha" +
                        "rset\030\003 \002(\t\"%\n\023SubstringComparator\022\016\n\006sub" +
                        "str\030\001 \002(\tBF\n*org.apache.hadoop.hbase.pro" +
                        "tobuf.generatedB\020ComparatorProtosH\001\210\001\001\240\001" +
                        "\001"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        internal_static_Comparator_descriptor =
                                getDescriptor().getMessageTypes().get(0);
                        internal_static_Comparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_Comparator_descriptor,
                                new java.lang.String[] { "Name", "SerializedComparator", });
                        internal_static_ByteArrayComparable_descriptor =
                                getDescriptor().getMessageTypes().get(1);
                        internal_static_ByteArrayComparable_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_ByteArrayComparable_descriptor,
                                new java.lang.String[] { "Value", });
                        internal_static_BinaryComparator_descriptor =
                                getDescriptor().getMessageTypes().get(2);
                        internal_static_BinaryComparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_BinaryComparator_descriptor,
                                new java.lang.String[] { "Comparable", });
                        internal_static_BinaryPrefixComparator_descriptor =
                                getDescriptor().getMessageTypes().get(3);
                        internal_static_BinaryPrefixComparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_BinaryPrefixComparator_descriptor,
                                new java.lang.String[] { "Comparable", });
                        internal_static_BitComparator_descriptor =
                                getDescriptor().getMessageTypes().get(4);
                        internal_static_BitComparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_BitComparator_descriptor,
                                new java.lang.String[] { "Comparable", "BitwiseOp", });
                        internal_static_NullComparator_descriptor =
                                getDescriptor().getMessageTypes().get(5);
                        internal_static_NullComparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_NullComparator_descriptor,
                                new java.lang.String[] { });
                        internal_static_RegexStringComparator_descriptor =
                                getDescriptor().getMessageTypes().get(6);
                        internal_static_RegexStringComparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_RegexStringComparator_descriptor,
                                new java.lang.String[] { "Pattern", "PatternFlags", "Charset", });
                        internal_static_SubstringComparator_descriptor =
                                getDescriptor().getMessageTypes().get(7);
                        internal_static_SubstringComparator_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_SubstringComparator_descriptor,
                                new java.lang.String[] { "Substr", });
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[] {
                        }, assigner);
    }

    // @@protoc_insertion_point(outer_class_scope)
}
