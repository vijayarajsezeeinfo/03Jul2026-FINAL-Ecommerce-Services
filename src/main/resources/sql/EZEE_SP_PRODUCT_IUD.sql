/* Procedure structure for procedure `EZEE_SP_PRODUCT_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_PRODUCT_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_PRODUCT_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrName VARCHAR(50),
    IN pcrDescription VARCHAR(300),
    IN pdcPrice DECIMAL(12,2),
    IN pcrBrandCode VARCHAR(30),
    IN pcrCategoryCode VARCHAR(30),
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
* Variable Declare
*----------------------------------------------------------------------------------------------------
*/
    DECLARE litBrandId INT DEFAULT 0;
    DECLARE litCategoryId INT DEFAULT 0;

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
* Getting Brand ID and Category ID from their CODES
*----------------------------------------------------------------------------------------------------
*/
    IF (EZEE_FN_ISNOTNULL(pcrBrandCode)) THEN

        SELECT `id`
          INTO litBrandId
          FROM `brands`
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrBrandCode;

    END IF;

    IF (EZEE_FN_ISNOTNULL(pcrCategoryCode)) THEN

        SELECT `id`
          INTO litCategoryId
          FROM `categories`
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCategoryCode;

    END IF;

/*
*----------------------------------------------------------------------------------------------------
* Insert / Update / Delete Logic
*----------------------------------------------------------------------------------------------------
*/
    IF (pitActiveFlag = 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN   /* UPDATE */

        UPDATE `products`
           SET `name` = pcrName,
               `description` = pcrDescription,
               `price` = pdcPrice,
               `brand_id` = litBrandId,
               `category_id` = litCategoryId,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN   /* INSERT */

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('PRODUCT');

        INSERT INTO `products`
        (
            `code`,
            `name`,
            `description`,
            `price`,
            `brand_id`,
            `category_id`,
            `namespace_id`,
            `active_flag`,
            `updated_by`,
            `updated_at`
        )
        VALUES
        (
            pcrCode,
            pcrName,
            pcrDescription,
            pdcPrice,
            litBrandId,
            litCategoryId,
            litNamespaceId,
            1,
            pitUpdatedBy,
            NOW()
        );

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag != 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN  /* Re-Activation & Delete */

        IF (pitActiveFlag = 9) THEN
            SET pitActiveFlag = 1;
        END IF;

        UPDATE `products`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = litNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;
