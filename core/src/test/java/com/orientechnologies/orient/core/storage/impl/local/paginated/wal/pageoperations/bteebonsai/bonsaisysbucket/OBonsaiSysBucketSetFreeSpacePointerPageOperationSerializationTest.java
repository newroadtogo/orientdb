package com.orientechnologies.orient.core.storage.impl.local.paginated.wal.pageoperations.bteebonsai.bonsaisysbucket;

import com.orientechnologies.orient.core.storage.impl.local.paginated.wal.OOperationUnitId;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OBonsaiSysBucketSetFreeSpacePointerPageOperationSerializationTest {
  @Test
  public void testStreamSerialization() {
    final int fileId = 345;
    final int pageIndex = 543;
    final OOperationUnitId operationUnitId = OOperationUnitId.generateId();

    final int prevPageIndex = 789;
    final int prevPageOffset = 987;

    OBonsaiSysBucketSetFreeSpacePointerPageOperation operation = new OBonsaiSysBucketSetFreeSpacePointerPageOperation(prevPageIndex,
        prevPageOffset);

    operation.setOperationUnitId(operationUnitId);
    operation.setFileId(fileId);
    operation.setPageIndex(pageIndex);

    final int serializedSize = operation.serializedSize();
    final byte[] stream = new byte[serializedSize + 1];
    int offset = operation.toStream(stream, 1);
    Assert.assertEquals(serializedSize + 1, offset);

    OBonsaiSysBucketSetFreeSpacePointerPageOperation restoredOperation = new OBonsaiSysBucketSetFreeSpacePointerPageOperation();
    offset = restoredOperation.fromStream(stream, 1);
    Assert.assertEquals(serializedSize + 1, offset);
    Assert.assertEquals(fileId, restoredOperation.getFileId());
    Assert.assertEquals(pageIndex, restoredOperation.getPageIndex());
    Assert.assertEquals(operationUnitId, restoredOperation.getOperationUnitId());
    Assert.assertEquals(prevPageIndex, restoredOperation.getPrevPointerPageIndex());
    Assert.assertEquals(prevPageOffset, restoredOperation.getPrevPointerPageOffset());
  }

  @Test
  public void testBufferSerialization() {
    final int fileId = 345;
    final int pageIndex = 543;
    final OOperationUnitId operationUnitId = OOperationUnitId.generateId();

    final int prevPageIndex = 789;
    final int prevPageOffset = 987;

    OBonsaiSysBucketSetFreeSpacePointerPageOperation operation = new OBonsaiSysBucketSetFreeSpacePointerPageOperation(prevPageIndex,
        prevPageOffset);

    operation.setOperationUnitId(operationUnitId);
    operation.setFileId(fileId);
    operation.setPageIndex(pageIndex);

    final int serializedSize = operation.serializedSize();
    final ByteBuffer buffer = ByteBuffer.allocate(serializedSize + 1).order(ByteOrder.nativeOrder());
    buffer.position(1);

    operation.toStream(buffer);
    Assert.assertEquals(serializedSize + 1, buffer.position());

    OBonsaiSysBucketSetFreeSpacePointerPageOperation restoredOperation = new OBonsaiSysBucketSetFreeSpacePointerPageOperation();
    int offset = restoredOperation.fromStream(buffer.array(), 1);
    Assert.assertEquals(serializedSize + 1, offset);
    Assert.assertEquals(fileId, restoredOperation.getFileId());
    Assert.assertEquals(pageIndex, restoredOperation.getPageIndex());
    Assert.assertEquals(operationUnitId, restoredOperation.getOperationUnitId());
    Assert.assertEquals(prevPageIndex, restoredOperation.getPrevPointerPageIndex());
    Assert.assertEquals(prevPageOffset, restoredOperation.getPrevPointerPageOffset());
  }
}