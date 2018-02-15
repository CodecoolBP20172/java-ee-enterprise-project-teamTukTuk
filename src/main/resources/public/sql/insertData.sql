INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (37, 'john@email.com', 'John', 'Doe', '$2a$10$.FIKdsGgUnC7Qe.MRGypC./pNX8VtvsJSaDsjkQ/lEz4Gnqud9t4C', 'male', 'female', FALSE , 'INVESTIGATOR', 'REFORMER');
INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (26, 'Carla@email.com', 'Carla', 'Jackson', '$2a$10$.FIKdsGgUnC7Qe.MRGypC./pNX8VtvsJSaDsjkQ/lEz4Gnqud9t4C', 'female', 'male', FALSE , 'LOYALIST', 'HELPER');
INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (31, 'Clare@email.com', 'Clare', 'Fraeser', '$2a$10$.FIKdsGgUnC7Qe.MRGypC./pNX8VtvsJSaDsjkQ/lEz4Gnqud9t4C', 'female', 'male', FALSE , 'REFORMER', 'INVESTIGATOR');
INSERT INTO users (age, email, firstname, lastname, password, gender, partnergender, inConversation, optpartnerperstype, personalitytype) VALUES (22, 'Mike@email.com', 'Mike', 'Gregg', '$2a$10$.FIKdsGgUnC7Qe.MRGypC./pNX8VtvsJSaDsjkQ/lEz4Gnqud9t4C', 'male', 'female', FALSE , 'LOYALIST', 'CHALLENGER');

INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (1, 2, FALSE);
INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (1, 3, FALSE);
INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (1, 4, FALSE);
INSERT INTO chatbox (firstuser_id, seconduser_id, active) VALUES (2, 4, FALSE);
