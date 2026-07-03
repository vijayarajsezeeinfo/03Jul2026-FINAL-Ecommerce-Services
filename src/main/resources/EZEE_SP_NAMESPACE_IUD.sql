/* Procedure structure for procedure `EZEE_SP_NAMESPACE_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_NAMESPACE_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_NAMESPACE_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrName VARCHAR(100),
    IN pitActiveFlag TINYINT,
    IN pitUpdatedBy INT,
    IN pitDebugFlag TINYINT,
    OUT pitRowCount INT
)
BEGIN

/*
*----------------------------------------------------------------------------------------------------
* Variable Initialized
*----------------------------------------------------------------------------------------------------
*/
    SET pitRowCount = 0;

/*
*----------------------------------------------------------------------------------------------------
* Insert / Update / Delete Logic
*----------------------------------------------------------------------------------------------------
*/
    IF (pitActiveFlag = 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        /* UPDATE */

        UPDATE `namespace`
           SET `name` = pcrName,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        /* INSERT */

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('NS');

        INSERT INTO `namespace`
        (
            `code`,
            `name`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            pcrName,
            1,
            pitUpdatedBy,
            NOW()
        );

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag != 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        /* SOFT DELETE / RE-ACTIVATE */

        IF (pitActiveFlag = 9) THEN
            SET pitActiveFlag = 1;
        END IF;

        UPDATE `namespace`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;
