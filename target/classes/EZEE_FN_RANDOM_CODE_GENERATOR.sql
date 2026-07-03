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
