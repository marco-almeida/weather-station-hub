-- Create the 'station' table if it doesn't exist
CREATE TABLE IF NOT EXISTS station (
    id INT PRIMARY KEY,
    measurements JSON
);

-- Insert 5 entries with empty measurements
INSERT INTO station (id, measurements) VALUES (1, '[]');
INSERT INTO station (id, measurements) VALUES (2, '[]');
INSERT INTO station (id, measurements) VALUES (3, '[]');
INSERT INTO station (id, measurements) VALUES (4, '[]');
INSERT INTO station (id, measurements) VALUES (5, '[]');
