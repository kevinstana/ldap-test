Enable VPN

```bash
docker-compose up
```

```bash
curl -X POST http://localhost:8080/api/login -H "Content-Type: application/json" -d '{"username":"<hua username>","password":"<password>"}' | jq
```

```bash
curl http://localhost:8080/api/ -H "Authorization: Bearer <accessToken>"
```
