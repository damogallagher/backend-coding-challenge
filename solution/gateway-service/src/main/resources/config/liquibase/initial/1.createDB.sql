--liquibase formatted sql

--changeset engage:1

CREATE TABLE IF NOT EXISTS T_AUTH (
  id INT(11) NOT NULL AUTO_INCREMENT,
  api_key VARCHAR(100) NULL, 
  name VARCHAR(40) NOT NULL,
  email_address VARCHAR(40) NOT NULL,
  date_added TIMESTAMP NOT NULL,  
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

