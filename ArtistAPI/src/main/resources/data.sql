USE artistdbv4;

INSERT IGNORE INTO artist (name) VALUES ('Queen');
INSERT IGNORE INTO artist (name) VALUES ('Foo Fighters');
INSERT IGNORE INTO artist (name) VALUES ('Taylor Swift');

INSERT IGNORE INTO album(album_name, artist_id) VALUES ('Echoes of Silence Patience and Grace', '2');
INSERT IGNORE INTO album(album_name, artist_id) VALUES ('Wasting Light', 2);
INSERT IGNORE INTO album(album_name, artist_id) VALUES ('There Is Nothing Left to Lose', 2);
INSERT IGNORE INTO album(album_name, artist_id) VALUES ('The Colour and the Shape', 2);
INSERT IGNORE INTO album(album_name, artist_id) VALUES ('1989', 3);

INSERT IGNORE INTO song (song_name, artist_id, album_id) VALUES
('The Pretender', 2, 1),
('Let It Die', 2, 1),
('Erase/Replace', 2, 1),
('Long Road to Ruin', 2, 1),
('Come Alive', 2, 1),
('Stranger Things Have Happened', 2, 1),
('Cheer Up, Boys (Your Make Up Is Running)', 2, 1),
('Summer\'s End', 2, 1),
('Ballad of the Beaconsfield Miners', 2, 1),
('Statues', 2, 1),
('Home', 2, 1);

INSERT IGNORE INTO song (song_name, artist_id, album_id) VALUES
('Bridge Burning', 2, 2),
('Rope', 2, 2),
('Dear Rosemary', 2, 2),
('White Limo', 2, 2),
('Arlandria', 2, 2),
('These Days', 2, 2),
('Back & Forth', 2, 2),
('A Matter of Time', 2, 2),
('Miss the Misery', 2, 2),
('I Should Have Known', 2, 2),
('Walk', 2, 2);

INSERT IGNORE INTO song (song_name, artist_id, album_id) VALUES
('Stacked Actors', 2, 3),
('Breakout', 2, 3),
('Learn to Fly', 2, 3),
('Gimme Stitches', 2, 3),
('Aurora', 2, 3),
('Next Year', 2, 3),
('Headwires', 2, 3),
('Generator', 2, 3),
('M.I.A.', 2, 3),
('Aint It the Life', 2, 3);

INSERT IGNORE INTO song (song_name, artist_id, album_id) VALUES
('Doll', 2, 4),
('Monkey Wrench', 2, 4),
('Hey, Johnny Park!', 2, 4),
('My Poor Brain', 2, 4),
('Wind Up', 2, 4),
('Up in Arms', 2, 4),
('My Hero', 2, 4),
('Everlong', 2, 4),
('February Stars', 2, 4),
('Walking After You', 2, 4),
('New Way Home', 2, 4),
('Finest Hour', 2, 4);

INSERT IGNORE INTO song (song_name, artist_id, album_id) VALUES
('Welcome to New York', 3, 5),
('Blank Space', 3, 5),
('Style', 3, 5),
('Out of the Woods', 3, 5),
('All You Had to Do Was Stay', 3, 5),
('Shake It Off', 3, 5),
('I Wish You Would', 3, 5),
('Bad Blood', 3, 5),
('Wildest Dreams', 3, 5),
('How You Get the Girl', 3, 5),
('This Love', 3, 5),
('I Know Places', 3, 5),
('Clean', 3, 5);


