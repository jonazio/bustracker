# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Line_routes (
  line_id                   bigint not null,
  line_topic                varchar(255),
  line_type                 varchar(255),
  description               varchar(255),
  constraint pk_Line_routes primary key (line_id))
;

create table Positions (
  seq_id                    bigint not null,
  vehicle_id                bigint,
  line_id                   bigint,
  gps_x                     bigint,
  gps_y                     bigint,
  timestamp                 timestamp,
  constraint pk_Positions primary key (seq_id))
;

create sequence Line_routes_seq;

create sequence Positions_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists Line_routes;

drop table if exists Positions;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists Line_routes_seq;

drop sequence if exists Positions_seq;

