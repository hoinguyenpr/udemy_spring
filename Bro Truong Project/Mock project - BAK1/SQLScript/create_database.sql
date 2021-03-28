USE `demo`;
DROP TABLE IF exists `item_stock`;
DROP TABLE IF exists `part_detail`;
CREATE TABLE `part_detail`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`part_number` varchar(6) NOT NULL,
	`part_desc` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
);
CREATE TABLE `item_stock` (
	`id`	int(11)		NOT NULL AUTO_INCREMENT,
	`part_number` varchar(6) NOT NULL,
	`stock_qty`		int(11) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `FK_PARTNUM_idx` (`part_number`),
	CONSTRAINT `FK_PART` FOREIGN KEY (`part_number`)
	REFERENCES `part_detail` (`part_number`)
)