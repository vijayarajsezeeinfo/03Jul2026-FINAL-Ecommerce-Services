/* Function  structure for function  `EZEE_FN_ISNOTNULL` */

/*!50003 DROP FUNCTION IF EXISTS `EZEE_FN_ISNOTNULL` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `EZEE_FN_ISNOTNULL`(
    pcrString VARCHAR(1000)
) RETURNS tinyint
    DETERMINISTIC
BEGIN

    RETURN IF(
        ISNULL(pcrString)
        OR TRIM(pcrString) = ''
        OR TRIM(pcrString) = 'null'
        OR TRIM(pcrString) = 'NULL'
        OR TRIM(pcrString) = 'NA',
        FALSE,
        TRUE
    );

END */$$
DELIMITER ;

/* Function  structure for function  `EZEE_FN_ISNULL` */

/*!50003 DROP FUNCTION IF EXISTS `EZEE_FN_ISNULL` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `EZEE_FN_ISNULL`(
    pcrString VARCHAR(1000)
) RETURNS tinyint
    DETERMINISTIC
BEGIN

    RETURN IF(
        ISNULL(pcrString)
        OR TRIM(pcrString) = ''
        OR TRIM(pcrString) = 'null'
        OR TRIM(pcrString) = 'NULL'
        OR TRIM(pcrString) = 'NA',
        TRUE,
        FALSE
    );

END */$$
DELIMITER ;

/* Function  structure for function  `EZEE_FN_RANDOM_CODE_GENERATOR` */

/*!50003 DROP FUNCTION IF EXISTS `EZEE_FN_RANDOM_CODE_GENERATOR` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `EZEE_FN_RANDOM_CODE_GENERATOR`(pcrPrefix VARCHAR(15)) RETURNS varchar(20) CHARSET utf8mb4
    NO SQL
BEGIN

/*
*----------------------------------------------------------------------------------------------------
* Variable Declare
*-----------------------------------------------------------------------------------------------------
*/

 DECLARE lcrCode VARCHAR(20);

/*
*-----------------------------------------------------------------------------------------------------
* Variable Initialized
*-----------------------------------------------------------------------------------------------------
*/

  SET lcrCode = CONCAT(pcrPrefix, FLOOR(10000 + RAND() * 90000));

/*
*-----------------------------------------------------------------------------------------------------
* Return Generated Code
*-----------------------------------------------------------------------------------------------------
*/

  RETURN lcrCode;

END */$$
DELIMITER ;
