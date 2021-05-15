######RabobankCustomerStatementProcessor

Checkout the application from below github url,

  https://github.com/ramadg05/RabobankCustomerStatementProcessor/

Application Running port : 8080

Appliction URL : http://localhost:8080/api/v1/rabobank/process

###Sample data for post method :

[{"transactionReference":123455,
"accountNumber":"NL91RABO0315278769",
"startBalance" : 10,
"mutation":10,
"description":"First Record",
"endBalance":20
},
{"transactionReference":123456,
"accountNumber":"NL91RABO0315279235"
"startBalance" : 110,
"mutation":0,
"description":"Second Record",
"endBalance":120
}]



