// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: permits.proto

package com.yang.gateway.limiter;

public final class PermitsEntity {
  private PermitsEntity() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PermitsOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Permits)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *    double permitsPerSecond = 1;
     *    int32 maxBurstSeconds=2;
     * </pre>
     *
     * <code>int64 maxPermits = 1;</code>
     */
    long getMaxPermits();

    /**
     * <code>int64 storedPermits = 2;</code>
     */
    long getStoredPermits();

    /**
     * <code>int64 intervalMillis = 3;</code>
     */
    long getIntervalMillis();

    /**
     * <code>int64 nextFreeTicketMillis = 4;</code>
     */
    long getNextFreeTicketMillis();
  }
  /**
   * Protobuf type {@code Permits}
   */
  public  static final class Permits extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Permits)
      PermitsOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Permits.newBuilder() to construct.
    private Permits(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Permits() {
      maxPermits_ = 0L;
      storedPermits_ = 0L;
      intervalMillis_ = 0L;
      nextFreeTicketMillis_ = 0L;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Permits(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
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
            case 8: {

              maxPermits_ = input.readInt64();
              break;
            }
            case 16: {

              storedPermits_ = input.readInt64();
              break;
            }
            case 24: {

              intervalMillis_ = input.readInt64();
              break;
            }
            case 32: {

              nextFreeTicketMillis_ = input.readInt64();
              break;
            }
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
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
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.yang.gateway.limiter.PermitsEntity.internal_static_Permits_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.yang.gateway.limiter.PermitsEntity.internal_static_Permits_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.yang.gateway.limiter.PermitsEntity.Permits.class, com.yang.gateway.limiter.PermitsEntity.Permits.Builder.class);
    }

    public static final int MAXPERMITS_FIELD_NUMBER = 1;
    private long maxPermits_;
    /**
     * <pre>
     *    double permitsPerSecond = 1;
     *    int32 maxBurstSeconds=2;
     * </pre>
     *
     * <code>int64 maxPermits = 1;</code>
     */
    public long getMaxPermits() {
      return maxPermits_;
    }

    public static final int STOREDPERMITS_FIELD_NUMBER = 2;
    private long storedPermits_;
    /**
     * <code>int64 storedPermits = 2;</code>
     */
    public long getStoredPermits() {
      return storedPermits_;
    }

    public static final int INTERVALMILLIS_FIELD_NUMBER = 3;
    private long intervalMillis_;
    /**
     * <code>int64 intervalMillis = 3;</code>
     */
    public long getIntervalMillis() {
      return intervalMillis_;
    }

    public static final int NEXTFREETICKETMILLIS_FIELD_NUMBER = 4;
    private long nextFreeTicketMillis_;
    /**
     * <code>int64 nextFreeTicketMillis = 4;</code>
     */
    public long getNextFreeTicketMillis() {
      return nextFreeTicketMillis_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (maxPermits_ != 0L) {
        output.writeInt64(1, maxPermits_);
      }
      if (storedPermits_ != 0L) {
        output.writeInt64(2, storedPermits_);
      }
      if (intervalMillis_ != 0L) {
        output.writeInt64(3, intervalMillis_);
      }
      if (nextFreeTicketMillis_ != 0L) {
        output.writeInt64(4, nextFreeTicketMillis_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (maxPermits_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, maxPermits_);
      }
      if (storedPermits_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(2, storedPermits_);
      }
      if (intervalMillis_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, intervalMillis_);
      }
      if (nextFreeTicketMillis_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, nextFreeTicketMillis_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.yang.gateway.limiter.PermitsEntity.Permits)) {
        return super.equals(obj);
      }
      com.yang.gateway.limiter.PermitsEntity.Permits other = (com.yang.gateway.limiter.PermitsEntity.Permits) obj;

      boolean result = true;
      result = result && (getMaxPermits()
          == other.getMaxPermits());
      result = result && (getStoredPermits()
          == other.getStoredPermits());
      result = result && (getIntervalMillis()
          == other.getIntervalMillis());
      result = result && (getNextFreeTicketMillis()
          == other.getNextFreeTicketMillis());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + MAXPERMITS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getMaxPermits());
      hash = (37 * hash) + STOREDPERMITS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getStoredPermits());
      hash = (37 * hash) + INTERVALMILLIS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getIntervalMillis());
      hash = (37 * hash) + NEXTFREETICKETMILLIS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getNextFreeTicketMillis());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yang.gateway.limiter.PermitsEntity.Permits parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.yang.gateway.limiter.PermitsEntity.Permits prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Permits}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Permits)
        com.yang.gateway.limiter.PermitsEntity.PermitsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.yang.gateway.limiter.PermitsEntity.internal_static_Permits_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.yang.gateway.limiter.PermitsEntity.internal_static_Permits_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.yang.gateway.limiter.PermitsEntity.Permits.class, com.yang.gateway.limiter.PermitsEntity.Permits.Builder.class);
      }

      // Construct using com.yang.gateway.limiter.PermitsEntity.Permits.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        maxPermits_ = 0L;

        storedPermits_ = 0L;

        intervalMillis_ = 0L;

        nextFreeTicketMillis_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.yang.gateway.limiter.PermitsEntity.internal_static_Permits_descriptor;
      }

      @java.lang.Override
      public com.yang.gateway.limiter.PermitsEntity.Permits getDefaultInstanceForType() {
        return com.yang.gateway.limiter.PermitsEntity.Permits.getDefaultInstance();
      }

      @java.lang.Override
      public com.yang.gateway.limiter.PermitsEntity.Permits build() {
        com.yang.gateway.limiter.PermitsEntity.Permits result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.yang.gateway.limiter.PermitsEntity.Permits buildPartial() {
        com.yang.gateway.limiter.PermitsEntity.Permits result = new com.yang.gateway.limiter.PermitsEntity.Permits(this);
        result.maxPermits_ = maxPermits_;
        result.storedPermits_ = storedPermits_;
        result.intervalMillis_ = intervalMillis_;
        result.nextFreeTicketMillis_ = nextFreeTicketMillis_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return (Builder) super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.yang.gateway.limiter.PermitsEntity.Permits) {
          return mergeFrom((com.yang.gateway.limiter.PermitsEntity.Permits)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.yang.gateway.limiter.PermitsEntity.Permits other) {
        if (other == com.yang.gateway.limiter.PermitsEntity.Permits.getDefaultInstance()) return this;
        if (other.getMaxPermits() != 0L) {
          setMaxPermits(other.getMaxPermits());
        }
        if (other.getStoredPermits() != 0L) {
          setStoredPermits(other.getStoredPermits());
        }
        if (other.getIntervalMillis() != 0L) {
          setIntervalMillis(other.getIntervalMillis());
        }
        if (other.getNextFreeTicketMillis() != 0L) {
          setNextFreeTicketMillis(other.getNextFreeTicketMillis());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.yang.gateway.limiter.PermitsEntity.Permits parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.yang.gateway.limiter.PermitsEntity.Permits) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long maxPermits_ ;
      /**
       * <pre>
       *    double permitsPerSecond = 1;
       *    int32 maxBurstSeconds=2;
       * </pre>
       *
       * <code>int64 maxPermits = 1;</code>
       */
      public long getMaxPermits() {
        return maxPermits_;
      }
      /**
       * <pre>
       *    double permitsPerSecond = 1;
       *    int32 maxBurstSeconds=2;
       * </pre>
       *
       * <code>int64 maxPermits = 1;</code>
       */
      public Builder setMaxPermits(long value) {
        
        maxPermits_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *    double permitsPerSecond = 1;
       *    int32 maxBurstSeconds=2;
       * </pre>
       *
       * <code>int64 maxPermits = 1;</code>
       */
      public Builder clearMaxPermits() {
        
        maxPermits_ = 0L;
        onChanged();
        return this;
      }

      private long storedPermits_ ;
      /**
       * <code>int64 storedPermits = 2;</code>
       */
      public long getStoredPermits() {
        return storedPermits_;
      }
      /**
       * <code>int64 storedPermits = 2;</code>
       */
      public Builder setStoredPermits(long value) {
        
        storedPermits_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 storedPermits = 2;</code>
       */
      public Builder clearStoredPermits() {
        
        storedPermits_ = 0L;
        onChanged();
        return this;
      }

      private long intervalMillis_ ;
      /**
       * <code>int64 intervalMillis = 3;</code>
       */
      public long getIntervalMillis() {
        return intervalMillis_;
      }
      /**
       * <code>int64 intervalMillis = 3;</code>
       */
      public Builder setIntervalMillis(long value) {
        
        intervalMillis_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 intervalMillis = 3;</code>
       */
      public Builder clearIntervalMillis() {
        
        intervalMillis_ = 0L;
        onChanged();
        return this;
      }

      private long nextFreeTicketMillis_ ;
      /**
       * <code>int64 nextFreeTicketMillis = 4;</code>
       */
      public long getNextFreeTicketMillis() {
        return nextFreeTicketMillis_;
      }
      /**
       * <code>int64 nextFreeTicketMillis = 4;</code>
       */
      public Builder setNextFreeTicketMillis(long value) {
        
        nextFreeTicketMillis_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 nextFreeTicketMillis = 4;</code>
       */
      public Builder clearNextFreeTicketMillis() {
        
        nextFreeTicketMillis_ = 0L;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Permits)
    }

    // @@protoc_insertion_point(class_scope:Permits)
    private static final com.yang.gateway.limiter.PermitsEntity.Permits DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.yang.gateway.limiter.PermitsEntity.Permits();
    }

    public static com.yang.gateway.limiter.PermitsEntity.Permits getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Permits>
        PARSER = new com.google.protobuf.AbstractParser<Permits>() {
      @java.lang.Override
      public Permits parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Permits(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Permits> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Permits> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.yang.gateway.limiter.PermitsEntity.Permits getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Permits_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Permits_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rpermits.proto\"j\n\007Permits\022\022\n\nmaxPermits" +
      "\030\001 \001(\003\022\025\n\rstoredPermits\030\002 \001(\003\022\026\n\016interva" +
      "lMillis\030\003 \001(\003\022\034\n\024nextFreeTicketMillis\030\004 " +
      "\001(\003B)\n\030com.yang.gateway.limiterB\rPermits" +
      "Entityb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Permits_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Permits_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Permits_descriptor,
        new java.lang.String[] { "MaxPermits", "StoredPermits", "IntervalMillis", "NextFreeTicketMillis", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
