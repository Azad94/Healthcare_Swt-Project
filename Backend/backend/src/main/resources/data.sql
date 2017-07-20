-- Basic Database Login
INSERT INTO `role` (`id`,`name`) VALUES (1,'Administrator');
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('admin','admin',' ',CURRENT_TIMESTAMP,1);
-- Testdaten
INSERT INTO `role` (`id`,`name`) VALUES (2,'User');
INSERT INTO `role` (`id`,`name`) VALUES (3,'Schieber');
INSERT INTO `role` (`id`,`name`) VALUES (4,'Techniker');
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('user','123456',' ',CURRENT_TIMESTAMP,2);
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('w','1',' ',CURRENT_TIMESTAMP,3);
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('t','1',' ',CURRENT_TIMESTAMP,4);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (1,1,1,1);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (2,1,1,2);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (3,1,2,1);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (4,1,2,2);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (5,2,1,1);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000001-healthcarew123456789101",1,1);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000002-healthcarew123456789101",1,2);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000003-healthcarew123456789101",2,1);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000004-healthcarew123456789101",2,2);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000005-healthcarew123456789101",3,2);
INSERT INTO `beacon_object` (`id`,`beacon_object_type`,`name`,`state`,`beacon_uuid`,`location_id`) VALUES (1,"Standardbett","beacon",1,"00000005-healthcarew123456789101",1);
INSERT INTO `beacon_object` (`id`,`beacon_object_type`,`name`,`state`,`beacon_uuid`,`location_id`) VALUES (2,"Kinderbett","beacon",1,"00000001-healthcarew123456789101",2);
INSERT INTO `beacon_object` (`id`,`beacon_object_type`,`name`,`state`,`beacon_uuid`,`location_id`) VALUES (3,"Brandschutztür","beacon",1,"00000002-healthcarew123456789101",3);
INSERT INTO `task` (`id`,`creation_time`,`description`,`last_edited`,`level`,`state`,`beacon_object_id`,`creator_id`,`role_id`,`name`) VALUES (1, CURRENT_TIMESTAMP,"eine wichtige Aufgabe",CURRENT_TIMESTAMP,1,1,1,2,1,"Transi");
INSERT INTO `task` (`id`,`creation_time`,`description`,`last_edited`,`level`,`state`,`beacon_object_id`,`creator_id`,`role_id`,`name`) VALUES (2, CURRENT_TIMESTAMP,"eine wichtige Aufgabe",CURRENT_TIMESTAMP,1,1,2,2,1,"Maini");
INSERT INTO `task` (`id`,`creation_time`,`description`,`last_edited`,`level`,`state`,`beacon_object_id`,`creator_id`,`role_id`,`name`) VALUES (3, CURRENT_TIMESTAMP,"eine wichtige Aufgabe",CURRENT_TIMESTAMP,1,1,3,2,1,"Cleani");
INSERT INTO `transport_task` (`id`,`target_location_id`) VALUES (1,5);
INSERT INTO `cleaning_task` (`id`) VALUES (3);
INSERT INTO `maintainance_task` (`repeat_task_in_days`,`id`) VALUES (1,2);