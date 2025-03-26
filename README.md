

```bash
cd thesis-api
docker-compose up
```

If you get access denied, build the image of this repo

```bash
docker build -t ghcr.io/kevinstana/thesis-api:latest -f Dockerfile .
```

then download the repo [https://github.com/kevinstana/thesis-next](url) and:

```bash
cd thesis-next
docker build -t ghcr.io/kevinstana/thesis-next:latest -f Dockerfile .
```

now go back to /thesis-api and run:
```bash
docker compose up
```
