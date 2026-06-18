
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

/* Procedure structure for procedure `EZEE_SP_BRAND_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_BRAND_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_BRAND_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrName VARCHAR(50),
    IN pitNamespaceId INT,
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

        UPDATE `brands`
           SET `name` = pcrName,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = pitNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag = 1 AND EZEE_FN_ISNULL(pcrCode)) THEN

        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('BRAND');

        INSERT INTO `brands`
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
            pitNamespaceId,
            1,
            pitUpdatedBy,
            NOW()
        );

        SELECT ROW_COUNT() INTO pitRowCount;

    ELSEIF (pitActiveFlag != 1 AND EZEE_FN_ISNOTNULL(pcrCode)) THEN

        IF (pitActiveFlag = 9) THEN
            SET pitActiveFlag = 1;
        END IF;

        UPDATE `brands`
           SET `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = pitNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;

/* Procedure structure for procedure `EZEE_SP_CATEGORY_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_CATEGORY_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_CATEGORY_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrName VARCHAR(30),
    IN pitNamespaceId INT,
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

        UPDATE `categories`
           SET `name` = pcrName,
               `active_flag` = pitActiveFlag,
               `updated_by` = pitUpdatedBy,
               `updated_at` = NOW()
         WHERE `namespace_id` = pitNamespaceId
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
            pitNamespaceId,
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
         WHERE `namespace_id` = pitNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;

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

/* Procedure structure for procedure `EZEE_SP_PRODUCT_INVENTORY_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_PRODUCT_INVENTORY_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_PRODUCT_INVENTORY_IUD`(
    INOUT pcrCode VARCHAR(30),
    IN pcrProductCode VARCHAR(30),
    IN pitAvailableQuantity INT,
    IN pitNamespaceId INT,
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

/*
*----------------------------------------------------------------------------------------------------
* Variable Initialized
*----------------------------------------------------------------------------------------------------
*/
    SET pitRowCount = 0;

/*
*----------------------------------------------------------------------------------------------------
* Getting Product ID from CODE
*----------------------------------------------------------------------------------------------------
*/
    IF (EZEE_FN_ISNOTNULL(pcrProductCode)) THEN

        SELECT `id`
          INTO litProductId
          FROM `products`
         WHERE `namespace_id` = pitNamespaceId
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
         WHERE `namespace_id` = pitNamespaceId
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
            pitNamespaceId,
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
         WHERE `namespace_id` = pitNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;

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
    IN pitNamespaceId INT,
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
* Getting Brand ID and Category ID from their CODES
*----------------------------------------------------------------------------------------------------
*/
    IF (EZEE_FN_ISNOTNULL(pcrBrandCode)) THEN

        SELECT `id`
          INTO litBrandId
          FROM `brands`
         WHERE `namespace_id` = pitNamespaceId
           AND `code` = pcrBrandCode;

    END IF;

    IF (EZEE_FN_ISNOTNULL(pcrCategoryCode)) THEN

        SELECT `id`
          INTO litCategoryId
          FROM `categories`
         WHERE `namespace_id` = pitNamespaceId
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
         WHERE `namespace_id` = pitNamespaceId
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
            pitNamespaceId,
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
         WHERE `namespace_id` = pitNamespaceId
           AND `code` = pcrCode;

        SELECT ROW_COUNT() INTO pitRowCount;

    END IF;

END */$$
DELIMITER ;

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
               `password` = pcrPassword,
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
            `password`,
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

/* Procedure structure for procedure `sp_get_top_selling_products` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_get_top_selling_products` */;

DELIMITER $$
