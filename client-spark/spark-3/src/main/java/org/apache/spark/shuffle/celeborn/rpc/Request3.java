/**
 * Autogenerated by Thrift Compiler (0.14.2)
 *
 * <p>DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package org.apache.spark.shuffle.celeborn.rpc;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(
    value = "Autogenerated by Thrift Compiler (0.14.2)",
    date = "2024-01-19")
public class Request3
    implements org.apache.thrift.TBase<Request3, Request3._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<Request3> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC =
      new org.apache.thrift.protocol.TStruct("Request3");

  private static final org.apache.thrift.protocol.TField APPLICATION_ID_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "applicationId", org.apache.thrift.protocol.TType.STRING, (short) 1);
  private static final org.apache.thrift.protocol.TField HOST_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "host", org.apache.thrift.protocol.TType.STRING, (short) 2);
  private static final org.apache.thrift.protocol.TField PORT_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "port", org.apache.thrift.protocol.TType.I32, (short) 3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY =
      new Request3StandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY =
      new Request3TupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String applicationId; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String host; // required
  public int port; // required

  /**
   * The set of fields this struct contains, along with convenience methods for finding and
   * manipulating them.
   */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    APPLICATION_ID((short) 1, "applicationId"),
    HOST((short) 2, "host"),
    PORT((short) 3, "port");

    private static final java.util.Map<java.lang.String, _Fields> byName =
        new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /** Find the _Fields constant that matches fieldId, or null if its not found. */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch (fieldId) {
        case 1: // APPLICATION_ID
          return APPLICATION_ID;
        case 2: // HOST
          return HOST;
        case 3: // PORT
          return PORT;
        default:
          return null;
      }
    }

    /** Find the _Fields constant that matches fieldId, throwing an exception if it is not found. */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null)
        throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /** Find the _Fields constant that matches name, or null if its not found. */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __PORT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap =
        new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(
        _Fields.APPLICATION_ID,
        new org.apache.thrift.meta_data.FieldMetaData(
            "applicationId",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(
        _Fields.HOST,
        new org.apache.thrift.meta_data.FieldMetaData(
            "host",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(
        _Fields.PORT,
        new org.apache.thrift.meta_data.FieldMetaData(
            "port",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Request3.class, metaDataMap);
  }

  public Request3() {}

  public Request3(java.lang.String applicationId, java.lang.String host, int port) {
    this();
    this.applicationId = applicationId;
    this.host = host;
    this.port = port;
    setPortIsSet(true);
  }

  /** Performs a deep copy on <i>other</i>. */
  public Request3(Request3 other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetApplicationId()) {
      this.applicationId = other.applicationId;
    }
    if (other.isSetHost()) {
      this.host = other.host;
    }
    this.port = other.port;
  }

  public Request3 deepCopy() {
    return new Request3(this);
  }

  @Override
  public void clear() {
    this.applicationId = null;
    this.host = null;
    setPortIsSet(false);
    this.port = 0;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getApplicationId() {
    return this.applicationId;
  }

  public Request3 setApplicationId(
      @org.apache.thrift.annotation.Nullable java.lang.String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  public void unsetApplicationId() {
    this.applicationId = null;
  }

  /** Returns true if field applicationId is set (has been assigned a value) and false otherwise */
  public boolean isSetApplicationId() {
    return this.applicationId != null;
  }

  public void setApplicationIdIsSet(boolean value) {
    if (!value) {
      this.applicationId = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getHost() {
    return this.host;
  }

  public Request3 setHost(@org.apache.thrift.annotation.Nullable java.lang.String host) {
    this.host = host;
    return this;
  }

  public void unsetHost() {
    this.host = null;
  }

  /** Returns true if field host is set (has been assigned a value) and false otherwise */
  public boolean isSetHost() {
    return this.host != null;
  }

  public void setHostIsSet(boolean value) {
    if (!value) {
      this.host = null;
    }
  }

  public int getPort() {
    return this.port;
  }

  public Request3 setPort(int port) {
    this.port = port;
    setPortIsSet(true);
    return this;
  }

  public void unsetPort() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  /** Returns true if field port is set (has been assigned a value) and false otherwise */
  public boolean isSetPort() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  public void setPortIsSet(boolean value) {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PORT_ISSET_ID, value);
  }

  public void setFieldValue(
      _Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
      case APPLICATION_ID:
        if (value == null) {
          unsetApplicationId();
        } else {
          setApplicationId((java.lang.String) value);
        }
        break;

      case HOST:
        if (value == null) {
          unsetHost();
        } else {
          setHost((java.lang.String) value);
        }
        break;

      case PORT:
        if (value == null) {
          unsetPort();
        } else {
          setPort((java.lang.Integer) value);
        }
        break;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
      case APPLICATION_ID:
        return getApplicationId();

      case HOST:
        return getHost();

      case PORT:
        return getPort();
    }
    throw new java.lang.IllegalStateException();
  }

  /**
   * Returns true if field corresponding to fieldID is set (has been assigned a value) and false
   * otherwise
   */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
      case APPLICATION_ID:
        return isSetApplicationId();
      case HOST:
        return isSetHost();
      case PORT:
        return isSetPort();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof Request3) return this.equals((Request3) that);
    return false;
  }

  public boolean equals(Request3 that) {
    if (that == null) return false;
    if (this == that) return true;

    boolean this_present_applicationId = true && this.isSetApplicationId();
    boolean that_present_applicationId = true && that.isSetApplicationId();
    if (this_present_applicationId || that_present_applicationId) {
      if (!(this_present_applicationId && that_present_applicationId)) return false;
      if (!this.applicationId.equals(that.applicationId)) return false;
    }

    boolean this_present_host = true && this.isSetHost();
    boolean that_present_host = true && that.isSetHost();
    if (this_present_host || that_present_host) {
      if (!(this_present_host && that_present_host)) return false;
      if (!this.host.equals(that.host)) return false;
    }

    boolean this_present_port = true;
    boolean that_present_port = true;
    if (this_present_port || that_present_port) {
      if (!(this_present_port && that_present_port)) return false;
      if (this.port != that.port) return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetApplicationId()) ? 131071 : 524287);
    if (isSetApplicationId()) hashCode = hashCode * 8191 + applicationId.hashCode();

    hashCode = hashCode * 8191 + ((isSetHost()) ? 131071 : 524287);
    if (isSetHost()) hashCode = hashCode * 8191 + host.hashCode();

    hashCode = hashCode * 8191 + port;

    return hashCode;
  }

  @Override
  public int compareTo(Request3 other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetApplicationId(), other.isSetApplicationId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApplicationId()) {
      lastComparison =
          org.apache.thrift.TBaseHelper.compareTo(this.applicationId, other.applicationId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetHost(), other.isSetHost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.host, other.host);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetPort(), other.isSetPort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.port, other.port);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot)
      throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Request3(");
    boolean first = true;

    sb.append("applicationId:");
    if (this.applicationId == null) {
      sb.append("null");
    } else {
      sb.append(this.applicationId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("host:");
    if (this.host == null) {
      sb.append("null");
    } else {
      sb.append(this.host);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("port:");
    sb.append(this.port);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (applicationId == null) {
      throw new org.apache.thrift.protocol.TProtocolException(
          "Required field 'applicationId' was not present! Struct: " + toString());
    }
    if (host == null) {
      throw new org.apache.thrift.protocol.TProtocolException(
          "Required field 'host' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'port' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(
          new org.apache.thrift.protocol.TCompactProtocol(
              new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in)
      throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and
      // doesn't call the default constructor.
      __isset_bitfield = 0;
      read(
          new org.apache.thrift.protocol.TCompactProtocol(
              new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class Request3StandardSchemeFactory
      implements org.apache.thrift.scheme.SchemeFactory {
    public Request3StandardScheme getScheme() {
      return new Request3StandardScheme();
    }
  }

  private static class Request3StandardScheme
      extends org.apache.thrift.scheme.StandardScheme<Request3> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Request3 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true) {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // APPLICATION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.applicationId = iprot.readString();
              struct.setApplicationIdIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HOST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.host = iprot.readString();
              struct.setHostIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.port = iprot.readI32();
              struct.setPortIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetPort()) {
        throw new org.apache.thrift.protocol.TProtocolException(
            "Required field 'port' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Request3 struct)
        throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.applicationId != null) {
        oprot.writeFieldBegin(APPLICATION_ID_FIELD_DESC);
        oprot.writeString(struct.applicationId);
        oprot.writeFieldEnd();
      }
      if (struct.host != null) {
        oprot.writeFieldBegin(HOST_FIELD_DESC);
        oprot.writeString(struct.host);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PORT_FIELD_DESC);
      oprot.writeI32(struct.port);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class Request3TupleSchemeFactory
      implements org.apache.thrift.scheme.SchemeFactory {
    public Request3TupleScheme getScheme() {
      return new Request3TupleScheme();
    }
  }

  private static class Request3TupleScheme extends org.apache.thrift.scheme.TupleScheme<Request3> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Request3 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot =
          (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.applicationId);
      oprot.writeString(struct.host);
      oprot.writeI32(struct.port);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Request3 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot =
          (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.applicationId = iprot.readString();
      struct.setApplicationIdIsSet(true);
      struct.host = iprot.readString();
      struct.setHostIsSet(true);
      struct.port = iprot.readI32();
      struct.setPortIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(
      org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme())
            ? STANDARD_SCHEME_FACTORY
            : TUPLE_SCHEME_FACTORY)
        .getScheme();
  }
}