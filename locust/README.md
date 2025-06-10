
## CLI commands to run locust

### Change locust web port to 8090

> locust --config=locust.conf

# Heavy load test - 200 users, spawn 10 per second

> locust -f locust.py -H http://localhost:8765 --headless -u 200 -r 10 -t 5m

