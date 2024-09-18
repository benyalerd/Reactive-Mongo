#### API document

```
http://localhost:7081/webjars/swagger-ui/index.html
```

#### KafKa Setup

```
run docker compose up -d
```

#### Curl

```
#### register merchant
curl --location 'http://localhost:7081/merchant/insert' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstname":"Benya",
    "lastname":"Lerd",
    "email":"benya2540@hotmail.com",
    "tel":"0887035900",
    "createdBy":"system"
}'
```
```
#### create bill payment
curl --location 'http://localhost:7081/bill-payment/insert' \
--header 'Content-Type: application/json' \
--data '{
    "merchantNo":"85162197-8045-4d8a-b04c-2164966ff88e",
    "ref1":"1234",
    "ref2":"5555",
    "dueDate":"2021-09-17T11:48:06",
    "status":"AWAITING_PAYMENT",
    "amount":500,
    "createdBy":"system"
}'
```

```
#### update payment status
curl --location 'http://localhost:7081/bill-payment/update/payment-status' \
--header 'Content-Type: application/json' \
--data '{
    "paymentDetailList": [
        {
            "merchantNo": "85162197-8045-4d8a-b04c-2164966ff88e",
            "ref1": "0009",
            "ref2": "4444"
        }
    ]
}'
```


