# Drop Table Bank
# Drop Table Branch
# Drop Table Address
# Drop Table Geo_Location
# Drop Table User
# Drop Table Customer
# Drop Table Employee
# Drop Table Admin
# Drop Table Account
# Drop Table Current_Account
# Drop Table Fixed_Deposit_Account
# Drop Table Saving_Account
# Drop Table Customer_Account_Map
# Drop Table Nominee
# Drop Table Employee_Branch_Map
# Drop Table Loan
# Drop Table Gold_Loan
# Drop Table Home_Loan
# Drop Table Card_MASTER
# Drop Table CO_BRANDED_CREDIT_CARD_MASTER
# Drop Table CREDIT_CARD_MASTER
# Drop Table DEBIT_CARD_MASTER
# Drop Table CARD
# Drop Table CREDIT_CARD
# Drop Table DEBIT_CARD
# Drop Table CO_BRANDED_CREDIT_CARD
# Drop Table Transaction
# Drop Table Deposit_Transaction
# Drop Table Withdraw_Transaction
# Drop Table Transfer_Transaction
# Drop Table Card_Transaction_Map
# Drop Table Cheque
# Drop Table Cash




#Address
Create Table Address(
Address_Id INTEGER AUTO_INCREMENT PRIMARY KEY,
address_Line_One VARCHAR(200),
address_Line_Two VARCHAR(200),
address_Line_Three VARCHAR(200),
landmark VARCHAR(100),
city VARCHAR(100),
state VARCHAR(100),
country VARCHAR(100),
pinCode VARCHAR(100));

#Geo Location
Create Table Geo_Location(
Geo_Location_Id Integer AUTO_INCREMENT PRIMARY KEY,
latitude DOUBLE,
longitude DOUBLE
);

# Bank
Create TABLE Bank(
bank_Identification_Number Integer PRIMARY KEY,
bank_Name Varchar(100),
bank_Code VARCHAR(5) UNIQUE,
bank_Type VARCHAR(20)
);

#Branch
Create TABLE Branch(
branch_Id Integer AUTO_INCREMENT PRIMARY KEY,
geoLocation_Id Integer,
bank_Id Integer,
address_Id Integer,
IFSC_Code VARCHAR(10),
branch_Name VARCHAR(100),
mobile_Number VARCHAR(20),
FOREIGN KEY (address_Id) REFERENCES Address(Address_Id),
FOREIGN KEY (bank_Id) REFERENCES Bank(bank_Identification_Number) ON DELETE CASCADE,
FOREIGN KEY (geoLocation_Id) REFERENCES Geo_Location(Geo_Location_Id)
);

#User
Create Table User(
Id Integer AUTO_INCREMENT PRIMARY KEY,
address_Id Integer,
first_Name VARCHAR(100),
middle_Name VARCHAR(100),
last_Name VARCHAR(100),
email_Id VARCHAR(100) UNIQUE,
gender VARCHAR(20),
password VARCHAR(100),
age INTEGER(3),
mobile_Number VARCHAR(20),
FOREIGN KEY (address_Id) REFERENCES Address(Address_Id)
);

#Admin
Create Table Admin(
Id Integer AUTO_INCREMENT PRIMARY KEY,
user_Id Integer,
is_Active BOOL,
UNIQUE KEY (user_Id),
FOREIGN KEY (user_Id) REFERENCES User(id) ON DELETE CASCADE
);

#Customer
Create Table Customer(
CIF_Number Double AUTO_INCREMENT PRIMARY KEY,
user_Id Integer,
CKYC_Verification_Document VARCHAR(150),
CKYC_Verification_Id VARCHAR(150),
PAN_Number VARCHAR(10),
UNIQUE KEY (user_Id),
FOREIGN KEY (user_Id) REFERENCES User(id) ON DELETE CASCADE
)Auto_Increment=20000000000;

#Employee
Create Table Employee(
Employee_Id Double AUTO_INCREMENT PRIMARY KEY,
user_Id Integer,
employee_Designation VARCHAR(150),
employee_CTC DOUBLE(14,4),
year_Of_Experience INTEGER,
is_Active BOOL,
UNIQUE Key (user_Id),
FOREIGN KEY (user_Id) REFERENCES User(id) ON DELETE CASCADE
)Auto_Increment=10000000000;

#Employee Branch Map
Create TABLE Employee_Branch_Map(
employee_Id Double,
branch_Id Integer,
FOREIGN KEY (employee_Id) REFERENCES Employee(Employee_Id) ON DELETE CASCADE,
FOREIGN KEY (branch_Id) REFERENCES Branch(branch_Id) ON DELETE CASCADE
);

#Account
Create TABLE Account(
account_Number Double(18,0) AUTO_INCREMENT PRIMARY KEY,
current_Balance DOUBLE(12,4),
available_Balance DOUBLE(12,4),
credit_Score Double(5,2),
account_Inception_DateTime DateTime,
branch_Id Integer,
FOREIGN KEY (branch_Id) REFERENCES Branch(branch_Id) ON Delete Cascade
)Auto_Increment=2450000000000000;

#Nominee
Create Table Nominee(
Id Integer AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
first_Name VARCHAR(100),
middle_Name VARCHAR(100),
last_Name VARCHAR(100),
email_Id VARCHAR(100),
gender VARCHAR(20),
age INTEGER(3),
mobile_Number VARCHAR(20),
CKYC_Verification_Document VARCHAR(150),
CKYC_Verification_Id VARCHAR(150),
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
);

#Current Account
Create TABLE Current_Account(
Id Integer AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
over_Draft_Limit DOUBLE(12,4),
UNIQUE KEY (account_Number),
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
); 

#FD Account
Create TABLE Fixed_Deposit_Account(
Id Integer AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
tenure Integer,
rate_Of_Interest DOUBLE(5,2),
mature_DateTime DateTime,
UNIQUE KEY (account_Number),
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
);

#Saving Account
Create TABLE Saving_Account(
Id Integer AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
minimum_Account_Balance DOUBLE(12,4),
withdrawal_Limit DOUBLE(12,4),
rate_Of_Interest DOUBLE(5,2),
UNIQUE KEY (account_Number),
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
);

#Loan
Create TABLE Loan(
Id Integer AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
loan_Amount DOUBLE(12,4),
rate_Of_Interest DOUBLE(5,2),
loan_Type VARCHAR(10),
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
);

#Gold Loan
Create TABLE Gold_Loan(
Id Integer AUTO_INCREMENT PRIMARY KEY,
loan_Id Integer,
gold_Purity VARCHAR(100),
gold_Value_Per_Gram DOUBLE(12,4),
gold_Weight_In_Gram DOUBLE(12,4),
UNIQUE KEY (loan_Id),
FOREIGN KEY (loan_Id) REFERENCES Loan(id) ON DELETE CASCADE);

#Home Loan
Create TABLE Home_Loan(
Id Integer AUTO_INCREMENT PRIMARY KEY,
loan_Id Integer,
build_Up_Area DOUBLE(12,4),
total_Area DOUBLE(12,4),
total_Value DOUBLE(12,4),
total_No_Of_Floors Integer,
UNIQUE KEY (loan_Id),
FOREIGN KEY (loan_Id) REFERENCES Loan(id) ON DELETE CASCADE);

#Cus tomer Account MAp
Create TABLE Customer_Account_Map(
CIFNumber Double,
account_Number Double,
FOREIGN KEY (CIFNumber) REFERENCES Customer(CIF_Number) ON DELETE CASCADE,
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
);

#CARD MASTER
CREATE TABLE Card_MASTER(
Id Integer AUTO_INCREMENT PRIMARY KEY,
card_Name VARCHAR(100) UNIQUE,
inception_Date Date,
payment_Gateway VARCHAR(50),
is_Active Bool
);

#CO Branded CREDIT CARD MASTER
CREATE TABLE CO_BRANDED_CREDIT_CARD_MASTER(
card_Id Integer,
interest_Free_Credit_Days INTEGER(100),
rate_Of_Interest DOUBLE(5,2),
merchant_Name VARCHAR(100),
merchant_Offer_Percentage DOUBLE(5,2),
UNIQUE KEY (card_Id),
FOREIGN KEY (card_Id) REFERENCES Card_MASTER(id) ON DELETE CASCADE
);

#CREDIT CARD MASTER
CREATE TABLE CREDIT_CARD_MASTER(
card_Id Integer,
interest_Free_Credit_Days INTEGER(100),
rate_Of_Interest DOUBLE(5,2),
UNIQUE KEY (card_Id),
FOREIGN KEY (card_Id) REFERENCES Card_MASTER(id) ON DELETE CASCADE
);

#DEBIT CARD MASTER
CREATE TABLE DEBIT_CARD_MASTER(
card_Id Integer,
withdrawal_Limit DOUBLE(12,8),
UNIQUE KEY (card_Id),
FOREIGN KEY (card_Id) REFERENCES Card_MASTER(id) ON DELETE CASCADE
);

#CARD
CREATE TABLE Card(
card_Number Double AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
card_Holder_Name VARCHAR(100),
inception_Date Date,
valid_from Date,
expiry_Date Date,
payment_Gateway VARCHAR(50),
CVV INTEGER,
pin_Number INTEGER,
FOREIGN KEY (account_Number) REFERENCES Account(account_Number) ON DELETE CASCADE
)Auto_Increment=1000000000000000;

#CREDIT CARD
CREATE TABLE CREDIT_CARD(
Id Integer AUTO_INCREMENT PRIMARY KEY,
card_Id Double,
interest_Free_Credit_Days INTEGER(100),
rate_Of_Interest DOUBLE(5,2),
UNIQUE KEY (card_Id),
FOREIGN KEY (card_Id) REFERENCES Card(card_Number) ON DELETE CASCADE
);

#Debit card
CREATE TABLE DEBIT_CARD(
Id Integer AUTO_INCREMENT PRIMARY KEY,
card_Id Double,
withdrawal_Limit DOUBLE(12,8),
UNIQUE KEY (card_Id),
FOREIGN KEY (card_Id) REFERENCES Card(card_Number) ON DELETE CASCADE
);

#CO Branded CREDIT CARD
CREATE TABLE CO_BRANDED_CREDIT_CARD(
Id Integer AUTO_INCREMENT PRIMARY KEY,
card_Id Double,
merchant_Name VARCHAR(100),
merchant_Offer_Percentage DOUBLE(5,2),
UNIQUE KEY (card_Id),
FOREIGN KEY (card_Id) REFERENCES Card(card_Number) ON DELETE CASCADE
);

#TRANSACTION
CREATE TABLE Transaction(
Id Integer AUTO_INCREMENT PRIMARY KEY,
account_Number Double,
transaction_DateTime DateTime,
transaction_Amount DOUBLE(12,4),
FOREIGN KEY (account_Number) REFERENCES Account(account_Number)
);

#DEPOSIT TRANSACTION
CREATE TABLE Deposit_Transaction(
Id Integer AUTO_INCREMENT PRIMARY KEY,
transaction_Id Integer,
deposit_IFSC_Code VARCHAR(20),
UNIQUE KEY (transaction_Id),
FOREIGN KEY (transaction_Id) REFERENCES Transaction(id) ON DELETE CASCADE
);

#WITHDRAWAL TRANSACTION
CREATE TABLE Withdraw_Transaction(
Id Integer AUTO_INCREMENT PRIMARY KEY,
transaction_Id Integer,
withdraw_IFSC_Code VARCHAR(20),
UNIQUE KEY (transaction_Id),
FOREIGN KEY (transaction_Id) REFERENCES Transaction(id) ON DELETE CASCADE
);

#TRANSFER TRANSACTION
CREATE TABLE Transfer_Transaction(
Id Integer AUTO_INCREMENT PRIMARY KEY,
transaction_Id Integer,
transaction_Type VARCHAR(20),
beneficiary_IFSC_Code VARCHAR(20),
beneficiary_Account_Number Double(20,0),
UNIQUE KEY (transaction_Id),
FOREIGN KEY (transaction_Id) REFERENCES Transaction(id) ON DELETE CASCADE
);

#CASH
CREATE TABLE Cash(
Id Integer AUTO_INCREMENT PRIMARY KEY,
transaction_Id Integer,
cash_Amount DOUBLE(12,4),
FOREIGN KEY (transaction_Id) REFERENCES Transaction(id) ON DELETE CASCADE
);

#CHEQUE
CREATE TABLE Cheque(
Id Integer AUTO_INCREMENT PRIMARY KEY,
transaction_Id Integer,
chequeNumber INTEGER(10),
beneficiaryName VARCHAR(100),
FOREIGN KEY (transaction_Id) REFERENCES Transaction(id) ON DELETE CASCADE
);

# CARD TRANSACTION MAP
CREATE TABLE Card_Transaction_Map(
card_Number Double,
transaction_Id Integer,
FOREIGN KEY (card_Number) REFERENCES Card(card_Number),
FOREIGN KEY (transaction_Id) REFERENCES Transaction(id));


commit;



# INSERT INTO Customer_Account_Map(CIFNumber,account_Number) VALUE(?,?);
# INSERT INTO Nominee(account_Number,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,CKYC_Verification_Document,CKYC_Verification_Id) value(?,?,?,?,?,?,?,?,?,?);
# INSERT INTO Employee_Branch_Map(employee_Id,branch_Id) VALUE(?,?);
# INSERT INTO Loan(account_Number,loan_Amount,rate_Of_Interest,loan_Type) VALUE(?,?,?,?);
# INSERT INTO Gold_Loan(loan_Id,gold_Purity,gold_Value_Per_Gram,gold_Weight_In_Gram) VALUE(?,?,?,?);
# INSERT INTO Home_Loan(loan_Id,build_Up_Area,total_Area,total_Value,total_No_Of_Floors) VALUE(?,?,?,?,?);
# INSERT INTO Card_Master(card_Name,inception_Date,from_Date,expiry_Date,is_Active) VALUE (?,?,?,?,'TRUE');
# UPDATE Card_Master SET is_Active='False' WHERE Id=?;
# SELECT Id,card_Name FROM Card_Master JOIN DEBIT_CARD_MASTER ON card_Id=Id WHERE is_Active='TRUE';
# INSERT INTO Co_Branded_Credit_Card_Master(card_Id,merchant_Name,merchant_Offer_Percentage) VALUE (?,?,?);
# SELECT card_Name,inception_Date,merchant_Name,merchant_Offer_Percentage FROM Card_MASTER JOIN CO_BRANDED_CREDIT_CARD_MASTER ON Id=card_Id WHERE Id=?
# SELECT card_Name,inception_Date,interest_Free_Credit_Days,rate_Of_Interest FROM Card_MASTER JOIN CO_BRANDED_CREDIT_CARD_MASTER ON Id=card_Id WHERE Id=?
# INSERT INTO Credit_Card_Master(card_Id,interest_Free_Credit_Days,rate_Of_Interest) VALUE (?,?,?);
# SELECT card_Name,inception_Date,withdrawal_Limit FROM Card_MASTER JOIN DEBIT_CARD_MASTER ON Id=card_Id WHERE Id=?
# INSERT INTO Debit_Card_Master(card_Id,withdrawal_Limit) VALUE(?,?);
# INSERT INTO Card(account_Number,card_Holder_Name,inception_Date,valid_from,expiry_Date,payment_Gateway,CVV,pin_Number) VALUE(?,?,?,?,?,?,?,?);
# INSERT INTO Credit_Card(card_Id,interest_Free_Credit_Days,rate_Of_Interest) value(?,?,?);
# INSERT INTO Transaction(account_Number,transaction_DateTime,transaction_Amount) VALUE(?,CURRENT_TIMESTAMP,?);
# INSERT INTO Deposit_Transaction(transaction_Id,deposit_IFSC_Code) VALUE(?,?);
# INSERT INTO Withdraw_Transaction(transaction_Id,withdraw_IFSC_Code) VALUE(?,?);
# INSERT INTO Transfer_Transaction(transaction_Id,transaction_Type,beneficiary_IFSC_Code,beneficiary_Account_Number) VALUE(?,?,?,?);
# INSERT INTO Card_Transaction_Map(card_Number,transaction_Id) VALUE(?,?);
# INSERT INTO Cheque(transaction_Id,chequeNumber,beneficiaryName) VALUE(?,?,?);
# INSERT INTO Saving_Account(account_Number,minimum_Account_Balance,withdrawal_Limit,rate_Of_Interest) VALUE(?,?,?,?);
# INSERT INTO User(first_Name,middle_Name,last_Name,address_Id,email_Id,gender,password,age,mobile_Number) Value(?,?,?,?,?,?,?,?,?);
# SELECT CIF_Number,USER.Id,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,Address_Id,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,CKYC_Verification_Document,CKYC_Verification_Id,PAN_Number FROM CUSTOMER JOIN USER JOIN ADDRESS ON USER.Id = CUSTOMER.user_Id and ADDRESS.id=USER.address_id;
# SELECT CIF_Number,USER.Id,first_Name,middle_Name,last_Name,email_Id,gender,age,mobile_Number,password,Address_Id,address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode,CKYC_Verification_Document,CKYC_Verification_Id,PAN_Number FROM CUSTOMER JOIN USER JOIN ADDRESS ON USER.Id = CUSTOMER.user_Id and ADDRESS.id=USER.address_id WHERE CUSTOMER.CIF_Number=6774;
# INSERT INTO Cash(transaction_Id,cash_Amount) VALUE(?,?);