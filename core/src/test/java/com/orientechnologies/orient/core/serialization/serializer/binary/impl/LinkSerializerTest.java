/*
 * Copyright 2010-2016 OrientDB LTD (http://orientdb.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orientechnologies.orient.core.serialization.serializer.binary.impl;

import com.orientechnologies.common.serialization.types.OLongSerializer;
import com.orientechnologies.common.serialization.types.OShortSerializer;
import com.orientechnologies.orient.core.id.ORecordId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author Ilya Bershadskiy (ibersh20-at-gmail.com)
 * @since 07.02.12
 */
public class LinkSerializerTest {
  private static final int             FIELD_SIZE = OShortSerializer.SHORT_SIZE + OLongSerializer.LONG_SIZE;
  private              byte[]          stream     = new byte[FIELD_SIZE];
  private static final int             clusterId  = 5;
  private static final long            position   = 100500L;
  private              ORecordId       OBJECT;
  private              OLinkSerializer linkSerializer;

  @Before
  public void beforeClass() {
    OBJECT = new ORecordId(clusterId, position);
    linkSerializer = new OLinkSerializer();
  }

  @Test
  public void testFieldSize() {
    Assert.assertEquals(linkSerializer.getObjectSize(null), FIELD_SIZE);
  }

  @Test
  public void testSerialize() {
    linkSerializer.serialize(OBJECT, stream, 0);
    Assert.assertEquals(linkSerializer.deserialize(stream, 0), OBJECT);
  }

  @Test
  public void testSerializeInByteBuffer() {
    final int serializationOffset = 5;

    final ByteBuffer buffer = ByteBuffer.allocate(FIELD_SIZE + serializationOffset);

    buffer.position(serializationOffset);
    linkSerializer.serializeInByteBufferObject(OBJECT, buffer);

    final int binarySize = buffer.position() - serializationOffset;
    Assert.assertEquals(binarySize, FIELD_SIZE);

    buffer.position(serializationOffset);
    Assert.assertEquals(linkSerializer.getObjectSizeInByteBuffer(buffer), FIELD_SIZE);

    buffer.position(serializationOffset);
    Assert.assertEquals(linkSerializer.deserializeFromByteBufferObject(buffer), OBJECT);

    Assert.assertEquals(buffer.position() - serializationOffset, FIELD_SIZE);
  }
}
