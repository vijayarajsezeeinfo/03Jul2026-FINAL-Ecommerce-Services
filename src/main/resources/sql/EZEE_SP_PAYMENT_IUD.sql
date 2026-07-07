/* Procedure structure for procedure `EZEE_SP_PAYMENT_IUD` */

/*!50003 DROP PROCEDURE IF EXISTS  `EZEE_SP_PAYMENT_IUD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `EZEE_SP_PAYMENT_IUD`(
 INOUT pcrCode VARCHAR(30),
 IN pitOrderId INT,
 IN pitPaymentMode TINYINT,
 IN pdcTotalAmountToPay DECIMAL(12,2),
 IN pdcPaidAmount DECIMAL(12,2),
 IN pdcBalanceAmount DECIMAL(12,2),
 IN pitBillingStatus TINYINT,
 IN pcrTransactionId VARCHAR(50),
 IN pcrRemarks VARCHAR(150),
 IN pitNamespaceId INT,
 IN pitActiveFlag INT,
 IN pitUpdatedBy INT,
 OUT pitRowCount INT
 )
BEGIN
 
 DECLARE lcrCode VARCHAR(30) DEFAULT NULL;
 
    
    IF EZEE_FN_ISNULL(pcrCode) AND pitActiveFlag = 1 THEN
    
    SET lcrCode = EZEE_FN_RANDOM_CODE_GENERATOR('PYMT');
       
       INSERT INTO `payments`
       ( 
         `code`,
         `order_id`,
         `payment_mode`,
         `total_amount_to_pay`,
         `paid_amount`,
         `balance_amount`,
         `billing_status`,
         `transaction_id`,
         `remarks`,
         `namespace_id`,
         `active_flag`,
         `updated_by`,
         `updated_at`
        )
        VALUES
        (
          lcrCode,
          pitOrderId,
          pitPaymentMode,
          pdcTotalAmountToPay,
          pdcPaidAmount,
          pdcBalanceAmount,
          pitBillingStatus,
          pcrTransactionId,
          pcrRemarks,
          pitNamespaceId,
          1,
          pitUpdatedBy,
          NOW()
         );
         
         SET pcrCode = lcrCode; 
        
      END IF;  
          SELECT ROW_COUNT() INTO pitRowCount;
    
 END */$$
DELIMITER ;
