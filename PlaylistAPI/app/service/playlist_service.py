from repository.playlist_repository import PlaylistRepository
from utils.utils import get_user, get_songs, get_artist, get_playlist_info_query
from fastapi import HTTPException

class PlaylistService:
    def __init__(self, playlist_repository: PlaylistRepository):
        self.playlist_repository = playlist_repository

    async def get_playlists(self):
        playlists = await self.playlist_repository.get_playlists()
            
        return playlists
    
    async def get_playlist_by_id(self, id):
        playlist = await self.playlist_repository.get_playlist_by_id(id)

        return playlist
    
    async def get_playlists_by_user_id(self, user_id):
        user = await get_user(user_id)
        
        playlists = await self.playlist_repository.get_playlists_by_user_id(user_id)
        
        for playlist in playlists:
            playlist.pop('user')
            
        return {"playlists": playlists, "user": user}
    
    async def save_playlist(self, playlist):
        playlist_dict = playlist.dict()
        user_id = playlist_dict.get("user_id")
        user = await get_user(user_id)
        
        playlist_dict.pop('user_id')
        playlist_dict["user"] = user
        
        songs = await get_songs(playlist_dict)
        await get_artist(songs)
            
        return await self.playlist_repository.save_playlist(playlist_dict)
    
    async def save_playlist_query(self, playlist):
        playlist_dict = playlist.dict()
        user_id = playlist_dict.get("user_id")
        user = await get_user(user_id)
        
        playlist_dict.pop('user_id')
        playlist_dict["user"] = user

        await get_playlist_info_query(playlist_dict)

        return await self.playlist_repository.save_playlist(playlist_dict)
    
    # Sincronizacion de datos

    async def update_user_from_playlists(self, user):
        return await self.playlist_repository.update_user_from_playlists(user)
    
    async def update_artist_from_playlists(self, artist):
        return await self.playlist_repository.update_artist_from_playlists(artist)
    
    async def update_song_from_playlists(self, song):
        return await self.playlist_repository.update_song_from_playlists(song)
    
    async def update_album_from_playlists(self, album):
        return await self.playlist_repository.update_album_from_playlists(album)