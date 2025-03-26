Download the repo:  

HTTPS:
```bash
git clone https://github.com/kevinstana/thesis-api.git
```  
SSH:
```bash
git clone git@github.com:kevinstana/thesis-api.git
```

Then
```bash
cd thesis-api
docker-compose up
```
or
```bash
docker compose up
```
<br><br>
If you get access denied, build the image of this repo

```bash
docker build -t ghcr.io/kevinstana/thesis-api:latest -f Dockerfile .
```  
  
Then download the repo for the frontend:  
  
HTTPS:
```bash
git clone https://github.com/kevinstana/thesis-next.git
```  
SSH:
```bash
git clone git@github.com:kevinstana/thesis-next.git
```
  
And build its image:
```bash
cd thesis-next
docker build -t ghcr.io/kevinstana/thesis-next:latest -f Dockerfile .
```  
  
Now go back to `/thesis-api` and run:  
  
```bash
docker compose up
```
  
*VPN is required to test HUA account*

You can also test dummy accounts:  
  
 username |    password    
----|-----------
  admin | password 
  student1 | password 
  student2 | password 
  student3 | password 
  student4 | password 
  student5 | password 
  professor1 | password 
  professor2 | password 
  professor3 | password 
  professor4 | password 
  professor5 | password 
  secretary1 | password 
  secretary2 | password 
  secretary3 | password 
  secretary4 | password 
  secretary5 | password 
