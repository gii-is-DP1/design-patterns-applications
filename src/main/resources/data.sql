-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Ten player users, named player1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'player1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'player2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'player3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'player4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'player5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'player6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'player7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'player8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'player9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'player10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);

-- Famous Chess Match 1: "The Immortal Game" - Anderssen vs. Kieseritzky, 1851
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (1, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (1, 'The Immortal Match, Anderssen vs Kieseritzky 1851', null, 5, 1, 0, null, null, 600);

-- Pieces in the "Immortal Game" (near the famous position before Anderssen's sacrifices)
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (26, 'KING', 'WHITE', 5, 1, 1),
    (27, 'QUEEN', 'WHITE', 6, 3, 1),
    (28, 'BISHOP', 'WHITE', 5, 2, 1),
    (29, 'BISHOP', 'WHITE', 3, 1, 1),
    (30, 'KNIGHT', 'WHITE', 4, 3, 1),
    (31, 'KNIGHT', 'WHITE', 3, 5, 1),
    (32, 'ROOK', 'WHITE', 1, 1, 1),
    (33, 'ROOK', 'WHITE', 8, 1, 1),
    (34, 'PAWN', 'WHITE', 4, 2, 1),
    (35, 'PAWN', 'WHITE', 6, 4, 1),
    (36, 'KING', 'BLACK', 5, 8, 1),
    (37, 'BISHOP', 'BLACK', 4, 6, 1),
    (38, 'QUEEN', 'BLACK', 5, 6, 1),
    (39, 'PAWN', 'BLACK', 5, 5, 1),
    (40, 'PAWN', 'BLACK', 4, 5, 1),
    (41, 'ROOK', 'BLACK', 8, 8, 1);

-- Famous Chess Match 2: "Game of the Century" - Donald Byrne vs. Bobby Fischer, 1956
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (2, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (2, 'Game of the Century, Byrne vs Fischer, 1956',null, 5, 2, 0, null, null, 600);

-- Piece positions in the "Game of the Century (Donald Byrne vs. Bobby Fischer, 1956)"
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (42, 'KING', 'WHITE', 5, 1, 2),
    (43, 'QUEEN', 'WHITE', 7, 3, 2),
    (44, 'BISHOP', 'WHITE', 5, 2, 2),
    (45, 'KNIGHT', 'WHITE', 4, 6, 2),
    (46, 'KNIGHT', 'WHITE', 6, 3, 2),
    (47, 'ROOK', 'WHITE', 1, 1, 2),
    (48, 'PAWN', 'WHITE', 5, 4, 2),
    (49, 'PAWN', 'WHITE', 6, 5, 2),
    (50, 'KING', 'BLACK', 8, 8, 2),
    (51, 'QUEEN', 'BLACK', 6, 5, 2),
    (52, 'BISHOP', 'BLACK', 3, 7, 2),
    (53, 'KNIGHT', 'BLACK', 4, 4, 2),
    (54, 'ROOK', 'BLACK', 8, 6, 2),
    (55, 'PAWN', 'BLACK', 7, 5, 2);

-- Famous Chess Match 3: "Opera Game" - Morphy vs. Duke Karl/Count Isouard, 1858
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (3, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (3, 'Opera Game, Morphy vs Isouard, 1858', null, 9, 3, 0, null, null, 600);

-- Piece positions in the "Opera Game" 
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (56, 'KING', 'WHITE', 5, 1, 3),
    (57, 'QUEEN', 'WHITE', 6, 5, 3),
    (58, 'BISHOP', 'WHITE', 4, 4, 3),
    (59, 'KNIGHT', 'WHITE', 3, 5, 3),
    (60, 'ROOK', 'WHITE', 8, 3, 3),
    (61, 'PAWN', 'WHITE', 5, 3, 3),
    (62, 'KING', 'BLACK', 8, 8, 3),
    (63, 'QUEEN', 'BLACK', 4, 8, 3),
    (64, 'KNIGHT', 'BLACK', 3, 8, 3),
    (65, 'PAWN', 'BLACK', 4, 7, 3),
    (66, 'PAWN', 'BLACK', 6, 6, 3),
    (67, 'PAWN', 'BLACK', 5, 5, 3);

-- Famous Chess Match 4: "Deep Blue vs. Garry Kasparov" - IBM Deep Blue, 1997
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (4, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (4, 'Deep Blue vs. Kasparov, 1997', null, 5, 4, 0, null, null, 600);

-- Piece positions in "Deep Blue vs. Kasparov"
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (68, 'KING', 'WHITE', 4, 2, 4),
    (69, 'QUEEN', 'WHITE', 5, 1, 4),
    (70, 'ROOK', 'WHITE', 1, 1, 4),
    (71, 'BISHOP', 'WHITE', 3, 1, 4),
    (72, 'PAWN', 'WHITE', 4, 4, 4),
    (73, 'KING', 'BLACK', 8, 8, 4),
    (74, 'ROOK', 'BLACK', 8, 7, 4),
    (75, 'PAWN', 'BLACK', 6, 6, 4),
    (76, 'KNIGHT', 'BLACK', 5, 5, 4);

-- Chess Opening 1: Ruy López Opening
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (6, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (6, 'Ruy Lopez Opening', null, 5, 6, 0, null, null, 600);

-- Position for Ruy López Opening (1.e4 e5 2.Nf3 Nc6 3.Bb5)
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (77, 'KING', 'WHITE', 5, 1, 6),
    (78, 'QUEEN', 'WHITE', 4, 1, 6),
    (79, 'BISHOP', 'WHITE', 3, 1, 6),
    (80, 'BISHOP', 'WHITE', 5, 4, 6), -- Bb5
    (81, 'KNIGHT', 'WHITE', 2, 1, 6),
    (82, 'KNIGHT', 'WHITE', 3, 5, 6), -- Nf3
    (83, 'ROOK', 'WHITE', 1, 1, 6),
    (84, 'ROOK', 'WHITE', 8, 1, 6),
    (85, 'PAWN', 'WHITE', 5, 2, 6),
    (86, 'PAWN', 'WHITE', 4, 2, 6),
    (87, 'KING', 'BLACK', 5, 8, 6),
    (88, 'QUEEN', 'BLACK', 4, 8, 6),
    (89, 'BISHOP', 'BLACK', 3, 8, 6),
    (90, 'KNIGHT', 'BLACK', 3, 6, 6), -- Nc6
    (91, 'PAWN', 'BLACK', 5, 7, 6),
    (92, 'PAWN', 'BLACK', 4, 7, 6);

-- Chess Opening 2: Sicilian Defense
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (7, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (7, 'Sicilian Defense', null, 6, 7, 0, null, null, 600);

-- Position for Sicilian Defense (1.e4 c5)
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (93, 'KING', 'WHITE', 5, 1, 7),
    (94, 'QUEEN', 'WHITE', 4, 1, 7),
    (95, 'BISHOP', 'WHITE', 3, 1, 7),
    (96, 'KNIGHT', 'WHITE', 2, 1, 7),
    (97, 'PAWN', 'WHITE', 5, 2, 7), -- e4
    (98, 'KING', 'BLACK', 5, 8, 7),
    (99, 'QUEEN', 'BLACK', 4, 8, 7),
    (100, 'BISHOP', 'BLACK', 3, 8, 7),
    (101, 'PAWN', 'BLACK', 3, 7, 7); -- c5

-- Chess Opening 3: French Defense
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (8, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (8, 'French Defense', null, 6, 8, 0, null, null, 600);

-- Position for French Defense (1.e4 e6)
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (102, 'KING', 'WHITE', 5, 1, 8),
    (103, 'QUEEN', 'WHITE', 4, 1, 8),
    (104, 'PAWN', 'WHITE', 5, 2, 8), -- e4
    (105, 'KING', 'BLACK', 5, 8, 8),
    (106, 'QUEEN', 'BLACK', 4, 8, 8),
    (107, 'PAWN', 'BLACK', 5, 7, 8); -- e6

-- Chess Opening 4: Queen’s Gambit
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (9, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (9, 'Queens Gambit', null, 8, 9, 0, null, null, 600);

-- Position for Queen’s Gambit (1.d4 d5 2.c4)
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (108, 'KING', 'WHITE', 5, 1, 9),
    (109, 'QUEEN', 'WHITE', 4, 1, 9),
    (110, 'PAWN', 'WHITE', 3, 2, 9), -- c4
    (111, 'PAWN', 'WHITE', 4, 2, 9), -- d4
    (112, 'KING', 'BLACK', 5, 8, 9),
    (113, 'QUEEN', 'BLACK', 4, 8, 9),
    (114, 'PAWN', 'BLACK', 4, 7, 9); -- d5

-- Chess Opening 5: King’s Indian Defense
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (10, 0, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (10, 'Kings Indian Defense', null, 10, 10, 0, null, null, 600);

-- Position for King’s Indian Defense (1.d4 Nf6 2.c4 g6)
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (115, 'KING', 'WHITE', 5, 1, 10),
    (116, 'QUEEN', 'WHITE', 4, 1, 10),
    (117, 'PAWN', 'WHITE', 4, 2, 10), -- d4
    (118, 'PAWN', 'WHITE', 3, 2, 10), -- c4
    (119, 'KING', 'BLACK', 5, 8, 10),
    (120, 'KNIGHT', 'BLACK', 6, 6, 10), -- Nf6
    (121, 'PAWN', 'BLACK', 6, 7, 10), -- g6
    (122, 'QUEEN', 'BLACK', 4, 8, 10);

-- Test Game between player1 and player2
INSERT INTO chess_board (id, creator_turn, jaque, current_turn_start) VALUES (100, 1, 0, null);
INSERT INTO chess_match (id, name, creator_id, opponent_id, board_id, type, finish, start, turn_duration) VALUES 
    (100, 'Test Game', 4, 5, 100, 0, null, null, 600);

-- Position for Test Game
INSERT INTO Piece(id, type, color, x_position, y_position, board_id) VALUES 
    (123, 'ROOK',   'WHITE', 1, 1, 100),
    (124, 'KNIGHT', 'WHITE', 2, 1, 100),
    (125, 'BISHOP', 'WHITE', 3, 1, 100),
    (126, 'QUEEN',  'WHITE', 4, 1, 100),
    (127, 'KING',   'WHITE', 5, 1, 100),
    (128, 'BISHOP', 'WHITE', 6, 1, 100),
    (129, 'KNIGHT', 'WHITE', 7, 1, 100),
    (130, 'ROOK',   'WHITE', 8, 1, 100),
    (131, 'PAWN',   'WHITE', 1, 2, 100),
    (132, 'PAWN',   'WHITE', 2, 2, 100),
    (133, 'PAWN',   'WHITE', 3, 2, 100),
    (134, 'PAWN',   'WHITE', 4, 2, 100),
    (135, 'PAWN',   'WHITE', 5, 2, 100),
    (136, 'PAWN',   'WHITE', 6, 2, 100),
    (137, 'PAWN',   'WHITE', 7, 2, 100),
    (138, 'PAWN',   'WHITE', 8, 2, 100),
    (139, 'ROOK',   'BLACK', 1, 8, 100),
    (140, 'KNIGHT', 'BLACK', 2, 8, 100),
    (141, 'BISHOP', 'BLACK', 3, 8, 100),
    (142, 'QUEEN',  'BLACK', 4, 8, 100),
    (143, 'KING',   'BLACK', 5, 8, 100),
    (144, 'BISHOP', 'BLACK', 6, 8, 100),
    (145, 'KNIGHT', 'BLACK', 7, 8, 100),
    (146, 'ROOK',   'BLACK', 8, 8, 100),
    (147, 'PAWN',   'BLACK', 1, 7, 100),
    (148, 'PAWN',   'BLACK', 2, 7, 100),
    (149, 'PAWN',   'BLACK', 3, 7, 100),
    (150, 'PAWN',   'BLACK', 4, 7, 100),
    (151, 'PAWN',   'BLACK', 5, 7, 100),
    (152, 'PAWN',   'BLACK', 6, 7, 100),
    (153, 'PAWN',   'BLACK', 7, 7, 100),
    (154, 'PAWN',   'BLACK', 8, 7, 100);
