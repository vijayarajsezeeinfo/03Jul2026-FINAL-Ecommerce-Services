/* Procedure structure for procedure `EZEE_SP_CATEGORY_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_CATEGORY_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_CATEGORY_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrName VARCHAR(30),
    IN pcrNamespaceCode VARCHAR(30),
    IN pitActiveFlag TINYINT,
    IN pitUpdatedBy INT,
    IN pitDebugFlag TINYINT,
    OUT pitRowCount INT
)
BEGIN

   DECLARE litNamespaceId INT DEFAULT 0;

/*
*----------------------------------------------------------------------------------------------------
* Variable Initialized
*----------------------------------------------------------------------------------------------------
*/
    SET pitRowCount = 0;
    
/*  
*----------------------------------------------------------------------------------------------------
* Getting Namespace ID from CODE
*----------------------------------------------------------------------------------------------------
*/    
    IF (EZEE_FN_ISNOTNULL(pcrNamespaceCode)) THEN
    
    SELECT `id` INTO litNamespaceId FROM `namespace` WHERE `code`=pcrNamespaceCode;

    END IF;
    
/*
*----------------------------------------------------------------------------------------------------
* Insert / Update / Delete Logic
*----------------------------------------------------------------------------------------------------
*/
    IF (pitActiveFlag = 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        /* UPDATE */

        UPDATE `categories`
           SET `name` = pcrName,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        /* INSERT */

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('CAT');

        INSERT INTO `categories`
        (
            `code`,
            `name`,
            `namespace_id`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            pcrName,
            litNamespaceId,
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

        UPDATE `categories`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;
