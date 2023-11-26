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

INSERT INTO users (user_name, tier, solved_rank, img_url) VALUES 
('kjh105702', 0, 0, ''),
('csj9912', 0, 0, ''),
('bmike0413', 0, 0, ''),
('98cline', 0, 0, ''),
('pisouz7', 0, 0, ''),
('jwon000', 0, 0, ''),
('tbsapc', 0, 0, ''),
('29tigerhg', 0, 0, ''),
('ygj9605', 0, 0, ''),
('soyi_java', 0, 0, ''),
('wbypes18', 0, 0, ''),
('end24', 0, 0, ''),
('d_heon', 0, 0, ''),
('yhu2121', 0, 0, ''),
('leeje0506', 0, 0, ''),
('lsy9752', 0, 0, ''),
('oree', 0, 0, ''),
('thakd221', 0, 0, ''),
('lbsoo1021', 0, 0, ''),
('kwonjaehyunssafy10th', 0, 0, ''),
('deu03161', 0, 0, ''),
('hyes0211', 0, 0, ''),
('wlghks98k', 0, 0, ''),
('lache9688', 0, 0, ''),
('minah', 0, 0, ''),
('suzy0120', 0, 0, ''),
('gardener', 0, 0, ''),
('jo8gwang', 0, 0, ''),
('kasey', 0, 0, '');