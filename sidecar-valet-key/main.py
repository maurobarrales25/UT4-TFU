from fastapi import FastAPI, HTTPException
from jose import jwt
from datetime import datetime, timedelta, timezone
from enum import Enum
import os
from dotenv import load_dotenv

app = FastAPI()

load_dotenv()
secret_key = os.getenv("SECRET_KEY")

class ScopeEnum(str, Enum):
    read = "read"
    write = "write"

@app.get('/')
async def create_valet_key(scope : ScopeEnum):
    payload = {"scope": scope, "exp": datetime.now(timezone.utc) + timedelta(minutes=5)}
    token = jwt.encode(payload, secret_key, "HS256")

    return {"token": token}

@app.post('/check')
async def check_valet_key(token : str, method: str):
    try:
        decoded_token = jwt.decode(token, secret_key, algorithms=["HS256"])
    except:
        raise HTTPException(403, "Invalid Valet Key")
    
    scope = decoded_token.get('scope')

    if method.upper() == "GET":
        if scope != "read":
            raise HTTPException(403, "Token scope is not meant for read operations")
    elif method.upper() in ["POST", "PUT", "PATCH", "DELETE"]:
        if scope != "write":
            raise HTTPException(403, "Token scope is not meant for write operations")
    else:
        raise HTTPException(400, "Unknown method, known methods are 'GET/POST/PUT/PATCH/DELETE'")

    return {"valid": True, "expirates": decoded_token.get('exp'), "scope-access": scope}