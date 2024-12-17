Enable VPN.

Run db and spring containers:

```bash
docker-compose up
```

Login:

```bash
curl -X POST http://localhost:8080/api/login -H "Content-Type: application/json" -d '{"username":"<hua username>","password":"<password>"}' | jq
```

Check home page message:
```bash
curl http://localhost:8080/api/ -H "Authorization: Bearer <accessToken>"
```
