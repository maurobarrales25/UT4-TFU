import httpx
from fastapi import HTTPException, Depends, Request
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials

security = HTTPBearer()

async def sidecar_valet_key(request : Request, credentials: HTTPAuthorizationCredentials = Depends(security)):
    token = credentials.credentials
    method = request.method
    
    async with httpx.AsyncClient() as client:
        try:
            response = await client.post("http://valet-key-sidecar:8089/check", params={"token": token, "method": method})
            if response.status_code != 200:
                raise HTTPException(status_code=403, detail="Invalid valet key")
            
            return response
        except httpx.RequestError:
            raise HTTPException(status_code=503, detail="Sidecar unavailable")