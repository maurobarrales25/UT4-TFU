from fastapi import APIRouter, HTTPException
from typing import List
from service.playlist_service import PlaylistService
from repository.playlist_repository import PlaylistRepository
from schema.playlist_schema import PlaylistCreate
import httpx 

router = APIRouter(prefix="/playlist", tags=["playlist"])

playlist_repository = PlaylistRepository()
playlist_service = PlaylistService(playlist_repository)

@router.get("/health")
async def health_check():
    try:
        await playlist_service.get_playlists()

        async with httpx.AsyncClient() as client:
            user_response = await client.get("http://user-api:8000/usersapi/api/health")
            artist_response = await client.get("http://artist-api:8080/actuator/health")

        if user_response.status_code == 200 and artist_response.status_code == 200:
            return {"status": "UP"}

        raise HTTPException(
            status_code=503,
            detail={
                "status": "DOWN",
                "user_api": user_response.status_code,
                "artist_api": artist_response.status_code,
            },
        )

    except Exception as e:
        raise HTTPException(status_code=503, detail={"status": "DOWN", "error": str(e)})

@router.get("/")
async def get_playlists():
    playlists = await playlist_service.get_playlists()
    return playlists


@router.get("/{id}")
async def get_playlist_by_id(id: str):
    playlist = await playlist_service.get_playlist_by_id(id)
    return playlist


@router.get("/user/{user_id}")
async def get_playlists_by_user_id(user_id: int):
    playlists = await playlist_service.get_playlists_by_user_id(user_id)
    return playlists


@router.post("/command")
async def save_playlist(playlist: PlaylistCreate):
    return await playlist_service.save_playlist(playlist)


@router.post("/query")
async def save_playlist_query(playlist: PlaylistCreate):
    return await playlist_service.save_playlist_query(playlist)
