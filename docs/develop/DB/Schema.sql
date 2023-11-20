CREATE SCHEMA bboard;

USE bboard;

CREATE TABLE `users` (
  `user_id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `tier` integer NOT NULL,
  `solved_rank` integer NOT NULL,
  `img_url` varchar(255) NOT NULL
);

CREATE TABLE `tier_problem` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `tier` integer NOT NULL,
  `problem_count` integer NOT NULL,
  `page_no` integer NOT NULL,
  `page_idx` integer NOT NULL
);

CREATE TABLE `user_group` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `group_id` integer NOT NULL
);

CREATE TABLE `problem` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `problem_num` integer NOT NULL,
  `tier` integer NOT NULL,
  `title` varchar(255) NOT NULL
);

CREATE TABLE `problem_algorithm` (
  `problem_num` integer PRIMARY KEY,
  `algorithm` varchar(255) NOT NULL
);

CREATE TABLE `group` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
);

CREATE TABLE `recom_problem` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `group_id` integer NOT NULL,
  `problem_num` integer NOT NULL,
  `title` varchar(255) NOT NULL,
  `tier` integer NOT NULL
);

CREATE TABLE `user_tier_problem` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `tier` integer NOT NULL,
  `problem_num` integer NOT NULL,
  `title` varchar(255) NOT NULL
);

ALTER TABLE `tier_problem` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `user_group` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `problem` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `problem` ADD FOREIGN KEY (`problem_num`) REFERENCES `problem_algorithm` (`problem_num`);

ALTER TABLE `user_group` ADD FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE CASCADE;

ALTER TABLE `recom_problem` ADD FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE CASCADE;

ALTER TABLE `recom_problem` ADD FOREIGN KEY (`problem_num`) REFERENCES `problem_algorithm` (`problem_num`);

ALTER TABLE `user_tier_problem` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `user_tier_problem` ADD FOREIGN KEY (`problem_num`) REFERENCES `problem_algorithm` (`problem_num`);

