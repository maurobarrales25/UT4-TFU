from fastapi import APIRouter, Depends
from typing import List
from app.utils.utils import sidecar_valet_key
from app.schema.user import User as UserSchema, UserCreate
from app.service.user_service import UserService
from app.repository.user_repository import UserRepository
router = APIRouter()

user_repository = UserRepository()
user_service = UserService(user_repository)

from fastapi import HTTPException

@router.get("/health")
async def health_check():
    try:
        user_service.get_all_users()  
        return {"status": "UP"}
    except Exception as e:
        raise HTTPException(status_code=503, detail={"status": "DOWN", "error": str(e)})

@router.get("/users", dependencies=[Depends(sidecar_valet_key)], response_model=List[UserSchema])
async def get_users():
    return user_service.get_all_users()

@router.get("/users/{id}", dependencies=[Depends(sidecar_valet_key)], response_model=UserSchema)
def get_user_by_id(id: int):
    return user_service.get_user_by_id(id)

@router.post("/users", dependencies=[Depends(sidecar_valet_key)], response_model=UserSchema)
def create_user(user: UserCreate):
    return user_service.create_user(user)

@router.put("/users/{id}", response_model=UserSchema)
def update_user(id: int, user_update: UserCreate):
    return user_service.update_user(id, user_update)
