# How to start
- start docker desktop 
  - docker-compose up in terminal (project root) and
  - Run NetzfilmApplication

# How to connect to application
- localhost:9000

# How to stop
- Stop application
- stop terminal process (strg+C)
- docker-compose down

# How to configure pgadmin
- connect to localhost:5050 with admin@admin.com and root
- Create new Server with: 
  - host: host.docker.internal
  - database: netzfilm
  - user: postgres
  - password: postgres