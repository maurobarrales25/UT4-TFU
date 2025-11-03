db = db.getSiblingDB('artistmongodb');

db.artists.insertMany([
  { _id: 1, artistName: "Queen" },
  { _id: 2, artistName: "Foo Fighters" },
  { _id: 3, artistName: "Taylor Swift" }
]);

db.albums.insertMany([
  { _id: 1, albumName: "Echoes of Silence Patience and Grace", artistId: 2, artistName: "Foo Fighters",
    songs: [
      { songId: 1, songName: "The Pretender" },
      { songId: 2, songName: "Let It Die" },
      { songId: 3, songName: "Erase/Replace" },
      { songId: 4, songName: "Long Road to Ruin" },
      { songId: 5, songName: "Come Alive" },
      { songId: 6, songName: "Stranger Things Have Happened" },
      { songId: 7, songName: "Cheer Up, Boys (Your Make Up Is Running)" },
      { songId: 8, songName: "Summer's End" },
      { songId: 9, songName: "Ballad of the Beaconsfield Miners" },
      { songId: 10, songName: "Statues" },
      { songId: 11, songName: "Home" }
    ]
  },
  { _id: 2, albumName: "Wasting Light", artistId: 2, artistName: "Foo Fighters",
    songs: [
      { songId: 12, songName: "Bridge Burning" },
      { songId: 13, songName: "Rope" },
      { songId: 14, songName: "Dear Rosemary" },
      { songId: 15, songName: "White Limo" },
      { songId: 16, songName: "Arlandria" },
      { songId: 17, songName: "These Days" },
      { songId: 18, songName: "Back & Forth" },
      { songId: 19, songName: "A Matter of Time" },
      { songId: 20, songName: "Miss the Misery" },
      { songId: 21, songName: "I Should Have Known" },
      { songId: 22, songName: "Walk" }
    ]
  },
  { _id: 3, albumName: "There Is Nothing Left to Lose", artistId: 2, artistName: "Foo Fighters",
    songs: [
      { songId: 23, songName: "Stacked Actors" },
      { songId: 24, songName: "Breakout" },
      { songId: 25, songName: "Learn to Fly" },
      { songId: 26, songName: "Gimme Stitches" },
      { songId: 27, songName: "Aurora" },
      { songId: 28, songName: "Next Year" },
      { songId: 29, songName: "Headwires" },
      { songId: 30, songName: "Generator" },
      { songId: 31, songName: "M.I.A." },
      { songId: 32, songName: "Aint It the Life" }
    ]
  },
  { _id: 4, albumName: "The Colour and the Shape", artistId: 2, artistName: "Foo Fighters",
    songs: [
      { songId: 33, songName: "Doll" },
      { songId: 34, songName: "Monkey Wrench" },
      { songId: 35, songName: "Hey, Johnny Park!" },
      { songId: 36, songName: "My Poor Brain" },
      { songId: 37, songName: "Wind Up" },
      { songId: 38, songName: "Up in Arms" },
      { songId: 39, songName: "My Hero" },
      { songId: 40, songName: "Everlong" },
      { songId: 41, songName: "February Stars" },
      { songId: 42, songName: "Walking After You" },
      { songId: 43, songName: "New Way Home" },
      { songId: 44, songName: "Finest Hour" }
    ]
  },
  { _id: 5, albumName: "1989", artistId: 3, artistName: "Taylor Swift",
    songs: [
      { songId: 45, songName: "Welcome to New York" },
      { songId: 46, songName: "Blank Space" },
      { songId: 47, songName: "Style" },
      { songId: 48, songName: "Out of the Woods" },
      { songId: 49, songName: "All You Had to Do Was Stay" },
      { songId: 50, songName: "Shake It Off" },
      { songId: 51, songName: "I Wish You Would" },
      { songId: 52, songName: "Bad Blood" },
      { songId: 53, songName: "Wildest Dreams" },
      { songId: 54, songName: "How You Get the Girl" },
      { songId: 55, songName: "This Love" },
      { songId: 56, songName: "I Know Places" },
      { songId: 57, songName: "Clean" }
    ]
  }
]);

db.songs.insertMany([

  { _id: 1, songName: "The Pretender", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 2, songName: "Let It Die", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 3, songName: "Erase/Replace", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 4, songName: "Long Road to Ruin", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 5, songName: "Come Alive", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 6, songName: "Stranger Things Have Happened", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 7, songName: "Cheer Up, Boys (Your Make Up Is Running)", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 8, songName: "Summer's End", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 9, songName: "Ballad of the Beaconsfield Miners", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 10, songName: "Statues", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },
  { _id: 11, songName: "Home", artistId: 2, artistName: "Foo Fighters", albumId: 1, albumName: "Echoes of Silence Patience and Grace" },

  { _id: 12, songName: "Bridge Burning", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 13, songName: "Rope", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 14, songName: "Dear Rosemary", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 15, songName: "White Limo", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 16, songName: "Arlandria", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 17, songName: "These Days", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 18, songName: "Back & Forth", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 19, songName: "A Matter of Time", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 20, songName: "Miss the Misery", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 21, songName: "I Should Have Known", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },
  { _id: 22, songName: "Walk", artistId: 2, artistName: "Foo Fighters", albumId: 2, albumName: "Wasting Light" },

  { _id: 23, songName: "Stacked Actors", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 24, songName: "Breakout", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 25, songName: "Learn to Fly", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 26, songName: "Gimme Stitches", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 27, songName: "Aurora", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 28, songName: "Next Year", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 29, songName: "Headwires", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 30, songName: "Generator", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 31, songName: "M.I.A.", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },
  { _id: 32, songName: "Aint It the Life", artistId: 2, artistName: "Foo Fighters", albumId: 3, albumName: "There Is Nothing Left to Lose" },

  { _id: 33, songName: "Doll", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 34, songName: "Monkey Wrench", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 35, songName: "Hey, Johnny Park!", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 36, songName: "My Poor Brain", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 37, songName: "Wind Up", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 38, songName: "Up in Arms", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 39, songName: "My Hero", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 40, songName: "Everlong", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 41, songName: "February Stars", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 42, songName: "Walking After You", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 43, songName: "New Way Home", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },
  { _id: 44, songName: "Finest Hour", artistId: 2, artistName: "Foo Fighters", albumId: 4, albumName: "The Colour and the Shape" },

  { _id: 45, songName: "Welcome to New York", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 46, songName: "Blank Space", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 47, songName: "Style", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 48, songName: "Out of the Woods", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 49, songName: "All You Had to Do Was Stay", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 50, songName: "Shake It Off", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 51, songName: "I Wish You Would", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 52, songName: "Bad Blood", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 53, songName: "Wildest Dreams", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 54, songName: "How You Get the Girl", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 55, songName: "This Love", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 56, songName: "I Know Places", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" },
  { _id: 57, songName: "Clean", artistId: 3, artistName: "Taylor Swift", albumId: 5, albumName: "1989" }
]);

db.artists.createIndex({ artistName: 1 });
db.albums.createIndex({ artistId: 1 });
db.albums.createIndex({ artistName: 1 });
db.albums.createIndex({ "songs.songName": 1 });
db.songs.createIndex({ albumId: 1 });
db.songs.createIndex({ artistId: 1 });
db.songs.createIndex({ songName: 1 });