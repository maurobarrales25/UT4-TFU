from database.database import db
from utils.utils import serialize_object_id, serialize_object_ids
from bson import ObjectId
from fastapi import HTTPException

class PlaylistRepository:
    def __init__(self):
        pass

    async def get_playlists(self):
        playlists_cursor = db.get_collection("playlist").find()
        playlists = await playlists_cursor.to_list()

        return serialize_object_ids(playlists)
    
    async def get_playlist_by_id(self, id):
        try:
            playlist = await db.get_collection("playlist").find_one({"_id": ObjectId(id)})
        except:
            raise HTTPException(status_code=500, detail="Playlist no encontrada")
        
        return serialize_object_id(playlist)

    async def get_playlists_by_user_id(self, user_id):
        playlists_cursor = db.get_collection("playlist").find({"user.id": user_id})
        playlists = await playlists_cursor.to_list()

        if len(playlists) == 0:
            raise HTTPException(status_code=500, detail="No tiene ninguna playlist este usuario")

        return serialize_object_ids(playlists)

    async def save_playlist(self, playlist):
        result = await db.get_collection("playlist").insert_one(playlist)
        return await self.get_playlist_by_id(result.inserted_id)
    
    # Sincronizacion de datos
    
    async def update_user_from_playlists(self, user):
        result = await db.get_collection("playlist").update_many(
            {"user.id": user.get("id")},
            {
                "$set": {"user": user}
            }
        )

        print(result)
    
    async def update_artist_from_playlists(self, artist):
        result = await db.get_collection("playlist").update_many(
            {"songs.artist.artistId": artist.get('artistId')},
            {
                "$set": {
                    "songs.$[elem].artist" : artist
                }
            },
            array_filters=[{"elem.artist.artistId" : artist.get('artistId')}]
        )

        print(result)
    
    async def update_song_from_playlists(self, song):
        result = await db.get_collection("playlist").update_many(
            {"songs.songId": song.get('songId')},
            {
                "$set": {
                    "songs.$[elem].songName" : song.get('songName')
                }
            },
            array_filters=[{"elem.songId": song.get('songId')}]
        )

        print(result)

    async def update_album_from_playlists(self, album):
        result = await db.get_collection("playlist").update_many(
            {"songs.album.albumId": album.get('albumId')},
            {
                "$set": {
                    "songs.$[elem].album.albumName" : album.get('albumName')
                }
            },
            array_filters=[{"elem.album.albumId" : album.get('albumId')}]
        )

        print(result)