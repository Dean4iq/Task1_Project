package ua.training.util;

public interface DBQueries {
    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `task1`.`fullcontactdata` (\n" +
            "  `db_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` LONGTEXT NULL,\n" +
            "  `lastname` LONGTEXT NULL,\n" +
            "  `nickname` LONGTEXT NULL,\n" +
            "  `phone` LONGTEXT NULL,\n" +
            "  `id` LONGTEXT NULL,\n" +
            "  PRIMARY KEY (`db_id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`db_id` ASC))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8\n" +
            "COLLATE = utf8_bin;";
    String GET_DATA_FROM_TABLE = "SELECT * FROM fullcontactdata";
    String GET_SORTED_DATA_BY_NAME_FROM_TABLE_ASC = "SELECT * FROM fullcontactdata ORDER BY name ASC";
    String GET_SORTED_DATA_BY_NAME_FROM_TABLE_DESC = "SELECT * FROM fullcontactdata ORDER BY name DESC";
    String GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_ASC = "SELECT * FROM fullcontactdata ORDER BY lastname ASC";
    String GET_SORTED_DATA_BY_LASTNAME_FROM_TABLE_DESC = "SELECT * FROM fullcontactdata ORDER BY lastname DESC";
    String HEADER_INSERT_TO_DB = "INSERT INTO fullcontactdata (";
    String DELETE_BY_ID_QUERY = "DELETE FROM fullcontactdata WHERE db_id IN (";
    String HEADER_SELECT_FROM_WHERE_ID = "SELECT * FROM fullcontactdata WHERE db_id IN (";
    String HEADER_UPDATE_ALL_COLUMNS = "UPDATE fullcontactdata SET ";
}
