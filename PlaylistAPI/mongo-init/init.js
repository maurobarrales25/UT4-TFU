db = db.getSiblingDB('playlist');
db.playlist.insertMany([
  { 
    name: "playlist1", 
    user: { "name": "pepe", "email": "pepe@gmail.com","id": 1 }, 
    songs: [
    {
      "songID": 1,
      "songName": "The Pretender",
      "artist": {
        "artistID": 2,
        "name": "Foo Fighters"
      }
    },
    {
      "songID": 2,
      "songName": "Let It Die",
      "artist": {
        "artistID": 2,
        "name": "Foo Fighters"
      }
    },
    {
      "songID": 3,
      "songName": "Erase/Replace",
      "artist": {
        "artistID": 2,
        "name": "Foo Fighters",
      }
    }
  ]
  },
  { 
    name: "playlist2", 
    user: { "name": "lucia", "email": "lucia@outlook.com", "id": 2}, 
    songs: [
    {
      "songID": 3,
      "songName": "Erase/Replace",
      "artist": {
        "artistID": 2,
        "name": "Foo Fighters"
      }
    },
    {
      "songID": 5,
      "songName": "Come Alive",
      "artist": {
        "artistID": 2,
        "name": "Foo Fighters"
      }
    }
  ] }
]);