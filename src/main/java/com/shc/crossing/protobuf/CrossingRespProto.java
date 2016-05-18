// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: CrossingResp.proto

package com.shc.crossing.protobuf;

public final class CrossingRespProto {
  private CrossingRespProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface CrossingRespOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protobuf.CrossingResp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required int64 seqId = 1;</code>
     */
    boolean hasSeqId();
    /**
     * <code>required int64 seqId = 1;</code>
     */
    long getSeqId();

    /**
     * <code>required string serviceName = 2;</code>
     */
    boolean hasServiceName();
    /**
     * <code>required string serviceName = 2;</code>
     */
    java.lang.String getServiceName();
    /**
     * <code>required string serviceName = 2;</code>
     */
    com.google.protobuf.ByteString
        getServiceNameBytes();

    /**
     * <code>required string interfaceName = 3;</code>
     */
    boolean hasInterfaceName();
    /**
     * <code>required string interfaceName = 3;</code>
     */
    java.lang.String getInterfaceName();
    /**
     * <code>required string interfaceName = 3;</code>
     */
    com.google.protobuf.ByteString
        getInterfaceNameBytes();

    /**
     * <code>required string params = 4;</code>
     */
    boolean hasParams();
    /**
     * <code>required string params = 4;</code>
     */
    java.lang.String getParams();
    /**
     * <code>required string params = 4;</code>
     */
    com.google.protobuf.ByteString
        getParamsBytes();
  }
  /**
   * Protobuf type {@code protobuf.CrossingResp}
   */
  public static final class CrossingResp extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:protobuf.CrossingResp)
      CrossingRespOrBuilder {
    // Use CrossingResp.newBuilder() to construct.
    private CrossingResp(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private CrossingResp(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final CrossingResp defaultInstance;
    public static CrossingResp getDefaultInstance() {
      return defaultInstance;
    }

    public CrossingResp getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private CrossingResp(
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
            case 8: {
              bitField0_ |= 0x00000001;
              seqId_ = input.readInt64();
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              serviceName_ = bs;
              break;
            }
            case 26: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000004;
              interfaceName_ = bs;
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000008;
              params_ = bs;
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
      return com.shc.crossing.protobuf.CrossingRespProto.internal_static_protobuf_CrossingResp_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.shc.crossing.protobuf.CrossingRespProto.internal_static_protobuf_CrossingResp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.class, com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.Builder.class);
    }

    public static com.google.protobuf.Parser<CrossingResp> PARSER =
        new com.google.protobuf.AbstractParser<CrossingResp>() {
      public CrossingResp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new CrossingResp(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<CrossingResp> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int SEQID_FIELD_NUMBER = 1;
    private long seqId_;
    /**
     * <code>required int64 seqId = 1;</code>
     */
    public boolean hasSeqId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int64 seqId = 1;</code>
     */
    public long getSeqId() {
      return seqId_;
    }

    public static final int SERVICENAME_FIELD_NUMBER = 2;
    private java.lang.Object serviceName_;
    /**
     * <code>required string serviceName = 2;</code>
     */
    public boolean hasServiceName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string serviceName = 2;</code>
     */
    public java.lang.String getServiceName() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          serviceName_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string serviceName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getServiceNameBytes() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serviceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int INTERFACENAME_FIELD_NUMBER = 3;
    private java.lang.Object interfaceName_;
    /**
     * <code>required string interfaceName = 3;</code>
     */
    public boolean hasInterfaceName() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required string interfaceName = 3;</code>
     */
    public java.lang.String getInterfaceName() {
      java.lang.Object ref = interfaceName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          interfaceName_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string interfaceName = 3;</code>
     */
    public com.google.protobuf.ByteString
        getInterfaceNameBytes() {
      java.lang.Object ref = interfaceName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        interfaceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PARAMS_FIELD_NUMBER = 4;
    private java.lang.Object params_;
    /**
     * <code>required string params = 4;</code>
     */
    public boolean hasParams() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required string params = 4;</code>
     */
    public java.lang.String getParams() {
      java.lang.Object ref = params_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          params_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string params = 4;</code>
     */
    public com.google.protobuf.ByteString
        getParamsBytes() {
      java.lang.Object ref = params_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        params_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      seqId_ = 0L;
      serviceName_ = "";
      interfaceName_ = "";
      params_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasSeqId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasServiceName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasInterfaceName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasParams()) {
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
        output.writeInt64(1, seqId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getServiceNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, getInterfaceNameBytes());
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeBytes(4, getParamsBytes());
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
          .computeInt64Size(1, seqId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getServiceNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, getInterfaceNameBytes());
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, getParamsBytes());
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

    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.shc.crossing.protobuf.CrossingRespProto.CrossingResp prototype) {
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
     * Protobuf type {@code protobuf.CrossingResp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protobuf.CrossingResp)
        com.shc.crossing.protobuf.CrossingRespProto.CrossingRespOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.shc.crossing.protobuf.CrossingRespProto.internal_static_protobuf_CrossingResp_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.shc.crossing.protobuf.CrossingRespProto.internal_static_protobuf_CrossingResp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.class, com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.Builder.class);
      }

      // Construct using com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.newBuilder()
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
        seqId_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        serviceName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        interfaceName_ = "";
        bitField0_ = (bitField0_ & ~0x00000004);
        params_ = "";
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.shc.crossing.protobuf.CrossingRespProto.internal_static_protobuf_CrossingResp_descriptor;
      }

      public com.shc.crossing.protobuf.CrossingRespProto.CrossingResp getDefaultInstanceForType() {
        return com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.getDefaultInstance();
      }

      public com.shc.crossing.protobuf.CrossingRespProto.CrossingResp build() {
        com.shc.crossing.protobuf.CrossingRespProto.CrossingResp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.shc.crossing.protobuf.CrossingRespProto.CrossingResp buildPartial() {
        com.shc.crossing.protobuf.CrossingRespProto.CrossingResp result = new com.shc.crossing.protobuf.CrossingRespProto.CrossingResp(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.seqId_ = seqId_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.serviceName_ = serviceName_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.interfaceName_ = interfaceName_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.params_ = params_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.shc.crossing.protobuf.CrossingRespProto.CrossingResp) {
          return mergeFrom((com.shc.crossing.protobuf.CrossingRespProto.CrossingResp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.shc.crossing.protobuf.CrossingRespProto.CrossingResp other) {
        if (other == com.shc.crossing.protobuf.CrossingRespProto.CrossingResp.getDefaultInstance()) return this;
        if (other.hasSeqId()) {
          setSeqId(other.getSeqId());
        }
        if (other.hasServiceName()) {
          bitField0_ |= 0x00000002;
          serviceName_ = other.serviceName_;
          onChanged();
        }
        if (other.hasInterfaceName()) {
          bitField0_ |= 0x00000004;
          interfaceName_ = other.interfaceName_;
          onChanged();
        }
        if (other.hasParams()) {
          bitField0_ |= 0x00000008;
          params_ = other.params_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasSeqId()) {
          
          return false;
        }
        if (!hasServiceName()) {
          
          return false;
        }
        if (!hasInterfaceName()) {
          
          return false;
        }
        if (!hasParams()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.shc.crossing.protobuf.CrossingRespProto.CrossingResp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.shc.crossing.protobuf.CrossingRespProto.CrossingResp) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private long seqId_ ;
      /**
       * <code>required int64 seqId = 1;</code>
       */
      public boolean hasSeqId() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int64 seqId = 1;</code>
       */
      public long getSeqId() {
        return seqId_;
      }
      /**
       * <code>required int64 seqId = 1;</code>
       */
      public Builder setSeqId(long value) {
        bitField0_ |= 0x00000001;
        seqId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 seqId = 1;</code>
       */
      public Builder clearSeqId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        seqId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object serviceName_ = "";
      /**
       * <code>required string serviceName = 2;</code>
       */
      public boolean hasServiceName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string serviceName = 2;</code>
       */
      public java.lang.String getServiceName() {
        java.lang.Object ref = serviceName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            serviceName_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string serviceName = 2;</code>
       */
      public com.google.protobuf.ByteString
          getServiceNameBytes() {
        java.lang.Object ref = serviceName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          serviceName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string serviceName = 2;</code>
       */
      public Builder setServiceName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        serviceName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string serviceName = 2;</code>
       */
      public Builder clearServiceName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        serviceName_ = getDefaultInstance().getServiceName();
        onChanged();
        return this;
      }
      /**
       * <code>required string serviceName = 2;</code>
       */
      public Builder setServiceNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        serviceName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object interfaceName_ = "";
      /**
       * <code>required string interfaceName = 3;</code>
       */
      public boolean hasInterfaceName() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required string interfaceName = 3;</code>
       */
      public java.lang.String getInterfaceName() {
        java.lang.Object ref = interfaceName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            interfaceName_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string interfaceName = 3;</code>
       */
      public com.google.protobuf.ByteString
          getInterfaceNameBytes() {
        java.lang.Object ref = interfaceName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          interfaceName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string interfaceName = 3;</code>
       */
      public Builder setInterfaceName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        interfaceName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string interfaceName = 3;</code>
       */
      public Builder clearInterfaceName() {
        bitField0_ = (bitField0_ & ~0x00000004);
        interfaceName_ = getDefaultInstance().getInterfaceName();
        onChanged();
        return this;
      }
      /**
       * <code>required string interfaceName = 3;</code>
       */
      public Builder setInterfaceNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        interfaceName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object params_ = "";
      /**
       * <code>required string params = 4;</code>
       */
      public boolean hasParams() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required string params = 4;</code>
       */
      public java.lang.String getParams() {
        java.lang.Object ref = params_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            params_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string params = 4;</code>
       */
      public com.google.protobuf.ByteString
          getParamsBytes() {
        java.lang.Object ref = params_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          params_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string params = 4;</code>
       */
      public Builder setParams(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        params_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string params = 4;</code>
       */
      public Builder clearParams() {
        bitField0_ = (bitField0_ & ~0x00000008);
        params_ = getDefaultInstance().getParams();
        onChanged();
        return this;
      }
      /**
       * <code>required string params = 4;</code>
       */
      public Builder setParamsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        params_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protobuf.CrossingResp)
    }

    static {
      defaultInstance = new CrossingResp(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:protobuf.CrossingResp)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_CrossingResp_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protobuf_CrossingResp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022CrossingResp.proto\022\010protobuf\"Y\n\014Crossi" +
      "ngResp\022\r\n\005seqId\030\001 \002(\003\022\023\n\013serviceName\030\002 \002" +
      "(\t\022\025\n\rinterfaceName\030\003 \002(\t\022\016\n\006params\030\004 \002(" +
      "\tB.\n\031com.shc.crossing.protobufB\021Crossing" +
      "RespProto"
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
    internal_static_protobuf_CrossingResp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protobuf_CrossingResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_protobuf_CrossingResp_descriptor,
        new java.lang.String[] { "SeqId", "ServiceName", "InterfaceName", "Params", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}