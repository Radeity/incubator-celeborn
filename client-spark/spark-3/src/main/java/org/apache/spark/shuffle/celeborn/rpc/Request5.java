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
public class Request5
    implements org.apache.thrift.TBase<Request5, Request5._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<Request5> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC =
      new org.apache.thrift.protocol.TStruct("Request5");

  private static final org.apache.thrift.protocol.TField MAP_STAGE_ID_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "mapStageId", org.apache.thrift.protocol.TType.I32, (short) 1);
  private static final org.apache.thrift.protocol.TField NUM_MAPPERS_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "numMappers", org.apache.thrift.protocol.TType.I32, (short) 2);
  private static final org.apache.thrift.protocol.TField NUM_REDUCERS_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "numReducers", org.apache.thrift.protocol.TType.I32, (short) 3);
  private static final org.apache.thrift.protocol.TField SHUFFLE_ID_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "shuffleId", org.apache.thrift.protocol.TType.I32, (short) 4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY =
      new Request5StandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY =
      new Request5TupleSchemeFactory();

  public int mapStageId; // required
  public int numMappers; // required
  public int numReducers; // required
  public int shuffleId; // required

  /**
   * The set of fields this struct contains, along with convenience methods for finding and
   * manipulating them.
   */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MAP_STAGE_ID((short) 1, "mapStageId"),
    NUM_MAPPERS((short) 2, "numMappers"),
    NUM_REDUCERS((short) 3, "numReducers"),
    SHUFFLE_ID((short) 4, "shuffleId");

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
        case 1: // MAP_STAGE_ID
          return MAP_STAGE_ID;
        case 2: // NUM_MAPPERS
          return NUM_MAPPERS;
        case 3: // NUM_REDUCERS
          return NUM_REDUCERS;
        case 4: // SHUFFLE_ID
          return SHUFFLE_ID;
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
  private static final int __MAPSTAGEID_ISSET_ID = 0;
  private static final int __NUMMAPPERS_ISSET_ID = 1;
  private static final int __NUMREDUCERS_ISSET_ID = 2;
  private static final int __SHUFFLEID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap =
        new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(
        _Fields.MAP_STAGE_ID,
        new org.apache.thrift.meta_data.FieldMetaData(
            "mapStageId",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(
        _Fields.NUM_MAPPERS,
        new org.apache.thrift.meta_data.FieldMetaData(
            "numMappers",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(
        _Fields.NUM_REDUCERS,
        new org.apache.thrift.meta_data.FieldMetaData(
            "numReducers",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(
        _Fields.SHUFFLE_ID,
        new org.apache.thrift.meta_data.FieldMetaData(
            "shuffleId",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Request5.class, metaDataMap);
  }

  public Request5() {}

  public Request5(int mapStageId, int numMappers, int numReducers, int shuffleId) {
    this();
    this.mapStageId = mapStageId;
    setMapStageIdIsSet(true);
    this.numMappers = numMappers;
    setNumMappersIsSet(true);
    this.numReducers = numReducers;
    setNumReducersIsSet(true);
    this.shuffleId = shuffleId;
    setShuffleIdIsSet(true);
  }

  /** Performs a deep copy on <i>other</i>. */
  public Request5(Request5 other) {
    __isset_bitfield = other.__isset_bitfield;
    this.mapStageId = other.mapStageId;
    this.numMappers = other.numMappers;
    this.numReducers = other.numReducers;
    this.shuffleId = other.shuffleId;
  }

  public Request5 deepCopy() {
    return new Request5(this);
  }

  @Override
  public void clear() {
    setMapStageIdIsSet(false);
    this.mapStageId = 0;
    setNumMappersIsSet(false);
    this.numMappers = 0;
    setNumReducersIsSet(false);
    this.numReducers = 0;
    setShuffleIdIsSet(false);
    this.shuffleId = 0;
  }

  public int getMapStageId() {
    return this.mapStageId;
  }

  public Request5 setMapStageId(int mapStageId) {
    this.mapStageId = mapStageId;
    setMapStageIdIsSet(true);
    return this;
  }

  public void unsetMapStageId() {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MAPSTAGEID_ISSET_ID);
  }

  /** Returns true if field mapStageId is set (has been assigned a value) and false otherwise */
  public boolean isSetMapStageId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MAPSTAGEID_ISSET_ID);
  }

  public void setMapStageIdIsSet(boolean value) {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MAPSTAGEID_ISSET_ID, value);
  }

  public int getNumMappers() {
    return this.numMappers;
  }

  public Request5 setNumMappers(int numMappers) {
    this.numMappers = numMappers;
    setNumMappersIsSet(true);
    return this;
  }

  public void unsetNumMappers() {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NUMMAPPERS_ISSET_ID);
  }

  /** Returns true if field numMappers is set (has been assigned a value) and false otherwise */
  public boolean isSetNumMappers() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NUMMAPPERS_ISSET_ID);
  }

  public void setNumMappersIsSet(boolean value) {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NUMMAPPERS_ISSET_ID, value);
  }

  public int getNumReducers() {
    return this.numReducers;
  }

  public Request5 setNumReducers(int numReducers) {
    this.numReducers = numReducers;
    setNumReducersIsSet(true);
    return this;
  }

  public void unsetNumReducers() {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NUMREDUCERS_ISSET_ID);
  }

  /** Returns true if field numReducers is set (has been assigned a value) and false otherwise */
  public boolean isSetNumReducers() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NUMREDUCERS_ISSET_ID);
  }

  public void setNumReducersIsSet(boolean value) {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NUMREDUCERS_ISSET_ID, value);
  }

  public int getShuffleId() {
    return this.shuffleId;
  }

  public Request5 setShuffleId(int shuffleId) {
    this.shuffleId = shuffleId;
    setShuffleIdIsSet(true);
    return this;
  }

  public void unsetShuffleId() {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SHUFFLEID_ISSET_ID);
  }

  /** Returns true if field shuffleId is set (has been assigned a value) and false otherwise */
  public boolean isSetShuffleId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SHUFFLEID_ISSET_ID);
  }

  public void setShuffleIdIsSet(boolean value) {
    __isset_bitfield =
        org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SHUFFLEID_ISSET_ID, value);
  }

  public void setFieldValue(
      _Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
      case MAP_STAGE_ID:
        if (value == null) {
          unsetMapStageId();
        } else {
          setMapStageId((java.lang.Integer) value);
        }
        break;

      case NUM_MAPPERS:
        if (value == null) {
          unsetNumMappers();
        } else {
          setNumMappers((java.lang.Integer) value);
        }
        break;

      case NUM_REDUCERS:
        if (value == null) {
          unsetNumReducers();
        } else {
          setNumReducers((java.lang.Integer) value);
        }
        break;

      case SHUFFLE_ID:
        if (value == null) {
          unsetShuffleId();
        } else {
          setShuffleId((java.lang.Integer) value);
        }
        break;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
      case MAP_STAGE_ID:
        return getMapStageId();

      case NUM_MAPPERS:
        return getNumMappers();

      case NUM_REDUCERS:
        return getNumReducers();

      case SHUFFLE_ID:
        return getShuffleId();
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
      case MAP_STAGE_ID:
        return isSetMapStageId();
      case NUM_MAPPERS:
        return isSetNumMappers();
      case NUM_REDUCERS:
        return isSetNumReducers();
      case SHUFFLE_ID:
        return isSetShuffleId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof Request5) return this.equals((Request5) that);
    return false;
  }

  public boolean equals(Request5 that) {
    if (that == null) return false;
    if (this == that) return true;

    boolean this_present_mapStageId = true;
    boolean that_present_mapStageId = true;
    if (this_present_mapStageId || that_present_mapStageId) {
      if (!(this_present_mapStageId && that_present_mapStageId)) return false;
      if (this.mapStageId != that.mapStageId) return false;
    }

    boolean this_present_numMappers = true;
    boolean that_present_numMappers = true;
    if (this_present_numMappers || that_present_numMappers) {
      if (!(this_present_numMappers && that_present_numMappers)) return false;
      if (this.numMappers != that.numMappers) return false;
    }

    boolean this_present_numReducers = true;
    boolean that_present_numReducers = true;
    if (this_present_numReducers || that_present_numReducers) {
      if (!(this_present_numReducers && that_present_numReducers)) return false;
      if (this.numReducers != that.numReducers) return false;
    }

    boolean this_present_shuffleId = true;
    boolean that_present_shuffleId = true;
    if (this_present_shuffleId || that_present_shuffleId) {
      if (!(this_present_shuffleId && that_present_shuffleId)) return false;
      if (this.shuffleId != that.shuffleId) return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + mapStageId;

    hashCode = hashCode * 8191 + numMappers;

    hashCode = hashCode * 8191 + numReducers;

    hashCode = hashCode * 8191 + shuffleId;

    return hashCode;
  }

  @Override
  public int compareTo(Request5 other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetMapStageId(), other.isSetMapStageId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMapStageId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mapStageId, other.mapStageId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetNumMappers(), other.isSetNumMappers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumMappers()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numMappers, other.numMappers);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetNumReducers(), other.isSetNumReducers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumReducers()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numReducers, other.numReducers);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetShuffleId(), other.isSetShuffleId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShuffleId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shuffleId, other.shuffleId);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Request5(");
    boolean first = true;

    sb.append("mapStageId:");
    sb.append(this.mapStageId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("numMappers:");
    sb.append(this.numMappers);
    first = false;
    if (!first) sb.append(", ");
    sb.append("numReducers:");
    sb.append(this.numReducers);
    first = false;
    if (!first) sb.append(", ");
    sb.append("shuffleId:");
    sb.append(this.shuffleId);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'mapStageId' because it's a primitive and you chose the non-beans
    // generator.
    // alas, we cannot check 'numMappers' because it's a primitive and you chose the non-beans
    // generator.
    // alas, we cannot check 'numReducers' because it's a primitive and you chose the non-beans
    // generator.
    // alas, we cannot check 'shuffleId' because it's a primitive and you chose the non-beans
    // generator.
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

  private static class Request5StandardSchemeFactory
      implements org.apache.thrift.scheme.SchemeFactory {
    public Request5StandardScheme getScheme() {
      return new Request5StandardScheme();
    }
  }

  private static class Request5StandardScheme
      extends org.apache.thrift.scheme.StandardScheme<Request5> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Request5 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true) {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // MAP_STAGE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.mapStageId = iprot.readI32();
              struct.setMapStageIdIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NUM_MAPPERS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.numMappers = iprot.readI32();
              struct.setNumMappersIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NUM_REDUCERS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.numReducers = iprot.readI32();
              struct.setNumReducersIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SHUFFLE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.shuffleId = iprot.readI32();
              struct.setShuffleIdIsSet(true);
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
      if (!struct.isSetMapStageId()) {
        throw new org.apache.thrift.protocol.TProtocolException(
            "Required field 'mapStageId' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetNumMappers()) {
        throw new org.apache.thrift.protocol.TProtocolException(
            "Required field 'numMappers' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetNumReducers()) {
        throw new org.apache.thrift.protocol.TProtocolException(
            "Required field 'numReducers' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetShuffleId()) {
        throw new org.apache.thrift.protocol.TProtocolException(
            "Required field 'shuffleId' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Request5 struct)
        throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(MAP_STAGE_ID_FIELD_DESC);
      oprot.writeI32(struct.mapStageId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NUM_MAPPERS_FIELD_DESC);
      oprot.writeI32(struct.numMappers);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NUM_REDUCERS_FIELD_DESC);
      oprot.writeI32(struct.numReducers);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SHUFFLE_ID_FIELD_DESC);
      oprot.writeI32(struct.shuffleId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class Request5TupleSchemeFactory
      implements org.apache.thrift.scheme.SchemeFactory {
    public Request5TupleScheme getScheme() {
      return new Request5TupleScheme();
    }
  }

  private static class Request5TupleScheme extends org.apache.thrift.scheme.TupleScheme<Request5> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Request5 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot =
          (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.mapStageId);
      oprot.writeI32(struct.numMappers);
      oprot.writeI32(struct.numReducers);
      oprot.writeI32(struct.shuffleId);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Request5 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot =
          (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.mapStageId = iprot.readI32();
      struct.setMapStageIdIsSet(true);
      struct.numMappers = iprot.readI32();
      struct.setNumMappersIsSet(true);
      struct.numReducers = iprot.readI32();
      struct.setNumReducersIsSet(true);
      struct.shuffleId = iprot.readI32();
      struct.setShuffleIdIsSet(true);
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