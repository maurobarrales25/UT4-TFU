from fastapi import FastAPI
from controller import playlist_controller
from prometheus_fastapi_instrumentator import Instrumentator

app = FastAPI(root_path="/playlistapi")

app.include_router(playlist_controller.router)

Instrumentator().instrument(app).expose(app, endpoint="/metrics")