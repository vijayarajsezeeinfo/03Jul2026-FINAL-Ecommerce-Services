/* Procedure structure for procedure `EZEE_SP_USER_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_USER_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_USER_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrUserName VARCHAR(100),
    IN pcrPassword VARCHAR(255),
    IN pcrEmail VARCHAR(150),
    IN pcrMobile VARCHAR(20),
    IN pitRole TINYINT,
    IN pitNamespaceId INT,
    IN pitActiveFlag TINYINT,
    IN pitUpdatedBy INT,
    IN pitDebugFlag TINYINT,
    OUT pitRowCount INT
)
BEGIN


   DECLARE lcrCartCode VARCHAR(30);
   DECLARE litUserId  INT;
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

        UPDATE `user`
           SET `username` = pcrUserName,
               `token` = pcrPassword,
               `email` = pcrEmail,
               `mobile` = pcrMobile,
               `role` = pitRole,
               `namespace_id` = pitNamespaceId,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;
        
        
        /* When user is updated his active_flag or namespace_id , his cart also should be updated */
        UPDATE `cart`
           SET `namespace_id` = pitNamespaceId,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `user_id`= (SELECT id FROM `user` WHERE `code` = pcrCode);
         
        /* When user is updated his active_flag or namespace_id , his address also should be updated */
        UPDATE `address`
           SET `namespace_id` = pitNamespaceId,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `user_id` =(SELECT id FROM `user` WHERE `code`=pcrCode);
         

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        /* INSERT */

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('USR');

        INSERT INTO `user`
        (
            `code`,
            `username`,
            `token`,
            `email`,
            `mobile`,
            `role`,
            `namespace_id`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            pcrUserName,
            pcrPassword,
            pcrEmail,
            pcrMobile,
            pitRole,
            pitNamespaceId,
            1,
            pitUpdatedBy,
            NOW()
        );

        SELECT ROW_COUNT() INTO pitRowCount;
        
        SET litUserId =LAST_INSERT_ID();
        
        SET lcrCartCode=EZEE_FN_RANDOM_CODE_GENERATOR('CRT');
        
        INSERT INTO `cart`
        (
           `code`,
           `user_id`,
           `namespace_id`,
           `active_flag`,
           `updated_by`,
           `updated_at`
         )
         VALUES
         (
           lcrCartCode,
           litUserId ,
           pitNamespaceId,
           1,
           pitUpdatedBy,
           NOW()
         );

    ELSEIF (pitActiveFlag != 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        /* SOFT DELETE / RE-ACTIVATE */

        IF (pitActiveFlag = 9) THEN
            SET pitActiveFlag = 1;
        END IF;

        UPDATE `user`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;
        
        /* When user is soft deleted or re-activated his active_flag or namespace_id , his cart also should be updated */
        UPDATE `cart`
           SET `active_flag`= pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE user_id = (SELECT id FROM `user` WHERE `code`=pcrCode);
         
        /* When user is soft deleted or re-activated his active_flag or namespace_id , his cart also should be updated */
         UPDATE `address`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `user_id` =(SELECT id FROM `user` WHERE `code`=pcrCode);
        


    END IF;

END */$$
DELIMITER ;