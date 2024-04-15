/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class PaymentRequest extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8286492638223015454L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PaymentRequest\",\"namespace\":\"avro\",\"fields\":[{\"name\":\"id\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"bookingId\",\"type\":\"string\"},{\"name\":\"price\",\"type\":\"double\"},{\"name\":\"cardNumber\",\"type\":\"string\"},{\"name\":\"cardHolderName\",\"type\":\"string\"},{\"name\":\"expirationMonth\",\"type\":\"string\"},{\"name\":\"expirationYear\",\"type\":\"string\"},{\"name\":\"cvv\",\"type\":\"int\"},{\"name\":\"currency\",\"type\":\"string\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"userEmail\",\"type\":[\"null\",\"string\"],\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<PaymentRequest> ENCODER =
      new BinaryMessageEncoder<PaymentRequest>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PaymentRequest> DECODER =
      new BinaryMessageDecoder<PaymentRequest>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<PaymentRequest> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<PaymentRequest> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<PaymentRequest>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this PaymentRequest to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a PaymentRequest from a ByteBuffer. */
  public static PaymentRequest fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence id;
  @Deprecated public java.lang.CharSequence bookingId;
  @Deprecated public double price;
  @Deprecated public java.lang.CharSequence cardNumber;
  @Deprecated public java.lang.CharSequence cardHolderName;
  @Deprecated public java.lang.CharSequence expirationMonth;
  @Deprecated public java.lang.CharSequence expirationYear;
  @Deprecated public int cvv;
  @Deprecated public java.lang.CharSequence currency;
  @Deprecated public java.lang.CharSequence status;
  @Deprecated public java.lang.CharSequence userEmail;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PaymentRequest() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param bookingId The new value for bookingId
   * @param price The new value for price
   * @param cardNumber The new value for cardNumber
   * @param cardHolderName The new value for cardHolderName
   * @param expirationMonth The new value for expirationMonth
   * @param expirationYear The new value for expirationYear
   * @param cvv The new value for cvv
   * @param currency The new value for currency
   * @param status The new value for status
   * @param userEmail The new value for userEmail
   */
  public PaymentRequest(java.lang.CharSequence id, java.lang.CharSequence bookingId, java.lang.Double price, java.lang.CharSequence cardNumber, java.lang.CharSequence cardHolderName, java.lang.CharSequence expirationMonth, java.lang.CharSequence expirationYear, java.lang.Integer cvv, java.lang.CharSequence currency, java.lang.CharSequence status, java.lang.CharSequence userEmail) {
    this.id = id;
    this.bookingId = bookingId;
    this.price = price;
    this.cardNumber = cardNumber;
    this.cardHolderName = cardHolderName;
    this.expirationMonth = expirationMonth;
    this.expirationYear = expirationYear;
    this.cvv = cvv;
    this.currency = currency;
    this.status = status;
    this.userEmail = userEmail;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return bookingId;
    case 2: return price;
    case 3: return cardNumber;
    case 4: return cardHolderName;
    case 5: return expirationMonth;
    case 6: return expirationYear;
    case 7: return cvv;
    case 8: return currency;
    case 9: return status;
    case 10: return userEmail;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.CharSequence)value$; break;
    case 1: bookingId = (java.lang.CharSequence)value$; break;
    case 2: price = (java.lang.Double)value$; break;
    case 3: cardNumber = (java.lang.CharSequence)value$; break;
    case 4: cardHolderName = (java.lang.CharSequence)value$; break;
    case 5: expirationMonth = (java.lang.CharSequence)value$; break;
    case 6: expirationYear = (java.lang.CharSequence)value$; break;
    case 7: cvv = (java.lang.Integer)value$; break;
    case 8: currency = (java.lang.CharSequence)value$; break;
    case 9: status = (java.lang.CharSequence)value$; break;
    case 10: userEmail = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.CharSequence getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'bookingId' field.
   * @return The value of the 'bookingId' field.
   */
  public java.lang.CharSequence getBookingId() {
    return bookingId;
  }

  /**
   * Sets the value of the 'bookingId' field.
   * @param value the value to set.
   */
  public void setBookingId(java.lang.CharSequence value) {
    this.bookingId = value;
  }

  /**
   * Gets the value of the 'price' field.
   * @return The value of the 'price' field.
   */
  public java.lang.Double getPrice() {
    return price;
  }

  /**
   * Sets the value of the 'price' field.
   * @param value the value to set.
   */
  public void setPrice(java.lang.Double value) {
    this.price = value;
  }

  /**
   * Gets the value of the 'cardNumber' field.
   * @return The value of the 'cardNumber' field.
   */
  public java.lang.CharSequence getCardNumber() {
    return cardNumber;
  }

  /**
   * Sets the value of the 'cardNumber' field.
   * @param value the value to set.
   */
  public void setCardNumber(java.lang.CharSequence value) {
    this.cardNumber = value;
  }

  /**
   * Gets the value of the 'cardHolderName' field.
   * @return The value of the 'cardHolderName' field.
   */
  public java.lang.CharSequence getCardHolderName() {
    return cardHolderName;
  }

  /**
   * Sets the value of the 'cardHolderName' field.
   * @param value the value to set.
   */
  public void setCardHolderName(java.lang.CharSequence value) {
    this.cardHolderName = value;
  }

  /**
   * Gets the value of the 'expirationMonth' field.
   * @return The value of the 'expirationMonth' field.
   */
  public java.lang.CharSequence getExpirationMonth() {
    return expirationMonth;
  }

  /**
   * Sets the value of the 'expirationMonth' field.
   * @param value the value to set.
   */
  public void setExpirationMonth(java.lang.CharSequence value) {
    this.expirationMonth = value;
  }

  /**
   * Gets the value of the 'expirationYear' field.
   * @return The value of the 'expirationYear' field.
   */
  public java.lang.CharSequence getExpirationYear() {
    return expirationYear;
  }

  /**
   * Sets the value of the 'expirationYear' field.
   * @param value the value to set.
   */
  public void setExpirationYear(java.lang.CharSequence value) {
    this.expirationYear = value;
  }

  /**
   * Gets the value of the 'cvv' field.
   * @return The value of the 'cvv' field.
   */
  public java.lang.Integer getCvv() {
    return cvv;
  }

  /**
   * Sets the value of the 'cvv' field.
   * @param value the value to set.
   */
  public void setCvv(java.lang.Integer value) {
    this.cvv = value;
  }

  /**
   * Gets the value of the 'currency' field.
   * @return The value of the 'currency' field.
   */
  public java.lang.CharSequence getCurrency() {
    return currency;
  }

  /**
   * Sets the value of the 'currency' field.
   * @param value the value to set.
   */
  public void setCurrency(java.lang.CharSequence value) {
    this.currency = value;
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public java.lang.CharSequence getStatus() {
    return status;
  }

  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.CharSequence value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'userEmail' field.
   * @return The value of the 'userEmail' field.
   */
  public java.lang.CharSequence getUserEmail() {
    return userEmail;
  }

  /**
   * Sets the value of the 'userEmail' field.
   * @param value the value to set.
   */
  public void setUserEmail(java.lang.CharSequence value) {
    this.userEmail = value;
  }

  /**
   * Creates a new PaymentRequest RecordBuilder.
   * @return A new PaymentRequest RecordBuilder
   */
  public static avro.PaymentRequest.Builder newBuilder() {
    return new avro.PaymentRequest.Builder();
  }

  /**
   * Creates a new PaymentRequest RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PaymentRequest RecordBuilder
   */
  public static avro.PaymentRequest.Builder newBuilder(avro.PaymentRequest.Builder other) {
    return new avro.PaymentRequest.Builder(other);
  }

  /**
   * Creates a new PaymentRequest RecordBuilder by copying an existing PaymentRequest instance.
   * @param other The existing instance to copy.
   * @return A new PaymentRequest RecordBuilder
   */
  public static avro.PaymentRequest.Builder newBuilder(avro.PaymentRequest other) {
    return new avro.PaymentRequest.Builder(other);
  }

  /**
   * RecordBuilder for PaymentRequest instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PaymentRequest>
    implements org.apache.avro.data.RecordBuilder<PaymentRequest> {

    private java.lang.CharSequence id;
    private java.lang.CharSequence bookingId;
    private double price;
    private java.lang.CharSequence cardNumber;
    private java.lang.CharSequence cardHolderName;
    private java.lang.CharSequence expirationMonth;
    private java.lang.CharSequence expirationYear;
    private int cvv;
    private java.lang.CharSequence currency;
    private java.lang.CharSequence status;
    private java.lang.CharSequence userEmail;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(avro.PaymentRequest.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.bookingId)) {
        this.bookingId = data().deepCopy(fields()[1].schema(), other.bookingId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.price)) {
        this.price = data().deepCopy(fields()[2].schema(), other.price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.cardNumber)) {
        this.cardNumber = data().deepCopy(fields()[3].schema(), other.cardNumber);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.cardHolderName)) {
        this.cardHolderName = data().deepCopy(fields()[4].schema(), other.cardHolderName);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.expirationMonth)) {
        this.expirationMonth = data().deepCopy(fields()[5].schema(), other.expirationMonth);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.expirationYear)) {
        this.expirationYear = data().deepCopy(fields()[6].schema(), other.expirationYear);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.cvv)) {
        this.cvv = data().deepCopy(fields()[7].schema(), other.cvv);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.currency)) {
        this.currency = data().deepCopy(fields()[8].schema(), other.currency);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.status)) {
        this.status = data().deepCopy(fields()[9].schema(), other.status);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.userEmail)) {
        this.userEmail = data().deepCopy(fields()[10].schema(), other.userEmail);
        fieldSetFlags()[10] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing PaymentRequest instance
     * @param other The existing instance to copy.
     */
    private Builder(avro.PaymentRequest other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.bookingId)) {
        this.bookingId = data().deepCopy(fields()[1].schema(), other.bookingId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.price)) {
        this.price = data().deepCopy(fields()[2].schema(), other.price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.cardNumber)) {
        this.cardNumber = data().deepCopy(fields()[3].schema(), other.cardNumber);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.cardHolderName)) {
        this.cardHolderName = data().deepCopy(fields()[4].schema(), other.cardHolderName);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.expirationMonth)) {
        this.expirationMonth = data().deepCopy(fields()[5].schema(), other.expirationMonth);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.expirationYear)) {
        this.expirationYear = data().deepCopy(fields()[6].schema(), other.expirationYear);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.cvv)) {
        this.cvv = data().deepCopy(fields()[7].schema(), other.cvv);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.currency)) {
        this.currency = data().deepCopy(fields()[8].schema(), other.currency);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.status)) {
        this.status = data().deepCopy(fields()[9].schema(), other.status);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.userEmail)) {
        this.userEmail = data().deepCopy(fields()[10].schema(), other.userEmail);
        fieldSetFlags()[10] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.CharSequence getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'bookingId' field.
      * @return The value.
      */
    public java.lang.CharSequence getBookingId() {
      return bookingId;
    }

    /**
      * Sets the value of the 'bookingId' field.
      * @param value The value of 'bookingId'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setBookingId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.bookingId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'bookingId' field has been set.
      * @return True if the 'bookingId' field has been set, false otherwise.
      */
    public boolean hasBookingId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'bookingId' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearBookingId() {
      bookingId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'price' field.
      * @return The value.
      */
    public java.lang.Double getPrice() {
      return price;
    }

    /**
      * Sets the value of the 'price' field.
      * @param value The value of 'price'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setPrice(double value) {
      validate(fields()[2], value);
      this.price = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'price' field has been set.
      * @return True if the 'price' field has been set, false otherwise.
      */
    public boolean hasPrice() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'price' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearPrice() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'cardNumber' field.
      * @return The value.
      */
    public java.lang.CharSequence getCardNumber() {
      return cardNumber;
    }

    /**
      * Sets the value of the 'cardNumber' field.
      * @param value The value of 'cardNumber'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setCardNumber(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.cardNumber = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'cardNumber' field has been set.
      * @return True if the 'cardNumber' field has been set, false otherwise.
      */
    public boolean hasCardNumber() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'cardNumber' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearCardNumber() {
      cardNumber = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'cardHolderName' field.
      * @return The value.
      */
    public java.lang.CharSequence getCardHolderName() {
      return cardHolderName;
    }

    /**
      * Sets the value of the 'cardHolderName' field.
      * @param value The value of 'cardHolderName'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setCardHolderName(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.cardHolderName = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'cardHolderName' field has been set.
      * @return True if the 'cardHolderName' field has been set, false otherwise.
      */
    public boolean hasCardHolderName() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'cardHolderName' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearCardHolderName() {
      cardHolderName = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'expirationMonth' field.
      * @return The value.
      */
    public java.lang.CharSequence getExpirationMonth() {
      return expirationMonth;
    }

    /**
      * Sets the value of the 'expirationMonth' field.
      * @param value The value of 'expirationMonth'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setExpirationMonth(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.expirationMonth = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'expirationMonth' field has been set.
      * @return True if the 'expirationMonth' field has been set, false otherwise.
      */
    public boolean hasExpirationMonth() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'expirationMonth' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearExpirationMonth() {
      expirationMonth = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'expirationYear' field.
      * @return The value.
      */
    public java.lang.CharSequence getExpirationYear() {
      return expirationYear;
    }

    /**
      * Sets the value of the 'expirationYear' field.
      * @param value The value of 'expirationYear'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setExpirationYear(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.expirationYear = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'expirationYear' field has been set.
      * @return True if the 'expirationYear' field has been set, false otherwise.
      */
    public boolean hasExpirationYear() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'expirationYear' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearExpirationYear() {
      expirationYear = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'cvv' field.
      * @return The value.
      */
    public java.lang.Integer getCvv() {
      return cvv;
    }

    /**
      * Sets the value of the 'cvv' field.
      * @param value The value of 'cvv'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setCvv(int value) {
      validate(fields()[7], value);
      this.cvv = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'cvv' field has been set.
      * @return True if the 'cvv' field has been set, false otherwise.
      */
    public boolean hasCvv() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'cvv' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearCvv() {
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'currency' field.
      * @return The value.
      */
    public java.lang.CharSequence getCurrency() {
      return currency;
    }

    /**
      * Sets the value of the 'currency' field.
      * @param value The value of 'currency'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setCurrency(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.currency = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'currency' field has been set.
      * @return True if the 'currency' field has been set, false otherwise.
      */
    public boolean hasCurrency() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'currency' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearCurrency() {
      currency = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public java.lang.CharSequence getStatus() {
      return status;
    }

    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setStatus(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.status = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearStatus() {
      status = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'userEmail' field.
      * @return The value.
      */
    public java.lang.CharSequence getUserEmail() {
      return userEmail;
    }

    /**
      * Sets the value of the 'userEmail' field.
      * @param value The value of 'userEmail'.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder setUserEmail(java.lang.CharSequence value) {
      validate(fields()[10], value);
      this.userEmail = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'userEmail' field has been set.
      * @return True if the 'userEmail' field has been set, false otherwise.
      */
    public boolean hasUserEmail() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'userEmail' field.
      * @return This builder.
      */
    public avro.PaymentRequest.Builder clearUserEmail() {
      userEmail = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaymentRequest build() {
      try {
        PaymentRequest record = new PaymentRequest();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.bookingId = fieldSetFlags()[1] ? this.bookingId : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.price = fieldSetFlags()[2] ? this.price : (java.lang.Double) defaultValue(fields()[2]);
        record.cardNumber = fieldSetFlags()[3] ? this.cardNumber : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.cardHolderName = fieldSetFlags()[4] ? this.cardHolderName : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.expirationMonth = fieldSetFlags()[5] ? this.expirationMonth : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.expirationYear = fieldSetFlags()[6] ? this.expirationYear : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.cvv = fieldSetFlags()[7] ? this.cvv : (java.lang.Integer) defaultValue(fields()[7]);
        record.currency = fieldSetFlags()[8] ? this.currency : (java.lang.CharSequence) defaultValue(fields()[8]);
        record.status = fieldSetFlags()[9] ? this.status : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.userEmail = fieldSetFlags()[10] ? this.userEmail : (java.lang.CharSequence) defaultValue(fields()[10]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PaymentRequest>
    WRITER$ = (org.apache.avro.io.DatumWriter<PaymentRequest>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PaymentRequest>
    READER$ = (org.apache.avro.io.DatumReader<PaymentRequest>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
