package com.orientechnologies.orient.core.storage.ridbag.sbtree;

import com.orientechnologies.common.serialization.types.OByteSerializer;
import com.orientechnologies.common.serialization.types.OIntegerSerializer;

public class AbsoluteChange implements Change {
  public static final byte TYPE = 1;
  private int value;

  AbsoluteChange(final int value) {
    this.value = value;

    checkPositive();
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public void increment() {
    value++;
  }

  @Override
  public void decrement() {
    value--;

    checkPositive();
  }

  @Override
  public int applyTo(final Integer value) {
    return this.value;
  }

  @Override
  public boolean isUndefined() {
    return true;
  }

  @Override
  public void applyDiff(final int delta) {
    value += delta;

    checkPositive();
  }

  @Override
  public byte getType() {
    return TYPE;
  }

  @Override
  public int serialize(final byte[] stream, final int offset) {
    OByteSerializer.serializeLiteral(TYPE, stream, offset);
    OIntegerSerializer.serializeLiteral(value, stream, offset + OByteSerializer.BYTE_SIZE);
    return OByteSerializer.BYTE_SIZE + OIntegerSerializer.INT_SIZE;
  }

  private void checkPositive() {
    if (value < 0)
      value = 0;
  }
}
