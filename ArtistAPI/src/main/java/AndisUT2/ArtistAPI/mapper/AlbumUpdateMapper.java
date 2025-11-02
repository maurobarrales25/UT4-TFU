package AndisUT2.ArtistAPI.mapper;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainAlbumUpdateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.AlbumUpdateEvent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlbumUpdateMapper {

    AlbumUpdateMapper INSTANCE = Mappers.getMapper(AlbumUpdateMapper.class);

    AlbumUpdateEvent toDomainAlbumUpdateEvent(DomainAlbumUpdateEvent event);
}
