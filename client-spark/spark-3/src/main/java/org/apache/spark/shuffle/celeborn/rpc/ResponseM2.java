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
public class ResponseM2
    implements org.apache.thrift.TBase<ResponseM2, ResponseM2._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<ResponseM2> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC =
      new org.apache.thrift.protocol.TStruct("ResponseM2");

  private static final org.apache.thrift.protocol.TField RES_CODE_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "resCode", org.apache.thrift.protocol.TType.I32, (short) 1);
  private static final org.apache.thrift.protocol.TField OUTPUT_SIZE_FIELD_DESC =
      new org.apache.thrift.protocol.TField(
          "outputSize", org.apache.thrift.protocol.TType.LIST, (short) 2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY =
      new ResponseM2StandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY =
      new ResponseM2TupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable RESCODE resCode; // required
  public @org.apache.thrift.annotation.Nullable java.util.List<
          java.util.List<java.util.List<java.lang.Long>>>
      outputSize; // required

  /**
   * The set of fields this struct contains, along with convenience methods for finding and
   * manipulating them.
   */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RES_CODE((short) 1, "resCode"),
    OUTPUT_SIZE((short) 2, "outputSize");

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
        case 1: // RES_CODE
          return RES_CODE;
        case 2: // OUTPUT_SIZE
          return OUTPUT_SIZE;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap =
        new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(
        _Fields.RES_CODE,
        new org.apache.thrift.meta_data.FieldMetaData(
            "resCode",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.FieldValueMetaData(
                org.apache.thrift.protocol.TType.ENUM, "RESCODE")));
    tmpMap.put(
        _Fields.OUTPUT_SIZE,
        new org.apache.thrift.meta_data.FieldMetaData(
            "outputSize",
            org.apache.thrift.TFieldRequirementType.REQUIRED,
            new org.apache.thrift.meta_data.ListMetaData(
                org.apache.thrift.protocol.TType.LIST,
                new org.apache.thrift.meta_data.ListMetaData(
                    org.apache.thrift.protocol.TType.LIST,
                    new org.apache.thrift.meta_data.ListMetaData(
                        org.apache.thrift.protocol.TType.LIST,
                        new org.apache.thrift.meta_data.FieldValueMetaData(
                            org.apache.thrift.protocol.TType.I64))))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ResponseM2.class, metaDataMap);
  }

  public ResponseM2() {}

  public ResponseM2(
      RESCODE resCode, java.util.List<java.util.List<java.util.List<java.lang.Long>>> outputSize) {
    this();
    this.resCode = resCode;
    this.outputSize = outputSize;
  }

  /** Performs a deep copy on <i>other</i>. */
  public ResponseM2(ResponseM2 other) {
    if (other.isSetResCode()) {
      this.resCode = other.resCode;
    }
    if (other.isSetOutputSize()) {
      java.util.List<java.util.List<java.util.List<java.lang.Long>>> __this__outputSize =
          new java.util.ArrayList<java.util.List<java.util.List<java.lang.Long>>>(
              other.outputSize.size());
      for (java.util.List<java.util.List<java.lang.Long>> other_element : other.outputSize) {
        java.util.List<java.util.List<java.lang.Long>> __this__outputSize_copy =
            new java.util.ArrayList<java.util.List<java.lang.Long>>(other_element.size());
        for (java.util.List<java.lang.Long> other_element_element : other_element) {
          java.util.List<java.lang.Long> __this__outputSize_copy_copy =
              new java.util.ArrayList<java.lang.Long>(other_element_element);
          __this__outputSize_copy.add(__this__outputSize_copy_copy);
        }
        __this__outputSize.add(__this__outputSize_copy);
      }
      this.outputSize = __this__outputSize;
    }
  }

  public ResponseM2 deepCopy() {
    return new ResponseM2(this);
  }

  @Override
  public void clear() {
    this.resCode = null;
    this.outputSize = null;
  }

  @org.apache.thrift.annotation.Nullable
  public RESCODE getResCode() {
    return this.resCode;
  }

  public ResponseM2 setResCode(@org.apache.thrift.annotation.Nullable RESCODE resCode) {
    this.resCode = resCode;
    return this;
  }

  public void unsetResCode() {
    this.resCode = null;
  }

  /** Returns true if field resCode is set (has been assigned a value) and false otherwise */
  public boolean isSetResCode() {
    return this.resCode != null;
  }

  public void setResCodeIsSet(boolean value) {
    if (!value) {
      this.resCode = null;
    }
  }

  public int getOutputSizeSize() {
    return (this.outputSize == null) ? 0 : this.outputSize.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.util.List<java.util.List<java.lang.Long>>>
      getOutputSizeIterator() {
    return (this.outputSize == null) ? null : this.outputSize.iterator();
  }

  public void addToOutputSize(java.util.List<java.util.List<java.lang.Long>> elem) {
    if (this.outputSize == null) {
      this.outputSize = new java.util.ArrayList<java.util.List<java.util.List<java.lang.Long>>>();
    }
    this.outputSize.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.util.List<java.util.List<java.lang.Long>>> getOutputSize() {
    return this.outputSize;
  }

  public ResponseM2 setOutputSize(
      @org.apache.thrift.annotation.Nullable
          java.util.List<java.util.List<java.util.List<java.lang.Long>>> outputSize) {
    this.outputSize = outputSize;
    return this;
  }

  public void unsetOutputSize() {
    this.outputSize = null;
  }

  /** Returns true if field outputSize is set (has been assigned a value) and false otherwise */
  public boolean isSetOutputSize() {
    return this.outputSize != null;
  }

  public void setOutputSizeIsSet(boolean value) {
    if (!value) {
      this.outputSize = null;
    }
  }

  public void setFieldValue(
      _Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
      case RES_CODE:
        if (value == null) {
          unsetResCode();
        } else {
          setResCode((RESCODE) value);
        }
        break;

      case OUTPUT_SIZE:
        if (value == null) {
          unsetOutputSize();
        } else {
          setOutputSize((java.util.List<java.util.List<java.util.List<java.lang.Long>>>) value);
        }
        break;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
      case RES_CODE:
        return getResCode();

      case OUTPUT_SIZE:
        return getOutputSize();
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
      case RES_CODE:
        return isSetResCode();
      case OUTPUT_SIZE:
        return isSetOutputSize();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof ResponseM2) return this.equals((ResponseM2) that);
    return false;
  }

  public boolean equals(ResponseM2 that) {
    if (that == null) return false;
    if (this == that) return true;

    boolean this_present_resCode = true && this.isSetResCode();
    boolean that_present_resCode = true && that.isSetResCode();
    if (this_present_resCode || that_present_resCode) {
      if (!(this_present_resCode && that_present_resCode)) return false;
      if (!this.resCode.equals(that.resCode)) return false;
    }

    boolean this_present_outputSize = true && this.isSetOutputSize();
    boolean that_present_outputSize = true && that.isSetOutputSize();
    if (this_present_outputSize || that_present_outputSize) {
      if (!(this_present_outputSize && that_present_outputSize)) return false;
      if (!this.outputSize.equals(that.outputSize)) return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetResCode()) ? 131071 : 524287);
    if (isSetResCode()) hashCode = hashCode * 8191 + resCode.getValue();

    hashCode = hashCode * 8191 + ((isSetOutputSize()) ? 131071 : 524287);
    if (isSetOutputSize()) hashCode = hashCode * 8191 + outputSize.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ResponseM2 other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetResCode(), other.isSetResCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resCode, other.resCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetOutputSize(), other.isSetOutputSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutputSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outputSize, other.outputSize);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ResponseM2(");
    boolean first = true;

    sb.append("resCode:");
    if (this.resCode == null) {
      sb.append("null");
    } else {
      sb.append(this.resCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("outputSize:");
    if (this.outputSize == null) {
      sb.append("null");
    } else {
      sb.append(this.outputSize);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (resCode == null) {
      throw new org.apache.thrift.protocol.TProtocolException(
          "Required field 'resCode' was not present! Struct: " + toString());
    }
    if (outputSize == null) {
      throw new org.apache.thrift.protocol.TProtocolException(
          "Required field 'outputSize' was not present! Struct: " + toString());
    }
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
      read(
          new org.apache.thrift.protocol.TCompactProtocol(
              new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ResponseM2StandardSchemeFactory
      implements org.apache.thrift.scheme.SchemeFactory {
    public ResponseM2StandardScheme getScheme() {
      return new ResponseM2StandardScheme();
    }
  }

  private static class ResponseM2StandardScheme
      extends org.apache.thrift.scheme.StandardScheme<ResponseM2> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ResponseM2 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true) {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // RES_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.resCode =
                  org.apache.spark.shuffle.celeborn.rpc.RESCODE.findByValue(iprot.readI32());
              struct.setResCodeIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OUTPUT_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list96 = iprot.readListBegin();
                struct.outputSize =
                    new java.util.ArrayList<java.util.List<java.util.List<java.lang.Long>>>(
                        _list96.size);
                @org.apache.thrift.annotation.Nullable
                java.util.List<java.util.List<java.lang.Long>> _elem97;
                for (int _i98 = 0; _i98 < _list96.size; ++_i98) {
                  {
                    org.apache.thrift.protocol.TList _list99 = iprot.readListBegin();
                    _elem97 = new java.util.ArrayList<java.util.List<java.lang.Long>>(_list99.size);
                    @org.apache.thrift.annotation.Nullable java.util.List<java.lang.Long> _elem100;
                    for (int _i101 = 0; _i101 < _list99.size; ++_i101) {
                      {
                        org.apache.thrift.protocol.TList _list102 = iprot.readListBegin();
                        _elem100 = new java.util.ArrayList<java.lang.Long>(_list102.size);
                        long _elem103;
                        for (int _i104 = 0; _i104 < _list102.size; ++_i104) {
                          _elem103 = iprot.readI64();
                          _elem100.add(_elem103);
                        }
                        iprot.readListEnd();
                      }
                      _elem97.add(_elem100);
                    }
                    iprot.readListEnd();
                  }
                  struct.outputSize.add(_elem97);
                }
                iprot.readListEnd();
              }
              struct.setOutputSizeIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ResponseM2 struct)
        throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resCode != null) {
        oprot.writeFieldBegin(RES_CODE_FIELD_DESC);
        oprot.writeI32(struct.resCode.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.outputSize != null) {
        oprot.writeFieldBegin(OUTPUT_SIZE_FIELD_DESC);
        {
          oprot.writeListBegin(
              new org.apache.thrift.protocol.TList(
                  org.apache.thrift.protocol.TType.LIST, struct.outputSize.size()));
          for (java.util.List<java.util.List<java.lang.Long>> _iter105 : struct.outputSize) {
            {
              oprot.writeListBegin(
                  new org.apache.thrift.protocol.TList(
                      org.apache.thrift.protocol.TType.LIST, _iter105.size()));
              for (java.util.List<java.lang.Long> _iter106 : _iter105) {
                {
                  oprot.writeListBegin(
                      new org.apache.thrift.protocol.TList(
                          org.apache.thrift.protocol.TType.I64, _iter106.size()));
                  for (long _iter107 : _iter106) {
                    oprot.writeI64(_iter107);
                  }
                  oprot.writeListEnd();
                }
              }
              oprot.writeListEnd();
            }
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class ResponseM2TupleSchemeFactory
      implements org.apache.thrift.scheme.SchemeFactory {
    public ResponseM2TupleScheme getScheme() {
      return new ResponseM2TupleScheme();
    }
  }

  private static class ResponseM2TupleScheme
      extends org.apache.thrift.scheme.TupleScheme<ResponseM2> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ResponseM2 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot =
          (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.resCode.getValue());
      {
        oprot.writeI32(struct.outputSize.size());
        for (java.util.List<java.util.List<java.lang.Long>> _iter108 : struct.outputSize) {
          {
            oprot.writeI32(_iter108.size());
            for (java.util.List<java.lang.Long> _iter109 : _iter108) {
              {
                oprot.writeI32(_iter109.size());
                for (long _iter110 : _iter109) {
                  oprot.writeI64(_iter110);
                }
              }
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ResponseM2 struct)
        throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot =
          (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.resCode = org.apache.spark.shuffle.celeborn.rpc.RESCODE.findByValue(iprot.readI32());
      struct.setResCodeIsSet(true);
      {
        org.apache.thrift.protocol.TList _list111 =
            iprot.readListBegin(org.apache.thrift.protocol.TType.LIST);
        struct.outputSize =
            new java.util.ArrayList<java.util.List<java.util.List<java.lang.Long>>>(_list111.size);
        @org.apache.thrift.annotation.Nullable
        java.util.List<java.util.List<java.lang.Long>> _elem112;
        for (int _i113 = 0; _i113 < _list111.size; ++_i113) {
          {
            org.apache.thrift.protocol.TList _list114 =
                iprot.readListBegin(org.apache.thrift.protocol.TType.LIST);
            _elem112 = new java.util.ArrayList<java.util.List<java.lang.Long>>(_list114.size);
            @org.apache.thrift.annotation.Nullable java.util.List<java.lang.Long> _elem115;
            for (int _i116 = 0; _i116 < _list114.size; ++_i116) {
              {
                org.apache.thrift.protocol.TList _list117 =
                    iprot.readListBegin(org.apache.thrift.protocol.TType.I64);
                _elem115 = new java.util.ArrayList<java.lang.Long>(_list117.size);
                long _elem118;
                for (int _i119 = 0; _i119 < _list117.size; ++_i119) {
                  _elem118 = iprot.readI64();
                  _elem115.add(_elem118);
                }
              }
              _elem112.add(_elem115);
            }
          }
          struct.outputSize.add(_elem112);
        }
      }
      struct.setOutputSizeIsSet(true);
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
