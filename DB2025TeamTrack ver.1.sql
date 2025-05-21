-- 1. 데이터베이스 및 사용자 생성
drop database if exists DB2025TeamTrack; -- 재실행 시 중복 오류 방지용
drop user if exists DB2025TeamTrack@localhost;
create user DB2025TeamTrack@localhost identified by 'DB2025TeamTrack'; 

-- JDBC 접속 정보
-- DB URL: jdbc:mysql://localhost:3306/DB2025TeamTrack
-- user: DB2025TeamTrack
-- password: DB2025TeamTrack

create database DB2025TeamTrack;
grant all privileges on DB2025TeamTrack.*to DB2025TeamTrack@localhost with grant option;
use DB2025TeamTrack;

-- 2. 테이블 생성
create table DB2025_user (
user_id int primary key auto_increment,
email varchar(100) not null,
password varchar(100) not null,
name varchar(50) not null,
role varchar(20) default 'student',
created_at datetime default current_timestamp
);

-- 샘플 유저 (6명)
insert into DB2025_user (email, password, name, role) values
('wonbin@example.com', 'pw0302', '원빈', 'leader'),
('eunseok@example.com', 'pw0319', '은석', 'student'),
('sungchan@example.com', 'pw0913', '성찬', 'student'),
('shotaro@example.com', 'pw1125', '쇼타로', 'coordinator'),
('sohee@example.com', 'pw1121', '소희', 'student'),
('anton@example.com', 'pw0321', '앤톤', 'student');


create table DB2025_team (
team_id int primary key auto_increment,
team_name varchar(100) not null,
subject varchar(100) not null,
deadline date not null,
leader_id int,
foreign key (leader_id) references DB2025_user(user_id)
);

-- 샘플 팀 (1개)
insert into DB2025_team (team_name, subject, deadline, leader_id) values
('Team Rize', 'Database Project', '2023-09-04', 1);

create table DB2025_team_member (
team_id int,
user_id int,
joined_at datetime default current_timestamp, -- 사용자가 team을 등록하면 joined_at이라는 열에 자동으로 등록 시간이 저장됨
primary key (team_id, user_id),
foreign key (team_id) references DB2025_team(team_id),
foreign key (user_id) references DB2025_user(user_id)
);

-- 팀 멤버 (6명 * 1팀 = 6개)
insert into DB2025_team_member(team_id, user_id) values
(1,1), (1,2), (1,3), (1,4), (1,5), (1,6);

create table DB2025_role_assignment (
team_id int,
user_id int,
role_name varchar(50) not null,
primary key (team_id, user_id),
foreign key (team_id) references DB2025_team(team_id),
foreign key (user_id) references DB2025_user(user_id)
);

-- 역할 배정 (6명)
insert into DB2025_role_assignment (team_id, user_id, role_name) values
(1, 1, 'ppt&발표'), (1, 2, '백엔드'), (1, 3, '백엔드'),
(1, 4, '프론트엔드'), (1, 5, '디자인'), (1, 6, 'DB담당');

create table DB2025_task (
task_id int primary key auto_increment,
title varchar(200) not null,
due_date date not null,
status enum('진행 중', '완료', '미제출', '지각 제출') default '진행 중', -- 선택지는 고정, 이 중 하나만 입력되는 타입
assigned_to int,
team_id int,
foreign key (assigned_to) references DB2025_user(user_id),
foreign key (team_id) references DB2025_team(team_id)
);

-- 과제 (5개)
insert into DB2025_task (title, due_date, status, assigned_to, team_id) values
('요구명세서 작성', '2025-04-20', '완료', 6, 1),
('초기 SQL 작성', '2025-05-21', '지각 제출', 6, 1),
('Java 구조 설계 1', '2025-05-22', '진행 중', 2, 1),
('Java 구조 설계 2', '2025-05-24', '진행 중', 3, 1),
('디자인 초안', '2025-04-28', '미제출', 5, 1);

create table DB2025_meeting (
meeting_id int primary key auto_increment,
team_id int,
meeding_date date not null,
content text,
decision text,
foreign key (team_id) references DB2025_team(team_id)
);

-- 회의록 (2개)
insert into DB2025_meeting (team_id, meeting_date, content, decision) values
(1, '2025-04-10', '역할 분배 및 일정 설정', '역할 확정 및 마감 일정 정함'),
(1, '2025-05-15', '1차 개발 점검 회의', '기능 확정 및 DB 진도 체크함');


create table DB2025_feedback (
feedback_id int primary key auto_increment,
sender_id int,
receiver_id int,
content text not null,
created_at datetime default current_timestamp,
foreign key (sender_id) references DB2025_user(user_id),
foreign key (receiver_id) references DB2025_user(user_id)
);

-- 피드백 (3개)
insert into DB2025_feedback (sender_id, receiver_id, content) values
(4, 1, '수고하셨습니다!'),
(1, 5, '마감기한 지켜주세요...ㅠㅠ'),
(2, 6, '초기 데이터 다양하게 넣으면 좋을 것 같아요!!');

create table DB2025_contribution (
user_id int,
team_id int,
submitted_count int default 0,
attended_count int default 0,
bonus float default 0.0,
percentage float default 0.0,
primary key (user_id, team_id),
foreign key (user_id) references DB2025_user(user_id),
foreign key (team_id) references DB2025_team(team_id)
);

-- 기여도(6명)
insert into DB2025_contribution(user_id, team_id, submitted_count, attended_count, bonus) values
(1, 1, 1, 2, 1.0),
(2, 1, 0, 1, 0.0),
(3, 1, 0, 1, 0.0),
(4, 1, 1, 2, 1.0),
(2, 1, 1, 2, 1.0),
(5, 1, 2, 2, 1.0);

-- 3. 인덱스 생성
create index idx_user_email on DB2025_user(email);
create index idx_task_status on DB2025_task(status);
create index idx_feedback_receiver on DB2025_feedback(receiver_id);
create index idx_team_member on DB2025_team_member(user_id);

-- 인덱스별 생성 이유
/* 1. idx_user email: 이메일로 사용자 로그인 시 빠르게 조회, 이메일은 특성상 unique
2. idx_task_status: 과제 상태(진행 중/완료/미제출/지각제출)별로 필터링할 때 속도 향상을 위해
3. idx_feedback_reciever: 자신이 "받은" 피드백만 빠르게 조회할 수 있도록 하기 위해
4. idx_team_member: 사용자가 자신이 속한 팀 목록을 빠르게 불러올 수 있도록 */

-- 4. 뷰 생성

-- 기여도 요약 뷰(뷰 생성 이유: 계산이 복잡하고, 자주 보여줘야 함)
create view DB2025_view_contribution_summary as
select
c.team_id, u.name,
((c.submitted_count*1.0) + (c.attended_count*0.5) + c.bonus) as raw_score,
round(
((c.submitted_count*1.0) + c.attended_count*0.5 + c.bonus) /
max((c.submitted_count*1.0 + c.attended_count*0.5 + c.bonus))
over (partition by c.team_id)*100, 2
 )as percenetage
from DB2025_contribution c
join DB2025_user u on c.user_id = u.user_id;

-- 과제 상태 뷰 (뷰 생성 이유: join이 매번 필요해서)
create view DB2025_view_task_status as
select
t.team_id, u.name as 담당자, t.title as 과제명, t.status as 상태
from DB2025_task t join DB2025_user u on t.assigned_to = u.user_id;




