package org.zalando.nakadi.service.publishing.check;

import org.springframework.stereotype.Component;
import org.zalando.nakadi.domain.EventType;
import org.zalando.nakadi.domain.NakadiMetadata;
import org.zalando.nakadi.domain.NakadiRecord;
import org.zalando.nakadi.domain.NakadiRecordResult;
import org.zalando.nakadi.exceptions.runtime.PartitioningException;
import org.zalando.nakadi.partitioning.PartitionResolver;

import java.util.Collections;
import java.util.List;

@Component
public class PartitioningCheck extends Check {

    private final PartitionResolver partitionResolver;

    public PartitioningCheck(final PartitionResolver partitionResolver) {
        this.partitionResolver = partitionResolver;
    }

    @Override
    public List<NakadiRecordResult> execute(final EventType eventType,
                                            final List<NakadiRecord> records) {
        for (final NakadiRecord record : records) {
            final NakadiMetadata metadata = record.getMetadata();
            try {
                final String partition = partitionResolver
                        .resolvePartition(eventType, metadata);
                record.getMetadata().setPartition(partition);
            } catch (PartitioningException pe) {
                return processError(records, record, pe);
            }
        }

        return Collections.emptyList();
    }

    @Override
    public NakadiRecordResult.Step getCurrentStep() {
        return NakadiRecordResult.Step.PARTITIONING;
    }
}
