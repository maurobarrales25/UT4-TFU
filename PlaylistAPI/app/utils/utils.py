from typing import List
from fastapi import HTTPException
import httpx

def serialize_object_id(doc):
    if doc is None:
        return None
    doc["_id"] = str(doc["_id"])
    return doc

def serialize_object_ids(docs: List):
    return [serialize_object_id(doc) for doc in docs]

async def get_user(user_id):
    async with httpx.AsyncClient() as client:
        token_data = await client.get(f"http://valet-key-sidecar:8089/?scope=read")
        token = token_data.json().get('token')
        response = await client.get(f"http://user-api:8000/usersapi/api/users/{str(user_id)}", headers = {"Authorization" : f"Bearer {token}"})
        
        data = response.json()

        if data.get("detail"):
            raise HTTPException(status_code=500, detail="Este usuario no existe")
        return data

async def get_songs(playlist):
    async with httpx.AsyncClient() as client:
        songs = []
        songs_id = playlist.get('songs_ids')

        for song_id in songs_id:
            try:
                response = await client.get(f"http://artist-api:8080/song/by-id-command?id={str(song_id)}")
                response.raise_for_status()
                data_songs = response.json()
                songs.append(data_songs)
            except httpx.HTTPError as e:
                raise HTTPException(status_code=500, detail=f"Error al obtener canción {song_id}")
            
        playlist.pop('songs_ids')
        playlist["songs"] = songs
    
    return songs    

async def get_artist(songs):
    async with httpx.AsyncClient() as client:
        for artist in songs:
            response = await client.get(f"http://artist-api:8080/artist/by-id-command?id={artist.get('artistID')}")
            data_artists = response.json()
            artist["artist"] = data_artists
            response2 = await client.get(f"http://artist-api:8080/album/by-id-command?id={artist.get('albumID')}")
            data_album = response2.json()
            data_album.pop('artistId')
            artist["album"] = data_album
            artist.pop('artistID')
            artist.pop('albumID')

async def get_song(song_id):
    async with httpx.AsyncClient() as client:

        response = await client.get(f"http://artist-api:8080/song/by-id-command?id={str(song_id)}")

        data = response.json()

        if data.get("status"):
            raise HTTPException(status_code=500, detail="Esta cancion no existe")
        return data
    

# METODO PARA LA BD DE READ

async def get_playlist_info_query(playlist):
    async with httpx.AsyncClient() as client:
        songs = []
        songs_id = playlist.get('songs_ids')

        for song_id in songs_id:
            try:
                response = await client.get(f"http://artist-api:8080/song/by-id-query?id={str(song_id)}")
                response.raise_for_status()
                data_songs = response.json()

                artist = {
                    "artistId" : data_songs.get('artistId'),
                    "artistName" : data_songs.get('artistName')
                }
                
                album = {
                    "albumId" : data_songs.get('albumId'),
                    "albumName" : data_songs.get('albumName')
                }

                song = {
                    "songId" : data_songs.get('songId'),
                    "songName" : data_songs.get('songName'),
                    "artist" : artist,
                    "album" : album
                }

                songs.append(song)

            except httpx.HTTPError as e:
                raise HTTPException(status_code=500, detail=f"Error al obtener canción {song_id}")
            
        playlist.pop('songs_ids')
        playlist["songs"] = songs