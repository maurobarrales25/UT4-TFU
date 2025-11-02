package AndisUT2.ArtistAPI.mapper;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistUpdateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.ArtistUpdateEvent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArtistUpdateMapper {
    ArtistUpdateMapper INSTANCE = Mappers.getMapper(ArtistUpdateMapper.class);

    ArtistUpdateEvent toKafkaEvent(DomainArtistUpdateEvent domainEvent);

    DomainArtistUpdateEvent toDomainEvent(ArtistUpdateEvent kafkaEvent);
}

