/* Procedure structure for procedure `EZEE_SP_CART_ITEMS_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_CART_ITEMS_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_CART_ITEMS_IUD`(
INOUT pcrCode VARCHAR(30),
IN pcrCartCode VARCHAR(30),
IN pcrProductCode VARCHAR(30),
IN pitQuantity INT,
IN pcrNamespaceCode VARCHAR(30),
IN pitActiveFlag INT,
IN pitUpdatedBy INT,
IN pitDebugFlag TINYINT,
OUT pitRowCount INT
)
BEGIN

   DECLARE litNamespaceId INT DEFAULT 0;
   DECLARE litCartId INT DEFAULT 0;
   DECLARE litProductId INT DEFAULT 0;   
   
/*
*----------------------------------------------------------------------------------------------------
* Getting ID from their CODES
*----------------------------------------------------------------------------------------------------
*/

   SELECT id INTO litNamespaceId FROM namespace WHERE `code`=pcrNamespaceCode;
   
   SELECT id INTO litCartId FROM cart WHERE `code` = pcrCartCode;
   
   SELECT id INTO litProductId FROM `products` WHERE `code` = pcrProductCode;
     
   
   
/*
*----------------------------------------------------------------------------------------------------
* Insert / Update / Delete Logic
*----------------------------------------------------------------------------------------------------
*/

   IF `EZEE_FN_ISNOTNULL`(pcrCode) AND pitActiveFlag = 1 THEN
       
       UPDATE `cart_items`
          SET `cart_id` = litCartId,
              `product_id` = litProductId,
              `quantity` = pitQuantity,
              `active_flag` = pitActiveFlag,
              `updated_by` = pitUpdatedBy,
              `updated_at` = NOW()
        WHERE `namespace_id` = litNamespaceId
          AND `code` = pcrCode;
          
          
        SELECT ROW_COUNT() INTO pitRowCount;
        
        
    ELSEIF pitActiveFlag = 1 AND `EZEE_FN_ISNULL`(pcrCode) THEN
    
       SET pcrCode =`EZEE_FN_RANDOM_CODE_GENERATOR`('CITEM');
    
       INSERT INTO `cart_items`
       (
       `code`,
       `cart_id`,
       `product_id`,
       `quantity`,
       `namespace_id`,
       `active_flag`,
       `updated_by`,
       `updated_at`
       )
       VALUES
       (
        pcrCode,
        litCartId,
        litProductId,
        pitQuantity,
        litNamespaceId,
        pitActiveFlag,
        pitUpdatedBy,
        NOW()
       );
       
       SELECT ROW_COUNT() INTO pitRowCount;

       
      ELSEIF pitActiveFlag != 1 AND `EZEE_FN_ISNOTNULL`(pcrCode) THEN
             
             IF pitActiveFlag = 9 THEN
                SET pitActiveFlag = 1;
             END IF;

             UPDATE `cart_items`
                SET `active_flag` = pitActiveFlag,
                    `updated_by` = pitUpdatedBy,
                    `updated_at` = NOW()
              WHERE `namespace_id` = litNamespaceId
                AND `code` = pcrCode;
                
                
         SELECT ROW_COUNT() INTO pitRowCount;
         
      END IF;
      
END */$$
DELIMITER ;