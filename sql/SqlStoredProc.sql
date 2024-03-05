DELIMITER $$

DROP PROCEDURE IF EXISTS update_Amount_On_Transaction$$
CREATE PROCEDURE update_Amount_On_Transaction(IN accountNumber DOUBLE,IN beneficieryAccountNumber DOUBLE,IN transactionMode VARCHAR(20),IN transactionAmount Double,OUT isTransactionCompleted BOOLEAN)
BEGIN
	DECLARE balance DOUBLE(12,4);
    
    SELECT available_Balance INTO @balance FROM Account WHERE account_Number=accountNumber;
    If(transactionMode='WITHDRAW') THEN
		UPDATE Account SET available_Balance=@balance-transactionAmount,current_Balance=@balance-transactionAmount WHERE account_Number=accountNumber;
        SET isTransactionCompleted=TRUE;
	ELSEIF(transactionMode='DEPOSIT') THEN
		UPDATE Account SET available_Balance=@balance+transactionAmount,current_Balance=@balance+transactionAmount WHERE account_Number=accountNumber;
        SET isTransactionCompleted=TRUE;
	ELSE
		UPDATE Account SET available_Balance=@balance-transactionAmount,current_Balance=@balance-transactionAmount WHERE account_Number=accountNumber;
        UPDATE Account SET available_Balance=@balance+transactionAmount,current_Balance=@balance+transactionAmount WHERE account_Number=beneficieryAccountNumber;
        SET isTransactionCompleted=TRUE;
    END IF;
    
END $$

DELIMITER ;




DELIMITER $$

DROP PROCEDURE IF EXISTS Check_Valid_Card_Transaction$$
CREATE PROCEDURE Check_Valid_Card_Transaction(IN accountNumber DOUBLE,IN cardNumber DOUBLE,IN transactionMode VARCHAR(20),IN transactionAmount Double,OUT validTransaction BOOLEAN)
BEGIN
	DECLARE count INT;
    DECLARE valid_Account BOOLEAN;
    
	CALL is_Valid_Account(accountNumber,transactionMode,transactionAmount,@valid_Account);
    SELECT @valid_Account into valid_Account;
    
    IF (valid_Account=TRUE) THEN
		SELECT COUNT(*) INTO count FROM CARD WHERE account_Number=accountNumber AND card_Number=cardNumber;
        If(count>0) THEN
			SET validTransaction=TRUE;
		ELSE
			SET validTransaction=FALSe;
		END IF;
	ELSE
			SET validTransaction=FALSe;
    END IF;
END $$

DELIMITER ;


DELIMITER $$
	
    DROP PROCEDURE IF EXISTS Get_Account_Type$$
    CREATE PROCEDURE Get_Account_Type(IN accountNumber DOUBLE,OUT accountType VARCHAR(20))
    BEGIN
	DECLARE count INT;
	SET count=0;
    SET accountType='NONE';
	
    SELECT COUNT(*) INTO count FROM Account JOIN Current_Account ON Account.account_Number=Current_Account.account_Number WHERE Account.account_Number=accountNumber;
	IF(count>0) THEN
		SET @accountType='CURRENT';
    ELSE
		SELECT COUNT(*) INTO count FROM Account JOIN Saving_Account ON Account.account_Number=Saving_Account.account_Number WHERE Account.account_Number=accountNumber;
		If(count>0) THEN
			SET accountType='SAVING';
        ELSE
			SELECT COUNT(*) INTO count FROM Account JOIN Fixed_Deposit_Account ON Account.account_Number=Fixed_Deposit_Account.account_Number WHERE Account.account_Number=accountNumber;
			IF(count>0)THEN
				SET accountType='FD';
			END IF;
        END If;
    END IF;
    END $$
    
DELIMITER ;



DELIMITER $$

DROP PROCEDURE IF EXISTS is_Valid_Account$$
CREATE PROCEDURE is_Valid_Account(IN accountNumber DOUBLE,IN transactionMode VARCHAR(20),IN transactionAmount Double,OUT valid_Account Boolean)
BEGIN
    DECLARE accountType VARCHAR(20);
    DECLARE currentBalance DOUBLE(12,4);
	DECLARE availableBalance DOUBLE(12,4);
    DECLARE overDraftLimit DOUBLE(12,4);
    DECLARE minimumBalance DOUBLE(12,4);
    DECLARE withdrawalLimit DOUBLE(12,4);
	DECLARE overAmount DOUBLE(12,4);
    DECLARE matureDateTime DateTime;
	
    CALL Get_Account_Type(accountNumber,@accountType);
    
    IF(accountType='NONE')THEN
		SET valid_Account=FALSE;
	Else
		IF(transactionMode = 'WITHDRAW') THEN
			IF(accountType = 'CURRENT') THEN
				SELECT current_Balance,available_Balance,over_Draft_Limit INTO @currentBalance,@availableBalance,@overDraftLimit FROM Account JOIN Current_Account ON Account.account_Number=Current_Account.account_Number WHERE Account.account_Number=accountNumber;
				IF(availableBalance<transactionAmount) THEN
					SET @overAmount=transactionAmount-availableBalance;
                    IF overAmount<overDraftLimit THEN
						SET valid_Account=True;
					ELSE
						SET valid_Account=FALSE;
                    END IF;
				ELSE
					SET valid_Account=False;
                END IF;
			ELSEIF (accountType='FD') THEN
				SELECT available_Balance,mature_DateTime INTO @availableBalance,@matureDateTime FROM Account JOIN Fixed_Deposit_Account ON Account.account_Number=Fixed_Deposit_Account.account_Number WHERE Account.account_Number=accountNumber;
                IF NOT (availableBalance>transactionAmount AND matureDateTime<CURRENT_TIMESTAMP) THEN
					SET valid_Account=FALSE;
				ELSE
					SET valid_Account=TRUE;
                END IF;
			ELSE
				SELECT available_Balance,minimum_Account_Balance,withdrawal_Limit INTO @availableBalance,@minimumBalance,@withdrawalLimit FROM Account JOIN Saving_Account ON Account.account_Number=Saving_Account.account_Number WHERE Account.account_Number=accountNumber;
                IF NOT (availableBalance>transactionAmount AND (availableBalance-transactionAmount)<=minimumBalance AND transactionAmount<=withdrawalLimit) THEN
					SET valid_Account=FALSE;
				ELSE
					SET valid_Account=TRUE;
				END IF;
            END IF;
		ELSEIF(transactionMode = 'DEPOSIT' OR transactionMode='TRANSFER') THEN
			IF(accountType = 'CURRENT') THEN
				SET valid_Account=TRUE;
			ELSEIF (accountType='FD') THEN
				SELECT available_Balance,mature_DateTime INTO @availableBalance,@matureDateTime FROM Account JOIN Fixed_Deposit_Account ON Account.account_Number=Fixed_Deposit_Account.account_Number WHERE Account.account_Number=accountNumber;
                IF NOT (matureDateTime<CURRENT_TIMESTAMP) THEN
					SET valid_Account=TRUE;
                END IF;
			ELSE
				SET valid_Account=TRUE;
            END IF;
		END IF;			
    END IF;
END $$

DELIMITER ;





















CALL update_Amount_On_Transaction(2450000000000001,0,'WITHDRAW',1000,@isTransactionCompleted);
SELECT @isTransactionCompleted;
CALL Check_Valid_Card_Transaction(2450000000000001,1000000000000000,'DEPOSIT',1000,@validTransaction);
select @validTransaction;
CALL is_Valid_Account(2450000000000001,'WITHDRAW',1000,@valid_Account);
select @valid_Account;