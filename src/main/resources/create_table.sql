create table PATIENT
(
    ID               BIGINT identity
                constraint PATIENT_PK
                          primary key,
    NAME       CHARACTER(50) not null,
    SURNAME    CHARACTER(50) not null,
    PATRONYMIC CHARACTER(50) not null,
    PHONE      CHARACTER(10) not null
);

insert into PATIENT (id, name, patronymic, surname, phone) values (1, 'Leon', 'Yurocjkin', 'Arrighetti', '5586343698');
insert into PATIENT (id, name, patronymic, surname, phone) values (2, 'Artemis', 'Ossenna', 'Coulman', '3085888564');
insert into PATIENT (id, name, patronymic, surname, phone) values (3, 'Kris', 'Gatley', 'Thomasen', '4206628002');
insert into PATIENT (id, name, patronymic, surname, phone) values (4, 'Wolfy', 'Mallinson', 'Tossell', '1242919336');
insert into PATIENT (id, name, patronymic, surname, phone) values (5, 'Rhett', 'Tregonna', 'Fardy', '8255312207');
insert into PATIENT (id, name, patronymic, surname, phone) values (6, 'Johnny', 'Coslitt', 'Sauniere', '4024683017');
insert into PATIENT (id, name, patronymic, surname, phone) values (7, 'Ronalda', 'Esel', 'Querrard', '6586192412');
insert into PATIENT (id, name, patronymic, surname, phone) values (8, 'Megen', 'Boyett', 'Antoniak', '9868367279');
insert into PATIENT (id, name, patronymic, surname, phone) values (9, 'Ashely', 'Copper', 'Blenkinsop', '6003287181');
insert into PATIENT (id, name, patronymic, surname, phone) values (10, 'Allis', 'Dunford', 'Polin', '9513100029');

create table DOCTOR
(
    ID                    BIGINT identity
                     constraint DOCTOR_PK
                              primary key,
    NAME           CHARACTER(50) not null,
    SURNAME        CHARACTER(50) not null,
    PATRONYMIC     CHARACTER(50) not null,
    SPECIALIZATION CHARACTER(50) not null
);

insert into DOCTOR (id, name, patronymic, surname, specialization) values (1, 'Zacherie', 'Housbie', 'MacCahee', 'SURGEON');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (2, 'Slade', 'Pantin', 'Barukh', 'PSYCHOTHERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (3, 'Ermengarde', 'Garham', 'Genty', 'THERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (4, 'Ettore', 'Batchley', 'Gutch', 'NEUROLOGIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (5, 'Vassily', 'Preist', 'Rose', 'PSYCHOTHERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (6, 'Aluin', 'Fantonetti', 'Jagson', 'DENTIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (7, 'Galen', 'Bosman', 'Eldridge', 'THERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (8, 'Georgy', 'Colbridge', 'Nussen', 'SURGEON');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (9, 'Ardith', 'Chace', 'Medina', 'SURGEON');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (10, 'Zilvia', 'Evins', 'Bellenie', 'DENTIST');

create table RECIPE
(
    ID                  BIGINT identity
               constraint TABLE_NAME_PK
                            primary key,
    DESCRIPTION   VARCHAR(200) not null,
    PATIENT_ID          BIGINT not null
        constraint RECIPE_PATIENT_ID_FK
                     references PATIENT,
    DOCTOR_ID           BIGINT not null
         constraint RECIPE_DOCTOR_ID_FK
                      references DOCTOR,
    DATE_CREATION                  DATE,
    VALIDITY                       DATE,
    PRIORITY      CHARACTER(20) not null
);

insert into RECIPE (id, description, patient_id, doctor_id, date_creation, validity, priority) values (1, 'dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi', 8, 5, '2019-08-19', '2019-11-19','NORMAL');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, validity, priority) values (2, 'cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum', 3, 3, '2019-09-08', '2019-10-19','NORMAL');


