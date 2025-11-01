from typing import List
from fastapi import HTTPException
import httpx
from tenacity import retry, stop_after_attempt, wait_fixed, RetryError, before_sleep_log
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def serialize_object_id(doc):
    if doc is None:
        return None
    doc["_id"] = str(doc["_id"])
    return doc

def serialize_object_ids(docs: List):
    return [serialize_object_id(doc) for doc in docs]

@retry(stop=stop_after_attempt(4), wait=wait_fixed(2), before_sleep=before_sleep_log(logger, logging.WARNING))
async def get_user(user_id):
    async with httpx.AsyncClient() as client:
        response = await client.get(f"http://user-api:8000/usersapi/api/users/{str(user_id)}")

        data = response.json()

        if data.get("detail"):
            raise HTTPException(status_code=500, detail="Este usuario no existe")
        return data
        
@retry(stop=stop_after_attempt(4), wait=wait_fixed(2), before_sleep=before_sleep_log(logger, logging.WARNING))
async def get_songs(playlist):
    async with httpx.AsyncClient() as client:
        songs = []
        songs_id = playlist.get('songs_ids')

        for song_id in songs_id:
            try:
                response = await client.get(f"http://artist-api:8080/song/by-id?id={str(song_id)}")
                response.raise_for_status()
                data_songs = response.json()
                songs.append(data_songs)
            except httpx.HTTPError as e:
                raise HTTPException(status_code=500, detail=f"Error al obtener canción {song_id}: {str(e)}")
            
        playlist.pop('songs_ids')
        playlist["songs"] = songs
    
    return songs    

@retry(stop=stop_after_attempt(4), wait=wait_fixed(2), before_sleep=before_sleep_log(logger, logging.WARNING))
async def get_artist(songs):
    async with httpx.AsyncClient() as client:
        for artist in songs:
            response = await client.get(f"http://artist-api:8080/artist/by-id?id={artist.get('artistID')}")
            data_artists = response.json()
            artist["artist"] = data_artists
            artist.pop('artistID')
            artist.pop('albumID')

@retry(stop=stop_after_attempt(4), wait=wait_fixed(2), before_sleep=before_sleep_log(logger, logging.WARNING))
async def get_song(song_id):
    async with httpx.AsyncClient() as client:

        response = await client.get(f"http://artist-api:8080/song/by-id?id={str(song_id)}")

        data = response.json()

        if data.get("status"):
            raise HTTPException(status_code=500, detail="Esta cancion no existe")
        return data
    
async def safe_get_user(user_id):
    try:
        return await get_user(user_id)
    
    except RetryError as e:
        raise HTTPException(
            status_code=503,
            detail=f"Error al obtener usuario después de varios intentos: {str(e.last_attempt.exception())}"
        )

async def safe_get_songs(playlist):
    try:
        return await get_songs(playlist)
    
    except RetryError as e:
        raise HTTPException(
            status_code=503,
            detail=f"Error al obtener canciones después de varios intentos: {str(e.last_attempt.exception())}"
        )

async def safe_get_artist(songs):
    try:
        return await get_artist(songs)
    
    except RetryError as e:
        raise HTTPException(
            status_code=503,
            detail=f"Error al obtener artistas después de varios intentos: {str(e.last_attempt.exception())}"
        )

async def safe_get_song(song_id):
    try:
        return await get_song(song_id)
    except RetryError as e:
        raise HTTPException(
            status_code=503,
            detail=f"Error al obtener la canción después de varios intentos: {str(e.last_attempt.exception())}"
        )