USE `my_DB`;

--
-- Message database initialization script, please refer
-- to report.pdf for more information.
-- ODD - 2022
--

DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `text_message`;
DROP TABLE IF EXISTS `file_message`;

create table `messages`(
  `messageID` integer not null auto_increment, 
  `sender` char(36) not null, 
  `receiver` char(36) not null, 
  `sendDate` bigint not null, 
  `contentID` integer not null, 
  `messageType` integer, 
  PRIMARY KEY (`messageID`)
);

create table `text_message`(
  `messageID` integer not null, 
  `messagePart` integer, 
  `content` varchar(512)
);

create table `file_message`(
  `messageID` integer not null, 
  `fileName` varchar(128) not null, 
  `fileID` char(36), 
  `size` integer
);
