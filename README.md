# RabobankCustomerStatementProcessor

  Rabobank receives monthly deliveries of customer statement records. This information is delivered in JSON Format.

# Run the Application

Checkout application from below github url,

https://github.com/ramadg05/RabobankCustomerStatementProcessor/tree/master

project folder -> mvn clean install

Run the application -> mvn spring-boot:run

Access below URL from POSTMAN / Rest Client

Set Content-Type:application/json

Application Default Running port : 8080

Application URL : http://localhost:8080/api/v1/rabobank/process

# Sample data for post method - no duplicate and no incorrect End balance:

[{"transactionReference":123455, "accountNumber":"NL90RABO0979679770", "startBalance" : 10, "mutation":10, "description":"First Record", "endBalance":20 }, {"transactionReference":123456, "accountNumber":"NL90RABO0979679769" "startBalance" : 110, "mutation":10, "description":"Second Record", "endBalance":120 }]


Note:Update the transaction reference number and mutation/End balance to verify other scenarios

# Logic Explained :

1)All transaction references should be unique
  
    Used Java 8 filter and map function to grouped based on transaction reference and filtered duplicated records 
  
2)The end balance needs to be validated

    End balance validated using the logic below,
  
    Start Balance +/- Mutation = End Balance
  
    If the result is 0.0 then it is correct end balance. Otherwise Im adding incorrect end balance to list to populate the end response
    
