-- create database resumeTaskDB;

create table skills(
                       id int primary key generated always as identity,
                       name varchar(20)
);

INSERT INTO skills (name) VALUES
                              ('JavaScript'),
                              ('Python'),
                              ('Java'),
                              ('C#'),
                              ('Ruby'),
                              ('PHP'),
                              ('HTML/CSS'),
                              ('SQL'),
                              ('Swift'),
                              ('Go'),
                              ('TypeScript'),
                              ('Kotlin'),
                              ('Rust'),
                              ('Dart'),
                              ('Scala');

create table  bad_workers(
                             id int primary key generated always as identity,
                             name varchar(40)
);


INSERT INTO bad_workers (name) VALUES
                                   ('Иванов Иван Иванович'),
                                   ('Петров Алексей Сергеевич'),
                                   ('Смирнова Мария Викторовна');

create table bad_company(
                            id int primary key generated always as identity,
                            name varchar(20)
);

INSERT INTO bad_company (name) VALUES
                                   ('Tech Innovations'),
                                   ('Future Solutions'),
                                   ('Code Masters'),
                                   ('Web Wizards'),
                                   ('Data Dynamics');

create type level_skills  as enum ('Junior','Middle','Senior');
create type category_enum  as enum ('FirstPriority','SecondPriority','LastPriority', 'Reject');
create type decide_enum  as enum ('Accept','Send test task','Additional interview','Reject');

create table resumes(
                        id int primary key generated always as identity,
                        fio varchar(80),
                        prev_company varchar(20),
                        position varchar(20),
                        level level_skills,
                        salary int,
                        description varchar(100),
                        category category_enum,
                        decide decide_enum
);


create table resume_skill(
                             resume_id int,
                             skill_id int,
                             FOREIGN KEY (resume_id) REFERENCES resumes(id),
                             FOREIGN KEY (skill_id) REFERENCES skills(id)
);

