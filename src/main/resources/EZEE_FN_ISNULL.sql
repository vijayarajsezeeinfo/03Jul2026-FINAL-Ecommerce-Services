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
