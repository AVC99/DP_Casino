CREATE DATABASE casino;
USE casino;
/* BALANCE HISTORY TABLE */
CREATE TABLE `balance_history` (
	`id` int NOT NULL AUTO_INCREMENT,
	`date` date NOT NULL,
	`total_balance` int NOT NULL,
	`username` varchar(200) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `user_balance_history_idx` (`username`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;

/* MATCH HISTORY TABLE */
  CREATE TABLE `match_history` (
	`match_id` int NOT NULL AUTO_INCREMENT,
	`match_date` date NOT NULL,
	`game_type` varchar(100) NOT NULL,
	`is_victory` tinyint NOT NULL,
	`username` varchar(200) NOT NULL,
	`amount` int NOT NULL,
	PRIMARY KEY (`match_id`),
	KEY `user_match_history_idx` (`username`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;

/* USERS TABLE */
  CREATE TABLE `users` (
	`username` varchar(200) NOT NULL,
	`total_balance` int NOT NULL DEFAULT '0',
	`password` varchar(200) NOT NULL,
	PRIMARY KEY (`username`),
	UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;