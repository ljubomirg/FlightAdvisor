insert into user values ('RegularUser', 'ljubo', 'Ljubomir', 'Grubljesic', 's', 's');
insert into user values ('Administrator', 'marko', 'Marko', 'Ikic', 'm', 'm');
insert into city values ('Banja Luka', 'RS', 'Veliki grad');
insert into city values ('Prijedor', 'RS', 'Manji grad');
insert into city values ('Novi Grad', 'RS', 'Mali grad');
insert into city values ('Mrkonic Grad', 'RS', 'Mali grad');
insert into comment(CREATED_DATE, COMMENT_DESCRIPTION, MODIFIED_DATE, CITY_NAME)  values(parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'), 'First comment',parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'), 'Prijedor');
insert into comment(CREATED_DATE, COMMENT_DESCRIPTION, MODIFIED_DATE, CITY_NAME)  values(parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'), 'Second comment',parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'), 'Novi Grad');
insert into comment(CREATED_DATE, COMMENT_DESCRIPTION, MODIFIED_DATE, CITY_NAME)  values(parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'), 'Third comment',parsedatetime('17-09-2012 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'), 'Prijedor');

