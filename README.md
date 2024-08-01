# fisa-01
우리fisa 팀 프로젝트
 - 우리 상품의 특징!! 무궁무진한 확장성과 정확한 데이터 표현!

1. 데이터 테이블 관리
2. 회원가입시 돈을입력받아서 500원 미만일 경우 실버, 500원 이상일 경우 골드, 다이아몬드
3. 이름은 3글자이하

=목표 기능=
1. 회원가입
2. 로그인 "누구누구님이 로그인 하셨습니다. 잔액은 얼마입니다, 그리고 등급은 무엇입니다."

Controller 기능:
1. showAllCustomer
 - 모든 회원번호, 이름, 잔액, 등급번호 출력
2. login:
 - 아이디를 입력받아서 이름과, 잔액, 등급 출력
3. signin
 - 이름과 돈을 입력받아 회원 추가
 - 회원번호, 이름과 돈을 입력받아 회원 추가

EndView 기능:
1. lineCounter
 - 행 번호 출력

MyUtil 기능:
1. strMapping
 - char배열 받아서 문자열 반환
 - 문자열 받아서 char배열 반환
2. strInteger
 - 래퍼클래스 받아서 널인경우 문자열 "null"반환

=작업 내역=

-9시타임-
1. 회의를 통한 명세서 작성
2. 데이터 테이블 설계
3. mysql을 dbeaver를 사용해서 projectA라는 데이터 베이스를 생성
4. 데이터 테이블 생성
5. 제약조건 설정/확인
6. 작업 검수

-10시타임-
1. 멤버십 테이블 레코드 입력 (3개)
2. 커스터머 테이블 레코드 입력 (3명)
3. 자바 프로젝트 셋팅 (메이븐: mysql커낵터, lombok)
4. 자바 프로젝트 시작을 위해 StartView Main에서 db에 접속하고, 데이터를 조회/출력하는 부분까지 완료

-11시타임-
1. StartView Main에 만들어둔 로직을 활용하기위해 분할

-12시타임-
1. 로직 분할해서 jdbc에 연결되는 부분까지 완료/확인

-1시타임-
1. 점심시간

-2시타임-
1. 라인카운터 기능 추가 (출력 행을 표시해 줍니다.)
2. 작업도중 resultSet에서 next를 안해주는 오류를 범했고, "유XX"님이 문제를 찾아 해결해주셨습니다
3. showCustomer와 showAllCustomer 기능 완성

-3시타임-
1. 로그인 기능 개발
2. 멤버쉽 조회기능 개발
3. 회원가입 기능 개발중 executeQuery문을 사용해서 문제가 발생, executeUpdate문을 사용하여 해결 (최XX 님이 해결)
4. 회원가입 기능 개발 완료

-4시타임-
1. 회원가입 예외처리 강화
2. rs

-5시타임-
1. 코드 확인
2. 오류 수정
Controller 33번 줄에 c==null 추가
Model 80~84번 줄 MyUtil 삭제 후 데이터 유효성 검사



SQL 명령어
```
create database project_w;

use project_w;

create table customer(
	cno int not null,
	cname varchar(3),
	cmoney int,
	cpw varchar(3),
	mno int,
	constraint pk_cno primary key (cno)
)

desc customer;

drop table membership;

create table membership(
	mno int not null,
	mname varchar(10),
	constraint pk_mno primary key (mno)
)

desc membership;

alter table customer 
add constraint fk_cus_mem foreign key (mno) references membership (mno)
on delete no action on update no action;

select * from information_schema.table_constraints where TABLE_NAME='customer';

insert into membership values(10, "silver");
insert into membership values(20, "gold");
insert into membership values(30, "diamond");

insert into customer values(101, "NOA", 100, "123", 10);
insert into customer values(102, "MOA", 300, "456", 20);
insert into customer values(103, "KAL", 600, "789", null);

select * from customer;
select * from membership;

update 
```
