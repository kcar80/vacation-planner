-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema vacation_app
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema vacation_app
-- -----------------------------------------------------
drop database if exists vacation_app;
create database vacation_app;

use vacation_app;

-- -----------------------------------------------------
-- Table `vacation_app`.`Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vacation_app`.`Location` (
  `location_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vacation_app`.`Vacation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vacation_app`.`Vacation` (
  `vacation_id` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `description` VARCHAR(250) NOT NULL,
  `leisure_level` INT NOT NULL,
  `location_id` INT NOT NULL,
  PRIMARY KEY (`vacation_id`),
  INDEX `fk_Vacation_Location_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_Vacation_Location`
    FOREIGN KEY (`location_id`)
    REFERENCES `vacation_app`.`Location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vacation_app`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vacation_app`.`User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `admin` TINYINT NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vacation_app`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vacation_app`.`Comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(250) NOT NULL,
  `user_id` INT NOT NULL,
  `vacation_id` INT NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_Comment_User1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Comment_Vacation1_idx` (`vacation_id` ASC) VISIBLE,
  CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `vacation_app`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comment_Vacation1`
    FOREIGN KEY (`vacation_id`)
    REFERENCES `vacation_app`.`Vacation` (`vacation_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vacation_app`.`Vacation_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vacation_app`.`Vacation_User` (
  `vacation_user_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `vacation_id` INT NOT NULL,
  PRIMARY KEY (`vacation_user_id`),
  INDEX `fk_Vacation_User_User1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Vacation_User_Vacation1_idx` (`vacation_id` ASC) VISIBLE,
  CONSTRAINT `fk_Vacation_User_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `vacation_app`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Vacation_User_Vacation1`
    FOREIGN KEY (`vacation_id`)
    REFERENCES `vacation_app`.`Vacation` (`vacation_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
