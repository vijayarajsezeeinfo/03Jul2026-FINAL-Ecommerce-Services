/* Procedure structure for procedure `EZEE_SP_ADDRESS_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_ADDRESS_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_ADDRESS_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrDoorNo VARCHAR(10),
    IN pcrStreet VARCHAR(100),
    IN pcrPlace VARCHAR(100),
    IN pcrCity VARCHAR(50),
    IN pcrState VARCHAR(50),
    IN pcrCountry VARCHAR(30),
    IN pitPincode INT,
    IN pcrUserCode VARCHAR(30),
    IN pcrNamespaceCode VARCHAR(30),
    IN pitActiveFlag TINYINT,
    IN pitUpdatedBy INT,
    IN pitDebugFlag TINYINT,
    OUT pitRowCount INT
)
BEGIN

/*
*----------------------------------------------------------------------------------------------------
* Variable Declare
*----------------------------------------------------------------------------------------------------
*/
    DECLARE litUserId INT DEFAULT 0;
    DECLARE litNamespaceId INT DEFAULT 0;

/*
*----------------------------------------------------------------------------------------------------
* Variable Initialized
*----------------------------------------------------------------------------------------------------
*/
    SET pitRowCount = 0;

/*
*----------------------------------------------------------------------------------------------------
* Resolve Foreign Key References
*----------------------------------------------------------------------------------------------------
*/

    IF (EZEE_FN_ISNOTNULL(pcrNamespaceCode)) THEN    
    
    SELECT `id`
    INTO litNamespaceId 
    FROM namespace 
    WHERE `code` =pcrNamespaceCode;
   
    END IF;

    IF (EZEE_FN_ISNOTNULL(pcrUserCode)) THEN

        SELECT `id`
          INTO litUserId
          FROM `user`
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrUserCode;

    END IF;
    
    
    

/*
*----------------------------------------------------------------------------------------------------
* Insert / Update / Delete Logic
*----------------------------------------------------------------------------------------------------
*/
    IF (pitActiveFlag = 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        /* UPDATE */

        UPDATE `address`
           SET `door_no` = pcrDoorNo,
               `street` = pcrStreet,
               `place` = pcrPlace,
               `city` = pcrCity,
               `state` = pcrState,
               `country` = pcrCountry,
               `pincode` = pitPincode,
               `user_id` = litUserId,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        /* INSERT */

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('ADDR');

        INSERT INTO `address`
        (
            `code`,
            `door_no`,
            `street`,
            `place`,
            `city`,
            `state`,
            `country`,
            `pincode`,
            `user_id`,
            `namespace_id`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            pcrDoorNo,
            pcrStreet,
            pcrPlace,
            pcrCity,
            pcrState,
            pcrCountry,
            pitPincode,
            litUserId,
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

        UPDATE `address`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;