INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (37, 'john@email.com', 'John', 'Doe', 'password', 'male', 'female', FALSE , 'INVESTIGATOR', 'REFORMER');
INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (26, 'Carla@email.com', 'Carla', 'Jackson', 'password', 'female', 'male', FALSE , 'LOYALIST', 'HELPER');
INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (31, 'Clare@email.com', 'Clare', 'Fraeser', 'password', 'female', 'male', FALSE , 'REFORMER', 'ACHIEVER');
INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (22, 'Mike@email.com', 'Mike', 'Gregg', 'password', 'male', 'female', FALSE , 'PEACEMAKER', 'CHALLENGER');

INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (1, 2, FALSE);
INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (1, 3, FALSE);
INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (1, 4, FALSE);
INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (2, 4, FALSE);