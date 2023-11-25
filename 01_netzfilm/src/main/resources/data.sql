insert into movie (RELEASE_DATE,FSK,ID,VERSION,UUID,TITLE) values ('1977-5-25','FSK_12',nextval('MOVIE_SEQ'),0,'984008c7-afee-4067-955e-0b55a21de59f','A new hope');
insert into movie (RELEASE_DATE,FSK,ID,VERSION,UUID,TITLE) values ('1980-5-17','FSK_12',nextval('MOVIE_SEQ'),0,'984008c7-afee-4067-955e-0b55a21de58e','The Empire strikes back');
insert into movie (RELEASE_DATE,FSK,ID,VERSION,UUID,TITLE) values ('1983-5-25','FSK_12',nextval('MOVIE_SEQ'),0,'984008c7-afee-4067-955e-0b55a21de58f','Return of the Jedi');
insert into movie (RELEASE_DATE,FSK,ID,VERSION,UUID,TITLE) values ('1999-12-1','FSK_18',nextval('MOVIE_SEQ'),0,'984008c7-afee-4067-955e-0b55a21de59d','Boese');

insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de59f'),'5582db45-710a-4500-b320-28506b855f93','VHS');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de59f'),'6509e039-0cbf-45d4-a3b8-b3c994189cf4','DVD');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de58e'),'cf80e78a-d1ec-482d-b46f-8025335631e7','VHS');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de58e'),'cf80e78a-d1ec-482d-b46f-8025335631e6','DVD');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de58f'),'08a12a6f-7114-4e1f-b36e-164245a9d086','VHS');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de58f'),'880567fc-6d44-4dd8-9fa3-2e8112c8e0e4','DVD');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de59d'),'a5a631ef-f4f1-4958-9de0-683d79ae575c','VHS');
insert into copy  (LENT,ID,VERSION,MOVIE_ID,UUID,TYPE) values (false,nextval('COPY_SEQ'),0, (select id from movie where uuid = '984008c7-afee-4067-955e-0b55a21de59d'),'ca8b95bf-4f6e-437f-96e6-0b77b3c96c74','DVD');
