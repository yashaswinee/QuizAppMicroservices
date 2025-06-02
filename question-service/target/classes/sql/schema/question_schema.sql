-- Question Table Schema

CREATE TABLE IF NOT EXISTS question (
    id SERIAL PRIMARY KEY,
    question_title VARCHAR(500) NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    difficulty VARCHAR(50) NOT NULL CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD'))
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_question_difficulty ON question(difficulty);
CREATE INDEX IF NOT EXISTS idx_question_title ON question(question_title);

-- Sample Questions Data

INSERT INTO question (question_title, option1, option2, option3, option4, answer, difficulty) VALUES
('What is the capital of France?', 'London', 'Berlin', 'Paris', 'Madrid', 'Paris', 'EASY'),
('Which programming language is known for "Write Once, Run Anywhere"?', 'Python', 'Java', 'C++', 'JavaScript', 'Java', 'EASY'),
('What is the time complexity of binary search?', 'O(n)', 'O(log n)', 'O(n²)', 'O(1)', 'O(log n)', 'MEDIUM'),
('Which design pattern ensures a class has only one instance?', 'Factory', 'Observer', 'Singleton', 'Strategy', 'Singleton', 'MEDIUM'),
('What is the CAP theorem in distributed systems?', 'Consistency, Availability, Partition tolerance', 'Cache, API, Performance', 'Code, Algorithm, Process', 'Create, Alter, Partition', 'Consistency, Availability, Partition tolerance', 'HARD');

-- Easy Questions
INSERT INTO question (question_title, option1, option2, option3, option4, answer, difficulty) VALUES
('What does HTML stand for?', 'Hyper Text Markup Language', 'High Tech Modern Language', 'Home Tool Markup Language', 'Hyperlink Text Management Language', 'Hyper Text Markup Language', 'EASY'),
('Which symbol is used for single-line comments in Java?', '//', '/* */', '#', '--', '//', 'EASY');

-- Medium Questions  
INSERT INTO question (question_title, option1, option2, option3, option4, answer, difficulty) VALUES
('What is polymorphism in OOP?', 'Having multiple constructors', 'Ability of objects to take multiple forms', 'Creating multiple classes', 'Using multiple inheritance', 'Ability of objects to take multiple forms', 'MEDIUM'),
('Which HTTP status code indicates "Not Found"?', '200', '404', '500', '403', '404', 'MEDIUM');

-- Hard Questions
INSERT INTO question (question_title, option1, option2, option3, option4, answer, difficulty) VALUES
('What is the Byzantine Generals Problem?', 'A military strategy problem', 'A consensus problem in distributed computing', 'A graph theory problem', 'A sorting algorithm problem', 'A consensus problem in distributed computing', 'HARD'),
('In microservices, what is the Saga pattern used for?', 'Service discovery', 'Load balancing', 'Distributed transaction management', 'API gateway routing', 'Distributed transaction management', 'HARD');