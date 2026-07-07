/* Procedure structure for procedure `EZEE_SP_ORDER_ITEMS_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_ORDER_ITEMS_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_ORDER_ITEMS_IUD`( 
  INOUT pcrCode VARCHAR(30),
  IN pitOrderId INT,
  IN pitProductId INT,
  IN pitQuantity INT,
  IN pdcPrice DECIMAL(12,2),
  IN pitNamespaceId INT,
  IN pitActiveFlag INT,
  IN pitUpdatedBy INT,
  OUT pitRowCount INT
)
BEGIN 
*----------------------------------------------------------------------------------------------------
* Insert 
*----------------------------------------------------------------------------------------------------
*/
    IF EZEE_FN_ISNULL(pcrCode) AND pitActiveFlag = 1 THEN
        SET pcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('OITEM');
       
        INSERT INTO `order_items`
        (
        `code`, 
        `order_id`, 
        `product_id`, 
        `quantity`, 
        `price`, 
         `namespace_id`,
         `active_flag`, 
         `updated_by`, 
         `updated_at`
         )
        VALUES
        (
        pcrCode, 
        pitOrderId, 
        pitProductId, 
        pitQuantity, 
        pdcPrice,
        pitNamespaceId,
        pitActiveFlag, 
        pitUpdatedBy, 
        NOW()
        );
    
    
    END IF;
    
    SELECT ROW_COUNT() INTO pitRowCount;
    
END */$$
DELIMITER ;
