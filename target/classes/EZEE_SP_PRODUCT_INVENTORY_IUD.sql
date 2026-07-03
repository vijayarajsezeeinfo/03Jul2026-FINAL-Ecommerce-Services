/* Procedure structure for procedure `EZEE_SP_PRODUCT_INVENTORY_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_PRODUCT_INVENTORY_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_PRODUCT_INVENTORY_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrProductCode VARCHAR(30),
    IN pitAvailableQuantity INT,
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
    DECLARE litProductId INT DEFAULT 0;
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
* Getting Product ID from CODE
*----------------------------------------------------------------------------------------------------
*/
    IF (EZEE_FN_ISNOTNULL(pcrProductCode)) THEN

        SELECT `id`
          INTO litProductId
          FROM `products`
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrProductCode;

    END IF;

/*
*----------------------------------------------------------------------------------------------------
* Insert / Update / Delete Logic
*----------------------------------------------------------------------------------------------------
*/
    IF (pitActiveFlag = 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        UPDATE `product_inventory`
           SET `product_id` = litProductId,
               `available_quantity` = pitAvailableQuantity,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('PINV');

        INSERT INTO `product_inventory`
        (
            `code`,
            `product_id`,
            `available_quantity`,
            `namespace_id`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            litProductId,
            pitAvailableQuantity,
            litNamespaceId,
            1,
            pitUpdatedBy,
            NOW()
        );

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag != 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        IF (pitActiveFlag = 9) THEN
            SET pitActiveFlag = 1;
        END IF;

        UPDATE `product_inventory`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;