# RabobankCustomerStatementProcessor

  Rabobank receives monthly deliveries of customer statement records. This information is delivered in JSON Format.

# Run the Application

Checkout application from below github url,

https://github.com/ramadg05/RabobankCustomerStatementProcessor/

Access below URL from postman / rest client

Set Content-Type:application/json

Application Running port : 8080

Appliction URL : http://localhost:8080/api/v1/rabobank/process

# Sample data for post method :

[{"transactionReference":123455, "accountNumber":"NL91RABO0315278769", "startBalance" : 10, "mutation":10, "description":"First Record", "endBalance":20 }, {"transactionReference":123456, "accountNumber":"NL91RABO0315279235" "startBalance" : 110, "mutation":0, "description":"Second Record", "endBalance":120 }]

# Logic Explained :

1)All transaction references should be unique
  
    Used Java 8 filter and map function to grouped based on transaction reference and filtered duplicated records 
  
2)The end balance needs to be validated

    End balance validated using the logic below,
  
    Start Balance +/- Mutation = End Balance
  
    If the result is 0.0 then it is correct end balance. Otherwise Im adding incorrect end balance to list to populate the end response
    
