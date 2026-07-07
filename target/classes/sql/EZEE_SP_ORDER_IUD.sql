/* Procedure structure for procedure `EZEE_SP_ORDER_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_ORDER_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_ORDER_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrUserCode VARCHAR(30),
    IN pitOrderStatus TINYINT,
    IN pdcTotalAmount DECIMAL(12,2),
    IN pdtOrderDate DATETIME,
    IN pcrNamespaceCode VARCHAR(30),
    IN pitActiveFlag TINYINT,
    IN pitUpdatedBy INT,
    IN pitDebugFlag TINYINT,
    OUT pitRowCount INT
)
BEGIN

/*
*-----------------------------------------------------------------------------------------------------------------------
* Variable Declaration
*-----------------------------------------------------------------------------------------------------------------------
*/
    DECLARE litNamespaceId INT DEFAULT 0;
    DECLARE litUserId INT DEFAULT 0;

/*
*-----------------------------------------------------------------------------------------------------------------------
* Getting IDs from CODE
*-----------------------------------------------------------------------------------------------------------------------
*/
    IF EZEE_FN_ISNOTNULL(pcrNamespaceCode) THEN

        SELECT id
        INTO litNamespaceId
        FROM namespace
        WHERE `code` = pcrNamespaceCode;

    END IF;

    IF EZEE_FN_ISNOTNULL(pcrUserCode) THEN

        SELECT id
        INTO litUserId
        FROM `user`
        WHERE `code` = pcrUserCode;

    END IF;

/*
*-----------------------------------------------------------------------------------------------------------------------
* Update
*-----------------------------------------------------------------------------------------------------------------------
*/
    IF (pitActiveFlag = 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        UPDATE `orders`
           SET `user_id` = litUserId,
               `order_status` = pitOrderStatus,
               `total_amount` = pdcTotalAmount,
               `order_date` = pdtOrderDate,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

/*
*-----------------------------------------------------------------------------------------------------------------------
* Insert
*-----------------------------------------------------------------------------------------------------------------------
*/
    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('ORDR');

        INSERT INTO `orders`
        (
            `code`,
            `user_id`,
            `order_status`,
            `total_amount`,
            `order_date`,
            `namespace_id`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            litUserId,
            pitOrderStatus,
            pdcTotalAmount,
            pdtOrderDate,
            litNamespaceId,
            1,
            pitUpdatedBy,
            NOW()
        );

        SELECT ROW_COUNT() INTO pitRowCount;

/*
*-----------------------------------------------------------------------------------------------------------------------
* Soft Delete / Reactivate
*-----------------------------------------------------------------------------------------------------------------------
*/
    ELSEIF (pitActiveFlag != 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        IF pitActiveFlag = 9 THEN
            SET pitActiveFlag = 1;
        END IF;

        UPDATE `orders`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;
