package org.zalando.nakadi.service.publishing;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DecoderFactory;
import org.springframework.stereotype.Service;
import org.zalando.nakadi.domain.NakadiRecord;
import org.zalando.nakadi.service.AvroSchema;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Input batch format:
 * tbd
 */
@Service
public class NakadiBatchMapper {

    private final AvroSchema avroSchema;

    public NakadiBatchMapper(final AvroSchema avroSchema) {
        this.avroSchema = avroSchema;
    }

    public List<NakadiRecord> map(final byte[] batch) throws IOException {
        final List<NakadiRecord> records = new LinkedList<>();
        final ByteBuffer tmp = ByteBuffer.wrap(batch);
        while (tmp.hasRemaining()) {
            final int recordStart = tmp.position();
            final byte metadataVersion = tmp.get();
            final int metadataLength = tmp.getInt();
            final byte[] metadata = new byte[metadataLength];
            tmp.get(metadata);

            final int payloadLength = tmp.getInt();
            final byte[] wholeRecord = new byte[1 + 4 + metadataLength + 4 + payloadLength];
            tmp.position(recordStart);
            tmp.get(wholeRecord);

            final Schema metadataSchema = avroSchema.getEventTypeSchema(
                    AvroSchema.METADATA_KEY, Byte.toString(metadataVersion));
            final GenericDatumReader datumReader = new GenericDatumReader(metadataSchema);
            final GenericRecord genericRecord = (GenericRecord) datumReader.read(null,
                    DecoderFactory.get().directBinaryDecoder(
                            new ByteArrayInputStream(metadata), null));
            records.add(new NakadiRecord()
                    .setEventMetadataVersion(metadataVersion)
                    .setEventMetadata(genericRecord)
                    .setData(wholeRecord));
        }

        return records;
    }

}
