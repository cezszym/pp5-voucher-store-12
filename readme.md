## Voucher store

![Java CI with Maven](https://github.com/cezszym/pp5-voucher-store-12/workflows/Java%20CI%20with%20Maven/badge.svg)
### Testing


```bash
mvn test
```

### crud testing

```bash
curl localhost:9999/api/clients -X POST -H "content-type: application/json" 
'{
    "firstname": "test",
    "lastname": "sad",
    "address": {
     "street": "ulica",
     "zip": "ziper",
     "city": "karkow"
    }
 }'

localhost:9999/api/clients | python -m json.tool


```